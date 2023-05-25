package com.example.carsharing.email.email_verification.email_service;



import com.example.carsharing.entity.EmailConfirmationToken;
import com.example.carsharing.entity.User;
import com.example.carsharing.exception.email_exception.EmailVerificationRuntimeException;
import com.example.carsharing.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EmailSenderService {

    private final UserService userService;
    private final EmailConfirmationTokenService confirmationTokenService;

    @Transactional
    public void confirmToken(String token) {
        EmailConfirmationToken confirmationToken = confirmationTokenService
                .getEmailConfirmationToken(token)
                .orElseThrow(() ->
                        new EmailVerificationRuntimeException("Link is not valid. You need to register newly."));

        if (confirmationToken.isConfirmed()) {
            throw new EmailVerificationRuntimeException("Email already confirmed.");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new EmailVerificationRuntimeException("Link is expired. You need to register newly.");
        }

        confirmationToken.setConfirmed(true);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        User user = confirmationToken.getUser();
        user.setEnabled(true);
        userService.save(user);
    }

    public String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c;background-color:#f6f8fa;padding:20px\">\n" +
                "\n" +
                "  <div style=\"font-size:28px;font-weight:bold;color:#ffffff;text-align:center;margin-bottom:10px;background-color:#000000;height:50px;line-height:50px\">\n" +
                "    <span style=\"color:#ffffff;\">Confirm your account</span>\n" +
                "  </div>\n" +
                "\n" +
                "  <div style=\"font-size:19px;line-height:25px;color:#0b0c0c;margin-bottom:20px\">\n" +
                "    Hi, " + name + ".<br><br>\n" +
                "    Thank you for registering. Please click on the below link to activate your account:<br><br>\n" +
                "    <a href=\"" + link + "\" style=\"background-color:#1D70B8;color:#ffffff;font-size:19px;padding:10px 20px;text-decoration:none;border-radius:4px\">Activate Now</a><br><br>\n" +
                "    Link will expire in 15 minutes.<br>\n" +
                "    See you soon!\n" +
                "  </div>\n" +
                "\n" +
                "</div>";
    }
}