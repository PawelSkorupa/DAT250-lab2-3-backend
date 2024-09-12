package com.hvl.dat250.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hvl.dat250.component.PollManager;
import com.hvl.dat250.model.Vote;
import com.hvl.dat250.model.User;
import com.hvl.dat250.model.VoteOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PollManager pollManager;

    private Vote vote1;
    private Vote vote2;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        User user = new User();
        user.setId(1);
        user.setUsername("Voter");

        VoteOption option = new VoteOption();
        option.setId(1);
        option.setCaption("Option A");

        vote1 = new Vote(1, user, option, Instant.now());
        vote2 = new Vote(2, user, option, Instant.now().plusSeconds(3600));

        when(pollManager.castVote(any(Vote.class))).thenReturn(vote1);
        when(pollManager.listVotes()).thenReturn(Arrays.asList(vote1, vote2));
        when(pollManager.getVoteById(1)).thenReturn(vote1);
        when(pollManager.getVoteById(2)).thenReturn(vote2);
    }

    @Test
    public void testCastVote() throws Exception {
        String voteJson = objectMapper.writeValueAsString(vote1);

        mockMvc.perform(post("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.user.username", is("Voter")))
                .andExpect(jsonPath("$.voteOption.caption", is("Option A")))
                .andExpect(jsonPath("$.publishedAt").exists());
    }

    @Test
    public void testListVotes() throws Exception {
        mockMvc.perform(get("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].user.username", is("Voter")))
                .andExpect(jsonPath("$[0].voteOption.caption", is("Option A")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].user.username", is("Voter")))
                .andExpect(jsonPath("$[1].voteOption.caption", is("Option A")));
    }

    @Test
    public void testGetVoteById() throws Exception {
        mockMvc.perform(get("/api/votes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.user.username", is("Voter")))
                .andExpect(jsonPath("$.voteOption.caption", is("Option A")));
    }
}
