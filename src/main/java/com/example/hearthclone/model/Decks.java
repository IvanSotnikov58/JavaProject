package com.example.hearthclone.model;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "decks")
public class Decks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User player;

    @ManyToMany
    @JoinTable(
            name = "decks_cards",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )



    private List<Cards> cards;

    protected Decks() {
    }

    public Decks(User player, List<Cards> cards) {
        this.player = player;
        this.cards = cards;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public List<Cards> getCards() {
        return cards;
    }

    public void setCards(List<Cards> cards) {
        this.cards = cards;
    }
}
