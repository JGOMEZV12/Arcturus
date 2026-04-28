package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class InmunityTimer extends RoleplayTimer {
    public InmunityTimer(GameClient client, int timeInSeconds) {
        super("inmunity", client, 1000, true, null);
        this.timeLeft = timeInSeconds * 1000;
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            client.getHabbo().whisper("*¡Tu inmunidad ha terminado!*", RoomChatMessageBubbles.getBubble(0));
            endTimer();
        }
    }
}