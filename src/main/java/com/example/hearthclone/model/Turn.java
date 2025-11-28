package com.example.hearthclone.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="turn_number")
    private int turnNumber;

    @ManyToOne
    private User player;

    @ManyToOne
    private Cards cards;

    private String target;
    private String result;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name="matches")
    private Match match;

public Turn(){}

    public Turn(int turnNumber, User player, Cards cards, String target, String result, LocalDateTime timestamp, Match match) {
        this.turnNumber = turnNumber;
        this.player = player;
        this.cards = cards;
        this.target = target;
        this.result = result;
        this.timestamp = timestamp;
        this.match = match;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Cards getCard() {
        return cards;
    }

    public void setCard(Cards cards) {
        this.cards = cards;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
