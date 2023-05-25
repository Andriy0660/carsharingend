package com.example.carsharing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChangePhoneRequest {
    @Pattern(regexp = "^\\+380\\d{9}$", message = "Invalid phone number")
    @NotBlank(message = "Phone must be not null")
    private String phone;
}
