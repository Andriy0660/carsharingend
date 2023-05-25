package com.example.carsharing.mapper;

import com.example.carsharing.dto.request.AddCarRequest;
import com.example.carsharing.entity.Car;

public class CarMapper {
    public static Car mapToAddCar(AddCarRequest carInfo) {
        Car car = new Car();
        car.setId(0L);
        car.setIsRented(false);
        car.setVendor(carInfo.getVendor());
        car.setModel(carInfo.getModel());
        car.setYear(carInfo.getYear());
        car.setColor(carInfo.getColor());
        car.setLocation(carInfo.getLocation());
        car.setDescription(carInfo.getDescription());
        return car;
    }
}
