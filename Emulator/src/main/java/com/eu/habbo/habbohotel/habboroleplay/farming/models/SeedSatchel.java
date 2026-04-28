package com.eu.habbo.habbohotel.habboroleplay.farming.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeedSatchel {
    public int blueStarflowerSeeds;
    public int yellowStarflowerSeeds;
    public int pinkDahliaSeeds;
    public int yellowPlumeriaSeeds;
    public int pinkPrimroseSeeds;
    public int bluePrimroseSeeds;
    public int yellowPrimroseSeeds;
    public int yellowDahliaSeeds;
    public int bluePlumeriaSeeds;
    public int pinkPlumeriaSeeds;
    public int redStarflowerSeeds;
    public int blueDahliaSeeds;

    public SeedSatchel(ResultSet row) throws SQLException {
        this.blueStarflowerSeeds = parse(row.getString("blue_starflower"), 0);
        this.yellowStarflowerSeeds = parse(row.getString("yellow_starflower"), 0);
        this.pinkDahliaSeeds = parse(row.getString("pink_dahlia"), 0);
        this.yellowPlumeriaSeeds = parse(row.getString("yellow_plumeria"), 0);
        this.pinkPrimroseSeeds = parse(row.getString("pink_primrose"), 0);
        this.bluePrimroseSeeds = parse(row.getString("blue_primrose"), 0);
        this.yellowPrimroseSeeds = parse(row.getString("yellow_primrose"), 0);
        this.yellowDahliaSeeds = parse(row.getString("yellow_dahlia"), 0);
        this.bluePlumeriaSeeds = parse(row.getString("blue_plumeria"), 0);
        this.pinkPlumeriaSeeds = parse(row.getString("pink_plumeria"), 0);
        this.redStarflowerSeeds = parse(row.getString("red_starflower"), 0);
        this.blueDahliaSeeds = parse(row.getString("blue_dahlia"), 0);
    }

    private int parse(String s, int index) {
        if (s == null) return 0;
        String[] parts = s.split(":");
        if (parts.length > index) return Integer.parseInt(parts[index]);
        return 0;
    }
}
