package com.eu.habbo.habbohotel.commands.habboroleplay;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser;
import com.eu.habbo.habbohotel.habboroleplay.misc.RoleplayManager;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;

public class RobCommand extends Command {
    public RobCommand() {
        super("command_criminal_activity_rob", new String[]{"robar"});
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        RoleplayUser rpUser = gameClient.getRoleplay();
        if (rpUser == null) return false;

        if (params.length < 2) {
            gameClient.getHabbo().whisper("¿A quién quieres robar? :robar <usuario>");
            return true;
        }

        GameClient targetClient = Emulator.getGameEnvironment().getHabboManager().getHabbo(params[1]).getClient();
        if (targetClient == null || targetClient.getHabbo().getHabboInfo().getCurrentRoom() != gameClient.getHabbo().getHabboInfo().getCurrentRoom()) {
            gameClient.getHabbo().whisper("El usuario no está en esta sala.");
            return true;
        }

        RoleplayUser targetRp = targetClient.getRoleplay();
        if (targetRp == null) return true;

        double distance = RoleplayManager.getDistanceBetweenPoints2D(gameClient.getHabbo().getRoomUnit().getX(), gameClient.getHabbo().getRoomUnit().getY(), targetClient.getHabbo().getRoomUnit().getX(), targetClient.getHabbo().getRoomUnit().getY());

        if (distance > 1) {
            gameClient.getHabbo().whisper("¡Debes estar más cerca del usuario!");
            return true;
        }

        int amount = 50; // Random logic would go here
        if (targetClient.getHabbo().getHabboInfo().getCredits() < amount) {
            gameClient.getHabbo().whisper("El usuario es muy pobre para robarle.");
            return true;
        }

        targetClient.getHabbo().getHabboInfo().addCredits(-amount);
        gameClient.getHabbo().getHabboInfo().addCredits(amount);

        gameClient.getHabbo().shout("*Roba $" + amount + " a " + targetClient.getHabbo().getHabboInfo().getUsername() + "*", RoomChatMessageBubbles.getBubble(4));
        targetClient.getHabbo().whisper("¡Te han robado $" + amount + "!");

        rpUser.setWanted(true);
        rpUser.setWantedLevel(2);
        rpUser.setWantedTimeLeft(7);

        return true;
    }
}
