package com.eu.habbo.habbohotel.habboroleplay.web;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class WebEventManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebEventManager.class);
    private final ConcurrentHashMap<String, IWebEvent> webEvents = new ConcurrentHashMap<>();

    public void init() {
        // Register events here
        LOGGER.info("WebEventManager -> Loaded!");
    }

    public void handleIncomingJson(GameClient client, String json) {
        try {
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            if (obj.has("event")) {
                String eventName = obj.get("event").getAsString();
                String data = obj.has("data") ? obj.get("data").getAsString() : "";
                executeWebEvent(client, eventName, data);
            }
        } catch (Exception e) {
            LOGGER.error("Error handling incoming JSON", e);
        }
    }

    public void executeWebEvent(GameClient client, String eventName, String data) {
        IWebEvent event = webEvents.get(eventName);
        if (event != null) {
            event.execute(client, data);
        }
    }

    public void sendDataDirect(GameClient client, String data) {
        if (client != null && client.getChannel().isOpen()) {
            client.getChannel().writeAndFlush(new TextWebSocketFrame(data));
        }
    }
}
