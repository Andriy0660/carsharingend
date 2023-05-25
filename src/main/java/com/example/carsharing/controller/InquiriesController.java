package com.example.carsharing.controller;

import com.example.carsharing.dto.request.AddInquireRequest;
import com.example.carsharing.dto.response.InquireResponse;
import com.example.carsharing.entity.Inquire;
import com.example.carsharing.entity.User;
import com.example.carsharing.entity.UserDetailsImpl;
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
@RequestMapping("/carsharing/inquiries")
@RequiredArgsConstructor
@Validated
public class InquiriesController {
    private final InquireService inquireService;

    @PostMapping("/add")
    public ResponseEntity<Inquire> addNewInquire(@RequestBody@Valid AddInquireRequest request){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        Inquire inquire = InquireMapper.mapToAddInquire(request);
        inquire.setVolunteer(user);

        inquire = inquireService.save(inquire);

        return ResponseEntity.ok(inquire);
    }

    @GetMapping("/getInquires")
    public ResponseEntity<List<InquireResponse>> getAllInquire(){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        List<InquireResponse> inquries = inquireService
                .findAllByOwnerIsNull()
                .stream()
                .filter(i -> i.getOwner().getId() != user.getId())
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
    public void lendCar(@RequestParam("id") Long id){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Inquire inquire = inquireService.findById(id);
        inquire.setOwner(user);
    }
}
