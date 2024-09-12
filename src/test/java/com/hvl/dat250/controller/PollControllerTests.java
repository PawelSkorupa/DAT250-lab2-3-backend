package com.hvl.dat250.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hvl.dat250.component.PollManager;
import com.hvl.dat250.model.Poll;
import com.hvl.dat250.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PollControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PollManager pollManager;

    private Poll poll1;
    private Poll poll2;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        User creator = new User();
        creator.setId(1);
        creator.setUsername("Creator");

        poll1 = new Poll(1, creator, "What is your favorite color?",
                Instant.now(), Instant.now().plusSeconds(3600));
        poll1.setOptions(new HashSet<>());

        poll2 = new Poll(2, creator, "What is your favorite programming language?",
                Instant.now(), Instant.now().plusSeconds(3600));
        poll2.setOptions(new HashSet<>());

        when(pollManager.createPoll(any(Poll.class))).thenReturn(poll1);
        when(pollManager.listPolls()).thenReturn(Arrays.asList(poll1, poll2));
        when(pollManager.getPollById(1)).thenReturn(poll1);
        when(pollManager.getPollById(2)).thenReturn(poll2);
        doNothing().when(pollManager).deletePoll(1);
    }

    @Test
    public void testCreatePoll() throws Exception {
        String pollJson = objectMapper.writeValueAsString(poll1);

        mockMvc.perform(post("/api/polls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pollJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.question", is("What is your favorite color?")))
                .andExpect(jsonPath("$.creator.username", is("Creator")));
    }

    @Test
    public void testListPolls() throws Exception {
        mockMvc.perform(get("/api/polls")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].question", is("What is your favorite color?")))
                .andExpect(jsonPath("$[0].creator.username", is("Creator")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].question", is("What is your favorite programming language?")))
                .andExpect(jsonPath("$[1].creator.username", is("Creator")));
    }

    @Test
    public void testGetPollById() throws Exception {
        mockMvc.perform(get("/api/polls/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.question", is("What is your favorite color?")))
                .andExpect(jsonPath("$.creator.username", is("Creator")));
    }

    @Test
    public void testDeletePoll() throws Exception {
        mockMvc.perform(delete("/api/polls/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(pollManager, Mockito.times(1)).deletePoll(1);
    }
}
