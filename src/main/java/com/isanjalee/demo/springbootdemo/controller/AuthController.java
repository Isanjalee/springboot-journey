package com.isanjalee.demo.springbootdemo.controller;

import com.isanjalee.demo.springbootdemo.dto.*;
import com.isanjalee.demo.springbootdemo.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        // TEMP: hardcoded user (for learning)
        if ("admin".equals(request.username) && "admin123".equals(request.password)) {
            String token = jwtUtil.generateToken(request.username);
            return new AuthResponse(token);
        }
        throw new RuntimeException("Invalid credentials");
    }
}
