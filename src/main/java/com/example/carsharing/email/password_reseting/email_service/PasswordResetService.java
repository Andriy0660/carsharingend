package com.example.carsharing.email.password_reseting.email_service;



import com.example.carsharing.email.password_reseting.email_sender.EmailPasswordService;
import com.example.carsharing.entity.ResettingPasswordToken;
import com.example.carsharing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final EmailPasswordTokenService emailService;
    private final EmailPasswordService emailSender;
    private final EmailPasswordSenderService emailSenderService;

    public void resetPassword(User user){
        String token = UUID.randomUUID().toString();
        ResettingPasswordToken resettingPasswordToken = new ResettingPasswordToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                false,
                user
        );

        emailService.saveConfirmationToken(
                resettingPasswordToken);

        String link = "http://localhost:8080/auth" +
                "/resetPassword?token=" + token;
        emailSender.send(
                user.getEmail(),
                emailSenderService.buildEmail(user.getFirstname(), link));

    }
}
