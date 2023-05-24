package com.example.carsharing.exception.autherization_excepton;


import com.example.carsharing.exception.user_exception.UserIncorrectData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AuthenticationGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(IllegalArgumentException exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(DisabledException exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(BadCredentialsException exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.UNAUTHORIZED);
    }

}
