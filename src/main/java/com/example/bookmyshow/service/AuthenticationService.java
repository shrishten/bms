package com.example.bookmyshow.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bookmyshow.DTO.LoginRequestDTO;
import com.example.bookmyshow.DTO.LoginResponseDTO;
import com.example.bookmyshow.DTO.RegisterRequestDTO;
import com.example.bookmyshow.entities.User;
import com.example.bookmyshow.repository.UserRepository;
import com.example.bookmyshow.jwt.JwtService;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public User registerNormalUser(RegisterRequestDTO registerRequestDTO) {
        // Logic to register a normal user
        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists: " + registerRequestDTO.getUsername());
        }
        Set<String> role = new HashSet<>();
        role.add("ROLE_USER");
        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRole(role);
        return userRepository.save(user);
    }

    public User registerAdminUser(RegisterRequestDTO registerRequestDTO) {
        // Logic to register an admin user
        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists: " + registerRequestDTO.getUsername());
        }
        Set<String> role = new HashSet<>();
        role.add("ROLE_USER");
        role.add("ROLE_ADMIN");
        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRole(role);
        return userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(
                        () -> new RuntimeException("User not found with username: " + loginRequestDTO.getUsername()));

        authenticationManager
                .authenticate(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

        String jwtToken = jwtService.generateToken(user);

        return LoginResponseDTO.builder()
                .username(user.getUsername())
                .jwtToken(jwtToken)
                .roles(user.getRole())
                .build();
    }
}