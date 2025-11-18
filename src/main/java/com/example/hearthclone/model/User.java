package com.example.hearthclone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    private int rating;
    private int wins;
    private int losses;

    protected User() {
    }

    public User(Long id, String name, String password, int rating) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.rating = rating;
        this.wins = wins;
        this.losses = losses;
    }
    //test
}

