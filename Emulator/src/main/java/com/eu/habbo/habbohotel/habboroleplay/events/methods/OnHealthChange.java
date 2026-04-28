package com.eu.habbo.habbohotel.habboroleplay.events.methods;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.events.IEvent;

public class OnHealthChange implements IEvent {
    @Override
    public void execute(Object source, Object[] params) {
        GameClient client = (GameClient) source;
        if (client == null || client.getRoleplay() == null) return;

        if (client.getRoleplay().getCurHealth() <= 0 && !client.getRoleplay().isDead()) {
            client.getRoleplay().setDead(true);
            client.getHabbo().whisper("*Sus signos vitales ya ni se sienten y es trasladado al hospital*");
            // Hospital teleport logic
        }
    }
}
