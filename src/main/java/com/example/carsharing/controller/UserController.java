package com.example.carsharing.controller;

import com.example.carsharing.dto.response.IsVolunteerResponse;
import com.example.carsharing.dto.response.UserProfileResponse;
import com.example.carsharing.entity.User;
import com.example.carsharing.entity.UserDetailsImpl;
import com.example.carsharing.exception.BadRequestException;
import com.example.carsharing.mapper.UserProfileMapper;
import com.example.carsharing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carsharing/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public UserProfileResponse profile() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        return UserProfileMapper.mapToUserProfile(user);
    }

    @PutMapping("/confirmvolunteerstatus")
    public ResponseEntity<UserProfileResponse> confirmVolunteerStatus(@RequestParam("number") String number) {
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
        return ResponseEntity.ok(UserProfileMapper.mapToUserProfile(user));
    }
    @PutMapping("/addphone")
    public ResponseEntity<?> addPhone(@RequestParam("phone") String phone) {
        if (!phone.matches("^380\\d{9}$")) throw new BadRequestException("phone is not valid");
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        user.setPhone("+"+phone);
        userService.save(user);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/checkvolunteerstatus")
    public ResponseEntity<IsVolunteerResponse> checkVolunteerStatus(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        return ResponseEntity.ok(new IsVolunteerResponse(user.isVolunteer()));
    }
}
