package com.example.carsharing.repository;

import com.example.carsharing.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface CarRepository extends JpaRepository<Car,Integer> {
    List<Car> findByIsRentedTrue();
    List<Car> findByIsRentedFalse();
    @Transactional
    @Modifying
    @Query("update Car set isRented = true, renter.id = :renter_id, " +
            "startTime = now(), endTime = :endTime where id = :id")

    void bookCar(Integer id, Integer renter_id, LocalDateTime endTime);
}
