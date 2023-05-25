package com.example.carsharing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChangePasswordRequest {
    @NotBlank
    private String currentPassword;
    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String newPassword;
}
