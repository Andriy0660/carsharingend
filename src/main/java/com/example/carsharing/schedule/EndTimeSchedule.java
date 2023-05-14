package com.example.carsharing.schedule;

import com.example.carsharing.entity.Car;
import com.example.carsharing.repository.CarRepository;
import com.example.carsharing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EndTimeSchedule {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    @Transactional
    @Scheduled(fixedRate = 60000) // перевіряти кожну хвилину
    public void checkRentedCars() {
        List<Car> rentedCars = carRepository.findByIsRentedTrue();

        for (Car car : rentedCars) {
            LocalDateTime endTime = car.getEndTime();
            LocalDateTime now = LocalDateTime.now();

            if (now.isAfter(endTime)) {
                car.setIsRented(false);
                car.setRenter(null);
                car.setStartTime(null);
                car.setEndTime(null);
                carRepository.save(car);
            }
        }
    }
}
