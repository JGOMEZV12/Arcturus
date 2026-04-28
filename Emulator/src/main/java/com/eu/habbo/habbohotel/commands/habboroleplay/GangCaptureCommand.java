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
import com.eu.habbo.habbohotel.users.HabboItem;

public class GangCaptureCommand extends Command {
    public GangCaptureCommand() {
        super("command_gang_capture", new String[]{"capturar"});
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        RoleplayUser rpUser = gameClient.getHabbo().getHabboRoleplay();
        if (rpUser == null) return false;

        if (rpUser.getJobId() == 1000) {
            gameClient.getHabbo().whisper("¡No perteneces a ninguna Pandilla!", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();
        if (room == null || !room.isTurfEnabled()) {
            gameClient.getHabbo().whisper("Este no es un territorio capturable.", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        if (room.isTurfCapturing()) {
            gameClient.getHabbo().whisper("¡El territorio ya está siendo capturado!", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        RoleplayGroup gang = RoleplayGroupManager.getGang(rpUser.getJobId());
        if (gang == null) return true;

        if (gang.isBankRuptcy()) {
            gameClient.getHabbo().whisper("¡Tu banda está en bancarrota!", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        // Logic for checking near flag (army_c15_groupflag)
        boolean nearFlag = false;
        for (HabboItem item : room.getFloorItems()) {
            if (item.getBaseItem().getName().equalsIgnoreCase("army_c15_groupflag")) {
                if (Math.abs(item.getX() - gameClient.getHabbo().getRoomUnit().getX()) <= 1 && Math.abs(item.getY() - gameClient.getHabbo().getRoomUnit().getY()) <= 1) {
                    nearFlag = true;
                    break;
                }
            }
        }

        if (!nearFlag) {
            gameClient.getHabbo().whisper("¡Debes estar cerca de la bandera de territorio!", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        gameClient.getHabbo().shout("*Empieza a apoderarse del territorio para " + gang.getName() + " [-10 Energia]*", RoomChatMessageBubbles.getBubble(4));
        rpUser.setCurEnergy(rpUser.getCurEnergy() - 10);

        rpUser.setTurfCapturing(true);
        room.setTurfCapturing(true);
        room.setTurfUserAttackerId(gameClient.getHabbo().getHabboInfo().getId());

        rpUser.getTimerManager().createTimer("turfcapture", 1000, true);

        return true;
    }
}
