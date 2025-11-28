package com.example.hearthclone.model;

import jakarta.persistence.*;

import javax.swing.*;
import java.time.LocalDateTime;
@Entity
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="turn_number")
    private int turnNumber;

    @ManyToOne
    private Long player;

    @ManyToOne
    private Cards cards;

    private String target;
    private String result;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name="matches")
    private Long match;
    private Action action;

public Turn(){}

    public Turn(int turnNumber, User player, Cards cards, String target, String result, LocalDateTime timestamp, Match match, Action action) {
        this.turnNumber = turnNumber;
        this.player = player.getId();
        this.cards = cards;
        this.target = target;
        this.result = result;
        this.timestamp = timestamp;
        this.match = match.getId();
        this.action= action;
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

    public Long getPlayer() {
        return player;
    }

    public void setPlayer(Long player) {
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

    public Long getMatch() {
        return match;
    }

    public void setMatch(Long match) {
        this.match = match;
    }

    public Long setMatchId(Long matchId) {
    return match;
    }
    public void setAction(Action action){
    this.action=action;
    }
}
