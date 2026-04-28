package com.eu.habbo.habbohotel.habboroleplay.turfs;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TurfManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(TurfManager.class);
    public static final ConcurrentHashMap<Integer, Turf> turfList = new ConcurrentHashMap<>();

    public void initialize() {
        turfList.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT r.id, r.group_id FROM rp_rooms rr JOIN rooms r ON rr.id = r.id WHERE rr.turf_enabled = '1'")) {

            while (row.next()) {
                int roomId = row.getInt("id");
                int gangId = row.getInt("group_id");

                Turf turf = new Turf(roomId, gangId);
                turfList.put(roomId, turf);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading TurfManager", e);
        }
        LOGGER.info("TurfManager -> Loaded {} turfs.", turfList.size());
    }

    public static Turf getTurf(int roomId) {
        return turfList.get(roomId);
    }

    public List<Turf> getTurfsByGang(int gangId) {
        return turfList.values().stream()
                .filter(t -> t.getGangId() == gangId)
                .collect(Collectors.toList());
    }

    public List<Turf> getAllTurfs() {
        return new ArrayList<>(turfList.values());
    }
}
