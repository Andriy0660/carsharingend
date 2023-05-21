package com.example.carsharing.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Email is null")
    private String email;
    @NotBlank(message = "Password is null")
    private String password;
    @NotBlank(message = "First name is null")
    private String firstName;
    @NotBlank(message = "Last name is null")
    private String lastName;
    @Pattern(regexp = "^\\+380\\d{9}$", message = "Invalid phone number")
    @NotBlank(message = "phone is null")
    private String phone;
}
