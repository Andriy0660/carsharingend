package com.example.carsharing.controller;

import com.example.carsharing.dto.response.UserProfile;
import com.example.carsharing.entity.User;
import com.example.carsharing.mapper.UserProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carsharing/users")
@RequiredArgsConstructor
public class UserController {
    @GetMapping("/profile")
    public UserProfile profile(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UserProfileMapper.mapToUserProfile(user);
    }
    //оновити номер телефону

    //оновити пароль

}