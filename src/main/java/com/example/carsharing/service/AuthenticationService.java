package com.example.carsharing.service;


import com.example.carsharing.dto.request.AuthenticationRequest;
import com.example.carsharing.dto.request.RegisterRequest;
import com.example.carsharing.dto.response.AuthenticationResponse;
import com.example.carsharing.entity.User;
import com.example.carsharing.entity.UserDetailsImpl;
import com.example.carsharing.exception.BadRequestException;
import com.example.carsharing.exception.ServerErrorException;
import com.example.carsharing.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    public void signUp(RegisterRequest request)
    {
        String email = request.getEmail();
        String phone = request.getPhone();

        if(userService.existsUserByEmail(email)){
            throw new BadRequestException("The email is already used");
        }
        if(userService.existsByPhone(phone)){
            throw new BadRequestException("The phone is already used" );
        }

        User user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
        userService.save(user);
    }

    public ResponseEntity<AuthenticationResponse> loginByGoogle(String code)
    {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        JSONObject requestBodyForAccessToken = new JSONObject();
        requestBodyForAccessToken.put("code", code);
        requestBodyForAccessToken.put("client_id", clientId);
        requestBodyForAccessToken.put("client_secret", clientSecret);
        requestBodyForAccessToken.put("redirect_uri", redirectUri);
        requestBodyForAccessToken.put("grant_type", "authorization_code");

        RequestBody bodyForAccessToken = RequestBody.create(mediaType, requestBodyForAccessToken.toString());
        Request requestForAccessToken = new Request.Builder()
                .url("https://accounts.google.com/o/oauth2/token")
                .header("Content-Type", "application/json")
                .post(bodyForAccessToken)
                .build();
        String access_token = null;
        try {
            Response response = client.newCall(requestForAccessToken).execute();
            if (response.isSuccessful()) {
                String responseBody =response.body().string();
                response.close();
                JSONObject jsonObject = new JSONObject(responseBody);
                access_token = jsonObject.get("access_token").toString();
            } else {
                System.out.println("ERROR WHILE GET ACCESS TOKEN: " + response.code() + " " + response.message());
                throw new ServerErrorException("An error occurred, please try another way to register");
            }
        } catch (IOException e) {
            System.out.println("ERROR WHILE GET STRING OF RESPONSE BODY");
            throw new ServerErrorException("An error occurred, please try another way to register");
        }

        Request requestForUserInfo = new Request.Builder()
                .url("https://www.googleapis.com/oauth2/v3/userinfo")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + access_token)
                .get()
                .build();
        try {
            Response response = client.newCall(requestForUserInfo).execute();
            if (response.isSuccessful()) {
                String responseBody =response.body().string();
                response.close();
                JSONObject jsonObject = new JSONObject(responseBody);
                String firstName = jsonObject.getString("given_name");
                String lastName = jsonObject.getString("family_name");
                String email = jsonObject.getString("email");

                if(userService.existsUserByEmail(email)){                 //authenticate user

                    UserDetailsImpl userDetails = (UserDetailsImpl) userDetailService.loadUserByUsername(email);
                    User user = userDetails.getUser();

                    if(!user.isSignUpByGoogle()) throw new BadRequestException("The email is already used");

                    if(user.getFirstname().equals(firstName)&&user.getLastname().equals(lastName)){
                        return ResponseEntity.ok(new AuthenticationResponse(jwtService.generateToken(userDetails)));
                    }
                    else{
                        throw new ServerErrorException("An error occurred, contact to API developers AZN_Corp");
                    }
                }
                else {                                  //register user
                    User user = User.builder()
                            .firstname(firstName)
                            .lastname(lastName)
                            .password(passwordEncoder.encode(RandomStringUtils.random(15)))
                            .email(email)
                            .isSignUpByGoogle(true)
                            .build();
                    userService.save(user);
                    UserDetailsImpl userDetails = (UserDetailsImpl) userDetailService.loadUserByUsername(email);
                    return ResponseEntity.ok(new AuthenticationResponse(jwtService.generateToken(userDetails)));
                }
            } else {
                System.out.println("ERROR WHILE GET USER INFO: " + response.code() + " " + response.message());
                throw new ServerErrorException("An error occurred, please try another way to register");
            }
        } catch (IOException e) {
            System.out.println("ERROR WHILE GET STRING OF RESPONSE BODY");
            throw new ServerErrorException("An error occurred, please try another way to register");
        }
    }

    public AuthenticationResponse signIn(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new UnauthorizedException("Username or password is wrong");
        }

        UserDetailsImpl user = (UserDetailsImpl) userDetailService.loadUserByUsername(request.getEmail());

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}