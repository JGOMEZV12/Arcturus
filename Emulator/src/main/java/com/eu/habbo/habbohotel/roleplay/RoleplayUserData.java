package com.eu.habbo.habbohotel.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.database.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;

/**
 * RoleplayUserData: Manages roleplay-specific user data.
 * Handles loading and saving RP statistics (health, energy, level, etc.)
 */
public class RoleplayUserData {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayUserData.class);

    private final int userId;
    private int health = 100;
    private int energy = 100;
    private int level = 1;
    private int experience = 0;
    private String status = "idle";
    private double money = 0.0;
    private boolean loaded = false;

    public RoleplayUserData(int userId) {
        this.userId = userId;
    }

    /**
     * Load roleplay data from database
     */
    public void load() {
        try {
            // Check if rp_users table exists, if not create it
            ensureTableExists();

            Database db = Emulator.getDatabase();
            if (db == null) {
                LOGGER.warn("Database not available for loading roleplay data for user {}", userId);
                return;
            }

            // TODO: Load from database when ready
            // For now, use defaults
            loaded = true;
            LOGGER.debug("Loaded roleplay data for user {}", userId);
        } catch (Exception e) {
            LOGGER.error("Error loading roleplay data for user {}", userId, e);
        }
    }

    /**
     * Save roleplay data to database
     */
    public void save() {
        try {
            if (!loaded) {
                return;
            }

            Database db = Emulator.getDatabase();
            if (db == null) {
                LOGGER.warn("Database not available for saving roleplay data for user {}", userId);
                return;
            }

            // TODO: Save to database when ready
            LOGGER.debug("Saved roleplay data for user {}", userId);
        } catch (Exception e) {
            LOGGER.error("Error saving roleplay data for user {}", userId, e);
        }
    }

    /**
     * Ensure required database table exists
     */
    private void ensureTableExists() {
        try {
            Database db = Emulator.getDatabase();
            if (db == null) return;

            // TODO: Create table if not exists
            // CREATE TABLE IF NOT EXISTS rp_users (
            //     user_id INT PRIMARY KEY,
            //     health INT DEFAULT 100,
            //     energy INT DEFAULT 100,
            //     level INT DEFAULT 1,
            //     experience INT DEFAULT 0,
            //     status VARCHAR(50) DEFAULT 'idle',
            //     money DOUBLE DEFAULT 0.0
            // )
        } catch (Exception e) {
            LOGGER.error("Error ensuring table exists", e);
        }
    }

    // ============ Getters & Setters ============

    public int getUserId() {
        return userId;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(100, health));
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(0, Math.min(100, energy));
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(1, level);
    }

    public int getExperience() {
        return experience;
    }

    public void addExperience(int amount) {
        this.experience += amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = Math.max(0, money);
    }

    public void addMoney(double amount) {
        this.money += amount;
    }

    public boolean isLoaded() {
        return loaded;
    }
}
