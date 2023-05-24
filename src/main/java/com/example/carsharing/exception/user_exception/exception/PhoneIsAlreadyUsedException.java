package com.example.carsharing.exception.user_exception.exception;

public class PhoneIsAlreadyUsedException extends RuntimeException{
    public PhoneIsAlreadyUsedException(String message) {
        super(message);
    }
}
