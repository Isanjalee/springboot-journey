package com.isanjalee.demo.springbootdemo.repository;

import com.isanjalee.demo.springbootdemo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByEmail() {
        userRepository.save(user("Alice", "alice@example.com", "secret", "USER"));

        var found = userRepository.findByEmail("alice@example.com");

        assertTrue(found.isPresent());
        assertEquals("Alice", found.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenEmailDoesNotExist() {
        var found = userRepository.findByEmail("missing@example.com");

        assertTrue(found.isEmpty());
    }

    @Test
    void shouldReportExistingEmail() {
        userRepository.save(user("Bob", "bob@example.com", "secret", "USER"));

        assertTrue(userRepository.existsByEmail("bob@example.com"));
        assertFalse(userRepository.existsByEmail("nobody@example.com"));
    }

    @Test
    void shouldSearchUsersByNameIgnoringCase() {
        userRepository.save(user("Alice Wonder", "alice@example.com", "secret", "USER"));
        userRepository.save(user("Bob Stone", "bob@example.com", "secret", "USER"));

        var result = userRepository.findByNameContainingIgnoreCase("ALICE", PageRequest.of(0, 5));

        assertEquals(1, result.getTotalElements());
        assertEquals("alice@example.com", result.getContent().get(0).getEmail());
    }

    @Test
    void shouldSearchUsersByEmailIgnoringCaseWithPagination() {
        userRepository.save(user("Evan", "evan@company.com", "secret", "USER"));
        userRepository.save(user("Dina", "dina@example.com", "secret", "USER"));

        var result = userRepository.findByEmailContainingIgnoreCase("COMPANY", PageRequest.of(0, 5));

        assertEquals(1, result.getTotalElements());
        assertEquals("Evan", result.getContent().get(0).getName());
    }

    private User user(String name, String email, String password, String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}
