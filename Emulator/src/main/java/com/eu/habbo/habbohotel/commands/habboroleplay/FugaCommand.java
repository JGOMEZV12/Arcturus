package com.eu.habbo.habbohotel.commands.habboroleplay;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser;
import com.eu.habbo.habbohotel.habboroleplay.misc.RoleplayManager;

public class FugaCommand extends Command {
    public FugaCommand() {
        super("command_fuga", new String[]{"fuga"});
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (params.length != 2) {
            gameClient.getHabbo().whisper("Uso correcto: :fuga <usuario>");
            return true;
        }

        GameClient targetClient = Emulator.getGameEnvironment().getHabboManager().getHabbo(params[1]).getClient();
        if (targetClient == null || targetClient.getRoleplay() == null) {
            gameClient.getHabbo().whisper("El usuario no está en línea.");
            return true;
        }

        if (!targetClient.getRoleplay().isJailed()) {
            gameClient.getHabbo().whisper("¡Ese usuario no está encarcelado!");
            return true;
        }

        gameClient.getHabbo().shout("*Se acerca sigilosamente y le susurra una propuesta de fuga a " + targetClient.getHabbo().getHabboInfo().getUsername() + "*", RoomChatMessageBubbles.getBubble(5));
        targetClient.getHabbo().whisper(gameClient.getHabbo().getHabboInfo().getUsername() + " te ofrece ayudarte a fugarte. Escribe ':aceptar fuga' para aceptar.");

        return true;
    }
}
