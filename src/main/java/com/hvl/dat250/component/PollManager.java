package com.hvl.dat250.component;

import com.hvl.dat250.model.Poll;
import com.hvl.dat250.model.User;
import com.hvl.dat250.model.Vote;
import com.hvl.dat250.model.VoteOption;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@Component
public class PollManager {
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Poll> polls = new HashMap<>();
    private final Map<Integer, VoteOption> voteOptions = new HashMap<>();
    private final Map<Integer, Vote> votes = new HashMap<>();

    private int userIdCounter = 1;
    private int pollIdCounter = 1;
    private int voteOptionIdCounter = 1;
    private int voteIdCounter = 1;

    public User createUser(User user) {
        user.setId(userIdCounter++);
        users.put(user.getId(), user);
        return user;
    }

    public Collection<User> listUsers() {
        return users.values();
    }

    public User getUserById(int id) {
        User user = users.get(id);
        if (user == null) {
            throw new NoSuchElementException("User not found with ID: " + id);
        }
        return user;
    }

    public Poll createPoll(Poll poll) {
        poll.setId(pollIdCounter++);
        polls.put(poll.getId(), poll);
        User user = poll.getCreator();
        Set<Integer> polls = user.getCreatedPolls();
        User person =  getUserById(user.getId());
        user.setEmail(person.getEmail());
        user.setVotes(person.getVotes());
        user.setUsername(person.getUsername());
        user.setId(person.getId());
        poll.setCreator(user);
        polls.add(poll.getId());
        user.setCreatedPolls(polls);
        return poll;
    }

    public Collection<Poll> listPolls() {
        return polls.values();
    }

    public Poll getPollById(int id) {
        Poll poll = polls.get(id);
        if (poll == null) {
            throw new NoSuchElementException("Poll not found with ID: " + id);
        }
        return poll;
    }

    public void deletePoll(int id) {
        polls.remove(id);
        votes.values().removeIf(vote -> vote.getUser().getId() == id);
    }

    public VoteOption createVoteOption(VoteOption voteOption) {
        voteOption.setId(voteOptionIdCounter++);
        voteOptions.put(voteOption.getId(), voteOption);
        return voteOption;
    }

    public Collection<VoteOption> listVoteOptions() {
        return voteOptions.values();
    }

    public VoteOption getVoteOptionById(int id) {
        VoteOption voteOption = voteOptions.get(id);
        if (voteOption == null) {
            throw new NoSuchElementException("VoteOption not found with ID: " + id);
        }
        return voteOption;
    }

    public Vote castVote(Vote vote) {

        VoteOption option = getVoteOptionById(vote.getVoteOption().getId());
        User user = getUserById(vote.getUser().getId());
        vote.setVoteOption(option);
        vote.setUser(user);
        vote.setId(voteIdCounter++);
        user.getVotes().add(vote.getId());
        votes.put(vote.getId(), vote);
        return vote;
    }

    public Collection<Vote> listVotes() {
        return votes.values();
    }

    public Vote getVoteById(int id) {
        Vote vote = votes.get(id);
        if (vote == null) {
            throw new NoSuchElementException("Vote not found with ID: " + id);
        }
        return vote;
    }

}
