package com.example.bookmyshow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookmyshow.DTO.LoginRequestDTO;
import com.example.bookmyshow.DTO.LoginResponseDTO;
import com.example.bookmyshow.DTO.RegisterRequestDTO;
import com.example.bookmyshow.entities.User;
import com.example.bookmyshow.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    public AuthenticationService authenticationService;

    @PostMapping("/registernormaluser")
    public ResponseEntity<User> registerNormalUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        // Logic to register a normal user
        return ResponseEntity.ok(authenticationService.registerNormalUser(registerRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        // Logic to authenticate user and generate JWT token
        return ResponseEntity.ok(authenticationService.login(loginRequestDTO));
    }

}
