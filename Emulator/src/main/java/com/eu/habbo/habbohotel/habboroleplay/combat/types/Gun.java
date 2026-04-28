package com.eu.habbo.habbohotel.habboroleplay.combat.types;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.combat.ICombat;

public class Gun implements ICombat {
    @Override
    public boolean canCombat(GameClient client, GameClient targetClient) {
        return true;
    }

    @Override
    public void execute(GameClient client, GameClient targetClient) {
        // Gun logic
    }
}
