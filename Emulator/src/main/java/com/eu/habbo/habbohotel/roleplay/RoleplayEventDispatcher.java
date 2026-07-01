package com.eu.habbo.habbohotel.roleplay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * RoleplayEventDispatcher: Routes incoming WebEvents to appropriate handlers.
 * Supports custom event handlers registration.
 */
public class RoleplayEventDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayEventDispatcher.class);

    private final HabboRoleplayManager manager;
    private final ConcurrentHashMap<String, BiConsumer<HabboRoleplayManager, JsonObject>> eventHandlers;

    public RoleplayEventDispatcher(HabboRoleplayManager manager) {
        this.manager = manager;
        this.eventHandlers = new ConcurrentHashMap<>();
        registerDefaultHandlers();
    }

    /**
     * Initialize the dispatcher
     */
    public void initialize() {
        LOGGER.debug("RoleplayEventDispatcher initialized for user {}", manager.getHabbo().getUsername());
    }

    /**
     * Dispatch an incoming WebEvent
     */
    public void dispatch(String eventType, String payload) {
        try {
            JsonObject data = JsonParser.parseString(payload).getAsJsonObject();

            BiConsumer<HabboRoleplayManager, JsonObject> handler = eventHandlers.get(eventType);
            if (handler != null) {
                handler.accept(manager, data);
                LOGGER.debug("Dispatched event '{}' for user {}", eventType, manager.getUserId());
            } else {
                LOGGER.warn("No handler registered for event type: {}", eventType);
            }
        } catch (Exception e) {
            LOGGER.error("Error dispatching event '{}' for user {}", eventType, manager.getUserId(), e);
        }
    }

    /**
     * Register a custom event handler
     */
    public void registerHandler(String eventType, BiConsumer<HabboRoleplayManager, JsonObject> handler) {
        eventHandlers.put(eventType, handler);
        LOGGER.debug("Registered handler for event type: {}", eventType);
    }

    /**
     * Unregister an event handler
     */
    public void unregisterHandler(String eventType) {
        eventHandlers.remove(eventType);
    }

    /**
     * Register default event handlers
     */
    private void registerDefaultHandlers() {
        // Chat events
        registerHandler("rp.chat.message", this::handleChatMessage);
        registerHandler("rp.chat.emote", this::handleChatEmote);

        // Combat events
        registerHandler("rp.combat.attack", this::handleCombatAttack);
        registerHandler("rp.combat.damage", this::handleCombatDamage);

        // Item events
        registerHandler("rp.item.use", this::handleItemUse);
        registerHandler("rp.item.drop", this::handleItemDrop);

        // User events
        registerHandler("rp.user.status", this::handleUserStatus);

        // Ping/Keep-alive
        registerHandler("rp.ping", this::handlePing);
    }

    // ============ Event Handlers ============

    private void handleChatMessage(HabboRoleplayManager manager, JsonObject data) {
        String message = data.has("message") ? data.get("message").getAsString() : "";
        LOGGER.debug("Chat message from {}: {}", manager.getHabbo().getUsername(), message);
        // TODO: Implement chat logic
    }

    private void handleChatEmote(HabboRoleplayManager manager, JsonObject data) {
        String emote = data.has("emote") ? data.get("emote").getAsString() : "";
        LOGGER.debug("Emote from {}: {}", manager.getHabbo().getUsername(), emote);
        // TODO: Implement emote logic
    }

    private void handleCombatAttack(HabboRoleplayManager manager, JsonObject data) {
        int targetId = data.has("targetId") ? data.get("targetId").getAsInt() : 0;
        LOGGER.debug("Combat attack from {} to user {}", manager.getUserId(), targetId);
        // TODO: Implement combat attack logic
    }

    private void handleCombatDamage(HabboRoleplayManager manager, JsonObject data) {
        int damage = data.has("damage") ? data.get("damage").getAsInt() : 0;
        LOGGER.debug("Damage received by {}: {}", manager.getUserId(), damage);
        // TODO: Implement damage logic
    }

    private void handleItemUse(HabboRoleplayManager manager, JsonObject data) {
        int itemId = data.has("itemId") ? data.get("itemId").getAsInt() : 0;
        LOGGER.debug("Item used by {}: {}", manager.getUserId(), itemId);
        // TODO: Implement item use logic
    }

    private void handleItemDrop(HabboRoleplayManager manager, JsonObject data) {
        int itemId = data.has("itemId") ? data.get("itemId").getAsInt() : 0;
        LOGGER.debug("Item dropped by {}: {}", manager.getUserId(), itemId);
        // TODO: Implement item drop logic
    }

    private void handleUserStatus(HabboRoleplayManager manager, JsonObject data) {
        String status = data.has("status") ? data.get("status").getAsString() : "idle";
        LOGGER.debug("Status update from {}: {}", manager.getHabbo().getUsername(), status);
        // TODO: Implement status logic
    }

    private void handlePing(HabboRoleplayManager manager, JsonObject data) {
        // Send pong response
        JsonObject response = new JsonObject();
        response.addProperty("type", "rp.pong");
        response.addProperty("timestamp", System.currentTimeMillis());
        manager.sendRoleplayEvent("rp.pong", response.toString());
    }

    /**
     * Cleanup dispatcher
     */
    public void dispose() {
        eventHandlers.clear();
        LOGGER.debug("RoleplayEventDispatcher disposed");
    }
}
