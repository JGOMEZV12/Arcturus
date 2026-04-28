package com.eu.habbo.habbohotel.habboroleplay.phones.apps;

public class PhoneApp {
    private int id;
    private String name;
    private String displayName;
    private String icon;
    private String developerName;
    private String code;
    private int price;
    private String version;

    public PhoneApp(int id, String name, String displayName, String icon, String developerName, String code, int price, String version) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.icon = icon;
        this.developerName = developerName;
        this.code = code;
        this.price = price;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return icon;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public String getCode() {
        return code;
    }

    public int getPrice() {
        return price;
    }

    public String getVersion() {
        return version;
    }
}
