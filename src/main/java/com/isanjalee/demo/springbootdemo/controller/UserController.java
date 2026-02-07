package com.isanjalee.demo.springbootdemo.controller;

import com.isanjalee.demo.springbootdemo.dto.UserCreateRequest;
import com.isanjalee.demo.springbootdemo.dto.UserResponse;
import com.isanjalee.demo.springbootdemo.dto.UserUpdateRequest;
import com.isanjalee.demo.springbootdemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ADMIN only creates users
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        log.info("API Request → Create User");

        UserResponse created = userService.createUser(request);

        // Location header should be absolute/relative to this resource path
        URI location = URI.create("/users/" + created.getId());

        // 201 Created
        return ResponseEntity.created(location).body(created);
    }

    // 200 OK
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // 200 OK
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @PageableDefault(size = 5, sort = "id") Pageable pageable) {

        Page<UserResponse> page = userService.getAllUsers(pageable);
        return ResponseEntity.ok(page);
    }

    // 204 No Content (BEST PRACTICE)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // 200 OK (since we return updated entity)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {

        UserResponse updated = userService.updateUser(id, request);
        return ResponseEntity.ok(updated);
    }

    // 200 OK
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<Page<UserResponse>> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @PageableDefault(size = 5, sort = "id") Pageable pageable) {

        Page<UserResponse> result;

        if (name != null && !name.trim().isEmpty()) {
            result = userService.searchUsersByName(name.trim(), pageable);
        } else if (email != null && !email.trim().isEmpty()) {
            result = userService.searchUsersByEmail(email.trim(), pageable);
        } else {
            result = userService.getAllUsers(pageable);
        }

        return ResponseEntity.ok(result);
    }
}
