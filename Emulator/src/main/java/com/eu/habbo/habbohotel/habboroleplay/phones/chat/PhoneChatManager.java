package com.eu.habbo.habbohotel.habboroleplay.phones.chat;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PhoneChatManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneChatManager.class);
    public static final ConcurrentHashMap<Integer, PhoneChat> chatList = new ConcurrentHashMap<>();

    public void initialize() {
        chatList.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_phones_chat` ORDER BY timestamp DESC LIMIT 500")) {

            while (row.next()) {
                int id = row.getInt("id");
                int type = row.getInt("type");
                int senderId = row.getInt("sender_id");
                String senderName = row.getString("sender_name");
                int receiverId = row.getInt("receiver_id");
                String receiverName = row.getString("receiver_name");
                String message = row.getString("message");
                long timestamp = row.getLong("timestamp");

                PhoneChat chat = new PhoneChat(id, type, senderId, senderName, receiverId, receiverName, message, new Date(timestamp * 1000L));
                chatList.put(id, chat);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading PhoneChatManager", e);
        }
        LOGGER.info("PhoneChatManager -> Loaded {} chat messages.", chatList.size());
    }

    public List<PhoneChat> getPhoneChats(int myId, int type) {
        return chatList.values().stream()
                .filter(c -> (c.getSenderId() == myId || c.getReceiverId() == myId) && c.getType() == type)
                .collect(Collectors.toList());
    }

    public List<PhoneChat> getWhatsAppHistory(int myId, int targetId) {
        return chatList.values().stream()
                .filter(c -> ((c.getSenderId() == myId && c.getReceiverId() == targetId) || (c.getSenderId() == targetId && c.getReceiverId() == myId)) && c.getType() == 2)
                .sorted((c1, c2) -> c1.getTimestamp().compareTo(c2.getTimestamp()))
                .collect(Collectors.toList());
    }
}
