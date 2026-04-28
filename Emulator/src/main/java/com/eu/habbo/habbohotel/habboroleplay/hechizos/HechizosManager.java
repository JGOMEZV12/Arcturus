package com.eu.habbo.habbohotel.habboroleplay.hechizos;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

public class HechizosManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(HechizosManager.class);
    public static final ConcurrentHashMap<String, Hechizos> hechizos = new ConcurrentHashMap<>();

    public static void initialize() {
        hechizos.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_hechizos`")) {

            while (row.next()) {
                int id = row.getInt("id");
                String name = row.getString("name");
                String publicName = row.getString("publicname");
                String message = row.getString("message");
                int power = row.getInt("power");
                int firingRange = row.getInt("firingrange");
                int shields = row.getInt("shields");
                int firingDamage = row.getInt("firingdamage");
                int health = row.getInt("health");
                int cost = row.getInt("cost");
                int costFine = row.getInt("costfine");
                int stock = row.getInt("stock");

                Hechizos hechizo = new Hechizos(id, name, publicName, message, power, firingRange, shields, firingDamage, health, cost, costFine, stock);
                hechizos.put(name, hechizo);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading HechizosManager", e);
        }
        LOGGER.info("HechizosManager -> Loaded {} hechizos.", hechizos.size());
    }

    public static Hechizos getHechizo(String name) {
        return hechizos.get(name);
    }
}
