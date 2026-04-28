package com.eu.habbo.habbohotel.habboroleplay.misc;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoleplayData {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayData.class);
    private static final ConcurrentHashMap<String, Map<String, String>> data = new ConcurrentHashMap<>();

    public static void initialize() {
        data.clear();
        loadData();
        LOGGER.info("RoleplayData -> LOADED!");
    }

    public static void loadData() {
        data.clear();
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM `rp_data`")) {

            while (resultSet.next()) {
                insertData(resultSet);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading RoleplayData", e);
        }
    }

    private static void insertData(ResultSet row) {
        try {
            String mainKey = row.getString("MainKey").toLowerCase();
            String dataString = row.getString("Data");
            dataString = dataString.replace(" ", "");

            String[] dataArray = dataString.split("\\|");
            Map<String, String> keyValue = new HashMap<>();

            for (String s : dataArray) {
                String[] parts = s.split(":");
                if (parts.length < 2) continue;

                String subKey = parts[0].toLowerCase();
                String value = parts[1];

                if (subKey.isEmpty() || value.isEmpty())
                    continue;

                keyValue.put(subKey, value);
            }

            data.put(mainKey, keyValue);
        } catch (Exception e) {
            LOGGER.error("Error inserting RoleplayData row", e);
        }
    }

    public static String getData(String mainKey, String subKey) {
        mainKey = mainKey.toLowerCase();
        subKey = subKey.toLowerCase();

        if (!data.containsKey(mainKey))
            return null;

        if (!data.get(mainKey).containsKey(subKey))
            return null;

        return data.get(mainKey).get(subKey);
    }

    public static int getInt(String mainKey, String subKey) {
        String val = getData(mainKey, subKey);
        return val != null ? Integer.parseInt(val) : 0;
    }

    public static boolean getBool(String mainKey, String subKey) {
        String val = getData(mainKey, subKey);
        return val != null && (val.equalsIgnoreCase("true") || val.equals("1"));
    }
}
