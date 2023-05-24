package com.example.carsharing.exception.user_exception;

import com.example.carsharing.exception.user_exception.exception.UserRegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class UserGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(UserRegistrationException exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(AccessDeniedException exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.UNAUTHORIZED);
    }


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException exception) {
//        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        ErrorResponse response = new ErrorResponse("Validation Failed", errors);
//        return ResponseEntity.badRequest().body(response);
//    }
}
