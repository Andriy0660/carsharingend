package com.example.carsharing.controllers;

import com.example.carsharing.model.Car;
import com.example.carsharing.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carsharing")
public class RentedOwnedController { //
    @GetMapping("/ownedcars")
    public ResponseEntity<List<Car>> getOwnedCars() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Car> ownedCars = user.getOwnedCars();
        return ResponseEntity.ok(ownedCars);
    }

    @GetMapping("/rentedcars")
    public ResponseEntity<List<Car>> getRentedCars() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Car> rentedCars = user.getRentedCars();
        return ResponseEntity.ok(rentedCars);
    }
}
