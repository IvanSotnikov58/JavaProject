package com.example.hearthclone.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player01_id")
    private User player01;

    @ManyToOne
    @JoinColumn(name = "player02_id")
    private User player02;

    @ManyToOne
    @JoinColumn(name = "winner_id", nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User winner;

//    @ManyToOne
//    @JoinColumn(name = "winner_id")
//    private User winner;

    private String status;

    @OneToOne
    @JoinColumn(name = "decks1_id")
    private Decks decks1;

    @OneToOne
    @JoinColumn(name = "decks2_id")
    private Decks decks2;

    @Column(name = "player01_health")
    private int player01Health = 100;

    @Column(name = "player02_health")
    private int player02Health = 100;

    protected Match() {
    }

    public Match(User player01, User player02, User winner, String status) {
        this.player01 = player01;
        this.player02 = player02;
        this.winner = winner;
        this.status = status;
    }

    public Match(User player1, User player2) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPlayer01() {
        return player01;
    }

    public void setPlayer01(User player01) {
        this.player01 = player01;
    }

    public User getPlayer02() {
        return player02;
    }

    public void setPlayer02(User player02) {
        this.player02 = player02;
    }

    public Long getWinner() {
        return (winner != null) ? winner.getId() : null;
    }
    public void setWinner(User winner) {
        this.winner = winner;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDeck1(Decks decks1) {
        this.decks1 = decks1;
    }

    public void setDeck2(Decks decks2) {
        this.decks2 = decks2;
    }

    public Decks getDecks1() {
        return decks1;
    }

    public Decks getDecks2() {
        return decks2;
    }

    public int getPlayerHealth(User player) {
        if (player.getId().equals(player01.getId())) return player01Health;
        else return player02Health;
    }

    public void setPlayerHealth(User player, int health) {
        if (player.getId().equals(player01.getId())) player01Health = health;
        else player02Health = health;
    }

    public int getPlayer01Health() {
        return player01Health;
    }
    public int getPlayer02Health(){
        return player02Health;
    }
}


