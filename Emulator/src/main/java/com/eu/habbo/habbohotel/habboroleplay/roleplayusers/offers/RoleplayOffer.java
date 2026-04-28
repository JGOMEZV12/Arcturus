package com.eu.habbo.habbohotel.habboroleplay.roleplayusers.offers;

public class RoleplayOffer {
    private String type;
    private int offererId;
    private int cost;
    private Object[] params;

    public RoleplayOffer(String type, int offererId, int cost, Object[] params) {
        this.type = type;
        this.offererId = offererId;
        this.cost = cost;
        this.params = params;
    }

    public String getType() {
        return type;
    }

    public int getOffererId() {
        return offererId;
    }

    public int getCost() {
        return cost;
    }

    public Object[] getParams() {
        return params;
    }
}
