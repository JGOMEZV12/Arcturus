package com.eu.habbo.habbohotel.commands.habboroleplay;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser;
import com.eu.habbo.habbohotel.habboroleplay.misc.RoleplayManager;

public class StartWorkCommand extends Command {
    public StartWorkCommand() {
        super("command_corp_work_start", new String[]{"trabajar"});
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        RoleplayUser rpUser = gameClient.getRoleplay();
        if (rpUser == null) return false;

        if (rpUser.isWorking()) {
            gameClient.getHabbo().whisper("¡Usted ya está trabajando!");
            return true;
        }

        if (rpUser.getJobId() <= 1) {
            gameClient.getHabbo().whisper("¡No tienes empleo! Busca uno primero.");
            return true;
        }

        rpUser.setWorking(true);
        gameClient.getHabbo().shout("*Comienza a trabajar como " + rpUser.getJobId() + "*", RoomChatMessageBubbles.getBubble(4));
        rpUser.getTimerManager().createTimer("work", 1000, true);

        return true;
    }
}
