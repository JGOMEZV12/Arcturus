package com.eu.habbo.habbohotel.habboroleplay.timers.types;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;

public class HealTimer extends RoleplayTimer {
    public HealTimer(String type, GameClient client, int time, boolean forever, Object[] params) {
        super(type, client, time, forever, params);
        this.timeLeft = 5000;
    }

    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null || client.getRoleplay() == null) {
            endTimer();
            return;
        }

        int curHealth = client.getRoleplay().getCurHealth();
        int maxHealth = client.getRoleplay().getMaxHealth();

        if (curHealth >= maxHealth) {
            client.getRoleplay().setCurHealth(maxHealth);
            client.getHabbo().whisper("Su salud está ahora está llena " + curHealth + "/" + maxHealth + "!");
            endTimer();
            return;
        }

        timeLeft -= 1000;
        if (timeLeft <= 0) {
            int healAmount = 10;
            client.getRoleplay().setCurHealth(Math.min(maxHealth, curHealth + healAmount));
            timeLeft = 5000;
        }
    }
}
