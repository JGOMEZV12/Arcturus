package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class WantedTimer extends RoleplayTimer {
    public WantedTimer(GameClient client, int timeInMinutes) {
        super("wanted", client, 1000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            client.getRoleplay().setWanted(false);
            client.getRoleplay().setWantedLevel(0);
            client.getRoleplay().setWantedTimeLeft(0);
            client.getHabbo().shout("*Finalmente evade a la policía después de huir*", RoomChatMessageBubbles.getBubble(4));
            endTimer();
        }
    }
}