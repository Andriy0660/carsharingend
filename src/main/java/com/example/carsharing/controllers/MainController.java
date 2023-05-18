package com.example.carsharing.controllers;

import com.example.carsharing.annotations.RequiresUser;
import com.example.carsharing.entity.Car;
import com.example.carsharing.entity.User;
import com.example.carsharing.requests.AddCarRequest;
import com.example.carsharing.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/carsharing")
@RequiredArgsConstructor
public class MainController {
    private final CarService carService;

    @RequiresUser
    @GetMapping("/ownedcars")
    public ResponseEntity<List<Car>> getOwnedCars(@RequestHeader("Authorization") String jwt,
                                                  final User user){

        List<Car> ownedCars = user.getOwnedCars();
        return ResponseEntity.ok(ownedCars);
    }

    @RequiresUser
    @GetMapping("/rentedcars")
    public ResponseEntity<List<Car>> getRentedCars(@RequestHeader("Authorization") String jwt,
                                                   final User user){

        List<Car> rentedCars = user.getRentedCars();
        return ResponseEntity.ok(rentedCars);
    }

    @RequiresUser
    @GetMapping("/availablecars")
    public ResponseEntity<List<Car>> getAvailableCars(@RequestHeader("Authorization") String jwt,
                                                      final User user){

        List<Car> availableCars = carService.findByIsRentedFalse();

        return ResponseEntity.ok(availableCars.stream().
                filter(i->i.getOwner().getId()!=user.getId()).toList());
    }

    @RequiresUser
    @PutMapping("/cars")
    public ResponseEntity<?> rentCar(@RequestHeader("Authorization") String jwt,
                                     final User user,
                                     @RequestParam("id") Integer id,
                                     @RequestParam("startTime")@DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss")LocalDateTime startTime,
                                     @RequestParam("endTime")@DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss")LocalDateTime endTime){

        if(carService.findByIdAndIsRentedTrue(id).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This car has already rented");
        }
        carService.rentCar(id,user.getId(),startTime,endTime);
        return ResponseEntity.ok().build();
    }

    @RequiresUser
    @PostMapping("/cars")
    public ResponseEntity<?> addCar(@RequestHeader("Authorization") String jwt,
                                    final User user,
                                    @RequestBody @Valid AddCarRequest carInfo){

        Car car = new Car(0,user,null,false,null,null,
                carInfo.getVendor(),carInfo.getModel(),
                carInfo.getYear(),carInfo.getPrice(),carInfo.getColor(),
                carInfo.getLocation(),carInfo.getDescription());

        carService.save(car);
        return ResponseEntity.ok().build();
    }


}
