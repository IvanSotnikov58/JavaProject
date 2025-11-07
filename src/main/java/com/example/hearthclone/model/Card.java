package com.example.hearthclone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
 public class Card {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int damage;
    private int health;
    private String type;
    private String Description;

    protected Card() {
    }

    public Card(String description, String type, int health, int damage, String name) {
        Description = description;
        this.type = type;
        this.health = health;
        this.damage = damage;
        this.name = name;

    }


}

