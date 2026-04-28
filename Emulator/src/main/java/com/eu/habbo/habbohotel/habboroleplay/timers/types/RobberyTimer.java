package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class RobberyTimer extends RoleplayTimer {
    public RobberyTimer(GameClient client, int timeInMinutes) {
        super("robbery", client, 60000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        timeLeft -= 60000;
        if (timeLeft <= 0) {
            client.getHabbo().shout("*Terminó su robo*", RoomChatMessageBubbles.getBubble(4));
            endTimer();
        }
    }
}