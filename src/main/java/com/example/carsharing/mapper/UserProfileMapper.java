package com.example.carsharing.mapper;

import com.example.carsharing.dto.response.UserProfile;
import com.example.carsharing.entity.User;
import org.springframework.beans.factory.annotation.Value;

public class UserProfileMapper {
    @Value("${image.url}")
    private static String imageUrl;
    public static UserProfile mapToUserProfile(User user) {
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail(user.getEmail());
        userProfile.setPhone(user.getPhone());
        userProfile.setFirstName(user.getFirstname());
        userProfile.setLastName(user.getLastname());
        userProfile.setImageURL(imageUrl+
                user.getImageData().getName());
        return userProfile;
    }
}
