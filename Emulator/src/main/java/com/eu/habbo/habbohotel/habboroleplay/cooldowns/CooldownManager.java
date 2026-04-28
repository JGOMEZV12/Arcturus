package com.eu.habbo.habbohotel.habboroleplay.cooldowns;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.cooldowns.types.GunCooldown;

import java.util.concurrent.ConcurrentHashMap;

public class CooldownManager {
    protected final GameClient client;
    public final ConcurrentHashMap<String, Cooldown> activeCooldowns = new ConcurrentHashMap<>();

    public CooldownManager(GameClient client) {
        this.client = client;
    }

    public void createCooldown(String type, int time, int amount) {
        if (activeCooldowns.containsKey(type)) return;

        Cooldown cooldown = getCooldownFromType(type, time, amount);
        if (cooldown != null) {
            activeCooldowns.put(type, cooldown);
        }
    }

    private Cooldown getCooldownFromType(String type, int time, int amount) {
        if (type.equalsIgnoreCase("gun")) {
            return new GunCooldown(type, client, time, amount);
        }
        // Add more as needed
        return null;
    }

    public void endAllCooldowns() {
        for (Cooldown cooldown : activeCooldowns.values()) {
            cooldown.endCooldown();
        }
        activeCooldowns.clear();
    }
}
