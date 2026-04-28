package com.eu.habbo.habbohotel.habboroleplay.bots.manager;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.habboroleplay.bots.RoleplayBot;
import com.eu.habbo.habbohotel.habboroleplay.bots.RoleplayBotAIType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

public class RoleplayBotManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayBotManager.class);
    public static final ConcurrentHashMap<Integer, RoleplayBot> cachedRoleplayBots = new ConcurrentHashMap<>();

    public static void initialize() {
        cachedRoleplayBots.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_bots` WHERE `spawn_id` > '0'")) {

            while (row.next()) {
                int id = row.getInt("id");
                int ownerId = row.getInt("owner_id");
                String name = row.getString("name");
                String gender = row.getString("gender");
                String figure = row.getString("figure");
                String motto = row.getString("motto");
                int maxHealth = row.getInt("max_health");
                int curHealth = row.getInt("cur_health");
                int str = row.getInt("str");
                int level = row.getInt("level");
                int spawnId = row.getInt("spawn_id");
                int spawnX = row.getInt("spawn_x");
                int spawnY = row.getInt("spawn_y");
                int spawnZ = row.getInt("spawn_z");
                int spawnRot = row.getInt("spawn_rot");
                String aiTypeStr = row.getString("ai_type");
                RoleplayBotAIType aiType = getRoleplayBotAIType(aiTypeStr);
                int roamInterval = row.getInt("roam_interval");
                int attackInterval = row.getInt("attack_interval");
                int followInterval = row.getInt("follow_interval");
                int stayInterval = row.getInt("stay_interval");
                boolean roamBot = row.getBoolean("roam_bot");
                boolean roamCityBot = row.getBoolean("roam_city_bot");
                boolean addableBot = row.getBoolean("addable_bot");
                int corporationId = row.getInt("corporation_id");
                String stopworkItem = row.getString("stopwork_item");
                String workUniform = row.getString("work_uniform");
                boolean canBeAttacked = row.getBoolean("can_be_attacked");
                int attackPos = row.getInt("attack_pos");
                String actionOdds = row.getString("action_odds");
                int speechTimer = row.getInt("speech_timer");
                String petData = row.getString("pet_data");

                RoleplayBot bot = new RoleplayBot(id, ownerId, name, gender, figure, motto, maxHealth, curHealth, str, level, spawnId, spawnX, spawnY, (double)spawnZ, spawnRot, aiTypeStr, aiType, roamInterval, attackInterval, followInterval, stayInterval, roamBot, roamCityBot, addableBot, corporationId, stopworkItem, workUniform, canBeAttacked, attackPos, actionOdds, speechTimer, petData);
                cachedRoleplayBots.put(id, bot);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading RoleplayBotManager", e);
        }
        LOGGER.info("RoleplayBotManager -> Loaded {} bots.", cachedRoleplayBots.size());
    }

    public static RoleplayBotAIType getRoleplayBotAIType(String stringType) {
        if (stringType == null) return RoleplayBotAIType.BLANK;
        switch (stringType.toLowerCase()) {
            case "quest": return RoleplayBotAIType.QUEST;
            case "thug": return RoleplayBotAIType.THUG;
            case "jury": case "court": return RoleplayBotAIType.JURY;
            case "hosp": case "hospital": return RoleplayBotAIType.HOSP;
            case "gun": case "gunstore": case "gun_store": case "ammu": case "ammunation": return RoleplayBotAIType.GUNSTORE;
            case "phone": case "phonestore": case "phone_store": return RoleplayBotAIType.PHONESTORE;
            case "car": case "carstore": case "car_store": return RoleplayBotAIType.CARSTORE;
            case "bank": case "bank worker": return RoleplayBotAIType.BANKWORKER;
            case "food": case "food server": case "food_server": return RoleplayBotAIType.FOODSERVER;
            case "drink": case "drinks": case "drink server": case "drinks server": case "drink_server": case "drinks_server": return RoleplayBotAIType.DRINKSERVER;
            case "drug": case "drugseller": return RoleplayBotAIType.DRUGSELLER;
            case "plant": return RoleplayBotAIType.PLANTSELLER;
            case "business": case "job": case "mayor": return RoleplayBotAIType.BUSINESS;
            case "supermarket": return RoleplayBotAIType.SUPERMARKET;
            case "delivery": return RoleplayBotAIType.DELIVERY;
            case "mw": case "mafiawars": return RoleplayBotAIType.MAFIAWARS;
            case "meca": case "mecanico": return RoleplayBotAIType.MECANICO;
            case "pet": return RoleplayBotAIType.PET;
            default: return RoleplayBotAIType.BLANK;
        }
    }
}
