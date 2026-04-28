package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class StunTimer extends RoleplayTimer {
    public StunTimer(GameClient client, int timeInSeconds) {
        super("stun", client, 1000, true, null);
        this.timeLeft = timeInSeconds * 1000;
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            client.getRoleplay().setStunned(false);
            client.getHabbo().whisper("¡Su cuerpo deja de sentirse entumecido, los efectos del aturdimiento se han agotado!", RoomChatMessageBubbles.getBubble(0));
            endTimer();
        }
    }
}