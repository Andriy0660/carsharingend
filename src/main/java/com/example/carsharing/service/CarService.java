package com.example.carsharing.service;

import com.example.carsharing.model.Car;
import com.example.carsharing.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Optional<Car> findByIdAndIsRentedTrue(Integer id){
        return carRepository.findByIdAndIsRentedTrue(id);
    }
    public Optional<Car> findById(Integer id){return carRepository.findById(id);}
    public void rentCar(Integer id, Integer renterId, LocalDateTime startTime, LocalDateTime endTime){
        carRepository.rentCar(id, renterId,startTime,endTime);
    };
    public void save(Car car){
        carRepository.save(car);
    }
}
