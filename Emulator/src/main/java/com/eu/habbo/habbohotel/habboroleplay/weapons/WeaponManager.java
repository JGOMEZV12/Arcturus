package com.eu.habbo.habbohotel.habboroleplay.weapons;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class WeaponManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeaponManager.class);
    public static final ConcurrentHashMap<String, Weapon> weapons = new ConcurrentHashMap<>();
    public static final List<Integer> enables = new ArrayList<>();
    public static final List<Integer> handItems = new ArrayList<>();

    public static void initialize() {
        weapons.clear();
        enables.clear();
        handItems.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_weapons`")) {

            while (row.next()) {
                int id = row.getInt("id");
                String name = row.getString("name");
                String publicName = row.getString("publicname");
                String firingText = row.getString("firingtext");
                String equipText = row.getString("equiptext");
                String unEquipText = row.getString("unequiptext");
                String reloadText = row.getString("reloadtext");
                int energy = row.getInt("energy");
                int effectId = row.getInt("effectid");
                int handItem = row.getInt("handitem");
                int range = row.getInt("firingrange");
                int minDamage = row.getInt("mindamage");
                int maxDamage = row.getInt("maxdamage");
                int clipSize = row.getInt("clipsize");
                int reloadTime = row.getInt("reloadtime");
                int cost = row.getInt("cost");
                int costFine = row.getInt("costfine");
                int stock = row.getInt("stock");
                int levelRequirement = row.getInt("level_requirement");
                int wLife = row.getInt("life");
                boolean isVip = row.getBoolean("vip");

                String categoryStr = row.getString("category");
                WeaponCategory category = WeaponCategory.ArmaDeFuego;
                try {
                    category = WeaponCategory.valueOf(categoryStr);
                } catch (Exception ignored) {}

                Weapon weapon = new Weapon(id, name, publicName, firingText, equipText, unEquipText, reloadText, energy, effectId, handItem, range, minDamage, maxDamage, clipSize, reloadTime, cost, costFine, stock, levelRequirement, true, clipSize, wLife, isVip, 0, category);
                weapons.put(name, weapon);
                enables.add(effectId);
                handItems.add(handItem);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading WeaponManager", e);
        }
        LOGGER.info("WeaponManager -> Loaded {} weapons.", weapons.size());
    }

    public static Weapon getWeapon(String name) {
        return weapons.get(name);
    }
}
