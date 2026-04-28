package com.eu.habbo.habbohotel.commands.habboroleplay;
import com.eu.habbo.habbohotel.users.Habbo;

import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.groups.RoleplayGroup;
import com.eu.habbo.habbohotel.habboroleplay.groups.RoleplayGroupManager;
import com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser;
import com.eu.habbo.habbohotel.habboroleplay.roleplayusers.offers.RoleplayOffer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

public class AcceptOfferCommand extends Command {
    public AcceptOfferCommand() {
        super("command_offers_accept", new String[]{"aceptar"});
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        RoleplayUser rpUser = gameClient.getHabbo().getHabboRoleplay();
        if (rpUser == null) return false;

        if (params.length < 2) {
            gameClient.getHabbo().whisper("Especifica el tipo de oferta a aceptar.", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        String type = params[1].toLowerCase();
        RoleplayOffer offer = rpUser.getOfferManager().getActiveOffers().get(type);

        if (offer == null) {
            gameClient.getHabbo().whisper("No tienes ninguna oferta de tipo '" + type + "' activa.", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        if (type.equals("pandilla")) {
            RoleplayGroup gang = RoleplayGroupManager.getGang(offer.getCost());
            if (gang != null) {
                if (gameClient.getHabbo().getHabboInfo().getCredits() < 2000) {
                    gameClient.getHabbo().whisper("¡No tienes 2.000$ para unirte!", RoomChatMessageBubbles.getBubble(1));
                    return true;
                }

                gameClient.getHabbo().giveCredits(-2000);
                rpUser.setJobId(gang.getId());
                rpUser.setJobRank(1);

                gameClient.getHabbo().shout("*Acepta la oferta de pandilla y se une a " + gang.getName() + "*", RoomChatMessageBubbles.getBubble(4));
                rpUser.getOfferManager().getActiveOffers().remove(type);
            }
        }
        // Handle other types...

        return true;
    }
}
