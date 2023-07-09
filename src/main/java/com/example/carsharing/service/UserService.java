package com.example.carsharing.service;


import com.example.carsharing.entity.User;
import com.example.carsharing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository repository;

    boolean existsUserByEmail(String email){return repository.existsByEmail(email);}
    boolean existsUserByPhone(String phone){return repository.existsByPhone(phone);}
    public User findUserById(Long id){
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User with this id is not found");
        }
        return optionalUser.get();
    }
    public void save(User user) {
        repository.save(user);
    }

    public UserService() {
    }
}
