package com.example.carsharing.schedule;


import com.example.carsharing.email.email_verification.email_service.EmailConfirmationTokenService;
import com.example.carsharing.email.password_reseting.email_service.EmailPasswordTokenService;
import com.example.carsharing.entity.EmailConfirmationToken;
import com.example.carsharing.entity.ResettingPasswordToken;
import com.example.carsharing.entity.User;
import com.example.carsharing.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailChecking {
    private UserService userService;
    private EmailConfirmationTokenService emailService;
    private EmailPasswordTokenService emailPasswordService;

    @Scheduled(fixedRate = 6000)
    public void deleteUnconfirmedUser() {
        List<EmailConfirmationToken> emailConfirmationTokens = emailService.findAllEmailVerifications();
        LocalDateTime now = LocalDateTime.now();
        for (EmailConfirmationToken token : emailConfirmationTokens) {
            if (!token.isConfirmed() && token.getExpiredAt().isBefore(now) ) {
                User user = token.getUser();
                emailService.deleteConfirmationToken(token);
                userService.delete(user);

            }
        }
    }
    @Scheduled(fixedRate = 500000)
    public void deleteConfirmedVerificationToken() {
        List<EmailConfirmationToken> emailConfirmationTokens = emailService.findAllEmailVerifications();
        for (EmailConfirmationToken token : emailConfirmationTokens) {
            if (token.isConfirmed()) {
                emailService.deleteConfirmationToken(token);
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void deleteUnconfirmedResetPasswordRequest() {
        List<ResettingPasswordToken> resettingPasswordTokens = emailPasswordService.findAllEmailVerifications();
        LocalDateTime now = LocalDateTime.now();
        for (ResettingPasswordToken token : resettingPasswordTokens) {
            if (!token.isConfirmed() && token.getExpiredAt().isBefore(now) ) {
                emailPasswordService.deleteConfirmationToken(token);
            }
        }
    }

    @Scheduled(fixedRate = 500000)
    public void deleteConfirmedResetPasswordToken() {
        List<ResettingPasswordToken> resettingPasswordTokens = emailPasswordService.findAllEmailVerifications();
        for (ResettingPasswordToken token : resettingPasswordTokens) {
            if (token.isConfirmed()) {
                emailPasswordService.deleteConfirmationToken(token);
            }
        }
    }
}