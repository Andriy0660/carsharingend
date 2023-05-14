package com.example.carsharing.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCarRequest {
    String vendor;
    String model;
    Integer year;
    Double price;
    String color;
    String location;
    String description;
}
