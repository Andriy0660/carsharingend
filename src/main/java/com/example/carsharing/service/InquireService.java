package com.example.carsharing.service;

import com.example.carsharing.entity.Inquire;
import com.example.carsharing.exception.BadRequestException;
import com.example.carsharing.repository.InquireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InquireService {
    private final InquireRepository inquireRepository;

    public Inquire findById(Long id){
        return inquireRepository.findById(id).orElseThrow(()->new BadRequestException("There is no inquire with id " + id));
    }

    public Inquire save(Inquire inquire){
        return inquireRepository.save(inquire);
    }

    public List<Inquire> findAllByOwnerIsNull(){
        return inquireRepository.findAllByOwnerIsNull();
    }
    public List<Inquire> findAllByOwnerIsNotNullAndNeedsDeliveryIsTrue(){
        return inquireRepository.findAllByOwnerIsNotNullAndNeedsDeliveryIsTrue();
    }

}
