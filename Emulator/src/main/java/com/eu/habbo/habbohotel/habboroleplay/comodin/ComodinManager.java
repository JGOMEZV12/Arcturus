package com.eu.habbo.habbohotel.habboroleplay.comodin;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

public class ComodinManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComodinManager.class);
    public static final ConcurrentHashMap<Integer, Comodin> comodines = new ConcurrentHashMap<>();

    public static void initialize() {
        comodines.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_comodin`")) {

            while (row.next()) {
                int id = row.getInt("id");
                int furniId = row.getInt("furni_id");
                int roomId = row.getInt("room_id");
                String action = row.getString("action");

                Comodin comodin = new Comodin(id, furniId, roomId, action);
                comodines.put(furniId, comodin);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading ComodinManager", e);
        }
        LOGGER.info("ComodinManager -> Loaded {} comodines.", comodines.size());
    }

    public static Comodin getComodin(int furniId) {
        return comodines.get(furniId);
    }
}
