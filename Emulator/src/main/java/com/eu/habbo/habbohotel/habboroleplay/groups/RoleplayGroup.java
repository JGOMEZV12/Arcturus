package com.eu.habbo.habbohotel.habboroleplay.groups;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RoleplayGroup {
    private int id;
    private String name;
    private String description;
    private String badge;
    private int roomId;
    private int creatorId;
    private int balance;
    private int stock;
    private boolean isGang;
    private boolean bankRuptcy;

    private final ConcurrentHashMap<Integer, RoleplayGroupRank> ranks = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, RoleplayGroupMember> members = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, RoleplayGroupLog> logs = new ConcurrentHashMap<>();

    public RoleplayGroup(int id, String name, String description, String badge, int roomId, int creatorId, int balance, int stock, boolean isGang, boolean bankRuptcy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.badge = badge;
        this.roomId = roomId;
        this.creatorId = creatorId;
        this.balance = balance;
        this.stock = stock;
        this.isGang = isGang;
        this.bankRuptcy = bankRuptcy;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBadge() {
        return badge;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isGang() {
        return isGang;
    }

    public boolean isBankRuptcy() {
        return bankRuptcy;
    }

    public void setBankRuptcy(boolean bankRuptcy) {
        this.bankRuptcy = bankRuptcy;
    }

    public ConcurrentHashMap<Integer, RoleplayGroupRank> getRanks() {
        return ranks;
    }

    public ConcurrentHashMap<Integer, RoleplayGroupMember> getMembers() {
        return members;
    }

    public ConcurrentHashMap<Integer, RoleplayGroupLog> getLogs() {
        return logs;
    }
}
