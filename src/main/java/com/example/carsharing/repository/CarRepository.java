package com.example.carsharing.repository;

import com.example.carsharing.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findAllByIdIsIn(List<Long> ids);
}
