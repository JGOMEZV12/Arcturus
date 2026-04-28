package com.eu.habbo.habbohotel.habboroleplay.comodin;

public class Comodin {
    private int id;
    private int furniId;
    private int roomId;
    private String action;

    public Comodin(int id, int furniId, int roomId, String action) {
        this.id = id;
        this.furniId = furniId;
        this.roomId = roomId;
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public int getFurniId() {
        return furniId;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getAction() {
        return action;
    }
}
