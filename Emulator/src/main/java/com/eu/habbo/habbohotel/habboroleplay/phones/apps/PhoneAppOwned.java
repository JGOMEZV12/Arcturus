package com.eu.habbo.habbohotel.habboroleplay.phones.apps;

public class PhoneAppOwned {
    private int id;
    private int phoneId;
    private int appId;
    private int screenId;
    private int slotId;
    private String extradata;

    public PhoneAppOwned(int id, int phoneId, int appId, int screenId, int slotId, String extradata) {
        this.id = id;
        this.phoneId = phoneId;
        this.appId = appId;
        this.screenId = screenId;
        this.slotId = slotId;
        this.extradata = extradata;
    }

    public int getId() {
        return id;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public int getAppId() {
        return appId;
    }

    public int getScreenId() {
        return screenId;
    }

    public int getSlotId() {
        return slotId;
    }

    public String getExtradata() {
        return extradata;
    }
}
