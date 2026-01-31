package com.isanjalee.demo.springbootdemo.service;

import com.isanjalee.demo.springbootdemo.dto.LoginRequest;
import com.isanjalee.demo.springbootdemo.model.User;
import com.isanjalee.demo.springbootdemo.repository.UserRepository;
import com.isanjalee.demo.springbootdemo.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // compare raw password with hashed password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // roles in token
        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }
}
