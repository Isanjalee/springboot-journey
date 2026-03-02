package com.isanjalee.demo.springbootdemo.service;

import com.isanjalee.demo.springbootdemo.exception.DuplicateResourceException;
import com.isanjalee.demo.springbootdemo.model.User;
import com.isanjalee.demo.springbootdemo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldThrowException_WhenEmailExists() {

        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        var request = new com.isanjalee.demo.springbootdemo.dto.UserCreateRequest();
        request.setEmail("test@gmail.com");

        assertThrows(DuplicateResourceException.class, () -> {
            userService.createUser(request);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_ShouldSaveUser_WhenValid() {

        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("123")).thenReturn("encoded123");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("test@gmail.com");
        savedUser.setName("Test");
        savedUser.setRole("USER");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        var request = new com.isanjalee.demo.springbootdemo.dto.UserCreateRequest();
        request.setEmail("test@gmail.com");
        request.setName("Test");
        request.setPassword("123");

        var response = userService.createUser(request);

        assertEquals("Test", response.getName());
        assertEquals("test@gmail.com", response.getEmail());

        verify(userRepository).save(any(User.class));
    }
}