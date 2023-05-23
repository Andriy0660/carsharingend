package com.example.carsharing.schedule;

import com.example.carsharing.entity.Booking;
import com.example.carsharing.entity.Car;
import com.example.carsharing.repository.BookingRepository;
import com.example.carsharing.service.CarService;
import com.example.carsharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StartTimeSchedule {
    @Autowired
    private CarService carService;
    @Autowired

    private UserService userService;
    @Autowired

    private BookingRepository bookingRepository;

    @Scheduled(fixedRate = 1000) // перевіряти кожну хвилину
    @Transactional
    public void checkRentedCars() {
   List<Car> car1 = carService.findAll();
//        for (Booking booking : bookings) {
//            if (booking.getEndTime().isBefore(LocalDateTime.now())) {
//                car.getBookings().remove(booking); // Видалити бронювання зі списку
//
//                bookingRepository.delete(booking);
//            }
//
//        }
        for (Car car : car1) {
            List<Booking> bookings = new ArrayList<>(car.getBookings()); // Створити копію списку бронювань

            for (Booking booking : bookings) {
                if (booking.getEndTime().isBefore(LocalDateTime.now())) {
                    car.getBookings().remove(booking); // Видалити бронювання зі списку
                    bookingRepository.delete(booking); // Видалити бронювання з бази даних
                }
            }}
        List<Car> notRentedCars = carService.findByIsRentedFalse();
        for (Car car : notRentedCars) {
            for (Booking booking : car.getBookings()) {
                if (booking.getStartTime().isBefore(LocalDateTime.now())&&booking.getEndTime().isAfter(LocalDateTime.now())) {
                    car.setIsRented(true);
                    car.setRenter(userService.findById(booking.getRenterId()).orElseThrow());
                    carService.save(car); // Збереження змін у базі даних

                }
            }
        }

        List<Car> rentedCars = carService.findByIsRentedTrue();
        OUTER:
        for (Car car : rentedCars) {
            for (Booking booking : car.getBookings()) {
                if (booking.getStartTime().isBefore(LocalDateTime.now())&&booking.getEndTime().isAfter(LocalDateTime.now())) {
                    continue OUTER;
                }
            }
            car.setIsRented(false);
            car.setRenter(null);
            carService.save(car); // Збереження змін у базі даних

        }


    }
}
