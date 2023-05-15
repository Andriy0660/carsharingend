package com.example.carsharing.auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Email(message = "Invalid email address")

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @Pattern(regexp = "^\\+380\\d{7}$", message = "Invalid phone number")

    private String phone;
}
