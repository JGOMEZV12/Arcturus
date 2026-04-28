package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class VehicleJobTimer extends RoleplayTimer {
    public VehicleJobTimer(GameClient client, int timeInSeconds) {
        super("vehicle_job", client, 1000, true, null);
        this.timeLeft = timeInSeconds * 1000;
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            client.getHabbo().whisper("¡Tu Vehículo de trabajo ha sido regresado a su sitio por haberlo abandonado mucho tiempo!", RoomChatMessageBubbles.getBubble(0));
            endTimer();
        }
    }
}