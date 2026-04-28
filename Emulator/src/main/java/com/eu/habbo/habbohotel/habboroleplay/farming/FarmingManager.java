package com.eu.habbo.habbohotel.habboroleplay.farming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.farming.models.FarmingStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

public class FarmingManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(FarmingManager.class);

    public static void initialize() {
        // Load farming items, spaces etc if needed
        LOGGER.info("FarmingManager -> Loaded!");
    }

    public static int sellPlants(GameClient session) {
        if (session == null || session.getRoleplay() == null || session.getRoleplay().getFarmingStats() == null)
            return 0;

        // Implementation of selling plants logic based on Satchel
        return 0;
    }
}
