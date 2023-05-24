package com.example.carsharing.exception.user_exception.exception;

public class TokenIsNotValidException extends RuntimeException{
    public TokenIsNotValidException(String message) {
        super(message);
    }
}
