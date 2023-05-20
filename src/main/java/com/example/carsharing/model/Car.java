package com.example.carsharing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Integer id;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    User owner;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "renter_id")
    User renter;

    @Column(name = "rented")
    Boolean isRented;
    @Column(name = "start_time")
    LocalDateTime startTime;
    @Column(name = "end_time")
    LocalDateTime endTime;
    @Column(name = "vendor")
    String vendor;
    @Column(name = "model")
    String model;
    @Column(name = "year")
    Integer year;
    @Column(name = "price")
    Double price;
    @Column(name = "color")
    String color;
    @Column(name = "location")
    String location;
    @Column(name = "description")
    String description;

}
