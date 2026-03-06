package com.isanjalee.demo.springbootdemo.controller;

import com.isanjalee.demo.springbootdemo.dto.UserCreateRequest;
import com.isanjalee.demo.springbootdemo.dto.UserResponse;
import com.isanjalee.demo.springbootdemo.exception.DuplicateResourceException;
import com.isanjalee.demo.springbootdemo.exception.GlobalExceptionHandler;
import com.isanjalee.demo.springbootdemo.exception.ResourceNotFoundException;
import com.isanjalee.demo.springbootdemo.security.JwtAuthFilter;
import com.isanjalee.demo.springbootdemo.security.JwtUtil;
import com.isanjalee.demo.springbootdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({com.isanjalee.demo.springbootdemo.config.SecurityConfig.class, JwtAuthFilter.class, GlobalExceptionHandler.class})
class UserControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    void shouldReturnUnauthorizedForAnonymousUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnForbiddenForNonAdminUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnUsersPageForAdmin() throws Exception {
        var page = new PageImpl<>(
                List.of(new UserResponse(1L, "Alice", "alice@example.com", "USER")),
                PageRequest.of(0, 5),
                1
        );
        when(userService.getAllUsers(any())).thenReturn(page);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Alice"))
                .andExpect(jsonPath("$.content[0].email").value("alice@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateUserForAdmin() throws Exception {
        when(userService.createUser(any(UserCreateRequest.class)))
                .thenReturn(new UserResponse(1L, "New User", "new@example.com", "USER"));

        String body = """
                {
                  "name": "New User",
                  "email": "new@example.com",
                  "password": "123456"
                }
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New User"))
                .andExpect(jsonPath("$.email").value("new@example.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnBadRequestForInvalidPayload() throws Exception {
        String body = """
                {
                  "name": "A",
                  "email": "invalid-email",
                  "password": "123"
                }
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("name:")))
                .andExpect(jsonPath("$.message").value(containsString("email:")))
                .andExpect(jsonPath("$.message").value(containsString("password:")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnConflictWhenServiceDetectsDuplicateEmail() throws Exception {
        when(userService.createUser(any(UserCreateRequest.class)))
                .thenThrow(new DuplicateResourceException("Email already exists: duplicate@example.com"));

        String body = """
                {
                  "name": "Duplicate",
                  "email": "duplicate@example.com",
                  "password": "123456"
                }
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email already exists: duplicate@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnUserById() throws Exception {
        when(userService.getUserById(1L))
                .thenReturn(new UserResponse(1L, "Alice", "alice@example.com", "USER"));

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        when(userService.getUserById(99L))
                .thenThrow(new ResourceNotFoundException("User not found with id: 99"));

        mockMvc.perform(get("/users/{id}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with id: 99"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldSearchUsersByName() throws Exception {
        var page = new PageImpl<>(
                List.of(new UserResponse(2L, "Bob", "bob@example.com", "USER")),
                PageRequest.of(0, 5),
                1
        );
        when(userService.searchUsersByName(eq("bob"), any())).thenReturn(page);

        mockMvc.perform(get("/users/search").param("name", "bob"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Bob"));
    }
}
