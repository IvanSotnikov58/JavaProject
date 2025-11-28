package com.example.hearthclone.model;

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

    // <- changed to wrapper Long
    private Long winner;

    private String status;

    protected Match() {}

    public Match(User player01, User player02, Long winner, String status) {
        this.player01 = player01;
        this.player02 = player02;
        this.winner = winner;
        this.status = status;
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

    public Long getWinner() { return winner; }
    public void setWinner(Long winner) { this.winner = winner; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
