package com.example.carsharing.dto.response;

import com.example.carsharing.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InquireResponse {
    private Long id;
    private String volunteerFirstname;
    private String VolunteerLastname;
    private String phone;
    private String carType;
    private String description;
}
