package com.example.carsharing.service;

import com.example.carsharing.entity.Car;
import com.example.carsharing.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    public List<Car> findByIsRentedTrue(){
        return carRepository.findByIsRentedTrue();
    }

    public List<Car> findByIsRentedFalse(){
        return carRepository.findByIsRentedFalse();
    }

    public void bookCar(Integer id, Integer renterId, LocalDateTime startTime,LocalDateTime endTime){
        carRepository.bookCar(id, renterId,startTime,endTime);
    };
    public void save(Car car){
        carRepository.save(car);
    }
}
