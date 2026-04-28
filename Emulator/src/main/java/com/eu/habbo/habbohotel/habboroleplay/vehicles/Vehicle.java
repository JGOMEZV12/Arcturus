package com.eu.habbo.habbohotel.habboroleplay.vehicles;

public class Vehicle {
    private int id;
    private int itemId;
    private String itemName;
    private int effectId;
    private int price;
    private String model;
    private String displayName;
    private int maxFuel;
    private int maxTrunks;
    private int carType;
    private int maxDoors;
    private int carCorp;
    private int fastCar;

    public Vehicle(int id, int itemId, String itemName, int effectId, int price, String model, String displayName, int maxFuel, int maxTrunks, int carType, int maxDoors, int carCorp, int fastCar) {
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.effectId = effectId;
        this.price = price;
        this.model = model;
        this.displayName = displayName;
        this.maxFuel = maxFuel;
        this.maxTrunks = maxTrunks;
        this.carType = carType;
        this.maxDoors = maxDoors;
        this.carCorp = carCorp;
        this.fastCar = fastCar;
    }

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getEffectId() {
        return effectId;
    }

    public int getPrice() {
        return price;
    }

    public String getModel() {
        return model;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMaxFuel() {
        return maxFuel;
    }

    public int getMaxTrunks() {
        return maxTrunks;
    }

    public int getCarType() {
        return carType;
    }

    public int getMaxDoors() {
        return maxDoors;
    }

    public int getCarCorp() {
        return carCorp;
    }

    public int getFastCar() {
        return fastCar;
    }
}
