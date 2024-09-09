package com.hvl.dat250.controller;

import com.hvl.dat250.component.PollManager;
import com.hvl.dat250.model.Poll;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/polls")
public class PollController {
    private final PollManager pollManager;

    public PollController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @PostMapping
    public Poll createPoll(@RequestBody Poll poll) {
        return pollManager.createPoll(poll);
    }

    @GetMapping
    public Collection<Poll> listPolls() {
        return pollManager.listPolls();
    }

    @GetMapping("/{id}")
    public Poll getPollById(@PathVariable Integer id) {
        return pollManager.getPollById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePoll(@PathVariable Integer id) {
        pollManager.deletePoll(id);
    }
}
