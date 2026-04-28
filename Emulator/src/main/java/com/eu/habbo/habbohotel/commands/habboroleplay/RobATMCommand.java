package com.eu.habbo.habbohotel.commands.habboroleplay;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.misc.RoleplayManager;
import com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;

public class RobATMCommand extends Command {
    public RobATMCommand() {
        super("command_criminal_activity_rob", new String[]{"robarcajero"});
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        RoleplayUser rpUser = gameClient.getRoleplay();
        if (rpUser == null) return false;

        if (rpUser.getLevel() < 3) {
            gameClient.getHabbo().whisper("¡Tienes que ser minimo nivel 3 para robar el cajero!");
            return true;
        }

        if (rpUser.isDead() || rpUser.isJailed()) {
            gameClient.getHabbo().whisper("¡No puedes robar mientras estás muerto o en la cárcel!");
            return true;
        }

        if (!rpUser.nearItem("atm_moneymachine", 1)) {
            gameClient.getHabbo().whisper("¡No estás cerca de un cajero!");
            return true;
        }

        Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();
        if (room == null || (!room.isRobEnabled())) {
            gameClient.getHabbo().whisper("No se puede robar en esta habitación");
            return true;
        }

        rpUser.setWanted(true);
        rpUser.setWantedLevel(1);
        rpUser.setWantedTimeLeft(5);
        rpUser.setWantedFor(rpUser.getWantedFor() + "robbing, ");

        gameClient.getHabbo().shout("*Saca su palanca y empieza a golpear el cajero*", RoomChatMessageBubbles.getBubble(8));
        gameClient.getHabbo().whisper("Debes esperar un momento...");

        rpUser.getTimerManager().createTimer("atmrob", 1000, true);

        return true;
    }
}
