package com.example.carsharing.controller;

import com.example.carsharing.dto.response.UserProfile;
import com.example.carsharing.entity.Car;
import com.example.carsharing.entity.User;
import com.example.carsharing.mapper.UserProfileMapper;
import com.example.carsharing.service.CarService;
import com.example.carsharing.service.ImageService;
import com.example.carsharing.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RequiredArgsConstructor
@RestController
@RequestMapping("carsharing/images")
public class ImageController {
    @Value("${image.url}")

    private static String imageUrl;
    private final ImageService imageService;
    private final UserService userService;
    private final CarService carService;
    @PostMapping("/uploadUserImage")
    public ResponseEntity<?> uploadUserImage(@RequestParam("image") MultipartFile file) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        imageService.uploadImage(file);
        user.setImageData(imageService.getImageData(file.getOriginalFilename()));
        userService.save(user);

        UserProfile userProfile = UserProfileMapper.mapToUserProfile(user);
        return ResponseEntity.ok(userProfile);
    }
    @PostMapping("/uploadCarImage")
    public ResponseEntity<?> uploadCarImage(@RequestParam("image") MultipartFile file,
                                            @RequestParam("id")Integer id) throws IOException {
        imageService.uploadImage(file);

        Car car = carService.findById(id).orElseThrow();
        car.setImageData(imageService.getImageData(file.getOriginalFilename()));
        car.setImageURL(imageUrl + car.getImageData().getName());
        carService.save(car);

        return ResponseEntity.ok(car);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> getImage(@PathVariable String fileName){
        byte[] imageData=imageService.getImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}