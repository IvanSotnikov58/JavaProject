package com.example.hearthclone.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "rating")
    private int rating;
    @Column(name = "wins")
    private int wins;
    @Column(name = "losses")
    private int losses;
    private int health;

    protected User() {
    }

    public User(String name, String password, int rating) {
        this.name = name;
        this.password = password;
        this.rating = rating;
        this.wins = 0;
        this.losses = 0;
        this.health=100;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }


    public int getHealth() {
        return health;
    }
    public void setHealth(int health){
        this.health=health;
    }
}



//    public void incrementWins() {
//    }
//
//    public void incrementLosses() {
//    }
//}

