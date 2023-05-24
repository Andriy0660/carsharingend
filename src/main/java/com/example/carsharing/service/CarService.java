package com.example.carsharing.service;

import com.example.carsharing.entity.Booking;
import com.example.carsharing.entity.Car;
import com.example.carsharing.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService{
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
    public List<Car> findAll(){return carRepository.findAll();}
    public Optional<Car> findById(Integer id){return carRepository.findById(id);}
    public boolean isCarAvailable(Car car, LocalDateTime startDate, LocalDateTime endDate) {
        // Перевірити, чи немає конфліктів з іншими бронюваннями
        for (Booking booking : car.getBookings()) {
            if ((startDate.isAfter(booking.getStartTime())&&startDate.isBefore(booking.getEndTime()))||
                    (endDate.isAfter(booking.getStartTime()) && endDate.isBefore(booking.getEndTime()))) {
                return false;
            }
        }
        return true;
    }
    @Transactional
    public void save(Car car){
        carRepository.save(car);
    }
}
