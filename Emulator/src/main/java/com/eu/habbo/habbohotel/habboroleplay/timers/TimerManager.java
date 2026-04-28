package com.eu.habbo.habbohotel.habboroleplay.timers;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.types.*;
import java.util.concurrent.ConcurrentHashMap;
public class TimerManager {
    protected final GameClient client;
    public final ConcurrentHashMap<String, RoleplayTimer> activeTimers = new ConcurrentHashMap<>();
    public TimerManager(GameClient client) { this.client = client; }
    public void createTimer(String type, int time, boolean forever, Object... params) {
        if (activeTimers.containsKey(type)) return;
        RoleplayTimer timer = getTimerFromType(type, time, forever, params);
        if (timer != null) { activeTimers.put(type, timer); }
    }
    private RoleplayTimer getTimerFromType(String type, int time, boolean forever, Object[] params) {
        switch (type.toLowerCase()) {
            case "jail": return new JailTimer(client, time);
            case "death": return new DeathTimer(client, time);
            case "wanted": return new WantedTimer(client, time);
            case "animo": return new AnimoTimer(client);
            case "sida": return new SidaTimer(client);
            case "probation": return new ProbationTimer(client, time);
            case "cuff": return new CuffTimer(client, time);
            case "noob": return new NoobTimer(client, time);
            case "vehicle_job": return new VehicleJobTimer(client, time);
            case "stun": return new StunTimer(client, time);
            case "inmunity": return new InmunityTimer(client, time);
            case "general": return new GeneralTimer(client, time);
            case "robbery": return new RobberyTimer(client, time);
            case "workout": return new WorkoutTimer(client, 0, false);
            case "hunger": return new HungerTimer(type, client, time, forever, params);
            case "work": return new WorkTimer(type, client, time, forever, params);
            case "turfcapture": return new TurfCaptureTimer(client);
            case "hygiene": return new HygieneTimer(type, client, time, forever, params);
            case "poop": return new PoopTimer(type, client, time, forever, params);
            case "heal": return new HealTimer(type, client, time, forever, params);
        }
        return null;
    }
    public void stopTimer(String type) {
        RoleplayTimer timer = activeTimers.remove(type);
        if (timer != null) { timer.endTimer(); }
    }
    public void endAllTimers() { for (RoleplayTimer timer : activeTimers.values()) { timer.endTimer(); } activeTimers.clear(); }
}
