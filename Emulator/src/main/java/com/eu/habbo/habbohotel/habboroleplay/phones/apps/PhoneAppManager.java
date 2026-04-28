package com.eu.habbo.habbohotel.habboroleplay.phones.apps;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PhoneAppManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneAppManager.class);
    public static final ConcurrentHashMap<String, PhoneApp> phonesApps = new ConcurrentHashMap<>();

    public static void initialize() {
        phonesApps.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_phones_apps`")) {

            while (row.next()) {
                int id = row.getInt("id");
                String name = row.getString("name");
                String displayName = row.getString("display_name");
                String icon = row.getString("icon");
                String developerName = row.getString("developer_name");
                String code = row.getString("code");
                int price = row.getInt("price");
                String version = row.getString("version");

                PhoneApp phoneApp = new PhoneApp(id, name, displayName, icon, developerName, code, price, version);
                phonesApps.put(name, phoneApp);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading PhoneAppManager", e);
        }
        LOGGER.info("PhoneAppManager -> Loaded {} phone apps.", phonesApps.size());
    }

    public static PhoneApp getPhoneApp(String name) {
        return phonesApps.get(name);
    }
}
