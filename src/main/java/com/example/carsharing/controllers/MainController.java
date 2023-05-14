package com.example.carsharing.controllers;

import com.example.carsharing.config.JwtService;
import com.example.carsharing.entity.Car;
import com.example.carsharing.entity.User;
import com.example.carsharing.repository.UserRepository;
import com.example.carsharing.requests.AddCarRequest;
import com.example.carsharing.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/carsharing")
@RequiredArgsConstructor
public class MainController {
    private final JwtService jwtService;
    private final UserRepository userService;
    private final CarService carService;

    @GetMapping("/ownedcars")
    public ResponseEntity<List<Car>> getOwnedCars(@RequestHeader("Authorization") String jwt){
        String token = jwt.substring(7);
        String userEmail=jwtService.extractUsername(token);
        try {
            User user = userService.findByEmail(userEmail).orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));

            List<Car> ownedCars = user.getOwnedCars();

            return ResponseEntity.ok(ownedCars);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping("/rentedcars")
    public ResponseEntity<List<Car>> getRentedCars(@RequestHeader("Authorization") String jwt){
        String token = jwt.substring(7);
        String userEmail=jwtService.extractUsername(token);
        try {
            User user = userService.findByEmail(userEmail).orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));

            List<Car> rentedCars = user.getRentedCars();
            return ResponseEntity.ok(rentedCars);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/availablecars")
    public ResponseEntity<List<Car>> getAvailableCars(@RequestHeader("Authorization") String jwt){
        String token = jwt.substring(7);
        String userEmail=jwtService.extractUsername(token);

        try {
            User user = userService.findByEmail(userEmail).orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));

            List<Car> availableCars = carService.findByIsRentedFalse();

            return ResponseEntity.ok(availableCars.stream().
                    filter(i->i.getOwner().getId()!=user.getId()).toList());

        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/cars")
    public ResponseEntity<?> bookCar(@RequestParam("id") Integer id,
                                        @RequestParam("endTime")LocalDateTime endTime,
                                        @RequestHeader("Authorization") String jwt){

        String token = jwt.substring(7);
        String userEmail=jwtService.extractUsername(token);
        try {
            User user = userService.findByEmail(userEmail).orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));

            carService.bookCar(id,user.getId(),endTime);
            System.out.println(LocalDateTime.now());
            return ResponseEntity.ok().build();

        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/cars")
    public ResponseEntity<?> addCar(@RequestBody AddCarRequest carInfo,
                                    @RequestHeader("Authorization") String jwt){

        String token = jwt.substring(7);
        String userEmail=jwtService.extractUsername(token);
        try {
            User user = userService.findByEmail(userEmail).orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));

            Car car = new Car(0,user,null,false,null,null,
                    carInfo.getVendor(),carInfo.getModel(),
                    carInfo.getYear(),carInfo.getPrice(),carInfo.getColor(),
                    carInfo.getLocation(),carInfo.getDescription());

            carService.save(car);
            return ResponseEntity.ok().build();

        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
