package com.example.carsharing.controller;

import com.example.carsharing.dto.request.AddCarRequest;
import com.example.carsharing.mapper.CarMapper;
import com.example.carsharing.entity.Car;
import com.example.carsharing.entity.User;
import com.example.carsharing.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carsharing/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    @GetMapping("/available")
    public ResponseEntity<List<Car>> getAvailableCars(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Car> availableCars = carService.findByIsRentedFalse();

        return ResponseEntity.ok(availableCars.stream().
                filter(i->i.getOwner().getId()!=user.getId()).toList());
    }
    @GetMapping("/owned")
    public ResponseEntity<List<Car>> getOwnedCars() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Car> ownedCars = user.getOwnedCars();
        return ResponseEntity.ok(ownedCars);
    }

    @GetMapping("/rented")
    public ResponseEntity<List<Car>> getRentedCars() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var a = user.getRentedCars();
        List<Car> rentedCars = user.getRentedCars();
        return ResponseEntity.ok(rentedCars);
    }

    @PutMapping("/rent")
    public ResponseEntity<?> rentCar(@RequestParam("id") Integer id,
                                     @RequestParam("startTime")@DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") LocalDateTime startTime,
                                     @RequestParam("endTime")@DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss")LocalDateTime endTime){

        if(carService.findByIdAndIsRentedTrue(id).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This car has already rented");
        }

        User renter = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Car car;

        Optional<Car> optional = carService.findById(id);
        if(optional.isPresent()){
            car = optional.get();
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no car with id "+id);
        }

        User owner = car.getOwner();
        if(renter.getId() == owner.getId()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Yoy can not rent your car");
        }

        car.setRenter(renter);
        car.setStartTime(startTime);
        car.setEndTime(endTime);
        car.setIsRented(true);
        carService.save(car);
        //carService.rentCar(id,user.getId(),startTime,endTime);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCar(@RequestBody @Valid AddCarRequest carInfo){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Car car = CarMapper.mapToAddCar(carInfo);
        car.setOwner(user);

        carService.save(car);
        return ResponseEntity.ok().build();
    }
}
