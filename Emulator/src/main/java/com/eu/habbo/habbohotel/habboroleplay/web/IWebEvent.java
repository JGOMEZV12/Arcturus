package com.eu.habbo.habbohotel.habboroleplay.web;

import com.eu.habbo.habbohotel.gameclients.GameClient;

public interface IWebEvent {
    void execute(GameClient client, String data);
}
