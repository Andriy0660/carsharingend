package com.example.carsharing.service;


import com.example.carsharing.entity.User;
import com.example.carsharing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository repository;
    public User findUserByEmail(String email){
        Optional<User> optionalUser = repository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User with this email is not found");
        }
        return optionalUser.get();
    }
    public User findUserById(Long id){
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User with this id is not found");
        }
        return optionalUser.get();
    }
    public void delete(User user){
        repository.delete(user);
    }
    public void save(User user) {
        repository.save(user);
    }

}
