package com.example.carsharing.service;

import com.example.carsharing.entity.User;
import com.example.carsharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public Optional<User> findById(Integer id){
        return userRepository.findById(id);
    }
}
