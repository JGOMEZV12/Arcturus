package com.eu.habbo.habbohotel.habboroleplay.cooldowns.types;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.cooldowns.Cooldown;

public class GunCooldown extends Cooldown {
    public GunCooldown(String type, GameClient client, int time, int amount) {
        super(type, client, time, amount);
        this.timeLeft = 125;
    }

    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null) {
            endCooldown();
            return;
        }

        timeLeft -= 125;
    }
}
