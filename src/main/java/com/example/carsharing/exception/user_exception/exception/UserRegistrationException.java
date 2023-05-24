package com.example.carsharing.exception.user_exception.exception;

import java.util.List;

public class UserRegistrationException extends RuntimeException {
    private final List<Exception> exceptions;

    public UserRegistrationException(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    @Override
    public String getMessage() {
        String message = "[";
        for(Exception exception :exceptions)
        {
            message+=exception.getMessage() + ",";
        }
        message = message.substring(0,message.length()-1);
        message+="]";
        return message;
    }
}
