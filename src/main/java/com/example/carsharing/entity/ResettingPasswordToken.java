package com.example.carsharing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "resetting_password")
@NoArgsConstructor
public class ResettingPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "expired_at",nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "confirmed",nullable = false)
    private boolean confirmed;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public ResettingPasswordToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, boolean confirmed, User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.confirmed = confirmed;
        this.user = user;
    }
}
