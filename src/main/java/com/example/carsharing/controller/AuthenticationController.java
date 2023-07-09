package com.example.carsharing.controller;


import com.example.carsharing.dto.request.AuthenticationRequest;
import com.example.carsharing.dto.request.RegisterRequest;
import com.example.carsharing.dto.response.AuthenticationResponse;
import com.example.carsharing.exception.ExceptionDetails;
import com.example.carsharing.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("carsharing/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Contains authentication methods")

public class AuthenticationController {
    private final AuthenticationService authService;
    @Value("${loginPage}")
    private String loginPage;

    @Operation(
            summary = "Register user",
            description = "Register user in db, return nothing")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ExceptionDetails.class))}),
            @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = ExceptionDetails.class))})
            })
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(
            @RequestBody @Valid RegisterRequest request
    ){
        authService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authService.signIn(request));
    }

    @GetMapping("/login")
    public RedirectView login() {
        return new RedirectView(loginPage);
    }

    @GetMapping ("/loginbygoogle")
    public ResponseEntity<?> loginByGoogle(@RequestParam("code") String code) {
        return authService.loginByGoogle(code);
    }

}