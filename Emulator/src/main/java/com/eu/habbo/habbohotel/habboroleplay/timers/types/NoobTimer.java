package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class NoobTimer extends RoleplayTimer {
    public NoobTimer(GameClient client, int timeInMinutes) {
        super("noob", client, 1000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            client.getRoleplay().setNoob(false);
            client.getHabbo().whisper("¡La inmunidad se ha terminado, defiende tu mismo de las amenazas de la ciudad!", RoomChatMessageBubbles.getBubble(0));
            endTimer();
        }
    }
}