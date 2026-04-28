package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
public class SidaTimer extends RoleplayTimer {
    public SidaTimer(GameClient client) {
        super("sida", client, 60000, true, null);
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        int sida = client.getRoleplay().getSida();
        if (sida > 0 && sida < 100) client.getRoleplay().setSida(sida + 1);
    }
}