package com.eu.habbo.habbohotel.roleplay;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * RoleplayConstants: Constants and utilities for roleplay system.
 */
public class RoleplayConstants {

    // Event Types
    public static final String EVENT_CHAT = "rp.chat.message";
    public static final String EVENT_EMOTE = "rp.chat.emote";
    public static final String EVENT_ATTACK = "rp.combat.attack";
    public static final String EVENT_DAMAGE = "rp.combat.damage";
    public static final String EVENT_ITEM_USE = "rp.item.use";
    public static final String EVENT_ITEM_DROP = "rp.item.drop";
    public static final String EVENT_STATUS = "rp.user.status";
    public static final String EVENT_PING = "rp.ping";
    public static final String EVENT_PONG = "rp.pong";

    // Status Constants
    public static final String STATUS_IDLE = "idle";
    public static final String STATUS_WORKING = "working";
    public static final String STATUS_COMBAT = "combat";
    public static final String STATUS_DEAD = "dead";

    // Limits
    public static final int MAX_HEALTH = 100;
    public static final int MIN_HEALTH = 0;
    public static final int MAX_ENERGY = 100;
    public static final int MIN_ENERGY = 0;
    public static final int MIN_LEVEL = 1;

    /**
     * Create a JSON event payload
     */
    public static String createEventPayload(String eventType, Map<String, Object> data) {
        JsonObject json = new JsonObject();
        json.addProperty("event", eventType);
        json.addProperty("timestamp", System.currentTimeMillis());
        
        if (data != null) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof String) {
                    json.addProperty(entry.getKey(), (String) value);
                } else if (value instanceof Integer) {
                    json.addProperty(entry.getKey(), (Integer) value);
                } else if (value instanceof Double) {
                    json.addProperty(entry.getKey(), (Double) value);
                } else if (value instanceof Boolean) {
                    json.addProperty(entry.getKey(), (Boolean) value);
                }
            }
        }
        
        return json.toString();
    }
}
