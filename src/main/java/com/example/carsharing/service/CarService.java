package com.example.carsharing.service;

import com.example.carsharing.entity.Booking;
import com.example.carsharing.entity.Car;
import com.example.carsharing.exception.BadRequestException;
import com.example.carsharing.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService{
    private final CarRepository carRepository;

    public List<Car> findAll(){return carRepository.findAll();}
    public Car findById(Long id){return carRepository.findById(id).orElseThrow(
            ()->new BadRequestException("There is no car with id " + id));}
    public boolean isCarAvailable(Car car, LocalDateTime startDate, LocalDateTime endDate) {
        // Перевірити, чи немає конфліктів з іншими бронюваннями
        for (Booking booking : car.getBookings()) {
            if ((startDate.equals(booking.getStartTime()))
                    ||(endDate.equals(booking.getEndTime()))
                    ||(startDate.isAfter(booking.getStartTime())&&startDate.isBefore(booking.getEndTime()))
                    || (endDate.isAfter(booking.getStartTime()) && endDate.isBefore(booking.getEndTime()))) {
                return false;
            }
        }
        return true;
    }
    @Transactional
    public Car save(Car car){
        return carRepository.save(car);
    }
}
