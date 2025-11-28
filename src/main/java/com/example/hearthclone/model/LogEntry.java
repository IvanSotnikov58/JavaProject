package com.example.hearthclone.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "log_entry")
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "matches")
    private Match match;
    @ManyToOne
    private Turn turn;
    private String description;
    private LocalDateTime timestamp;

    protected LogEntry() {}

    public LogEntry(Long id, Match match, Turn turn, String description, LocalDateTime timestamp) {
        this.id = id;
        this.match = match;
        this.turn = turn;
        this.description = description;
        this.timestamp = timestamp;
    }

    public LogEntry(Long matchId, String player, String action) {
        this.match=new Match();
        this.description=action;
        this.timestamp=null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
