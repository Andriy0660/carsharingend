package com.example.carsharing.exception.email_exception;

public class EmailVerificationRuntimeException extends RuntimeException{
    public EmailVerificationRuntimeException(String message) {
        super(message);
    }
}
