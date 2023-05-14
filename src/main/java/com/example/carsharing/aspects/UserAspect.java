package com.example.carsharing.aspects;

import com.example.carsharing.config.JwtService;
import com.example.carsharing.entity.User;
import com.example.carsharing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UserAspect {
    private final UserService userService;
    private final JwtService jwtService;
    @Around("@annotation(com.example.carsharing.annotations.RequiresUser)")
    public Object checkUserAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        String jwt = (String) args[0];
        String token = jwt.substring(7);

        String userEmail = jwtService.extractUsername(token);

        try {
            User user = userService.findByEmail(userEmail).orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));
            args[1] = user;

        }catch(UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return joinPoint.proceed(args);
    }
}
