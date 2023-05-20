package com.example.carsharing.controllers;

import com.example.carsharing.model.Car;
import com.example.carsharing.model.User;
import com.example.carsharing.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carsharing")
@RequiredArgsConstructor
public class FirstPageController { //перша сторінка де юзер бачить всі машинки
    private final CarService carService;
    @GetMapping("/availablecars")
    public ResponseEntity<List<Car>> getAvailableCars(@RequestHeader("Authorization") String jwt,
                                                      final User user){

        List<Car> availableCars = carService.findByIsRentedFalse();

        return ResponseEntity.ok(availableCars.stream().
                filter(i->i.getOwner().getId()!=user.getId()).toList());
    }
}
