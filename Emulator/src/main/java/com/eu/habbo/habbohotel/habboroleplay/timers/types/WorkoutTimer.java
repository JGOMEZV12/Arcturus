package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class WorkoutTimer extends RoleplayTimer {
    public WorkoutTimer(GameClient client, int itemId, boolean strength) {
        super("workout", client, 1000, true, new Object[]{itemId, strength});
        this.timeLeft = 80000;
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            client.getHabbo().whisper("Has terminado tu entrenamiento.", RoomChatMessageBubbles.getBubble(0));
            client.getRoleplay().setWorkingOut(false);
            endTimer();
        }
    }
}