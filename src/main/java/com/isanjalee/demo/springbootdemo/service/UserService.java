package com.isanjalee.demo.springbootdemo.service;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User newUser) {
        return userRepository.findById(id).map(u -> {
            u.setName(newUser.getName());
            u.setEmail(newUser.getEmail());
            return userRepository.save(u);
        }).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
