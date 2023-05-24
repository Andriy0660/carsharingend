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
        Car car;
        Booking booking;

        OUTER:
        for (int i =0;i<allCars.size();i++) {
            car=allCars.get(i);
            List<Booking> bookings = car.getBookings();
            for (int j=0;j<bookings.size();j++) {
                booking=bookings.get(j);
                LocalDateTime now = LocalDateTime.now();

            if (!car.getIsRented()) {    //не арендовані
                    if (booking.getEndTime().isBefore(now)) {
                        car.getBookings().remove(booking);
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
                    car.getBookings().remove(booking);
                    bookingService.deleteById(booking.getId()); // Видалити бронювання з бази даних
                        continue;
                    }
                    if (booking.getStartTime().isBefore(now)){ //зараз після start_date
                        continue OUTER;
                    }
                car.setIsRented(false);
                car.setRenter(null);
                carService.save(car);
            }
            }
        }


    }
}
