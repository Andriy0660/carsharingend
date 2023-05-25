package com.example.carsharing.service;


import com.example.carsharing.dto.request.ChangePasswordRequest;
import com.example.carsharing.dto.request.ChangePhoneRequest;
import com.example.carsharing.entity.User;
import com.example.carsharing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    public User findUserByEmail(String email){
        Optional<User> optionalUser = repository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User with this email is not found");
        }
        return optionalUser.get();
    }
    boolean existsUserByEmail(String email){return repository.existsByEmail(email);}
    boolean existsByPhone(String phone){return repository.existsByPhone(phone);}
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

    public void changePassword(User user, ChangePasswordRequest request){
        String currentPass = request.getCurrentPassword();
        String newPass = request.getNewPassword();

        if (!passwordEncoder.matches(currentPass,newPass)){
            throw new BadCredentialsException("It is wrong password");
        }
        user.setPassword(passwordEncoder.encode(newPass));
        save(user);
    }
    public void changePhone(User user, ChangePhoneRequest request){
        user.setPhone(request.getPhone());
        save(user);
    }

}
