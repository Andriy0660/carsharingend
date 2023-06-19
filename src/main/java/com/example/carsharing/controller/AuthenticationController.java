package com.example.carsharing.controller;


import com.example.carsharing.dto.request.AuthenticationRequest;
import com.example.carsharing.dto.request.RegisterRequest;
import com.example.carsharing.dto.response.AuthenticationResponse;
import com.example.carsharing.exception.BadRequestException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Contains authentication methods")

public class AuthenticationController {
    private final AuthenticationService authService;
    @Operation(
            summary = "Register user",
            description = "Register user in db, return nothing")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ExceptionDetails.class))}),
            @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = ExceptionDetails.class))})
            })
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