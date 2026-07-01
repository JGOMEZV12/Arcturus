package com.eu.habbo.habbohotel.roleplay.events;

import com.google.gson.JsonObject;

/**
 * IRoleplayEventListener: Interface for classes that listen to roleplay events.
 */
public interface IRoleplayEventListener {
    void onChatMessage(int userId, String message);
    void onCombatAttack(int attackerId, int targetId);
    void onCombatDamage(int userId, int damage);
    void onItemUse(int userId, int itemId);
    void onItemDrop(int userId, int itemId);
    void onUserStatusChange(int userId, String status);
}
