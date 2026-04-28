package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
public class AnimoTimer extends RoleplayTimer {
    public AnimoTimer(GameClient client) {
        super("animo", client, 60000, true, null);
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        int animo = client.getRoleplay().getAnimo();
        if (animo > 0) client.getRoleplay().setAnimo(animo - 1);
    }
}