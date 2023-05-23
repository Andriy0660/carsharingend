package com.example.carsharing.repository;

import com.example.carsharing.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BookingRepository extends JpaRepository<Booking,Integer> {

}
