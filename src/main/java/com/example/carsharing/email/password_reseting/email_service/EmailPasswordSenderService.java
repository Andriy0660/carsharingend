package com.example.carsharing.email.password_reseting.email_service;



import com.example.carsharing.entity.ResettingPasswordToken;
import com.example.carsharing.entity.User;
import com.example.carsharing.exception.BadRequestException;
import com.example.carsharing.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EmailPasswordSenderService {
    private final UserService userService;
    private final EmailPasswordTokenService confirmationTokenService;
    private final PasswordEncoder passwordEncoder;


    public void confirmToken(String token) {
        ResettingPasswordToken confirmationToken = confirmationTokenService
                .getEmailConfirmationToken(token)
                .orElseThrow(() ->
                        new BadRequestException("Link is not valid. You need to generate new request."));

        if (confirmationToken.isConfirmed()) {
            throw new BadRequestException("Email already confirmed.");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Link is expired. You need to generate new request.");
        }

    }
    @Transactional
    public void updatePassword(String token,String password) {
        ResettingPasswordToken confirmationToken = confirmationTokenService
                .getEmailConfirmationToken(token)
                .orElseThrow(() ->
                        new BadRequestException("Link is not valid. You need to generate new request."));

        if (confirmationToken.isConfirmed()) {
            throw new BadRequestException("Email already confirmed.");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Link is expired. You need to generate new request.");
        }
        confirmationToken.setConfirmed(true);
        confirmationTokenService.saveConfirmationToken(confirmationToken);


        User user = confirmationToken.getUser();
        String updatedPassword = passwordEncoder.encode(password);
        user.setPassword(updatedPassword);
        userService.save(user);
    }
    public String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c;background-color:#f6f8fa;padding:20px\">\n" +
                "\n" +
                "  <div style=\"font-size:28px;font-weight:bold;color:#ffffff;text-align:center;margin-bottom:10px;background-color:#000000;height:50px;line-height:50px\">\n" +
                "    <span style=\"color:#ffffff;\">Reset your password</span>\n" +
                "  </div>\n" +
                "\n" +
                "  <div style=\"font-size:19px;line-height:25px;color:#0b0c0c;margin-bottom:20px\">\n" +
                "    Hi, " + name + ".<br><br>\n" +
                "    Please click on the below link to reset your password:<br><br>\n" +
                "    <a href=\"" + link + "\" style=\"background-color:#1D70B8;color:#ffffff;font-size:19px;padding:10px 20px;text-decoration:none;border-radius:4px\">Reset Now</a><br><br>\n" +
                "    Link will expire in 15 minutes.<br>\n" +
                "    See you soon!\n" +
                "  </div>\n" +
                "\n" +
                "</div>";
    }
}