package com.hvl.dat250.controller;

import com.hvl.dat250.component.PollManager;
import com.hvl.dat250.model.VoteOption;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/voteOptions")
@CrossOrigin(origins = "http://localhost:3000")
public class VoteOptionController {
    private final PollManager pollManager;

    public VoteOptionController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @PostMapping
    public VoteOption createVoteOption(@RequestBody VoteOption voteOption) {
        return pollManager.createVoteOption(voteOption);
    }

    @GetMapping
    public Collection<VoteOption> listVoteOptions() {
        return pollManager.listVoteOptions();
    }

    @GetMapping("/{id}")
    public VoteOption getVoteOptionById(@PathVariable Integer id) {
        return pollManager.getVoteOptionById(id);
    }
}
