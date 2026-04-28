package com.eu.habbo.habbohotel.commands.habboroleplay;
import com.eu.habbo.habbohotel.users.Habbo;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.phones.chat.PhoneChat;
import com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class SmsCommand extends Command {
    public SmsCommand() {
        super("command_sms", new String[]{"sms"});
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        RoleplayUser rpUser = gameClient.getHabbo().getHabboRoleplay();
        if (rpUser == null) return false;

        if (rpUser.getPhone() == 0) {
            gameClient.getHabbo().whisper("No tienes ningún teléfono comprado.", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        if (params.length < 3) {
            gameClient.getHabbo().whisper("Uso: :sms [número/contacto] [mensaje]", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        String target = params[1];
        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 2; i < params.length; i++) {
            messageBuilder.append(params[i]).append(" ");
        }
        String message = messageBuilder.toString().trim();

        // Target resolution logic
        int targetId = 0;
        // Search by username first
        Habbo targetHabbo = Emulator.getGameServer().getGameClientManager().getHabbo(params[1]);
        if (targetHabbo == null) { gameClient.getHabbo().whisper("Usuario no encontrado."); return true; }
        GameClient targetClient = targetHabbo.getClient();
        if (targetClient != null) {
            targetId = targetClient.getHabbo().getHabboInfo().getId();
        } else {
            // Search by phone number in GameClientManager mapping
            targetId = Emulator.getGameServer().getGameClientManager().getUserIdByPhoneNumber(target);
        }

        if (targetId <= 0) {
            gameClient.getHabbo().whisper("Destinatario no encontrado o número inválido.", RoomChatMessageBubbles.getBubble(1));
            return true;
        }

        // Save to database
        long timestamp = System.currentTimeMillis() / 1000L;
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO rp_phones_chat (type, sender_id, sender_name, receiver_id, receiver_name, message, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, 1);
            statement.setInt(2, gameClient.getHabbo().getHabboInfo().getId());
            statement.setString(3, gameClient.getHabbo().getHabboInfo().getUsername());
            statement.setInt(4, targetId);
            statement.setString(5, target); // Ideally get actual target name
            statement.setString(6, message);
            statement.setLong(7, timestamp);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    PhoneChat chat = new PhoneChat(id, 1, gameClient.getHabbo().getHabboInfo().getId(), gameClient.getHabbo().getHabboInfo().getUsername(), targetId, target, message, new Date(timestamp * 1000L));
                    // Optionally add to PhoneChatManager.chatList
                }
            }
        }

        gameClient.getHabbo().shout("*Ha enviado un Mensaje de Texto*", RoomChatMessageBubbles.getBubble(5));

        if (targetClient != null) {
            targetClient.getHabbo().whisper("*Recibes un Mensaje de Texto*", RoomChatMessageBubbles.getBubble(1));
            // Trigger WebSocket update if online
            Emulator.getGameEnvironment().getHabboRoleplayManager().getWebEventManager().executeWebEvent(targetClient, "event_phone", "open_chatrooms");
        }

        return true;
    }
}
