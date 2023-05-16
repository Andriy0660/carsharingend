package com.example.carsharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
@RestController
@RequestMapping("/nothing")
public class CarsharingApplication {
    @GetMapping("/nothing")
    public String nth(){
        return "nothing";
    }

    public static void main(String[] args) {
        SpringApplication.run(CarsharingApplication.class, args);
    }

}
