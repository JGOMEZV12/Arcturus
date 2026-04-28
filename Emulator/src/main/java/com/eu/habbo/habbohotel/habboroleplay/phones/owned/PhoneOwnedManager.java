package com.eu.habbo.habbohotel.habboroleplay.phones.owned;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PhoneOwnedManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneOwnedManager.class);
    public static final ConcurrentHashMap<Integer, PhoneOwned> phonesOwned = new ConcurrentHashMap<>();

    public void initialize() {
        phonesOwned.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_phones_owned`")) {

            while (row.next()) {
                int id = row.getInt("id");
                int phoneId = row.getInt("phone_id");
                int ownerId = row.getInt("user_id");
                String phoneNumber = row.getString("phone_number");

                PhoneOwned phoneOwned = new PhoneOwned(id, phoneId, ownerId, phoneNumber);
                phonesOwned.put(id, phoneOwned);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading PhoneOwnedManager", e);
        }
        LOGGER.info("PhoneOwnedManager -> Loaded {} owned phones.", phonesOwned.size());
    }

    public static PhoneOwned getPhoneOwned(int id) {
        return phonesOwned.get(id);
    }

    public List<PhoneOwned> getMyPhones(int ownerId) {
        return phonesOwned.values().stream()
                .filter(p -> p.getOwnerId() == ownerId)
                .collect(Collectors.toList());
    }
}
