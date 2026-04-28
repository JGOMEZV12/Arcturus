package com.eu.habbo.habbohotel.habboroleplay.timers.types;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;

public class HygieneTimer extends RoleplayTimer {
    public HygieneTimer(String type, GameClient client, int time, boolean forever, Object[] params) {
        super(type, client, time, forever, params);
    }

    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null || client.getRoleplay() == null) {
            endTimer();
            return;
        }

        timeCount++;
        // Using a default of 500 for now, should ideally come from RoleplayData
        if (timeCount < 500) return;

        timeCount = 0;

        int currentHygiene = client.getRoleplay().getHygiene();
        if (currentHygiene <= 0) {
            client.getRoleplay().setCurHealth(client.getRoleplay().getCurHealth() - 5);
            client.sendResponse(new BubbleAlertComposer("bathroom_shower1_icon", "¡Eres apestoso! Visite el gimnasio para obtener una ducha y rellenar su higiene.").compose());
            return;
        }

        int amount = 5; // Default decrease
        client.getRoleplay().setHygiene(Math.max(0, currentHygiene - amount));

        if (client.getRoleplay().getHygiene() <= 0) {
            client.sendResponse(new BubbleAlertComposer("bathroom_shower1_icon", "¡Te estás volviendo loco! Visite el gimnasio o vaya a su casa para obtener una ducha.").compose());
        }
    }
}
