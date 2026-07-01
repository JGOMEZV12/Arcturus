package com.eu.habbo.habbohotel.roleplay.commands;

import com.eu.habbo.habbohotel.roleplay.RoleplayManager;
import com.eu.habbo.habbohotel.roleplay.HabboRoleplayManager;
import com.eu.habbo.habbohotel.roleplay.jobs.JobManager;
import com.eu.habbo.habbohotel.roleplay.factions.FactionManager;
import com.eu.habbo.habbohotel.roleplay.economy.EconomyManager;
import com.eu.habbo.habbohotel.users.Habbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RoleplayCommandHandler: Handles roleplay-specific commands.
 */
public class RoleplayCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayCommandHandler.class);

    /**
     * Execute roleplay command
     */
    public static boolean executeCommand(Habbo habbo, String command, String[] args) {
        if (habbo == null) return false;

        HabboRoleplayManager manager = HabboRoleplayManager.getInstance(habbo);
        if (manager == null || !manager.isActive()) {
            return false;
        }

        try {
            switch (command.toLowerCase()) {
                case "status":
                    return cmdStatus(habbo, args);
                case "balance":
                    return cmdBalance(habbo, args);
                case "job":
                    return cmdJob(habbo, args);
                case "faction":
                    return cmdFaction(habbo, args);
                case "transfer":
                    return cmdTransfer(habbo, args);
                case "health":
                    return cmdHealth(habbo, args);
                default:
                    LOGGER.warn("Unknown roleplay command: {}", command);
                    return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error executing roleplay command: {}", command, e);
            return false;
        }
    }

    private static boolean cmdStatus(Habbo habbo, String[] args) {
        HabboRoleplayManager manager = HabboRoleplayManager.getInstance(habbo);
        if (manager == null) return false;

        String status = manager.getRoleplayData().getStatus();
        LOGGER.info("{} current status: {}", habbo.getUsername(), status);
        return true;
    }

    private static boolean cmdBalance(Habbo habbo, String[] args) {
        double balance = EconomyManager.getBalance(habbo);
        LOGGER.info("{} balance: {}", habbo.getUsername(), balance);
        return true;
    }

    private static boolean cmdJob(Habbo habbo, String[] args) {
        if (args.length < 1) {
            JobManager.Job job = JobManager.getUserJob(habbo);
            if (job != null) {
                LOGGER.info("{} current job: {}", habbo.getUsername(), job.getName());
            } else {
                LOGGER.info("{} is currently unemployed", habbo.getUsername());
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            LOGGER.info("Available jobs: Police Officer, Miner, Farmer, Chef, Fisherman, Merchant");
            return true;
        }

        return false;
    }

    private static boolean cmdFaction(Habbo habbo, String[] args) {
        if (args.length < 1) {
            FactionManager.Faction faction = FactionManager.getUserFaction(habbo);
            if (faction != null) {
                LOGGER.info("{} current faction: {} ({} members)", habbo.getUsername(), faction.getName(), faction.getMembers());
            } else {
                LOGGER.info("{} is not in a faction", habbo.getUsername());
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            LOGGER.info("Available factions: Police Department, Mafia, Gang, Business Guild");
            return true;
        }

        return false;
    }

    private static boolean cmdTransfer(Habbo habbo, String[] args) {
        if (args.length < 2) {
            LOGGER.info("Usage: /transfer <username> <amount>");
            return false;
        }

        String targetName = args[0];
        try {
            double amount = Double.parseDouble(args[1]);
            // TODO: Find target player and transfer money
            LOGGER.info("Transfer command executed: {} to transfer {} to {}", habbo.getUsername(), amount, targetName);
            return true;
        } catch (NumberFormatException e) {
            LOGGER.warn("Invalid amount format");
            return false;
        }
    }

    private static boolean cmdHealth(Habbo habbo, String[] args) {
        HabboRoleplayManager manager = HabboRoleplayManager.getInstance(habbo);
        if (manager == null) return false;

        int health = manager.getRoleplayData().getHealth();
        LOGGER.info("{} health: {}", habbo.getUsername(), health);
        return true;
    }
}
