package com.example.carsharing.schedule;

import com.example.carsharing.entity.Car;
import com.example.carsharing.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EndTimeSchedule {
    @Autowired
    private CarService carService;
    @Transactional
    @Scheduled(fixedRate = 60000) // перевіряти кожну хвилину
    public void checkRentedCars() {
        List<Car> rentedCars = carService.findByIsRentedTrue();

        for (Car car : rentedCars) {
            LocalDateTime endTime = car.getEndTime();
            LocalDateTime now = LocalDateTime.now();

            if (now.isAfter(endTime)) {
                car.setIsRented(false);
                car.setRenter(null);
                car.setStartTime(null);
                car.setEndTime(null);
                carService.save(car);
            }
        }
    }
}
