package com.example.carsharing.auth;

import com.example.carsharing.repository.UserRepository;
import com.example.carsharing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthenticationService authService;
    private final UserService userService;
    @PostMapping("/register")
    public void register(
             @RequestBody @Valid RegisterRequest request
    ){
        if(userService.findByEmail(request.getEmail()).isPresent()){
            throw new BadCredentialsException("Email already exists");
        }
        authService.register( request);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ){
        try{
            return ResponseEntity.ok(authService.authenticate(request));
        }catch(BadCredentialsException e){
            return ResponseEntity.status(UNAUTHORIZED).build();
        }
    }

}
