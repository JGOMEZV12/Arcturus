package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class ProbationTimer extends RoleplayTimer {
    public ProbationTimer(GameClient client, int timeInMinutes) {
        super("probation", client, 1000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            client.getRoleplay().setOnProbation(false);
            client.getRoleplay().setProbationTimeLeft(0);
            client.getHabbo().shout("*Completa su tiempo de prueba y se retira de la libertad condicional*", RoomChatMessageBubbles.getBubble(4));
            endTimer();
        }
    }
}