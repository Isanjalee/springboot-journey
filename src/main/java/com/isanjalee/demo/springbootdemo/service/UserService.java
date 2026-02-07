package com.isanjalee.demo.springbootdemo.service;

import com.isanjalee.demo.springbootdemo.dto.UserCreateRequest;
import com.isanjalee.demo.springbootdemo.dto.UserResponse;
import com.isanjalee.demo.springbootdemo.dto.UserUpdateRequest;
import com.isanjalee.demo.springbootdemo.model.User;
import com.isanjalee.demo.springbootdemo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ADMIN creates USER
    @CacheEvict(value = { "userById", "usersList" }, allEntries = true)
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        log.info("Creating user with email: {}", request.getEmail());

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        User saved = userRepository.save(user);

        log.info("User created successfully: {}", saved.getEmail());

        return mapToResponse(saved);
    }

    @Cacheable(cacheNames = "userById", key = "#id")
    public UserResponse getUserById(Long id) {
        log.info("DB hit: getUserById({})", id);

        User u = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return mapToResponse(u);
    }

    @CacheEvict(value = { "userById", "usersList" }, allEntries = true)
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User saved = userRepository.save(user);

        return mapToResponse(saved);
    }

    @CacheEvict(value = { "userById", "usersList" }, allEntries = true)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Cache pages correctly (key includes page, size, sort)
    @Cacheable(cacheNames = "usersPage", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort.toString()")
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        log.info("DB hit: getAllUsers({})", pageable);

        return userRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public Page<UserResponse> searchUsersByName(String name, Pageable pageable) {
        return userRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(this::mapToResponse);
    }

    public Page<UserResponse> searchUsersByEmail(String email, Pageable pageable) {
        return userRepository.findByEmailContainingIgnoreCase(email, pageable)
                .map(this::mapToResponse);
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

}
