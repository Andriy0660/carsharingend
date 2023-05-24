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
    @NotBlank(message = "Email must be not null")
    private String email;
    @NotBlank(message = "Password must be not null")
    private String password;
    @NotBlank(message = "Firstname must be not null")
    private String firstname;
    @NotBlank(message = "Lastname must be not null")
    private String lastname;
    @Pattern(regexp = "^\\+380\\d{9}$", message = "Invalid phone number")
    @NotBlank(message = "Phone must be notnull")
    private String phone;
}
