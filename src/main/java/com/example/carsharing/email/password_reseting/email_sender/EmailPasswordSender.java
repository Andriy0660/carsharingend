package com.example.carsharing.email.password_reseting.email_sender;
public interface EmailPasswordSender {
    void send(String to, String email);
}