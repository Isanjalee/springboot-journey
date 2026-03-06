package com.isanjalee.demo.springbootdemo.service;

import com.isanjalee.demo.springbootdemo.dto.UserCreateRequest;
import com.isanjalee.demo.springbootdemo.dto.UserUpdateRequest;
import com.isanjalee.demo.springbootdemo.exception.DuplicateResourceException;
import com.isanjalee.demo.springbootdemo.exception.ResourceNotFoundException;
import com.isanjalee.demo.springbootdemo.model.User;
import com.isanjalee.demo.springbootdemo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUserShouldThrowExceptionWhenEmailExists() {
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        UserCreateRequest request = new UserCreateRequest();
        request.setEmail("test@gmail.com");

        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> userService.createUser(request)
        );

        assertEquals("Email already exists: test@gmail.com", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(any(String.class));
    }

    @Test
    void createUserShouldSaveUserWhenValid() {
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("encoded123456");

        User savedUser = user(1L, "Test", "test@gmail.com", "encoded123456", "USER");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserCreateRequest request = new UserCreateRequest();
        request.setEmail("test@gmail.com");
        request.setName("Test");
        request.setPassword("123456");

        var response = userService.createUser(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User userToSave = userCaptor.getValue();
        assertEquals("Test", userToSave.getName());
        assertEquals("test@gmail.com", userToSave.getEmail());
        assertEquals("encoded123456", userToSave.getPassword());
        assertEquals("USER", userToSave.getRole());

        assertEquals(1L, response.getId());
        assertEquals("Test", response.getName());
        assertEquals("test@gmail.com", response.getEmail());
        assertEquals("USER", response.getRole());
    }

    @Test
    void getUserByIdShouldReturnUserWhenFound() {
        User savedUser = user(1L, "Alice", "alice@example.com", "secret", "ADMIN");
        when(userRepository.findById(1L)).thenReturn(Optional.of(savedUser));

        var response = userService.getUserById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Alice", response.getName());
        assertEquals("alice@example.com", response.getEmail());
        assertEquals("ADMIN", response.getRole());
    }

    @Test
    void getUserByIdShouldThrowExceptionWhenMissing() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getUserById(99L)
        );

        assertEquals("User not found with id: 99", exception.getMessage());
    }

    @Test
    void updateUserShouldUpdateNameAndEmailWhenFound() {
        User existingUser = user(1L, "Old Name", "old@example.com", "encoded", "USER");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(
                user(1L, "New Name", "new@example.com", "encoded", "USER")
        );

        UserUpdateRequest request = new UserUpdateRequest();
        request.setName("New Name");
        request.setEmail("new@example.com");

        var response = userService.updateUser(1L, request);

        assertEquals("New Name", existingUser.getName());
        assertEquals("new@example.com", existingUser.getEmail());
        assertEquals(1L, response.getId());
        assertEquals("New Name", response.getName());
        assertEquals("new@example.com", response.getEmail());
        assertEquals("USER", response.getRole());
    }

    @Test
    void updateUserShouldThrowExceptionWhenMissing() {
        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        UserUpdateRequest request = new UserUpdateRequest();
        request.setName("Missing");
        request.setEmail("missing@example.com");

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.updateUser(10L, request)
        );

        assertEquals("User not found with id: 10", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUserShouldDelegateToRepository() {
        userService.deleteUser(5L);

        verify(userRepository).deleteById(5L);
    }

    @Test
    void getAllUsersShouldMapRepositoryPageToResponsePage() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<User> userPage = new PageImpl<>(List.of(
                user(1L, "Alice", "alice@example.com", "p1", "USER"),
                user(2L, "Bob", "bob@example.com", "p2", "ADMIN")
        ), pageable, 2);

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        var responsePage = userService.getAllUsers(pageable);

        assertEquals(2, responsePage.getTotalElements());
        assertEquals("Alice", responsePage.getContent().get(0).getName());
        assertEquals("bob@example.com", responsePage.getContent().get(1).getEmail());
        assertEquals("ADMIN", responsePage.getContent().get(1).getRole());
    }

    @Test
    void searchUsersByNameShouldReturnMappedResults() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<User> userPage = new PageImpl<>(List.of(
                user(1L, "Alice Wonder", "alice@example.com", "p1", "USER")
        ), pageable, 1);

        when(userRepository.findByNameContainingIgnoreCase("alice", pageable)).thenReturn(userPage);

        var responsePage = userService.searchUsersByName("alice", pageable);

        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Alice Wonder", responsePage.getContent().get(0).getName());
        assertEquals("alice@example.com", responsePage.getContent().get(0).getEmail());
    }

    @Test
    void searchUsersByEmailShouldReturnMappedResults() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<User> userPage = new PageImpl<>(List.of(
                user(2L, "Evan", "evan@company.com", "p2", "USER")
        ), pageable, 1);

        when(userRepository.findByEmailContainingIgnoreCase("company", pageable)).thenReturn(userPage);

        var responsePage = userService.searchUsersByEmail("company", pageable);

        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Evan", responsePage.getContent().get(0).getName());
        assertEquals("evan@company.com", responsePage.getContent().get(0).getEmail());
        assertTrue(responsePage.getContent().stream().allMatch(user -> "USER".equals(user.getRole())));
    }

    private User user(Long id, String name, String email, String password, String role) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}
