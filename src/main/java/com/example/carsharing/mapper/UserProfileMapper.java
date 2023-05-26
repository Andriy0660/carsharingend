package com.example.carsharing.mapper;

import com.example.carsharing.dto.response.UserProfileResponse;
import com.example.carsharing.entity.User;


public class UserProfileMapper {

    public static UserProfileResponse mapToUserProfile(User user) {
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setEmail(user.getEmail());
        userProfileResponse.setPhone(user.getPhone());
        userProfileResponse.setFirstName(user.getFirstname());
        userProfileResponse.setLastName(user.getLastname());
        if(user.getImageData()!=null)
            userProfileResponse.setImageURL(user.getImageURL());
        userProfileResponse.setIsVolunteer(user.isVolunteer());
        return userProfileResponse;
    }
}
