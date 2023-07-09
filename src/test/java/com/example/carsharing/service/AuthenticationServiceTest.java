package com.example.carsharing.service;

import com.example.carsharing.dto.request.RegisterRequest;
import com.example.carsharing.entity.User;
import com.example.carsharing.exception.BadRequestException;
import com.example.carsharing.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
//tested only signUp()
class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService authenticationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDetailServiceImpl userDetailService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService(
                userDetailService,
                passwordEncoder,
                jwtService,
                authenticationManager,
                userService
        );
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void canRegisterUser() {
        RegisterRequest rr = new RegisterRequest(
                "sandriy666@gmail.com",
                "andriy",
                "Andriy",
                "Snovyda",
                "+380971694636");
        when(passwordEncoder.encode(rr.getPassword())).thenReturn("1234");

        User user = User.builder().email(rr.getEmail()).phone(rr.getPhone()).password(passwordEncoder.encode(rr.getPassword())).firstname(rr.getFirstName()).lastname(rr.getLastName()).build();
        authenticationService.signUp(rr);
        verify(userService).save(user);
        when(userService.existsUserByEmail(rr.getEmail())).thenReturn(true);
        assertThat(userService.existsUserByEmail("sandriy666@gmail.com")).isTrue();
    }
    @Test
    void shouldThrowExceptionWithEmailIsUsed(){
        RegisterRequest rr = new RegisterRequest(
            "sandriy666@gmail.com",
            "andriy",
            "Andriy",
            "Snovyda",
            "+380971694636");

        when(userService.existsUserByEmail("sandriy666@gmail.com")).thenReturn(true);
        assertThatThrownBy(() -> authenticationService.signUp(rr))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("The email is already used");
    }
    @Test
    void shouldThrowExceptionWithPhoneIsUsed(){
        RegisterRequest rr = new RegisterRequest(
                "sandriy666@gmail.com",
                "andriy",
                "Andriy",
                "Snovyda",
                "+380971694636");

        when(userService.existsUserByEmail("sandriy666@gmail.com")).thenReturn(false);
        when(userService.existsUserByPhone("+380971694636")).thenReturn(true);
        assertThatThrownBy(() -> authenticationService.signUp(rr))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("The phone is already used");
    }
}