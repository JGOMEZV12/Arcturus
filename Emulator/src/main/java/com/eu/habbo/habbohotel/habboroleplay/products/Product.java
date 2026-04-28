package com.eu.habbo.habbohotel.habboroleplay.products;
public class Product {
    private int id;
    private String name, displayName, type;
    private int price, maxCant;
    private boolean canStack;
    public Product(int id, String name, String displayName, int price, String type, boolean canStack, int maxCant) {
        this.id = id; this.name = name; this.displayName = displayName; this.price = price; this.type = type; this.canStack = canStack; this.maxCant = maxCant;
    }
    public int getId() { return id; }
    public String getProductName() { return name; }
    public String getDisplayName() { return displayName; }
    public int getPrice() { return price; }
    public String getType() { return type; }
    public boolean isCanStack() { return canStack; }
    public int getMaxCant() { return maxCant; }
}
