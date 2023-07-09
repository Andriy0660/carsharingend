package com.example.carsharing.service;

import com.example.carsharing.dto.request.RegisterRequest;
import com.example.carsharing.entity.User;
import com.example.carsharing.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//tested only existsUserByEmail()
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void existsUserByEmail() {
        RegisterRequest rr = new RegisterRequest(
                "sandriy666@gmail.com",
                "andriy",
                "Andriy",
                "Snovyda",
                "+380971694636");
        User user = User.builder().email(rr.getEmail()).phone(rr.getPhone()).password(rr.getPassword()).firstname(rr.getFirstName()).lastname(rr.getLastName()).build();
        assertThat(userService.existsUserByEmail(rr.getEmail())).isFalse();
        userService.save(user);
        verify(userRepository).save(user);
        when(userRepository.existsByEmail(rr.getEmail())).thenReturn(true);
        assertThat(userService.existsUserByEmail(rr.getEmail())).isTrue();
    }
}