package com.example.carsharing.repository;


import com.example.carsharing.entity.EmailConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailConfirmationTokenRepository
        extends JpaRepository<EmailConfirmationToken,Long> {
    Optional<EmailConfirmationToken> findByToken(String token);

}
