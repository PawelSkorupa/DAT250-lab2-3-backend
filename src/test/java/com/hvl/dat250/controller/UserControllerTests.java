package com.hvl.dat250.controller;

import com.hvl.dat250.component.PollManager;
import com.hvl.dat250.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PollManager pollManager;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        user1 = new User(1, "John Doe", "john.doe@example.com");
        user2 = new User(2, "Jane Doe", "jane.doe@example.com");

        when(pollManager.createUser(any(User.class))).thenReturn(user1);
        when(pollManager.listUsers()).thenReturn(Arrays.asList(user1, user2));
        when(pollManager.getUserById(1)).thenReturn(user1);
        when(pollManager.getUserById(2)).thenReturn(user2);
    }

    @Test
    public void testCreateUser() throws Exception {
        String userJson = "{\"id\":1,\"username\":\"John Doe\",\"email\":\"john.doe@example.com\"}";

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));
    }

    @Test
    public void testListUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("John Doe")))
                .andExpect(jsonPath("$[0].email", is("john.doe@example.com")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].username", is("Jane Doe")))
                .andExpect(jsonPath("$[1].email", is("jane.doe@example.com")));
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));
    }
}
