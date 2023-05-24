package com.example.carsharing.exception;

import com.example.carsharing.exception.user_exception.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse response = new ErrorResponse("Validation Failed", errors);
        return ResponseEntity.badRequest().body(response);
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


