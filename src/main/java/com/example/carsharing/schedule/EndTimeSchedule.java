//package com.example.carsharing.schedule;
//
//import com.example.carsharing.entity.Booking;
//import com.example.carsharing.entity.Car;
//import com.example.carsharing.repository.BookingRepository;
//import com.example.carsharing.service.CarService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class EndTimeSchedule {
//    @Autowired
//    private CarService carService;
//    @Autowired
//    private BookingRepository bookingRepository;
//    @Transactional
//    @Scheduled(fixedRate = 1000) // перевіряти кожну хвилину
//    public void checkRentedCars() {
//        List<Car> rentedCars = carService.findByIsRentedTrue();
//
//        OUTER:
//        for (Car car : rentedCars) {
//            for (Booking booking : car.getBookings()) {
//                if (booking.getStartTime().isBefore(LocalDateTime.now())&&booking.getEndTime().isAfter(LocalDateTime.now())) {
//                    continue OUTER;
//                }
//            }
//            car.setIsRented(false);
//            car.setRenter(null);
//        }
//    }
//}
