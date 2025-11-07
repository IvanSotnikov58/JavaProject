package com.example.hearthclone.model;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Deck {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private User player;

    @ManyToMany
    List<Card> cards;

    protected Deck() {
    }

    public Deck(int id, User player, List<Card> cards) {
        this.id = id;
        this.player = player;
        this.cards = cards;
    }
}
