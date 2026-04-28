package com.eu.habbo.habbohotel.habboroleplay.timers.types;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;

public class HungerTimer extends RoleplayTimer {
    public HungerTimer(String type, GameClient client, int time, boolean forever, Object[] params) {
        super(type, client, time, forever, params);
    }

    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null || client.getRoleplay() == null) {
            endTimer();
            return;
        }

        timeCount++;
        if (timeCount < 150) return;

        client.getRoleplay().setHunger(client.getRoleplay().getHunger() + 5);
        timeCount = 0;

        if (client.getRoleplay().getHunger() >= 100) {
            client.getRoleplay().setHunger(100);
            client.getRoleplay().setCurHealth(client.getRoleplay().getCurHealth() - 5);
            client.getHabbo().whisper("¡Estás hambriento! Visite el restaurante para obtener algo de comida.");
        }
    }
}
