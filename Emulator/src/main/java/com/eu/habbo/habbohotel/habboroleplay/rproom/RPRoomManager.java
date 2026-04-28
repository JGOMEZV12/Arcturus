package com.eu.habbo.habbohotel.habboroleplay.rproom;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class RPRoomManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(RPRoomManager.class);

    private final Map<String, List<RPRoom>> hospitalRooms = new HashMap<>();
    private final Map<String, List<RPRoom>> jailRooms = new HashMap<>();
    private final Map<String, List<RPRoom>> courtRooms = new HashMap<>();
    private final Map<String, List<RPRoom>> jailBack = new HashMap<>();
    private final Map<String, List<RPRoom>> polStationRooms = new HashMap<>();
    private final Map<String, List<RPRoom>> camionerosRooms = new HashMap<>();
    private final Map<String, List<RPRoom>> basurerosRooms = new HashMap<>();
    private final Map<String, List<RPRoom>> armerosRooms = new HashMap<>();

    private final Random random = new Random();

    public void init() {
        hospitalRooms.clear();
        jailRooms.clear();
        jailBack.clear();
        courtRooms.clear();
        polStationRooms.clear();
        camionerosRooms.clear();
        basurerosRooms.clear();
        armerosRooms.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_rooms` WHERE `is_hospital` = '1' OR `is_prison` = '1' OR `is_prisonback` = '1' OR `is_court` = '1' OR `is_camionero` = '1' OR `is_basurero` = '1' OR `is_polstation` = '1' OR `is_armero` = '1'")) {

            while (row.next()) {
                RPRoom room = buildRoom(row);
                String city = row.getString("city");

                if (row.getBoolean("is_hospital")) addToDict(hospitalRooms, city, room);
                if (row.getBoolean("is_prison")) addToDict(jailRooms, city, room);
                if (row.getBoolean("is_prisonback")) addToDict(jailBack, city, room);
                if (row.getBoolean("is_court")) addToDict(courtRooms, city, room);
                if (row.getBoolean("is_camionero")) addToDict(camionerosRooms, city, room);
                if (row.getBoolean("is_basurero")) addToDict(basurerosRooms, city, room);
                if (row.getBoolean("is_polstation")) addToDict(polStationRooms, city, room);
                if (row.getBoolean("is_armero")) addToDict(armerosRooms, city, room);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading RPRoomManager", e);
        }

        LOGGER.info("RPRoomManager -> Loaded {} Hospitals.", totalRooms(hospitalRooms));
        LOGGER.info("RPRoomManager -> Loaded {} Prisons.", totalRooms(jailRooms));
        LOGGER.info("RPRoomManager -> Loaded {} Police Stations.", totalRooms(polStationRooms));
    }

    public int tryToGetHospital(String city, RPRoom[] room) { return tryGetRandom(hospitalRooms, city, room); }
    public int tryToGetJail(String city, RPRoom[] room) { return tryGetRandom(jailRooms, city, room); }
    public int tryToGetJailBack(String city, RPRoom[] room) { return tryGetRandom(jailBack, city, room); }
    public int tryToGetCourt(String city, RPRoom[] room) { return tryGetRandom(courtRooms, city, room); }
    public int tryToGetCamioneros(String city, RPRoom[] room) { return tryGetRandom(camionerosRooms, city, room); }
    public int tryToGetPolStation(String city, RPRoom[] room) { return tryGetRandom(polStationRooms, city, room); }
    public int tryToGetBasureros(String city, RPRoom[] room) { return tryGetRandom(basurerosRooms, city, room); }
    public int tryToGetArmeros(String city, RPRoom[] room) { return tryGetRandom(armerosRooms, city, room); }

    private int tryGetRandom(Map<String, List<RPRoom>> dict, String city, RPRoom[] room) {
        List<RPRoom> rooms = dict.get(city);
        if (rooms != null && !rooms.isEmpty()) {
            room[0] = rooms.get(random.nextInt(rooms.size()));
            return room[0].getId();
        }
        room[0] = null;
        return 0;
    }

    private void addToDict(Map<String, List<RPRoom>> dict, String city, RPRoom room) {
        dict.computeIfAbsent(city, k -> new ArrayList<>()).add(room);
    }

    private RPRoom buildRoom(ResultSet row) throws Exception {
        return new RPRoom(
                row.getInt("id"),
                row.getString("city"),
                row.getBoolean("is_hospital"),
                row.getBoolean("is_prison"),
                row.getBoolean("is_court"),
                row.getBoolean("is_prisonback"),
                row.getBoolean("is_camionero"),
                row.getBoolean("is_mecanico"),
                row.getBoolean("is_basurero"),
                row.getBoolean("is_armero"),
                row.getBoolean("is_polstation")
        );
    }

    private int totalRooms(Map<String, List<RPRoom>> dict) {
        return dict.values().stream().mapToInt(List::size).sum();
    }
}
