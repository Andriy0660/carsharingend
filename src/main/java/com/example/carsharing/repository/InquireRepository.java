package com.example.carsharing.repository;

import com.example.carsharing.entity.Inquire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InquireRepository extends JpaRepository<Inquire,Long> {
    public List<Inquire> findAllByOwnerIsNull();
    public List<Inquire> findAllByOwnerIsNotNullAndNeedsDeliveryIsTrue();
}
