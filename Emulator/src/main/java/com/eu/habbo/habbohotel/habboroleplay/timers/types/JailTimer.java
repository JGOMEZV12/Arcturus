package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class JailTimer extends RoleplayTimer {
    public JailTimer(GameClient client, int timeInMinutes) {
        super("jail", client, 1000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            client.getRoleplay().setJailed(false);
            client.getRoleplay().setJailedTimeLeft(0);
            client.getHabbo().shout("*Se libera de la cárcel ya que han cumplido su condena*", RoomChatMessageBubbles.getBubble(4));
            endTimer();
        }
    }
}