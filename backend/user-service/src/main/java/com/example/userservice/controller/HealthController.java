package com.example.userservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
