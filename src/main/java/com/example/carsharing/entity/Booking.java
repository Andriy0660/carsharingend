package com.example.carsharing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bookings")
public class Booking {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @JsonIgnore
    @Column(name="car_id")
    private Integer carId;

    @JsonIgnore
    @Column(name="renter_id")
    private Integer renterId;

    @NotNull
    @Column(name="start_time")
    private LocalDateTime startTime;

    @NotNull
    @Column(name="end_time")
    private LocalDateTime endTime;

    public Booking(Integer carId, Integer renterId, LocalDateTime startTime, LocalDateTime endTime) {
        this.carId = carId;
        this.renterId = renterId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
