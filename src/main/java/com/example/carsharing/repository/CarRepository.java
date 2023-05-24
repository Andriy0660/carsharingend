package com.example.carsharing.repository;

import com.example.carsharing.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findByIsRentedTrue();
    List<Car> findByIsRentedFalse();
    Optional<Car> findByIdAndIsRentedTrue(Long id);

}
