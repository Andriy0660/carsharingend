package com.example.carsharing.mapper;

import com.example.carsharing.dto.response.UserProfile;
import com.example.carsharing.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class UserProfileMapper {

    public UserProfile mapToUserProfile(User user) {
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail(user.getEmail());
        userProfile.setPhone(user.getPhone());
        userProfile.setFirstName(user.getFirstname());
        userProfile.setLastName(user.getLastname());
        if(user.getImageData()!=null)
            userProfile.setImageURL(user.getImageURL());
        return userProfile;
    }
}
