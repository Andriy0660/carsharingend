package com.example.carsharing.mapper;

import com.example.carsharing.dto.request.AddCarRequest;
import com.example.carsharing.dto.response.UserProfile;
import com.example.carsharing.entity.Car;
import com.example.carsharing.entity.User;
import org.springframework.beans.factory.annotation.Value;

public class UserProfileMapper {
    @Value("${image.url}")
    private static String imageUrl;
    public static UserProfile mapToUserProfile(User user) {
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail(user.getEmail());
        userProfile.setPhone(user.getPhone());
        userProfile.setFirstName(user.getFirstName());
        userProfile.setLastName(user.getLastName());
        userProfile.setImageURL(imageUrl+
                user.getImageData().getName());
        return userProfile;
    }
}
