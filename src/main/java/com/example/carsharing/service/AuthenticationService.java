package com.example.carsharing.service;


import com.example.carsharing.dto.request.AuthenticationRequest;
import com.example.carsharing.dto.request.RegisterRequest;
import com.example.carsharing.dto.response.AuthenticationResponse;
import com.example.carsharing.email.email_verification.email_sender.EmailConfirmationSender;
import com.example.carsharing.email.email_verification.email_service.EmailConfirmationTokenService;
import com.example.carsharing.email.email_verification.email_service.EmailSenderService;
import com.example.carsharing.entity.EmailConfirmationToken;
import com.example.carsharing.entity.User;
import com.example.carsharing.exception.user_exception.exception.EmailIsUsedAlreadyException;
import com.example.carsharing.exception.user_exception.exception.PhoneIsAlreadyUsedException;
import com.example.carsharing.exception.user_exception.exception.UserRegistrationException;
import com.example.carsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailConfirmationTokenService emailService;
    private final EmailConfirmationSender emailSender;
    private final EmailSenderService emailSenderService;
    private final UserService userService;
    public void register(RegisterRequest request)
    {
        String email = request.getEmail();
        String phone = request.getPhone();

        List<Exception> exceptions = new ArrayList<>();
        if(userRepository.findByEmail(email).isPresent()){
            exceptions.add(new EmailIsUsedAlreadyException(" The email is already used."));
        }
        if(userRepository.findByPhone(phone).isPresent()){
            exceptions.add(new PhoneIsAlreadyUsedException(" The phone is already used." ));
       }
        if(!exceptions.isEmpty()){
            throw new UserRegistrationException(exceptions);
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .enabled(false)
                .phone(request.getPhone())
                .build();
        userRepository.save(user);
        String token = UUID.randomUUID().toString();
        EmailConfirmationToken emailConfirmationToken = new EmailConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                false,
                user
        );

        emailService.saveConfirmationToken(emailConfirmationToken);

        String link = "https://spring-carsharing-demo.azurewebsites.net/auth" +
                "/confirmRegistration?token=" + token;

        emailSender.send(
                request.getEmail(),
                emailSenderService.buildEmail(request.getFirstname(), link));

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            if(!checkPassword(request.getPassword(),
                    userService.findUserByEmail(request.getEmail()).getPassword())){
                throw new BadCredentialsException("Username or password is wrong");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new BadCredentialsException("Username or password is wrong");
        }


        var user = userDetailService.loadUserByUsername(request.getEmail());


        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    public boolean checkPassword(String enteredPassword, String hashedPassword) {
        return passwordEncoder.matches(enteredPassword, hashedPassword);
    }
}
