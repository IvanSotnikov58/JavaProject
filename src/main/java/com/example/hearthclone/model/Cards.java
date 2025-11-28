package com.example.hearthclone.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cards")
 public class Cards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int damage;
    private int health;
    private String type;
    private String Description;
    private String effectKey;
    private String effectPayload;
    private int cost;

    protected Cards() {
    }

    public Cards(Long id, String description, String type, int health, int damage, String name, int cost) {
        this.id=id;
        Description = description;
        this.type = type;
        this.health = health;
        this.damage = damage;
        this.name = name;
        this.cost=cost;

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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getEffectPayload() {
        return effectPayload;
    }

    public void setEffectPayload(String effectPayload) {
        this.effectPayload = effectPayload;
    }

    public String getEffectKey() {
        return effectKey;
    }

    public void setEffectKey(String effectKey) {
        this.effectKey = effectKey;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Card{"+
                "id="+ id +
                ", name=" + name + "\n" + ", cost=" + cost + ", damage=" + damage + ", health=" + health + ", type=" + type + ", effectKey=" + effectKey+ "}";
    }
    public  enum  CardType{
        MINION,
        SPELL,
        WEAPON,
        HERO_POWER
    }

}

