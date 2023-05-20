package com.example.carsharing.repository;

import com.example.carsharing.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Integer> {
    List<Car> findByIsRentedTrue();
    List<Car> findByIsRentedFalse();
    Optional<Car> findByIdAndIsRentedTrue(Integer id);
    @Transactional
    @Modifying
    @Query("UPDATE Car c SET c.isRented = true, c.renter.id = :renter_id, " +
            "c.startTime = :startTime, c.endTime = :endTime WHERE c.id = :id")
    void rentCar(@Param("id") Integer id, @Param("renter_id") Integer renterId, @Param("startTime") LocalDateTime startTime,@Param("endTime") LocalDateTime endTime);


}
