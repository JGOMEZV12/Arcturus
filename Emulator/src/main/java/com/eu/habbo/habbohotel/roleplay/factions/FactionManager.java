package com.eu.habbo.habbohotel.roleplay.factions;

import com.eu.habbo.habbohotel.users.Habbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * FactionManager: Manages factions and faction membership.
 */
public class FactionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(FactionManager.class);
    private static final ConcurrentHashMap<Integer, Faction> FACTIONS = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Integer, Integer> USER_FACTIONS = new ConcurrentHashMap<>();

    /**
     * Initialize default factions
     */
    public static void initialize() {
        addFaction(1, "Police Department", "Official law enforcement organization", 0);
        addFaction(2, "Mafia", "Criminal underground organization", 0);
        addFaction(3, "Gang", "Street level criminal group", 0);
        addFaction(4, "Business Guild", "Merchant and trader association", 0);
        LOGGER.info("Faction system initialized with {} factions", FACTIONS.size());
    }

    /**
     * Add a faction
     */
    private static void addFaction(int factionId, String name, String description, int leaderId) {
        Faction faction = new Faction(factionId, name, description, leaderId);
        FACTIONS.put(factionId, faction);
    }

    /**
     * Add user to faction
     */
    public static void addUserToFaction(Habbo habbo, int factionId) {
        if (habbo == null) return;

        Faction faction = FACTIONS.get(factionId);
        if (faction == null) {
            LOGGER.warn("Attempt to join non-existent faction: {}", factionId);
            return;
        }

        USER_FACTIONS.put(habbo.getId(), factionId);
        faction.addMember();
        LOGGER.info("User {} joined faction: {}", habbo.getUsername(), faction.getName());
    }

    /**
     * Remove user from faction
     */
    public static void removeUserFromFaction(Habbo habbo) {
        if (habbo == null) return;
        
        Integer factionId = USER_FACTIONS.get(habbo.getId());
        if (factionId != null) {
            Faction faction = FACTIONS.get(factionId);
            if (faction != null) {
                faction.removeMember();
            }
            USER_FACTIONS.remove(habbo.getId());
            LOGGER.info("User {} left faction", habbo.getUsername());
        }
    }

    /**
     * Get user's faction
     */
    public static Faction getUserFaction(Habbo habbo) {
        if (habbo == null) return null;
        Integer factionId = USER_FACTIONS.get(habbo.getId());
        return factionId != null ? FACTIONS.get(factionId) : null;
    }

    /**
     * Get faction by ID
     */
    public static Faction getFaction(int factionId) {
        return FACTIONS.get(factionId);
    }

    /**
     * Faction class
     */
    public static class Faction {
        private final int id;
        private final String name;
        private final String description;
        private int leaderId;
        private int members;

        public Faction(int id, String name, String description, int leaderId) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.leaderId = leaderId;
            this.members = 0;
        }

        public void addMember() { this.members++; }
        public void removeMember() { this.members = Math.max(0, this.members - 1); }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public int getLeaderId() { return leaderId; }
        public int getMembers() { return members; }
    }
}
