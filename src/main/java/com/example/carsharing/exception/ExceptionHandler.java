package com.example.carsharing.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        return ExceptionBuilder.exceptionBuilder(ex,HttpStatus.BAD_REQUEST);
        //return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(BadCredentialsException ex) {
        return ExceptionBuilder.exceptionBuilder(ex,HttpStatus.BAD_REQUEST);
        //return ExceptionBuilder.exceptionBuilder(ex,HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParameter(MissingServletRequestParameterException ex) {
        String parameterName = ex.getParameterName();
        String errorMessage = "Required parameter is missing: " + parameterName;
        return ExceptionBuilder.exceptionBuilder(ex,HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleMissingParameter(NoSuchElementException ex) {
        return ExceptionBuilder.exceptionBuilder(ex,HttpStatus.BAD_REQUEST);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleMissingParameter(IOException ex) {
        return ExceptionBuilder.exceptionBuilder(ex,HttpStatus.BAD_REQUEST);
    }


}


