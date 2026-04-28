package com.eu.habbo.habbohotel.habboroleplay.phones;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PhoneManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneManager.class);
    public static final ConcurrentHashMap<String, Phone> phones = new ConcurrentHashMap<>();
    public static final List<Integer> enables = new ArrayList<>();

    public static void initialize() {
        phones.clear();
        enables.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_phones`")) {

            while (row.next()) {
                int id = row.getInt("id");
                String modelName = row.getString("model_name");
                String displayName = row.getString("display_name");
                int price = row.getInt("price");
                int effectId = row.getInt("effect_id");
                int screenSlots = row.getInt("screen_slots");
                int dockSlots = row.getInt("dock_slots");

                Phone phone = new Phone(id, modelName, displayName, price, effectId, screenSlots, dockSlots);
                phones.put(modelName, phone);
                enables.add(effectId);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading PhoneManager", e);
        }
        LOGGER.info("PhoneManager -> Loaded {} phones.", phones.size());
    }

    public static Phone getPhone(String modelName) {
        return phones.get(modelName);
    }
}
