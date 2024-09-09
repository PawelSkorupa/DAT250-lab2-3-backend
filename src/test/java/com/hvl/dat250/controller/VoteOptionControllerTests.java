package com.hvl.dat250.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hvl.dat250.component.PollManager;
import com.hvl.dat250.model.VoteOption;
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
public class VoteOptionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PollManager pollManager;

    private VoteOption voteOption1;
    private VoteOption voteOption2;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        voteOption1 = new VoteOption();
        voteOption1.setId(1);
        voteOption1.setCaption("Option A");
        voteOption2 = new VoteOption();
        voteOption2.setId(2);
        voteOption2.setCaption("Option B");

        when(pollManager.createVoteOption(any(VoteOption.class))).thenReturn(voteOption1);
        when(pollManager.listVoteOptions()).thenReturn(Arrays.asList(voteOption1, voteOption2));
        when(pollManager.getVoteOptionById(1)).thenReturn(voteOption1);
        when(pollManager.getVoteOptionById(2)).thenReturn(voteOption2);
    }

    @Test
    public void testCreateVoteOption() throws Exception {
        String voteOptionJson = objectMapper.writeValueAsString(voteOption1);

        mockMvc.perform(post("/voteOptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voteOptionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.caption", is("Option A")));
    }

    @Test
    public void testListVoteOptions() throws Exception {
        mockMvc.perform(get("/voteOptions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].caption", is("Option A")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].caption", is("Option B")));
    }

    @Test
    public void testGetVoteOptionById() throws Exception {
        mockMvc.perform(get("/voteOptions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.caption", is("Option A")));
    }
}
