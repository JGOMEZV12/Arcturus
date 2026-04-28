package com.eu.habbo.habbohotel.habboroleplay.timers.types;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;

public class PoopTimer extends RoleplayTimer {
    public PoopTimer(String type, GameClient client, int time, boolean forever, Object[] params) {
        super(type, client, time, forever, params);
    }

    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null || client.getRoleplay() == null) {
            endTimer();
            return;
        }

        timeCount++;
        if (timeCount < 200) return;

        timeCount = 0;

        int currentPoop = client.getRoleplay().getPoop();
        if (currentPoop <= 0) {
            client.sendResponse(new BubbleAlertComposer("bathroom_toilet3_icon", "¡Te cagaste los pantalones y hueles a mierda! usa un toilet de inmediato.").compose());
            return;
        }

        int amount = 5;
        client.getRoleplay().setPoop(Math.max(0, currentPoop - amount));
    }
}
