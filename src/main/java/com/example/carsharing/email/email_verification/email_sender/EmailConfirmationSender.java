package com.example.carsharing.email.email_verification.email_sender;
public interface EmailConfirmationSender {
    void send(String to, String email);
}