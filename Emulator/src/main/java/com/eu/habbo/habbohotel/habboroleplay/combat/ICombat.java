package com.eu.habbo.habbohotel.habboroleplay.combat;

import com.eu.habbo.habbohotel.gameclients.GameClient;

public interface ICombat {
    boolean canCombat(GameClient client, GameClient targetClient);
    void execute(GameClient client, GameClient targetClient);
}
