package com.eu.habbo.habbohotel.habboroleplay.timers.types;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;

public class WorkTimer extends RoleplayTimer {
    public WorkTimer(String type, GameClient client, int time, boolean forever, Object[] params) {
        super(type, client, time, forever, params);
        this.timeLeft = 600000; // 10 minutes default shift
    }

    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null || client.getRoleplay() == null) {
            endTimer();
            return;
        }

        timeLeft -= 1000;
        if (timeLeft <= 0) {
            // Pay logic
            client.getHabbo().whisper("¡Has terminado tu turno y has recibido tu sueldo!");
            // Reset timer or end
            timeLeft = 600000;
        }
    }
}
