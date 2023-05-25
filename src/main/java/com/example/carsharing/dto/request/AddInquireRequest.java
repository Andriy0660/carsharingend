package com.example.carsharing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddInquireRequest {
    @NotBlank
    String carType;
    @NotBlank
    String description;
    @NotBlank
    boolean needsDelivery;
}
