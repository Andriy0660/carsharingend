package com.example.carsharing.controller;

import com.example.carsharing.dto.request.ChangePasswordRequest;
import com.example.carsharing.dto.request.ChangePhoneRequest;
import com.example.carsharing.dto.response.UserProfile;
import com.example.carsharing.entity.User;
import com.example.carsharing.entity.UserDetailsImpl;
import com.example.carsharing.mapper.UserProfileMapper;
import com.example.carsharing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carsharing/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    @GetMapping("/profile")
    public UserProfile profile(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        return UserProfileMapper.mapToUserProfile(user);
    }
    @PutMapping("/changePassword")
    public ResponseEntity<Void> changePassword(
            @RequestBody @Valid ChangePasswordRequest request
            ){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        userService.changePassword(user,request);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/changePhone")
    public ResponseEntity<Void> changePassword(
            @RequestBody @Valid ChangePhoneRequest request
    ){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        userService.changePhone(user,request);

        return ResponseEntity.ok().build();
    }



}
