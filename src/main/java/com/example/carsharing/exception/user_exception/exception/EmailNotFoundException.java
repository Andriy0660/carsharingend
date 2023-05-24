package com.example.carsharing.exception.user_exception.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(String message) {
        super(message);
    }
}
