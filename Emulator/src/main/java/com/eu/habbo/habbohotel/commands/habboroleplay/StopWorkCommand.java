package com.eu.habbo.habbohotel.commands.habboroleplay;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser;
import com.eu.habbo.habbohotel.habboroleplay.misc.RoleplayManager;

public class StopWorkCommand extends Command {
    public StopWorkCommand() {
        super("command_corp_work_stop", new String[]{"notrabajar"});
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        RoleplayUser rpUser = gameClient.getRoleplay();
        if (rpUser == null) return false;

        if (!rpUser.isWorking()) {
            gameClient.getHabbo().whisper("¡No estás trabajando!");
            return true;
        }

        rpUser.setWorking(false);
        gameClient.getHabbo().shout("*Termina de trabajar*", RoomChatMessageBubbles.getBubble(4));
        rpUser.getTimerManager().stopTimer("work");

        return true;
    }
}
