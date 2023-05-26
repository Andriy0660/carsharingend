package com.example.carsharing.controller;

import com.example.carsharing.dto.request.AddInquireRequest;
import com.example.carsharing.dto.response.InquireResponse;
import com.example.carsharing.entity.Inquire;
import com.example.carsharing.entity.User;
import com.example.carsharing.entity.UserDetailsImpl;
import com.example.carsharing.exception.BadRequestException;
import com.example.carsharing.mapper.InquireMapper;
import com.example.carsharing.service.InquireService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carsharing/inquires")
@RequiredArgsConstructor
@Validated
public class InquiriesController {
    private final InquireService inquireService;

    @PostMapping("/add")
    public ResponseEntity<Inquire> addNewInquire(@RequestBody@Valid AddInquireRequest request){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        if(user.isVolunteer()==false){
            throw new BadRequestException("You are not a volunteer, you can not rent car");
        }
        Inquire inquire = InquireMapper.mapToAddInquire(request);
        inquire.setVolunteer(user);

        inquireService.save(inquire);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getInquires")
    public ResponseEntity<List<InquireResponse>> getAllInquire(){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        List<InquireResponse> inquries = inquireService
                .findAllByOwnerIsNull()
                .stream()
                .filter(i -> i.getVolunteer().getId() != user.getId())
                .map((i)->new InquireResponse(
                        i.getId(),
                        i.getVolunteer().getFirstname(),
                        i.getVolunteer().getLastname(),
                        i.getVolunteer().getPhone(),
                        i.getCarType(),
                        i.getDescription())).toList();

        return ResponseEntity.ok(inquries);
    }

    @PutMapping("/lend")
    public ResponseEntity<?> lendCar(@RequestParam("id") Long id){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Inquire inquire = inquireService.findById(id);
        inquire.setOwner(user);
        inquireService.save(inquire);
        return ResponseEntity.ok().build();
    }
}
