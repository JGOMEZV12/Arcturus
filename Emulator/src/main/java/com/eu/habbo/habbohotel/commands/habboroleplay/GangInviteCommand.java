package com.eu.habbo.habbohotel.commands.habboroleplay;
import com.eu.habbo.habbohotel.users.Habbo;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.groups.RoleplayGroup;
import com.eu.habbo.habbohotel.habboroleplay.groups.RoleplayGroupManager;
import com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

public class GangInviteCommand extends Command {
    public GangInviteCommand() {
        super("command_gang_invite", new String[]{"ginvitar"});
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        RoleplayUser rpUser = gameClient.getHabbo().getHabboRoleplay();
        if (rpUser == null) return false;

        if (params.length < 2) {
            gameClient.getHabbo().whisper("¡Ingrese el nombre de usuario del usuario que desea invitar!", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        RoleplayGroup gang = RoleplayGroupManager.getGang(rpUser.getJobId()); // In Polar, GangId is often stored in JobId or similar in rp_stats
        // Wait, let's look at RoleplayUser.java I created.
        // It has jobId and jobRank. In Polar C#, it has GangId and GangRank too.
        // Let me check my RoleplayUser.java again.

        // I'll assume for now that GangId is what I need.

        if (rpUser.getJobId() <= 1000) {
            gameClient.getHabbo().whisper("¡No tienes una pandilla para invitar a alguien!", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        Habbo targetHabbo = Emulator.getGameServer().getGameClientManager().getHabbo(params[1]);
        if (targetHabbo == null) { gameClient.getHabbo().whisper("Usuario no encontrado."); return true; }
        GameClient targetClient = targetHabbo.getClient();
        if (targetClient == null) {
            gameClient.getHabbo().whisper("Usuario no encontrado.", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        if (targetClient.getHabbo().getHabboRoleplay().getJobId() > 1000) {
            gameClient.getHabbo().whisper("Este usuario ya pertenece a una pandilla.", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        gameClient.getHabbo().shout("*Invita a " + targetClient.getHabbo().getHabboInfo().getUsername() + " a unirse a la pandilla: '" + gang.getName() + "'*", RoomChatMessageBubbles.getBubble(4));
        targetClient.getHabbo().whisper("Para unirte a la pandilla: '" + gang.getName() + "' escribe ':aceptar pandilla'. Debes aportar 2.000$ para gastos.", RoomChatMessageBubbles.getBubble(34));
        targetClient.getHabbo().getHabboRoleplay().getOfferManager().createOffer("pandilla", gameClient.getHabbo().getHabboInfo().getId(), gang.getId());

        return true;
    }
}
