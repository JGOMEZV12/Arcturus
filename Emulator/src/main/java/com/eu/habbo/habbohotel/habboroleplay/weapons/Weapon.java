package com.eu.habbo.habbohotel.habboroleplay.weapons;

public class Weapon {
    private int id;
    private String name;
    private String publicName;
    private String firingText;
    private String equipText;
    private String unEquipText;
    private String reloadText;
    private int energy;
    private int effectId;
    private int handItem;
    private int range;
    private int minDamage;
    private int maxDamage;
    private int clipSize;
    private int reloadTime;
    private int cost;
    private int costFine;
    private int stock;
    private int levelRequirement;
    private boolean isLoaded;
    private int currentClip;
    private int life;
    private boolean isVip;
    private int lastShot;
    private WeaponCategory category;

    public Weapon(int id, String name, String publicName, String firingText, String equipText, String unEquipText, String reloadText, int energy, int effectId, int handItem, int range, int minDamage, int maxDamage, int clipSize, int reloadTime, int cost, int costFine, int stock, int levelRequirement, boolean isLoaded, int currentClip, int life, boolean isVip, int lastShot, WeaponCategory category) {
        this.id = id;
        this.name = name;
        this.publicName = publicName;
        this.firingText = firingText;
        this.equipText = equipText;
        this.unEquipText = unEquipText;
        this.reloadText = reloadText;
        this.energy = energy;
        this.effectId = effectId;
        this.handItem = handItem;
        this.range = range;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.clipSize = clipSize;
        this.reloadTime = reloadTime;
        this.cost = cost;
        this.costFine = costFine;
        this.stock = stock;
        this.levelRequirement = levelRequirement;
        this.isLoaded = isLoaded;
        this.currentClip = currentClip;
        this.life = life;
        this.isVip = isVip;
        this.lastShot = lastShot;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPublicName() { return publicName; }
    public String getFiringText() { return firingText; }
    public String getEquipText() { return equipText; }
    public String getUnEquipText() { return unEquipText; }
    public String getReloadText() { return reloadText; }
    public int getEnergy() { return energy; }
    public int getEffectId() { return effectId; }
    public int getHandItem() { return handItem; }
    public int getRange() { return range; }
    public int getMinDamage() { return minDamage; }
    public int getMaxDamage() { return maxDamage; }
    public int getClipSize() { return clipSize; }
    public int getReloadTime() { return reloadTime; }
    public int getCost() { return cost; }
    public int getCostFine() { return costFine; }
    public int getStock() { return stock; }
    public int getLevelRequirement() { return levelRequirement; }
    public boolean isLoaded() { return isLoaded; }
    public void setLoaded(boolean loaded) { isLoaded = loaded; }
    public int getCurrentClip() { return currentClip; }
    public void setCurrentClip(int currentClip) { this.currentClip = currentClip; }
    public int getLife() { return life; }
    public void setLife(int life) { this.life = life; }
    public boolean isVip() { return isVip; }
    public int getLastShot() { return lastShot; }
    public void setLastShot(int lastShot) { this.lastShot = lastShot; }
    public WeaponCategory getCategory() { return category; }
}
