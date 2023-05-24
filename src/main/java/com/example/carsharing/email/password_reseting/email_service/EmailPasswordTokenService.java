package com.example.carsharing.email.password_reseting.email_service;


import com.example.carsharing.entity.ResettingPasswordToken;
import com.example.carsharing.repository.ResettingPasswordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class EmailPasswordTokenService {
    private final ResettingPasswordRepository repository;
    public void saveConfirmationToken(ResettingPasswordToken token) {
        repository.save(token);
    }
    public List<ResettingPasswordToken> findAllEmailVerifications(){
        return repository.findAll();
    }
    public Optional<ResettingPasswordToken> getEmailConfirmationToken(String token) {
        return repository.findByToken(token);
    }
    public void deleteConfirmationToken(ResettingPasswordToken token){
        repository.delete(token);
    }
}
