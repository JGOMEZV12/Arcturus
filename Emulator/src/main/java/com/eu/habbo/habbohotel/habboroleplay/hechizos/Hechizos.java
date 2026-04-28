package com.eu.habbo.habbohotel.habboroleplay.hechizos;

public class Hechizos {
    private int id;
    private String name;
    private String publicName;
    private String message;
    private int power;
    private int firingRange;
    private int shields;
    private int firingDamage;
    private int health;
    private int cost;
    private int costFine;
    private int stock;

    public Hechizos(int id, String name, String publicName, String message, int power, int firingRange, int shields, int firingDamage, int health, int cost, int costFine, int stock) {
        this.id = id;
        this.name = name;
        this.publicName = publicName;
        this.message = message;
        this.power = power;
        this.firingRange = firingRange;
        this.shields = shields;
        this.firingDamage = firingDamage;
        this.health = health;
        this.cost = cost;
        this.costFine = costFine;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPublicName() {
        return publicName;
    }

    public String getMessage() {
        return message;
    }

    public int getPower() {
        return power;
    }

    public int getFiringRange() {
        return firingRange;
    }

    public int getShields() {
        return shields;
    }

    public int getFiringDamage() {
        return firingDamage;
    }

    public int getHealth() {
        return health;
    }

    public int getCost() {
        return cost;
    }

    public int getCostFine() {
        return costFine;
    }

    public int getStock() {
        return stock;
    }
}
