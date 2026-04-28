package com.eu.habbo.habbohotel.habboroleplay.products;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

public class ProductsManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsManager.class);
    public static final ConcurrentHashMap<String, Product> products = new ConcurrentHashMap<>();

    public static void initialize() {
        products.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_products`")) {

            while (row.next()) {
                int id = row.getInt("id");
                String name = row.getString("name");
                String displayName = row.getString("display_name");
                int price = row.getInt("price");
                String type = row.getString("type");
                boolean canStack = row.getBoolean("can_stack");
                int maxCant = row.getInt("max_cant");

                Product product = new Product(id, name, displayName, price, type, canStack, maxCant);
                products.put(name, product);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading ProductsManager", e);
        }
        LOGGER.info("ProductsManager -> Loaded {} products.", products.size());
    }

    public static Product getProduct(String name) {
        return products.get(name);
    }
}
