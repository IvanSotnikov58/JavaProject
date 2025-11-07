package com.example.hearthclone.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
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
}
