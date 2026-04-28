package com.eu.habbo.habbohotel.habboroleplay.events;

import com.eu.habbo.habbohotel.habboroleplay.events.methods.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class EventManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventManager.class);
    private static final Map<String, IEvent> events = new HashMap<>();

    public static void initialize() {
        events.clear();
        events.put("OnHealthChange", new OnHealthChange());
        // Add more events here
        LOGGER.info("EventManager -> Loaded!");
    }

    public static void triggerEvent(String eventName, Object source, Object... params) {
        IEvent event = events.get(eventName);
        if (event != null) {
            event.execute(source, params);
        }
    }
}
