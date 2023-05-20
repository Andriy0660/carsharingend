package com.example.carsharing.service;

import com.example.carsharing.model.User;
import com.example.carsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
