package com.example.carsharing.repository;

import com.example.carsharing.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findAllByRenterId(Long id);
}
