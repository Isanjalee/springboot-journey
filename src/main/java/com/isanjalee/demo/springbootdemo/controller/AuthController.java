package com.isanjalee.demo.springbootdemo.controller;

import com.isanjalee.demo.springbootdemo.dto.LoginRequest;
import com.isanjalee.demo.springbootdemo.dto.LoginResponse;
import com.isanjalee.demo.springbootdemo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return new LoginResponse(token);
    }
}
