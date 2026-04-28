package com.eu.habbo.habbohotel.habboroleplay.vehicles;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

public class VehicleManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleManager.class);
    public static final ConcurrentHashMap<Integer, Vehicle> vehicles = new ConcurrentHashMap<>();

    public static void initialize() {
        vehicles.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_vehicles`")) {

            while (row.next()) {
                int id = row.getInt("id");
                int itemId = row.getInt("item_id");
                String itemName = row.getString("item_name");
                int effectId = row.getInt("effect_id");
                int price = row.getInt("price");
                String model = row.getString("model");
                String displayName = row.getString("display_name");
                int maxFuel = row.getInt("max_fuel");
                int maxTrunks = row.getInt("max_trunks");
                int carType = row.getInt("car_type");
                int maxDoors = row.getInt("max_doors");
                int carCorp = row.getInt("car_corp");
                int fastCar = row.getInt("fast_car");

                Vehicle vehicle = new Vehicle(id, itemId, itemName, effectId, price, model, displayName, maxFuel, maxTrunks, carType, maxDoors, carCorp, fastCar);
                vehicles.put(id, vehicle);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading VehicleManager", e);
        }
        LOGGER.info("VehicleManager -> Loaded {} vehicles.", vehicles.size());
    }

    public static Vehicle getVehicle(int id) {
        return vehicles.get(id);
    }
}
