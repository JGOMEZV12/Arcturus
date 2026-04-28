package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class DeathTimer extends RoleplayTimer {
    public DeathTimer(GameClient client, int timeInMinutes) {
        super("death", client, 1000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            client.getRoleplay().setDead(false);
            client.getRoleplay().setDeadTimeLeft(0);
            client.getRoleplay().replenishStats();
            client.getHabbo().shout("*Recupera la conciencia [-1000$] por gastos médicos*", RoomChatMessageBubbles.getBubble(4));
            client.getHabbo().getHabboInfo().addCredits(-1000);
            endTimer();
        }
    }
}