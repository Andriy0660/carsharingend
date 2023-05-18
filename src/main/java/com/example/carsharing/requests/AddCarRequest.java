package com.example.carsharing.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCarRequest {
    @NotBlank

    String vendor;
    @NotBlank

    String model;
    @NotNull
    @Range(min = 1990,max=2023)
    Integer year;
    @NotNull
    @Min(0)
    Double price;
    @NotBlank

    String color;
    @NotBlank

    String location;
    @NotBlank
    String description;
}
