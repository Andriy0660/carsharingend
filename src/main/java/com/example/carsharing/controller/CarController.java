package com.example.carsharing.controller;

import com.example.carsharing.dto.request.AddCarRequest;
import com.example.carsharing.entity.Booking;
import com.example.carsharing.mapper.CarMapper;
import com.example.carsharing.entity.Car;
import com.example.carsharing.entity.User;
import com.example.carsharing.repository.BookingRepository;
import com.example.carsharing.repository.CarRepository;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carsharing/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final BookingRepository bookingRepository;

    @GetMapping("/available")
    public ResponseEntity<List<Car>> getAvailableCars() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Car> availableCars = carService.findAll();

        return ResponseEntity.ok(availableCars.stream().
                filter(i -> i.getOwner().getId() != user.getId()).toList());
    }

    @GetMapping("/availableByTime")
    public ResponseEntity<List<Car>> getAvailableCars(@RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") LocalDateTime startTime,
                                                      @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") LocalDateTime endTime) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Car> availableCars = carService.findByIsRentedFalse().stream().filter(i->isCarAvailable(i,startTime,endTime)).collect(Collectors.toList());

        return ResponseEntity.ok(availableCars.stream().
                filter(i -> i.getOwner().getId() != user.getId()).toList());
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
        List<Car> rentedCars = user.getRentedCars();
        return ResponseEntity.ok(rentedCars);
    }

    @PutMapping("/rent")
    public ResponseEntity<?> rentCar(@RequestParam("id") Integer id,
                                     @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") LocalDateTime startTime,
                                     @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") LocalDateTime endTime) {

        User renter = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Car> optional = carService.findById(id);
        Car car;

        if (!optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no car with id " + id);
        }

        car = optional.get();



        User owner = car.getOwner();
        if (renter.getId() == owner.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Yoy can not rent your car");
        }
        if (!isCarAvailable(car, startTime, endTime)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Цей автомобіль не доступний на обрані дати.");
        }
        Booking booking = new Booking(0,car.getId(),renter.getId(),startTime,endTime);
        bookingRepository.save(booking);
        //car.getBookings().add(booking);

//        car.setRenter(renter);
//        car.setStartTime(startTime);
//        car.setEndTime(endTime);
//        car.setIsRented(true);
        carService.save(car);
        //carService.rentCar(id,user.getId(),startTime,endTime);
        return ResponseEntity.ok().build();
    }

    private boolean isCarAvailable(Car car, LocalDateTime startDate, LocalDateTime endDate) {
        // Перевірити, чи немає конфліктів з іншими бронюваннями
        for (Booking booking : car.getBookings()) {
            if (!(endDate.isBefore(booking.getStartTime()) || startDate.isAfter(booking.getEndTime()))) {
                return false;
            }
        }

        return true;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCar(@RequestBody @Valid AddCarRequest carInfo) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Car car = CarMapper.mapToAddCar(carInfo);
        car.setOwner(user);

        carService.save(car);

        return ResponseEntity.ok().build();
    }
}
