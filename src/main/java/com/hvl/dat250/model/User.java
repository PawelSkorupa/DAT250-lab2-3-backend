package com.hvl.dat250.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private Integer id;

    private String username;

    private String email;

    private Set<Integer> createdPolls = new HashSet<>();

    private Set<Integer> votes = new HashSet<>();

    public User(Integer id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Integer> getCreatedPolls() {
        return createdPolls;
    }

    public void setCreatedPolls(Set<Integer> createdPolls) {
        this.createdPolls = createdPolls;
    }

    public Set<Integer> getVotes() {
        return votes;
    }

    public void setVotes(Set<Integer> votes) {
        this.votes = votes;
    }
}
