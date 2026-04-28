package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class CuffTimer extends RoleplayTimer {
    public CuffTimer(GameClient client, int timeInMinutes) {
        super("cuff", client, 1000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            client.getRoleplay().setCuffed(false);
            client.getHabbo().shout("*¡Se rompen sus esposas después de luchar por tanto tiempo!*", RoomChatMessageBubbles.getBubble(4));
            endTimer();
        }
    }
}