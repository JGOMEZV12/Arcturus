package com.eu.habbo.habbohotel.habboroleplay.farming.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FarmingStats {
    public int level;
    public int exp;
    public boolean hasSeedSatchel;
    public boolean hasPlantSatchel;
    public SeedSatchel seedSatchel;
    public PlantSatchel plantSatchel;

    public FarmingStats(ResultSet row) throws SQLException {
        if (row != null) {
            this.level = row.getInt("level");
            this.exp = row.getInt("exp");
            this.hasSeedSatchel = row.getBoolean("has_seed_satchel");
            this.hasPlantSatchel = row.getBoolean("has_plant_satchel");
            this.seedSatchel = new SeedSatchel(row);
            this.plantSatchel = new PlantSatchel(row);
        }
    }
}
