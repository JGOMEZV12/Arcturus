package com.eu.habbo.habbohotel.roleplay.chat;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.HabboRoleplayManager;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * RoleplayChatManager: Manages roleplay chat messages and actions.
 */
public class RoleplayChatManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayChatManager.class);
    private static final Pattern EMOTE_PATTERN = Pattern.compile("^/(\w+)\\s*(.*)");

    /**
     * Handle chat message
     */
    public static void handleChatMessage(Habbo habbo, String message) {
        if (habbo == null || message == null || message.isEmpty()) return;

        HabboRoleplayManager manager = HabboRoleplayManager.getInstance(habbo);
        if (manager == null || !manager.isActive()) {
            return;
        }

        // Check for emote command (starts with /)
        if (message.startsWith("/")) {
            handleEmote(habbo, message);
        } else {
            broadcastChatMessage(habbo, message);
        }
    }

    /**
     * Broadcast chat message to room
     */
    private static void broadcastChatMessage(Habbo habbo, String message) {
        try {
            // Sanitize message
            String sanitizedMessage = sanitizeMessage(message);

            // Create event data
            JsonObject eventData = new JsonObject();
            eventData.addProperty("username", habbo.getUsername());
            eventData.addProperty("message", sanitizedMessage);
            eventData.addProperty("timestamp", System.currentTimeMillis());

            // Send to all users in room
            if (habbo.getRoomUnit() != null && habbo.getRoomUnit().getRoom() != null) {
                Room room = habbo.getRoomUnit().getRoom();
                room.getHabbos().forEach(h -> {
                    HabboRoleplayManager targetManager = HabboRoleplayManager.getInstance(h);
                    if (targetManager != null && targetManager.isActive()) {
                        targetManager.sendRoleplayEvent("rp.chat.message", eventData.toString());
                    }
                });
            }

            LOGGER.debug("Chat message broadcast from {}: {}", habbo.getUsername(), sanitizedMessage);
        } catch (Exception e) {
            LOGGER.error("Error broadcasting chat message", e);
        }
    }

    /**
     * Handle emote commands
     */
    private static void handleEmote(Habbo habbo, String command) {
        try {
            String[] parts = command.substring(1).split(" ", 2);
            String emoteType = parts[0].toLowerCase();
            String target = parts.length > 1 ? parts[1] : "";

            JsonObject eventData = new JsonObject();
            eventData.addProperty("username", habbo.getUsername());
            eventData.addProperty("emote", emoteType);
            if (!target.isEmpty()) {
                eventData.addProperty("target", target);
            }

            HabboRoleplayManager manager = HabboRoleplayManager.getInstance(habbo);
            if (manager != null) {
                manager.sendRoleplayEvent("rp.chat.emote", eventData.toString());
            }

            LOGGER.debug("Emote executed by {}: {}", habbo.getUsername(), emoteType);
        } catch (Exception e) {
            LOGGER.error("Error handling emote", e);
        }
    }

    /**
     * Sanitize message to prevent spam/abuse
     */
    private static String sanitizeMessage(String message) {
        // Remove excessive whitespace
        message = message.replaceAll(" +", " ").trim();
        
        // Limit message length
        if (message.length() > 200) {
            message = message.substring(0, 200);
        }

        return message;
    }

    /**
     * Check if message contains spam
     */
    public static boolean isSpam(String message) {
        // Simple spam detection - multiple repeated characters
        if (message.matches(".*(.)\\1{5,}.*")) {
            return true;
        }
        
        // All caps check
        if (message.length() > 5 && message.equals(message.toUpperCase())) {
            return true;
        }

        return false;
    }
}
