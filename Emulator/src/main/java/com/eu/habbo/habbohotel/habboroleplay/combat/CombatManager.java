package com.eu.habbo.habbohotel.habboroleplay.combat;

import com.eu.habbo.habbohotel.habboroleplay.combat.types.Fist;
import com.eu.habbo.habbohotel.habboroleplay.combat.types.Gun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CombatManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CombatManager.class);
    private static final Map<String, ICombat> combatTypes = new HashMap<>();

    public static void initialize() {
        combatTypes.clear();
        combatTypes.put("fist", new Fist());
        combatTypes.put("gun", new Gun());
        LOGGER.info("CombatManager -> Loaded!");
    }

    public static ICombat getCombatType(String type) {
        return combatTypes.get(type.toLowerCase());
    }
}
