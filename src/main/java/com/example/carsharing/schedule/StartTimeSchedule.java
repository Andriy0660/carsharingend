package com.example.carsharing.schedule;

import com.example.carsharing.entity.Booking;
import com.example.carsharing.entity.Car;
import com.example.carsharing.service.BookingService;
import com.example.carsharing.service.CarService;
import com.example.carsharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StartTimeSchedule {
    @Autowired
    private CarService carService;
    @Autowired

    private UserService userService;
    @Autowired

    private BookingService bookingService;

    @Transactional
    @Scheduled(fixedRate = 10000) // перевіряти кожні 10 сек
    public void checkRentedCars() {

        //перевірка чи незаброньовані машини треба змінювати
        List<Car> allCars = carService.findAll();

        OUTER:
        for (Car car : allCars) {
            for (Booking booking : car.getBookings()) {
                LocalDateTime now = LocalDateTime.now();

            if (!car.getIsRented()) {    //не арендовані
                    if (booking.getEndTime().isBefore(now)) {
                        bookingService.deleteById(booking.getId()); // Видалити бронювання з бази даних
                        continue;
                    }
                    if (booking.getStartTime().isBefore(now)){ //зараз після start_date
                        car.setIsRented(true);
                        car.setRenter(userService.findById(booking.getRenterId()).orElseThrow());
                        carService.save(car);
                    }

            } else {   //арендовані
                    if (booking.getEndTime().isBefore(now)) {
                        bookingService.deleteById(booking.getId()); // Видалити бронювання з бази даних
                        continue;
                    }
                    if (booking.getStartTime().isBefore(now)){ //зараз після start_date
                        continue OUTER;
                    }
                }
                car.setIsRented(false);
                car.setRenter(null);
                carService.save(car);
            }
        }


    }
}
