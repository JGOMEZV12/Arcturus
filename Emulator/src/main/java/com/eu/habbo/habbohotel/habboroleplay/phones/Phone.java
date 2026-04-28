package com.eu.habbo.habbohotel.habboroleplay.phones;

public class Phone {
    private int id;
    private String modelName;
    private String displayName;
    private int price;
    private int effectId;
    private int screenSlots;
    private int dockSlots;

    public Phone(int id, String modelName, String displayName, int price, int effectId, int screenSlots, int dockSlots) {
        this.id = id;
        this.modelName = modelName;
        this.displayName = displayName;
        this.price = price;
        this.effectId = effectId;
        this.screenSlots = screenSlots;
        this.dockSlots = dockSlots;
    }

    public int getId() {
        return id;
    }

    public String getModelName() {
        return modelName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPrice() {
        return price;
    }

    public int getEffectId() {
        return effectId;
    }

    public int getScreenSlots() {
        return screenSlots;
    }

    public int getDockSlots() {
        return dockSlots;
    }
}
