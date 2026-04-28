package com.eu.habbo.habbohotel.habboroleplay.misc;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.Room;
public class RoleplayManager {
    public static void shout(GameClient client, String message, int bubbleId) {
        if (client != null && client.getHabbo() != null) client.getHabbo().shout(message, RoomChatMessageBubbles.getBubble(bubbleId));
    }
    public static void giveMoney(GameClient client, int amount) {
        if (client != null && client.getHabbo() != null) client.getHabbo().giveCredits(amount);
    }
    public static double getDistanceBetweenPoints2D(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    public static boolean generateRoom(int id, Room[] room) { return false; }
    public static void sendUser(GameClient c, int id, String m) {}
    public static void takeMoneyFromCompany(int id, int m) {}
}
