package com.isanjalee.demo.springbootdemo.controller;

import com.isanjalee.demo.springbootdemo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private List<User> users = new ArrayList<>();
    private Long idCounter = 1L;

    @GetMapping
    public List<User> getAll() {
        return users;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        user.setId(idCounter++);
        users.add(user);
        return user;
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User newUser) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                u.setName(newUser.getName());
                u.setEmail(newUser.getEmail());
                return u;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        users.removeIf(u -> u.getId().equals(id));
        return "User deleted";
    }
}