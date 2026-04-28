package com.eu.habbo.habbohotel.habboroleplay.apartments;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

public class ApartmentManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentManager.class);
    public static final ConcurrentHashMap<Integer, Apartment> apartments = new ConcurrentHashMap<>();

    public static void initialize() {
        apartments.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_apartments`")) {

            while (row.next()) {
                int id = row.getInt("id");
                String modelName = row.getString("model_name");
                int tiles = row.getInt("tiles");
                String image = row.getString("image");
                int price = row.getInt("price");

                Apartment apartment = new Apartment(id, modelName, tiles, image, price);
                apartments.put(id, apartment);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading ApartmentManager", e);
        }
        LOGGER.info("ApartmentManager -> Loaded {} apartments.", apartments.size());
    }

    public static Apartment getApartmentById(int id) {
        return apartments.get(id);
    }
}
