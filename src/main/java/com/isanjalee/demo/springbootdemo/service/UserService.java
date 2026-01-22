package com.isanjalee.demo.springbootdemo.service;

import com.isanjalee.demo.springbootdemo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();
    private Long idCounter = 1L;

    public List<User> getAllUsers() {
        return users;
    }

    public User createUser(User user) {
        user.setId(idCounter++);
        users.add(user);
        return user;
    }

    public User getUserById(Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public User updateUser(Long id, User newUser) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                u.setName(newUser.getName());
                u.setEmail(newUser.getEmail());
                return u;
            }
        }
        return null;
    }

    public void deleteUser(Long id) {
        users.removeIf(u -> u.getId().equals(id));
    }
}
