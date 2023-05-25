package com.example.carsharing.controller;

import com.example.carsharing.dto.response.UserProfileResponse;
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
    public UserProfileResponse profile() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        return UserProfileMapper.mapToUserProfile(user);
    }

    @PutMapping("/cofirmVolunteerStatus")
    public ResponseEntity<User> cofirmVolunteerStatus(@RequestParam("number") String number) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        try {
            Integer.parseInt(number);
            if (number.length() == 4) {
                user.setVolunteer(true);
                userService.save(user);
            }
        } catch (NumberFormatException ignored) {}
        return ResponseEntity.ok(user);
    }
}
