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
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email must be not null")
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password must be not null")
    private String password;
    @Size(min = 2, message = "First must be at least 6 characters long and maximum 20 characters.")
    @NotBlank(message = "Firstname must be not null")
    private String firstname;
    @NotBlank(message = "Lastname must be not null")
    @Size(min = 6, message = "Lastname must be at least 2 characters long and maximum 20 characters.")
    private String lastname;
    @Pattern(regexp = "^\\+380\\d{9}$", message = "Invalid phone number")
    @NotBlank(message = "Phone must be notnull")
    private String phone;
}
