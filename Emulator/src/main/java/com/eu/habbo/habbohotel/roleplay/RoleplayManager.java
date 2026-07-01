package com.eu.habbo.habbohotel.roleplay;

import com.eu.habbo.habbohotel.roleplay.events.RoleplayEventBus;
import com.eu.habbo.habbohotel.users.Habbo;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RoleplayManager: Central registry for all roleplay operations.
 * Provides static methods for roleplay actions and event broadcasting.
 */
public class RoleplayManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayManager.class);

    /**
     * Send a chat message (roleplay)
     */
    public static void sendChatMessage(Habbo habbo, String message) {
        if (habbo == null) return;

        HabboRoleplayManager manager = HabboRoleplayManager.getInstance(habbo);
        if (manager == null || !manager.isActive()) {
            LOGGER.warn("Attempt to send chat from inactive roleplay user: {}", habbo.getUsername());
            return;
        }

        try {
            JsonObject eventData = new JsonObject();
            eventData.addProperty("username", habbo.getUsername());
            eventData.addProperty("message", message);

            RoleplayEventBus.broadcastChatMessage(habbo, message);
            LOGGER.debug("Chat message sent by {}: {}", habbo.getUsername(), message);
        } catch (Exception e) {
            LOGGER.error("Error sending chat message", e);
        }
    }

    /**
     * Apply damage to a user
     */
    public static void applyDamage(Habbo habbo, int damage) {
        if (habbo == null) return;

        HabboRoleplayManager manager = HabboRoleplayManager.getInstance(habbo);
        if (manager == null || !manager.isActive()) {
            return;
        }

        try {
            RoleplayUserData data = manager.getRoleplayData();
            int currentHealth = data.getHealth();
            int newHealth = Math.max(0, currentHealth - damage);
            data.setHealth(newHealth);

            JsonObject eventData = new JsonObject();
            eventData.addProperty("damage", damage);
            eventData.addProperty("health", newHealth);

            manager.sendRoleplayEvent("rp.damage_taken", eventData.toString());
            RoleplayEventBus.broadcastCombatDamage(habbo.getId(), damage);

            LOGGER.debug("Damage applied to {}: {} (Health: {})", habbo.getUsername(), damage, newHealth);
        } catch (Exception e) {
            LOGGER.error("Error applying damage", e);
        }
    }

    /**
     * Heal a user
     */
    public static void heal(Habbo habbo, int amount) {
        if (habbo == null) return;

        HabboRoleplayManager manager = HabboRoleplayManager.getInstance(habbo);
        if (manager == null || !manager.isActive()) {
            return;
        }

        try {
            RoleplayUserData data = manager.getRoleplayData();
            int currentHealth = data.getHealth();
            int newHealth = Math.min(100, currentHealth + amount);
            data.setHealth(newHealth);

            JsonObject eventData = new JsonObject();
            eventData.addProperty("healed", amount);
            eventData.addProperty("health", newHealth);

            manager.sendRoleplayEvent("rp.healed", eventData.toString());
            LOGGER.debug("User healed {}: +{} (Health: {})", habbo.getUsername(), amount, newHealth);
        } catch (Exception e) {
            LOGGER.error("Error healing user", e);
        }
    }

    /**
     * Add money to a user
     */
    public static void addMoney(Habbo habbo, double amount) {
        if (habbo == null) return;

        HabboRoleplayManager manager = HabboRoleplayManager.getInstance(habbo);
        if (manager == null || !manager.isActive()) {
            return;
        }

        try {
            RoleplayUserData data = manager.getRoleplayData();
            data.addMoney(amount);

            JsonObject eventData = new JsonObject();
            eventData.addProperty("amount", amount);
            eventData.addProperty("total", data.getMoney());

            manager.sendRoleplayEvent("rp.money_added", eventData.toString());
            LOGGER.debug("Money added to {}: +{} (Total: {})", habbo.getUsername(), amount, data.getMoney());
        } catch (Exception e) {
            LOGGER.error("Error adding money", e);
        }
    }

    /**
     * Set user status
     */
    public static void setUserStatus(Habbo habbo, String status) {
        if (habbo == null) return;

        HabboRoleplayManager manager = HabboRoleplayManager.getInstance(habbo);
        if (manager == null || !manager.isActive()) {
            return;
        }

        try {
            RoleplayUserData data = manager.getRoleplayData();
            data.setStatus(status);

            JsonObject eventData = new JsonObject();
            eventData.addProperty("status", status);

            manager.sendRoleplayEvent("rp.status_changed", eventData.toString());
            RoleplayEventBus.broadcastUserStatusChange(habbo, status);

            LOGGER.debug("Status changed for {}: {}", habbo.getUsername(), status);
        } catch (Exception e) {
            LOGGER.error("Error setting user status", e);
        }
    }

    /**
     * Get roleplay manager for user
     */
    public static HabboRoleplayManager getRoleplayManager(Habbo habbo) {
        return habbo != null ? HabboRoleplayManager.getInstance(habbo.getId()) : null;
    }
}
