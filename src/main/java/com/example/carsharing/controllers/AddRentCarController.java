package com.example.carsharing.controllers;

import com.example.carsharing.annotations.RequiresUser;
import com.example.carsharing.model.Car;
import com.example.carsharing.model.User;
import com.example.carsharing.dto.AddCarRequest;
import com.example.carsharing.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/carsharing")
@RequiredArgsConstructor
public class AddRentCarController { //контролер для зміни машинок
    private final CarService carService;
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
        if(!carService.findById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car with this id does not exist");
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
