import os
import re

def write_file(path, content):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, 'w') as f: f.write(content)

timers_dir = 'Emulator/src/main/java/com/eu/habbo/habbohotel/habboroleplay/timers/types/'
timers = {
    "JailTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class JailTimer extends RoleplayTimer {
    public JailTimer(GameClient client, int timeInMinutes) {
        super("jail", client, 1000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            if (client.getHabbo().getHabboRoleplay() != null) client.getHabbo().getHabboRoleplay().setJailed(false);
            client.getHabbo().shout("*Se libera de la cárcel*", RoomChatMessageBubbles.getBubble(4));
            endTimer();
        }
    }
}""",
    "DeathTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class DeathTimer extends RoleplayTimer {
    public DeathTimer(GameClient client, int timeInMinutes) {
        super("death", client, 1000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            if (client.getHabbo().getHabboRoleplay() != null) {
                client.getHabbo().getHabboRoleplay().setDead(false);
                client.getHabbo().getHabboRoleplay().replenishStats();
            }
            client.getHabbo().shout("*Recupera la conciencia*", RoomChatMessageBubbles.getBubble(4));
            endTimer();
        }
    }
}""",
    "WantedTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class WantedTimer extends RoleplayTimer {
    public WantedTimer(GameClient client, int timeInMinutes) {
        super("wanted", client, 1000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            if (client.getHabbo().getHabboRoleplay() != null) {
                client.getHabbo().getHabboRoleplay().setWanted(false);
            }
            client.getHabbo().shout("*Finalmente evade a la policía*", RoomChatMessageBubbles.getBubble(4));
            endTimer();
        }
    }
}""",
    "AnimoTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
public class AnimoTimer extends RoleplayTimer {
    public AnimoTimer(GameClient client) {
        super("animo", client, 60000, true, null);
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null || client.getHabbo().getHabboRoleplay() == null) { endTimer(); return; }
        int animo = client.getHabbo().getHabboRoleplay().getAnimo();
        if (animo > 0) client.getHabbo().getHabboRoleplay().setAnimo(animo - 1);
    }
}""",
    "SidaTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
public class SidaTimer extends RoleplayTimer {
    public SidaTimer(GameClient client) {
        super("sida", client, 60000, true, null);
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null || client.getHabbo().getHabboRoleplay() == null) { endTimer(); return; }
        int sida = client.getHabbo().getHabboRoleplay().getSida();
        if (sida > 0 && sida < 100) client.getHabbo().getHabboRoleplay().setSida(sida + 1);
    }
}""",
    "ProbationTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class ProbationTimer extends RoleplayTimer {
    public ProbationTimer(GameClient client, int timeInMinutes) {
        super("probation", client, 1000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            if (client.getHabbo().getHabboRoleplay() != null) client.getHabbo().getHabboRoleplay().setOnProbation(false);
            client.getHabbo().shout("*Completa su tiempo de prueba*", RoomChatMessageBubbles.getBubble(4));
            endTimer();
        }
    }
}""",
    "CuffTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class CuffTimer extends RoleplayTimer {
    public CuffTimer(GameClient client, int timeInMinutes) {
        super("cuff", client, 1000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            if (client.getHabbo().getHabboRoleplay() != null) client.getHabbo().getHabboRoleplay().setCuffed(false);
            client.getHabbo().shout("*Se rompen sus esposas*", RoomChatMessageBubbles.getBubble(4));
            endTimer();
        }
    }
}""",
    "NoobTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class NoobTimer extends RoleplayTimer {
    public NoobTimer(GameClient client, int timeInMinutes) {
        super("noob", client, 1000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            if (client.getHabbo().getHabboRoleplay() != null) client.getHabbo().getHabboRoleplay().setNoob(false);
            client.getHabbo().whisper("¡La inmunidad se ha terminado!", RoomChatMessageBubbles.getBubble(0));
            endTimer();
        }
    }
}""",
    "VehicleJobTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class VehicleJobTimer extends RoleplayTimer {
    public VehicleJobTimer(GameClient client, int timeInSeconds) {
        super("vehicle_job", client, 1000, true, null);
        this.timeLeft = timeInSeconds * 1000;
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            client.getHabbo().whisper("¡Tu Vehículo de trabajo ha sido regresado!", RoomChatMessageBubbles.getBubble(0));
            endTimer();
        }
    }
}""",
    "StunTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class StunTimer extends RoleplayTimer {
    public StunTimer(GameClient client, int timeInSeconds) {
        super("stun", client, 1000, true, null);
        this.timeLeft = timeInSeconds * 1000;
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            if (client.getHabbo().getHabboRoleplay() != null) client.getHabbo().getHabboRoleplay().setStunned(false);
            client.getHabbo().whisper("¡Los efectos del aturdimiento se han agotado!", RoomChatMessageBubbles.getBubble(0));
            endTimer();
        }
    }
}""",
    "InmunityTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class InmunityTimer extends RoleplayTimer {
    public InmunityTimer(GameClient client, int timeInSeconds) {
        super("inmunity", client, 1000, true, null);
        this.timeLeft = timeInSeconds * 1000;
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            client.getHabbo().whisper("*¡Tu inmunidad ha terminado!*", RoomChatMessageBubbles.getBubble(0));
            endTimer();
        }
    }
}""",
    "RobberyTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class RobberyTimer extends RoleplayTimer {
    public RobberyTimer(GameClient client, int timeInMinutes) {
        super("robbery", client, 60000, true, null);
        this.timeLeft = timeInMinutes * 60000;
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null) { endTimer(); return; }
        timeLeft -= 60000;
        if (timeLeft <= 0) {
            client.getHabbo().shout("*Terminó su robo*", RoomChatMessageBubbles.getBubble(4));
            endTimer();
        }
    }
}""",
    "WorkoutTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class WorkoutTimer extends RoleplayTimer {
    public WorkoutTimer(GameClient client, int itemId, boolean strength) {
        super("workout", client, 1000, true, new Object[]{itemId, strength});
        this.timeLeft = 80000;
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            client.getHabbo().whisper("Has terminado tu entrenamiento.", RoomChatMessageBubbles.getBubble(0));
            endTimer();
        }
    }
}""",
    "GeneralTimer.java": """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
public class GeneralTimer extends RoleplayTimer {
    public GeneralTimer(GameClient client, int timeInSeconds) {
        super("general", client, 1000, true, null);
        this.timeLeft = timeInSeconds * 1000;
    }
    @Override
    public void execute() {
        if (client == null || client.getHabbo() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) { endTimer(); }
    }
}"""
}
for name, content in timers.items():
    write_file(os.path.join(timers_dir, name), content)

# RoleplayUser.java
write_file('Emulator/src/main/java/com/eu/habbo/habbohotel/habboroleplay/roleplayusers/RoleplayUser.java', """package com.eu.habbo.habbohotel.habboroleplay.roleplayusers;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.cooldowns.CooldownManager;
import com.eu.habbo.habbohotel.habboroleplay.farming.models.FarmingStats;
import com.eu.habbo.habbohotel.habboroleplay.phones.apps.PhoneAppOwned;
import com.eu.habbo.habbohotel.habboroleplay.roleplayusers.offers.OfferManager;
import com.eu.habbo.habbohotel.habboroleplay.timers.TimerManager;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
public class RoleplayUser {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayUser.class);
    private final GameClient client;
    private final ConcurrentHashMap<Integer, PhoneAppOwned> ownedPhonesApps = new ConcurrentHashMap<>();
    private final CooldownManager cooldownManager;
    private final TimerManager timerManager;
    private final OfferManager offerManager;
    private FarmingStats farmingStats;
    private int id, level, levelExp, jobId, jobRank, jobRequest, sendHomeTimeLeft;
    private String className;
    private boolean permanentClass, isWorking;
    private int maxHealth, curHealth, maxEnergy, curEnergy, armor, hunger, sida, hygiene, animo, poop;
    private int intelligence, strength, stamina, intelligenceExp, strengthExp, staminaExp;
    private boolean passiveMode, isStun, isDead, isJailed, isWanted, onProbation, cuffed, turfCapturing, isNoob, breakGeneralTimer, togglingPSV;
    private int deadTimeLeft, jailedTimeLeft, wantedLevel, wantedTimeLeft, probationTimeLeft, cuffedTimeLeft, loadingTimeLeft;
    private String wantedFor = "";
    private boolean isFuelCharging;
    private int fuelChargingCant;
    public RoleplayUser(GameClient client, ResultSet userRow) throws SQLException {
        this.client = client;
        this.cooldownManager = new CooldownManager(client);
        this.timerManager = new TimerManager(client);
        this.offerManager = new OfferManager(client);
    }
    public void replenishStats() { this.curHealth = this.maxHealth; }
    public GameClient getClient() { return client; }
    public CooldownManager getCooldownManager() { return cooldownManager; }
    public TimerManager getTimerManager() { return timerManager; }
    public OfferManager getOfferManager() { return offerManager; }
    public int getCurHealth() { return curHealth; }
    public void setCurHealth(int h) { curHealth = h; }
    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int h) { maxHealth = h; }
    public int getHunger() { return hunger; }
    public void setHunger(int h) { hunger = h; }
    public boolean isDead() { return isDead; }
    public void setDead(boolean b) { isDead = b; }
    public int getDeadTimeLeft() { return deadTimeLeft; }
    public void setDeadTimeLeft(int t) { deadTimeLeft = t; }
    public int getJobId() { return jobId; }
    public void setJobId(int id) { jobId = id; }
    public int getJobRank() { return jobRank; }
    public void setJobRank(int r) { jobRank = r; }
    public boolean isJailed() { return isJailed; }
    public void setJailed(boolean b) { isJailed = b; }
    public int getJailedTimeLeft() { return jailedTimeLeft; }
    public void setJailedTimeLeft(int t) { jailedTimeLeft = t; }
    public boolean isWanted() { return isWanted; }
    public void setWanted(boolean b) { isWanted = b; }
    public int getWantedLevel() { return wantedLevel; }
    public void setWantedLevel(int l) { wantedLevel = l; }
    public int getWantedTimeLeft() { return wantedTimeLeft; }
    public void setWantedTimeLeft(int t) { wantedTimeLeft = t; }
    public boolean isOnProbation() { return onProbation; }
    public void setOnProbation(boolean b) { onProbation = b; }
    public int getProbationTimeLeft() { return probationTimeLeft; }
    public void setProbationTimeLeft(int t) { probationTimeLeft = t; }
    public boolean isCuffed() { return cuffed; }
    public void setCuffed(boolean b) { cuffed = b; }
    public boolean isNoob() { return isNoob; }
    public void setNoob(boolean b) { isNoob = b; }
    public int getLevel() { return level; }
    public void setLevel(int l) { level = l; }
    public int getLevelExp() { return levelExp; }
    public void setLevelExp(int e) { levelExp = e; }
    public int getLoadingTimeLeft() { return loadingTimeLeft; }
    public void setLoadingTimeLeft(int t) { loadingTimeLeft = t; }
    public boolean isBreakGeneralTimer() { return breakGeneralTimer; }
    public void setBreakGeneralTimer(boolean b) { breakGeneralTimer = b; }
    public boolean isFuelCharging() { return isFuelCharging; }
    public void setFuelCharging(boolean b) { isFuelCharging = b; }
    public int getFuelChargingCant() { return fuelChargingCant; }
    public void setFuelChargingCant(int c) { fuelChargingCant = c; }
    public String getWantedFor() { return wantedFor; }
    public void setWantedFor(String s) { wantedFor = s; }
    public void setStunned(boolean b) { this.isStun = b; }
    public int getSida() { return sida; }
    public void setSida(int s) { sida = s; }
    public int getAnimo() { return animo; }
    public void setAnimo(int a) { animo = a; }
    public boolean isTogglingPSV() { return togglingPSV; }
    public void setTogglingPSV(boolean b) { togglingPSV = b; }
    public void setWorkingOut(boolean b) {}
    public boolean isWorking() { return isWorking; }
    public void setWorking(boolean b) { isWorking = b; }
    public int getIntelligence() { return intelligence; }
    public int getStamina() { return stamina; }
    public int getStrength() { return strength; }
    public int getHygiene() { return hygiene; }
    public void setHygiene(int h) { hygiene = h; }
    public int getPoop() { return poop; }
    public void setPoop(int p) { poop = p; }
    public int getCurEnergy() { return curEnergy; }
    public void setCurEnergy(int e) { curEnergy = e; }
    public int getMaxEnergy() { return maxEnergy; }
    public void setMaxEnergy(int e) { maxEnergy = e; }
    public boolean nearItem(String s, int i) { return true; }
    public int getPhone() { return 0; }
    public int getPhoneModelId() { return 0; }
    public String getPhoneNumber() { return ""; }
    public ConcurrentHashMap<Integer, PhoneAppOwned> getOwnedPhonesApps() { return ownedPhonesApps; }
    public void setUsingPhone(boolean b) {}
    public FarmingStats getFarmingStats() { return farmingStats; }
    public int getBankChequings() { return 0; }
    public int getBankSavings() { return 0; }
    public int getBankAccount() { return 0; }
    public int getIntelligenceExp() { return intelligenceExp; }
    public void setIntelligenceExp(int e) { intelligenceExp = e; }
    public void setTurfCapturing(boolean capturing) { this.turfCapturing = capturing; }
    public boolean isTurfCapturing() { return turfCapturing; }
}
""")

# TimerManager.java
write_file('Emulator/src/main/java/com/eu/habbo/habbohotel/habboroleplay/timers/TimerManager.java', """package com.eu.habbo.habbohotel.habboroleplay.timers;
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
            case "hunger": return new HungerTimer(type, client, time, forever, params);
            case "work": return new WorkTimer(type, client, time, forever, params);
            case "turfcapture": return new TurfCaptureTimer(client);
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
            case "hygiene": return new HygieneTimer(type, client, time, forever, params);
            case "poop": return new PoopTimer(type, client, time, forever, params);
            case "heal": return new HealTimer(type, client, time, forever, params);
        }
        return null;
    }
    public void endAllTimers() { for (RoleplayTimer timer : activeTimers.values()) { timer.endTimer(); } activeTimers.clear(); }
    public void stopTimer(String type) { RoleplayTimer timer = activeTimers.remove(type); if (timer != null) { timer.endTimer(); } }
}""")
