package com.example.carsharing.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@AllArgsConstructor
@RestControllerAdvice
public class ExceptionHandler {
    private final ExceptionBuilder exceptionBuilder;
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        return ExceptionBuilder.exceptionBuilder(ex,HttpStatus.BAD_REQUEST);
        //return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        //return ExceptionBuilder.exceptionBuilder(ex,HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingParameter(MissingServletRequestParameterException ex) {
        String parameterName = ex.getParameterName();
        String errorMessage = "Required parameter is missing: " + parameterName;
        //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}


