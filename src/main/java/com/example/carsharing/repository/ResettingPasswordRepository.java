package com.example.carsharing.repository;



import com.example.carsharing.entity.ResettingPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResettingPasswordRepository extends JpaRepository<ResettingPasswordToken,Long> {
    Optional<ResettingPasswordToken> findByToken(String token);
}
