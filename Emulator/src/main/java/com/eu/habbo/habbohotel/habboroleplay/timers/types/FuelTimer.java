package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class FuelTimer extends RoleplayTimer {
    public FuelTimer(GameClient client, int timeInSeconds) {
        super("fuel", client, 1000, true, null);
        this.timeLeft = timeInSeconds * 1000;
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            int price = client.getRoleplay().getFuelChargingCant() * 2;
            client.getHabbo().whisper("Combustible Cargado! -$" + price, RoomChatMessageBubbles.getBubble(0));
            client.getHabbo().getHabboInfo().addCredits(-price);
            client.getRoleplay().setFuelCharging(false);
            endTimer();
        }
    }
}