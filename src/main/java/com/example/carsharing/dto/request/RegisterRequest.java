package com.example.carsharing.dto.request;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Email
    private String email;
    @Size(min = 6)
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Pattern(regexp = "^\\+380\\d{9}$")
    private String phone;
}
