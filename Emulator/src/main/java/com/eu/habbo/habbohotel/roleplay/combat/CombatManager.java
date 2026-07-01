package com.eu.habbo.habbohotel.roleplay.combat;

import com.eu.habbo.habbohotel.roleplay.HabboRoleplayManager;
import com.eu.habbo.habbohotel.roleplay.RoleplayManager;
import com.eu.habbo.habbohotel.users.Habbo;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CombatManager: Manages combat system between players.
 */
public class CombatManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CombatManager.class);
    private static final ConcurrentHashMap<Integer, CombatSession> ACTIVE_COMBATS = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

    /**
     * Start combat between two players
     */
    public static void startCombat(Habbo attacker, Habbo target) {
        if (attacker == null || target == null) return;

        HabboRoleplayManager attackerManager = HabboRoleplayManager.getInstance(attacker);
        HabboRoleplayManager targetManager = HabboRoleplayManager.getInstance(target);

        if (attackerManager == null || targetManager == null || !attackerManager.isActive() || !targetManager.isActive()) {
            LOGGER.warn("Cannot start combat: managers not active");
            return;
        }

        CombatSession session = new CombatSession(attacker.getId(), target.getId(), attacker.getUsername(), target.getUsername());
        ACTIVE_COMBATS.put(attacker.getId(), session);
        ACTIVE_COMBATS.put(target.getId(), session);

        // Notify both players
        attackerManager.getRoleplayData().setStatus("combat");
        targetManager.getRoleplayData().setStatus("combat");

        JsonObject eventData = new JsonObject();
        eventData.addProperty("opponent", target.getUsername());
        attackerManager.sendRoleplayEvent("rp.combat.started", eventData.toString());

        eventData = new JsonObject();
        eventData.addProperty("opponent", attacker.getUsername());
        targetManager.sendRoleplayEvent("rp.combat.started", eventData.toString());

        LOGGER.info("Combat started: {} vs {}", attacker.getUsername(), target.getUsername());
    }

    /**
     * Deal damage in combat
     */
    public static void dealDamage(Habbo attacker, Habbo target, int baseDamage) {
        if (attacker == null || target == null) return;

        CombatSession session = ACTIVE_COMBATS.get(attacker.getId());
        if (session == null || !session.isValid(attacker.getId(), target.getId())) {
            LOGGER.warn("Invalid combat session");
            return;
        }

        // Calculate actual damage with random variance (80-120% of base damage)
        int actualDamage = (int) (baseDamage * (0.8 + Math.random() * 0.4));
        
        RoleplayManager.applyDamage(target, actualDamage);
        
        HabboRoleplayManager targetManager = HabboRoleplayManager.getInstance(target);
        if (targetManager != null && targetManager.getRoleplayData().getHealth() <= 0) {
            endCombat(attacker, target);
        }

        LOGGER.debug("{} dealt {} damage to {}", attacker.getUsername(), actualDamage, target.getUsername());
    }

    /**
     * End combat
     */
    public static void endCombat(Habbo winner, Habbo loser) {
        if (winner == null || loser == null) return;

        ACTIVE_COMBATS.remove(winner.getId());
        ACTIVE_COMBATS.remove(loser.getId());

        HabboRoleplayManager winnerManager = HabboRoleplayManager.getInstance(winner);
        HabboRoleplayManager loserManager = HabboRoleplayManager.getInstance(loser);

        if (winnerManager != null) {
            winnerManager.getRoleplayData().setStatus("idle");
            JsonObject eventData = new JsonObject();
            eventData.addProperty("result", "victory");
            eventData.addProperty("opponent", loser.getUsername());
            winnerManager.sendRoleplayEvent("rp.combat.ended", eventData.toString());
        }

        if (loserManager != null) {
            loserManager.getRoleplayData().setHealth(100);
            loserManager.getRoleplayData().setStatus("idle");
            JsonObject eventData = new JsonObject();
            eventData.addProperty("result", "defeat");
            eventData.addProperty("opponent", winner.getUsername());
            loserManager.sendRoleplayEvent("rp.combat.ended", eventData.toString());
        }

        LOGGER.info("Combat ended: {} defeated {}", winner.getUsername(), loser.getUsername());
    }

    /**
     * Check if player is in combat
     */
    public static boolean isInCombat(Habbo habbo) {
        if (habbo == null) return false;
        return ACTIVE_COMBATS.containsKey(habbo.getId());
    }

    /**
     * Get active combat session
     */
    public static CombatSession getCombatSession(Habbo habbo) {
        if (habbo == null) return null;
        return ACTIVE_COMBATS.get(habbo.getId());
    }

    /**
     * CombatSession class
     */
    public static class CombatSession {
        private final int attackerId;
        private final int targetId;
        private final String attackerName;
        private final String targetName;
        private final long startTime;
        private static final long COMBAT_TIMEOUT = 5 * 60 * 1000; // 5 minutes

        public CombatSession(int attackerId, int targetId, String attackerName, String targetName) {
            this.attackerId = attackerId;
            this.targetId = targetId;
            this.attackerName = attackerName;
            this.targetName = targetName;
            this.startTime = System.currentTimeMillis();
        }

        public boolean isValid(int userId1, int userId2) {
            boolean valid = (userId1 == attackerId && userId2 == targetId) ||
                           (userId1 == targetId && userId2 == attackerId);
            
            // Check for timeout
            if (System.currentTimeMillis() - startTime > COMBAT_TIMEOUT) {
                return false;
            }

            return valid;
        }

        public int getAttackerId() { return attackerId; }
        public int getTargetId() { return targetId; }
        public String getAttackerName() { return attackerName; }
        public String getTargetName() { return targetName; }
    }
}
