package com.eu.habbo.habbohotel.habboroleplay.turfs;

import com.eu.habbo.habbohotel.users.HabboItem;
import java.util.ArrayList;
import java.util.List;

public class Turf {
    private HabboItem flag;
    private int roomId;
    private int gangId;
    private int flagX;
    private int flagY;
    private boolean flagSpawned;

    public Turf(int roomId, int gangId) {
        this.roomId = roomId;
        this.gangId = gangId;
        this.flagSpawned = false;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getGangId() {
        return gangId;
    }

    public void setGangId(int gangId) {
        this.gangId = gangId;
    }

    public int getFlagX() {
        return flagX;
    }

    public void setFlagX(int flagX) {
        this.flagX = flagX;
    }

    public int getFlagY() {
        return flagY;
    }

    public void setFlagY(int flagY) {
        this.flagY = flagY;
    }

    public HabboItem getFlag() {
        return flag;
    }

    public void setFlag(HabboItem flag) {
        this.flag = flag;
    }

    public boolean isFlagSpawned() {
        return flagSpawned;
    }

    public void setFlagSpawned(boolean flagSpawned) {
        this.flagSpawned = flagSpawned;
    }
}
