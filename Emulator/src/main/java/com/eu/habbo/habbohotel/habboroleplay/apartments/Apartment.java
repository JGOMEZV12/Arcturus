package com.eu.habbo.habbohotel.habboroleplay.apartments;

public class Apartment {
    private int id;
    private String modelName;
    private int tiles;
    private String image;
    private int price;

    public Apartment(int id, String modelName, int tiles, String image, int price) {
        this.id = id;
        this.modelName = modelName;
        this.tiles = tiles;
        this.image = image;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getModelName() {
        return modelName;
    }

    public int getTiles() {
        return tiles;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
