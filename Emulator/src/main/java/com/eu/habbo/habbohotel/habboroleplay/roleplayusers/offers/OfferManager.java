package com.eu.habbo.habbohotel.habboroleplay.roleplayusers.offers;

import com.eu.habbo.habbohotel.gameclients.GameClient;

import java.util.concurrent.ConcurrentHashMap;

public class OfferManager {
    private final GameClient client;
    private final ConcurrentHashMap<String, RoleplayOffer> activeOffers;

    public OfferManager(GameClient client) {
        this.client = client;
        this.activeOffers = new ConcurrentHashMap<>();
    }

    public void createOffer(String type, int offererId, int cost, Object... params) {
        if (activeOffers.containsKey(type)) return;

        RoleplayOffer offer = new RoleplayOffer(type, offererId, cost, params);
        activeOffers.put(type, offer);
    }

    public ConcurrentHashMap<String, RoleplayOffer> getActiveOffers() {
        return activeOffers;
    }

    public void endAllOffers() {
        activeOffers.clear();
    }
}
