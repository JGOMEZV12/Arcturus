package com.eu.habbo.habbohotel.habboroleplay.farming.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlantSatchel {
    public int blueStarflowers;
    public int yellowStarflowers;
    public int pinkDahlias;
    public int yellowPlumerias;
    public int pinkPrimroses;
    public int bluePrimroses;
    public int yellowPrimroses;
    public int yellowDahlias;
    public int bluePlumerias;
    public int pinkPlumerias;
    public int redStarflowers;
    public int blueDahlias;

    public PlantSatchel(ResultSet row) throws SQLException {
        this.blueStarflowers = parse(row.getString("blue_starflower"), 1);
        this.yellowStarflowers = parse(row.getString("yellow_starflower"), 1);
        this.pinkDahlias = parse(row.getString("pink_dahlia"), 1);
        this.yellowPlumerias = parse(row.getString("yellow_plumeria"), 1);
        this.pinkPrimroses = parse(row.getString("pink_primrose"), 1);
        this.bluePrimroses = parse(row.getString("blue_primrose"), 1);
        this.yellowPrimroses = parse(row.getString("yellow_primrose"), 1);
        this.yellowDahlias = parse(row.getString("yellow_dahlia"), 1);
        this.bluePlumerias = parse(row.getString("blue_plumeria"), 1);
        this.pinkPlumerias = parse(row.getString("pink_plumeria"), 1);
        this.redStarflowers = parse(row.getString("red_starflower"), 1);
        this.blueDahlias = parse(row.getString("blue_dahlia"), 1);
    }

    private int parse(String s, int index) {
        if (s == null) return 0;
        String[] parts = s.split(":");
        if (parts.length > index) return Integer.parseInt(parts[index]);
        return 0;
    }
}
