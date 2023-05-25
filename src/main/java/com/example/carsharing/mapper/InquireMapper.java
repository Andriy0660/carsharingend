package com.example.carsharing.mapper;

import com.example.carsharing.dto.request.AddInquireRequest;
import com.example.carsharing.entity.Inquire;

public class InquireMapper {
    public static Inquire mapToAddInquire(AddInquireRequest request) {
        Inquire inquire = new Inquire();
        inquire.setId(0L);
        inquire.setCarType(request.getCarType());
        inquire.setDescription(request.getDescription());
        inquire.setNeedsDelivery(request.isNeedsDelivery());
        return inquire;
    }
}
