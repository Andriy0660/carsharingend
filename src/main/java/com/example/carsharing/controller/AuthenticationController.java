package com.example.carsharing.controller;


import com.example.carsharing.dto.request.AuthenticationRequest;
import com.example.carsharing.dto.request.RegisterRequest;
import com.example.carsharing.dto.request.ResetPasswordRequest;
import com.example.carsharing.dto.response.AuthenticationResponse;
import com.example.carsharing.dto.response.ResetPasswordResponse;
import com.example.carsharing.email.email_verification.email_service.EmailSenderService;
import com.example.carsharing.email.password_reseting.email_service.EmailPasswordSenderService;
import com.example.carsharing.email.password_reseting.email_service.PasswordResetService;
import com.example.carsharing.entity.User;
import com.example.carsharing.service.AuthenticationService;
import com.example.carsharing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthenticationService authService;
    private final EmailSenderService emailService;
    private final PasswordResetService passwordResetService;
    private final UserService userService;
    private final EmailPasswordSenderService emailPasswordSenderService;
    @PostMapping("/resetPasswordRequest")
    public ResponseEntity<Void> resetPassword(
            @RequestBody ResetPasswordRequest request
    ){
        User user = userService.findUserByEmail(request.getEmail());
        passwordResetService.resetPassword(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/confirmRegistration")
    public ModelAndView confirm(@RequestParam String token){
        emailService.confirmToken(token);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("EmailResponsePage");
        modelAndView.addObject("message", "Confirmation successful. Thank you for registration!");
        return modelAndView;
    }
    @GetMapping("/resetPassword")
    public ModelAndView resetPassword(@RequestParam String token){
        emailPasswordSenderService.confirmToken(token);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ResetPasswordPage");
        modelAndView.addObject("token",token);
        return modelAndView;
    }
    @PostMapping("/resetPassword")
    public ModelAndView resetPassword(@ModelAttribute("resetPasswordResponse") ResetPasswordResponse response,
                                      @RequestParam("token") String token){
        emailPasswordSenderService.updatePassword(token,response.getPassword());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("EmailResponsePage");
        modelAndView.addObject("message", "Password resetting is successful!");
        return modelAndView;
    }
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid RegisterRequest request
    ){
        authService.register(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }
}