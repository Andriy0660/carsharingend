package com.example.carsharing.exception.user_exception.exception;

public class EmailIsUsedAlreadyException extends RuntimeException{
    public EmailIsUsedAlreadyException(String message) {
        super(message);
    }
}
