package com.example.carsharing.email.email_verification.email_service;



import com.example.carsharing.entity.EmailConfirmationToken;
import com.example.carsharing.repository.EmailConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class EmailConfirmationTokenService {
    private final EmailConfirmationTokenRepository repository;
    public void saveConfirmationToken(EmailConfirmationToken token) {
        repository.save(token);
    }

    public List<EmailConfirmationToken> findAllEmailVerifications(){
        return repository.findAll();
    }
    public Optional<EmailConfirmationToken> getEmailConfirmationToken(String token) {
        return repository.findByToken(token);
    }
    public void deleteConfirmationToken(EmailConfirmationToken token){
        repository.delete(token);
    }

}
