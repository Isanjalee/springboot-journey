package com.isanjalee.demo.springbootdemo.service;

import com.isanjalee.demo.springbootdemo.dto.UserRequest;
import com.isanjalee.demo.springbootdemo.dto.UserResponse;
import com.isanjalee.demo.springbootdemo.model.User;
import com.isanjalee.demo.springbootdemo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User saved = userRepository.save(user);

        return new UserResponse(saved.getId(), saved.getName(), saved.getEmail());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existing.setName(request.getName());
        existing.setEmail(request.getEmail());

        User saved = userRepository.save(existing);

        return new UserResponse(saved.getId(), saved.getName(), saved.getEmail());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
