package com.eu.habbo.habbohotel.habboroleplay.phones.owned;

public class PhoneOwned {
    private int id;
    private int phoneId;
    private int ownerId;
    private String phoneNumber;

    public PhoneOwned(int id, int phoneId, int ownerId, String phoneNumber) {
        this.id = id;
        this.phoneId = phoneId;
        this.ownerId = ownerId;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
