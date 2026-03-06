package com.isanjalee.demo.springbootdemo.controller;

import com.isanjalee.demo.springbootdemo.model.User;
import com.isanjalee.demo.springbootdemo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldRejectAnonymousAccessToUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldRejectNonAdminAccessToUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnUsers() throws Exception {
        saveUser("Alice Admin", "alice@example.com", "USER");

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Alice Admin"))
                .andExpect(jsonPath("$.content[0].email").value("alice@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateUser() throws Exception {
        String body = """
                {
                  "name": "Integration Test",
                  "email": "integration@test.com",
                  "password": "123456"
                }
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/users/")))
                .andExpect(jsonPath("$.name").value("Integration Test"))
                .andExpect(jsonPath("$.email").value("integration@test.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldRejectInvalidCreateRequest() throws Exception {
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
    void shouldReturnConflictWhenEmailAlreadyExists() throws Exception {
        saveUser("Existing User", "duplicate@example.com", "USER");

        String body = """
                {
                  "name": "Another User",
                  "email": "duplicate@example.com",
                  "password": "123456"
                }
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(containsString("Email already exists")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetUserById() throws Exception {
        User user = saveUser("Bob", "bob@example.com", "USER");

        mockMvc.perform(get("/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value("bob@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnNotFoundForMissingUser() throws Exception {
        mockMvc.perform(get("/users/{id}", 9999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("User not found with id: 9999")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateUser() throws Exception {
        User user = saveUser("Carol", "carol@example.com", "USER");

        String body = """
                {
                  "name": "Carol Updated",
                  "email": "carol.updated@example.com"
                }
                """;

        mockMvc.perform(put("/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Carol Updated"))
                .andExpect(jsonPath("$.email").value("carol.updated@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteUser() throws Exception {
        User user = saveUser("Delete Me", "delete@example.com", "USER");

        mockMvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldSearchUsersByName() throws Exception {
        saveUser("Alice Wonder", "alice@search.com", "USER");
        saveUser("Bob Stone", "bob@search.com", "USER");

        mockMvc.perform(get("/users/search").param("name", "alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].email").value("alice@search.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldSearchUsersByEmail() throws Exception {
        saveUser("Dana", "dana@example.com", "USER");
        saveUser("Evan", "evan@company.com", "USER");

        mockMvc.perform(get("/users/search").param("email", "company"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Evan"));
    }

    private User saveUser(String name, String email, String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(role);
        return userRepository.save(user);
    }
}
