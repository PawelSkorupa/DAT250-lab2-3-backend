package com.hvl.dat250.model;

import java.time.Instant;

public class Vote {
    private Integer id;

    private User user;

    private VoteOption voteOption;

    private Instant publishedAt;

    public Vote(Integer id, User user, VoteOption voteOption, Instant publishedAt) {
        this.id = id;
        this.user = user;
        this.voteOption = voteOption;
        this.publishedAt = publishedAt;
    }

    public Vote() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VoteOption getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(VoteOption voteOption) {
        this.voteOption = voteOption;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }
}
