package com.example.carsharing.service;


import com.example.carsharing.dto.request.AuthenticationRequest;
import com.example.carsharing.dto.request.RegisterRequest;
import com.example.carsharing.dto.response.AuthenticationResponse;
import com.example.carsharing.email.email_verification.email_sender.EmailConfirmationSender;
import com.example.carsharing.email.email_verification.email_service.EmailConfirmationTokenService;
import com.example.carsharing.email.email_verification.email_service.EmailSenderService;
import com.example.carsharing.entity.EmailConfirmationToken;
import com.example.carsharing.entity.User;
import com.example.carsharing.exception.BadRequestException;
import com.example.carsharing.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailConfirmationTokenService emailService;
    private final EmailConfirmationSender emailSender;
    private final EmailSenderService emailSenderService;
    private final UserService userService;
    @Value("${confirm.registration.url}")
    private String confirmRegistrationURL;
    public void register(RegisterRequest request)
    {
        String email = request.getEmail();
        String phone = request.getPhone();

        if(userService.existsUserByEmail(email)){
            throw new BadRequestException("The email is already used");
        }
        if(userService.existsByPhone(phone)){
            throw new BadRequestException("The phone is already used" );
        }


        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .enabled(false)
                .phone(request.getPhone())
                .build();
        userService.save(user);
        String token = UUID.randomUUID().toString();
        EmailConfirmationToken emailConfirmationToken = new EmailConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                false,
                user
        );

        emailService.saveConfirmationToken(emailConfirmationToken);

        String link = confirmRegistrationURL + token;

        emailSender.send(
                request.getEmail(),
                emailSenderService.buildEmail(request.getFirstname(), link));

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            if(!checkPassword(request.getPassword(),
                    userService.findUserByEmail(request.getEmail()).getPassword())){
                throw new UnauthorizedException("Username or password is wrong");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new UnauthorizedException("Username or password is wrong");
        }


        var user = userDetailService.loadUserByUsername(request.getEmail());


        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    public boolean checkPassword(String enteredPassword, String hashedPassword) {
        return passwordEncoder.matches(enteredPassword, hashedPassword);
    }
}