package com.example.hearthclone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
@Entity
public class Turn {
    @Id
    @GeneratedValue
    private Long id;

    private int turnNumber;

    @ManyToOne
    private User player;

    @ManyToOne
    private Card card;

    private String target;
    private String result;

    private LocalDateTime timestamp;

    @ManyToOne
    private Match match;

protected Turn(){}

    public Turn(int turnNumber, User player, Card card, String target, String result, LocalDateTime timestamp, Match match) {
        this.turnNumber = turnNumber;
        this.player = player;
        this.card = card;
        this.target = target;
        this.result = result;
        this.timestamp = timestamp;
        this.match = match;
    }
}
