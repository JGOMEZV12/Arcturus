package com.eu.habbo.habbohotel.roleplay.events;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.HabboRoleplayManager;
import com.eu.habbo.habbohotel.users.Habbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * RoleplayEventBus: Central event system for roleplay events.
 * Broadcasts roleplay events to all listeners.
 */
public class RoleplayEventBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayEventBus.class);
    private static final CopyOnWriteArrayList<IRoleplayEventListener> listeners = new CopyOnWriteArrayList<>();

    /**
     * Register a listener
     */
    public static void subscribe(IRoleplayEventListener listener) {
        listeners.add(listener);
        LOGGER.debug("Listener registered: {}", listener.getClass().getSimpleName());
    }

    /**
     * Unregister a listener
     */
    public static void unsubscribe(IRoleplayEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Broadcast chat message event
     */
    public static void broadcastChatMessage(Habbo habbo, String message) {
        if (habbo == null) return;
        for (IRoleplayEventListener listener : listeners) {
            try {
                listener.onChatMessage(habbo.getId(), message);
            } catch (Exception e) {
                LOGGER.error("Error in chat message listener", e);
            }
        }
    }

    /**
     * Broadcast combat attack event
     */
    public static void broadcastCombatAttack(int attackerId, int targetId) {
        for (IRoleplayEventListener listener : listeners) {
            try {
                listener.onCombatAttack(attackerId, targetId);
            } catch (Exception e) {
                LOGGER.error("Error in combat attack listener", e);
            }
        }
    }

    /**
     * Broadcast combat damage event
     */
    public static void broadcastCombatDamage(int userId, int damage) {
        for (IRoleplayEventListener listener : listeners) {
            try {
                listener.onCombatDamage(userId, damage);
            } catch (Exception e) {
                LOGGER.error("Error in combat damage listener", e);
            }
        }
    }

    /**
     * Broadcast item use event
     */
    public static void broadcastItemUse(int userId, int itemId) {
        for (IRoleplayEventListener listener : listeners) {
            try {
                listener.onItemUse(userId, itemId);
            } catch (Exception e) {
                LOGGER.error("Error in item use listener", e);
            }
        }
    }

    /**
     * Broadcast item drop event
     */
    public static void broadcastItemDrop(int userId, int itemId) {
        for (IRoleplayEventListener listener : listeners) {
            try {
                listener.onItemDrop(userId, itemId);
            } catch (Exception e) {
                LOGGER.error("Error in item drop listener", e);
            }
        }
    }

    /**
     * Broadcast user status change event
     */
    public static void broadcastUserStatusChange(Habbo habbo, String status) {
        if (habbo == null) return;
        for (IRoleplayEventListener listener : listeners) {
            try {
                listener.onUserStatusChange(habbo.getId(), status);
            } catch (Exception e) {
                LOGGER.error("Error in user status listener", e);
            }
        }
    }

    /**
     * Get listener count
     */
    public static int getListenerCount() {
        return listeners.size();
    }
}
