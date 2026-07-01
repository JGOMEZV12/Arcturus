package com.eu.habbo.habbohotel.roleplay.economy;

import com.eu.habbo.habbohotel.users.Habbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * EconomyManager: Manages player economy, transactions, and balance.
 */
public class EconomyManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(EconomyManager.class);
    private static final ConcurrentHashMap<Integer, Double> USER_BALANCES = new ConcurrentHashMap<>();
    private static final double INITIAL_BALANCE = 1000.0;

    /**
     * Initialize player balance
     */
    public static void initializeBalance(Habbo habbo) {
        if (habbo != null) {
            USER_BALANCES.putIfAbsent(habbo.getId(), INITIAL_BALANCE);
            LOGGER.debug("Balance initialized for {}: {}", habbo.getUsername(), INITIAL_BALANCE);
        }
    }

    /**
     * Get player balance
     */
    public static double getBalance(Habbo habbo) {
        if (habbo == null) return 0;
        return USER_BALANCES.getOrDefault(habbo.getId(), 0.0);
    }

    /**
     * Add money to player
     */
    public static void addBalance(Habbo habbo, double amount) {
        if (habbo == null || amount <= 0) return;
        USER_BALANCES.merge(habbo.getId(), amount, Double::sum);
        LOGGER.debug("Balance added to {}: +{}", habbo.getUsername(), amount);
    }

    /**
     * Remove money from player
     */
    public static boolean removeBalance(Habbo habbo, double amount) {
        if (habbo == null || amount <= 0) return false;

        double currentBalance = getBalance(habbo);
        if (currentBalance < amount) {
            LOGGER.warn("Insufficient funds for {}: Required {}, Have {}", habbo.getUsername(), amount, currentBalance);
            return false;
        }

        USER_BALANCES.merge(habbo.getId(), -amount, Double::sum);
        LOGGER.debug("Balance removed from {}: -{}", habbo.getUsername(), amount);
        return true;
    }

    /**
     * Transfer money between players
     */
    public static boolean transfer(Habbo from, Habbo to, double amount) {
        if (from == null || to == null || amount <= 0) return false;

        if (!removeBalance(from, amount)) {
            return false;
        }

        addBalance(to, amount);
        LOGGER.info("Money transferred - From: {}, To: {}, Amount: {}", from.getUsername(), to.getUsername(), amount);
        return true;
    }

    /**
     * Set player balance
     */
    public static void setBalance(Habbo habbo, double amount) {
        if (habbo != null && amount >= 0) {
            USER_BALANCES.put(habbo.getId(), amount);
            LOGGER.debug("Balance set for {}: {}", habbo.getUsername(), amount);
        }
    }
}
