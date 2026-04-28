import os
import re

def write_file(path, content):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, 'w') as f: f.write(content)

# 1. Fix Habbo.java
path = 'Emulator/src/main/java/com/eu/habbo/habbohotel/users/Habbo.java'
with open(path, 'r') as f:
    lines = f.readlines()

limit = -1
for i, line in enumerate(lines):
    if 'public Set<Integer> getForbiddenClothing()' in line:
        brace_count = 0
        for j in range(i, len(lines)):
            brace_count += lines[j].count('{')
            brace_count -= lines[j].count('}')
            if brace_count == 0 and '{' in "".join(lines[i:j+1]):
                limit = j
                break
        break

if limit != -1:
    clean_lines = lines[:limit + 1]
    clean_lines.append('\n    private com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser habboRoleplay;\n')
    clean_lines.append('\n    public com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser getHabboRoleplay() {\n')
    clean_lines.append('        return this.habboRoleplay;\n    }\n')
    clean_lines.append('\n    public void setHabboRoleplay(com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser habboRoleplay) {\n')
    clean_lines.append('        this.habboRoleplay = habboRoleplay;\n    }\n}\n')
    write_file(path, "".join(clean_lines))

# 2. Fix RoleplayUser.java
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
    private int maxHealth, curHealth, maxEnergy, curEnergy, curAlcohol, maxAlcohol, armor, hunger, sida, hygiene, animo, poop;
    private int intelligence, strength, stamina, intelligenceExp, strengthExp, staminaExp;
    private boolean passiveMode, isStun, isDead, isJailed, isWanted, onProbation, cuffed, turfCapturing, isNoob, breakGeneralTimer, togglingPSV;
    private int deadTimeLeft, jailedTimeLeft, wantedLevel, wantedTimeLeft, probationTimeLeft, cuffedTimeLeft, loadingTimeLeft, turfFlagId, dynamite;
    private String wantedFor = "", phoneNumber = "", inApp = "", lastChat = "", lastWhatsChat = "", huntSkins = "", heridaName = "";
    private int phone, phoneModelId, drivingCarId, carFuel, fuelChargingCant, mecPriceTo, mecUserToRepair, mecCarToRepair, mecPartsTo, mecNewState, mecLvl, mecExp, camLvl, camExp, armLvl, armExp, basuLvl, basuExp, huntPoints, medicine, cigarettes, pills;
    private boolean usingPhone, updateChats, updateWhatsChats, viewShopPhones, isFuelCharging, isCamLoading, isCamUnLoading, isMecLoading, isWorkingOut, isStunned, isParalyzed;

    public RoleplayUser(GameClient client, ResultSet userRow) throws SQLException {
        this.client = client;
        this.cooldownManager = new CooldownManager(client);
        this.timerManager = new TimerManager(client);
        this.offerManager = new OfferManager(client);
        if (userRow != null) {
            this.id = userRow.getInt("id");
            this.level = userRow.getInt("level");
            this.levelExp = userRow.getInt("level_exp");
            // ... more init
        }
    }

    public void replenishStats() { this.curHealth = this.maxHealth; }
    public GameClient getClient() { return client; }
    public CooldownManager getCooldownManager() { return cooldownManager; }
    public TimerManager getTimerManager() { return timerManager; }
    public OfferManager getOfferManager() { return offerManager; }
    public FarmingStats getFarmingStats() { return farmingStats; }
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
    public void setWorkingOut(boolean b) { isWorkingOut = b; }
    public boolean isWorking() { return isWorking; }
    public void setWorking(boolean b) { isWorking = b; }
    public boolean isTurfCapturing() { return turfCapturing; }
    public void setTurfCapturing(boolean b) { turfCapturing = b; }
    public int getIntelligenceExp() { return intelligenceExp; }
    public void setIntelligenceExp(int e) { intelligenceExp = e; }
    public int getStrength() { return strength; }
    public void setStrength(int s) { strength = s; }
    public int getStamina() { return stamina; }
    public void setStamina(int s) { stamina = s; }
    public int getIntelligence() { return intelligence; }
    public void setIntelligence(int i) { intelligence = i; }
    public int getHygiene() { return hygiene; }
    public void setHygiene(int h) { hygiene = h; }
    public int getPoop() { return poop; }
    public void setPoop(int p) { poop = p; }
    public int getCurEnergy() { return curEnergy; }
    public void setCurEnergy(int e) { curEnergy = e; }
    public int getMaxEnergy() { return maxEnergy; }
    public void setMaxEnergy(int e) { maxEnergy = e; }
    public boolean nearItem(String s, int i) { return true; }
    public int getPhone() { return phone; }
    public int getPhoneModelId() { return phoneModelId; }
    public String getPhoneNumber() { return phoneNumber; }
    public ConcurrentHashMap<Integer, PhoneAppOwned> getOwnedPhonesApps() { return ownedPhonesApps; }
    public void setUsingPhone(boolean b) { usingPhone = b; }
    public int getBankChequings() { return bankChequings; }
    public int getBankSavings() { return bankSavings; }
    public int getBankAccount() { return bankAccount; }
}
""")

# 3. Fix TimerManager.java
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
    public void stopTimer(String type) {
        RoleplayTimer timer = activeTimers.remove(type);
        if (timer != null) { timer.endTimer(); }
    }
    public void endTimer(String type) { stopTimer(type); }
}
""")

# 4. Fix Cooldown.java
write_file('Emulator/src/main/java/com/eu/habbo/habbohotel/habboroleplay/cooldowns/Cooldown.java', """package com.eu.habbo.habbohotel.habboroleplay.cooldowns;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
public abstract class Cooldown {
    protected final GameClient client;
    protected final String type;
    protected int time, amount, timeLeft;
    protected boolean ended = false;
    private ScheduledFuture<?> timer;
    public Cooldown(String type, GameClient client, int time, int amount) {
        this.type = type; this.client = client; this.time = time; this.amount = amount;
        this.timer = Emulator.getThreading().getService().scheduleAtFixedRate(() -> {
            try { execute(); if (timeLeft <= 0) { endCooldown(); } } catch (Exception e) { endCooldown(); }
        }, time, time, TimeUnit.MILLISECONDS);
    }
    public void endCooldown() {
        if (ended) return;
        if (timer != null) { timer.cancel(false); timer = null; }
        if (client != null && client.getHabbo() != null && client.getHabbo().getHabboRoleplay() != null) {
            client.getHabbo().getHabboRoleplay().getCooldownManager().activeCooldowns.remove(type);
        }
        ended = true;
    }
    public abstract void execute();
    public String getType() { return type; }
    public int getTimeLeft() { return timeLeft; }
    public int getAmount() { return (int) (amount / 1000); }
}
""")

# 5. Fix FuelTimer.java
write_file('Emulator/src/main/java/com/eu/habbo/habbohotel/habboroleplay/timers/types/FuelTimer.java', """package com.eu.habbo.habbohotel.habboroleplay.timers.types;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
public class FuelTimer extends RoleplayTimer {
    public FuelTimer(GameClient client, int timeInSeconds) {
        super("fuel", client, 1000, true, null);
        this.timeLeft = timeInSeconds * 1000;
    }
    @Override
    public void execute() {
        if (client == null || client.getRoleplay() == null) { endTimer(); return; }
        timeLeft -= 1000;
        if (timeLeft <= 0) {
            int price = client.getRoleplay().getFuelChargingCant() * 2;
            client.getHabbo().whisper("Combustible Cargado! -$" + price, RoomChatMessageBubbles.getBubble(0));
            client.getHabbo().getHabboInfo().addCredits(-price);
            client.getRoleplay().setFuelCharging(false);
            endTimer();
        }
    }
}""")

# 6. Fix GameClient.java (Ensure getRoleplay uses getHabboRoleplay)
path = 'Emulator/src/main/java/com/eu/habbo/habbohotel/gameclients/GameClient.java'
with open(path, 'r') as f: content = f.read()
if 'public com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser getRoleplay()' not in content:
    limit = content.rfind('}')
    content = content[:limit] + """
    public com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser getRoleplay() {
        if (this.habbo != null) {
            return this.habbo.getHabboRoleplay();
        }
        return null;
    }
}
"""
    write_file(path, content)

# 7. Fix RoleplayGroupManager.java (Remove junk)
path = 'Emulator/src/main/java/com/eu/habbo/habbohotel/habboroleplay/groups/RoleplayGroupManager.java'
with open(path, 'r') as f: content = f.read()
content = content.replace('\\n', '\n')
write_file(path, content)

# 8. Fix commands shout calls and GameClientManager calls
for root, dirs, files in os.walk('Emulator/src/main/java/com/eu/habbo/habbohotel/commands/habboroleplay'):
    for file in files:
        if file.endswith('.java'):
            p = os.path.join(root, file)
            with open(p, 'r') as f: c = f.read()
            if 'import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;' not in c:
                c = c.replace('import com.eu.habbo.habbohotel.commands.Command;', 'import com.eu.habbo.habbohotel.commands.Command;\nimport com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;')
            if 'import com.eu.habbo.habbohotel.users.Habbo;' not in c:
                c = c.replace('package com.eu.habbo.habbohotel.commands.habboroleplay;', 'package com.eu.habbo.habbohotel.commands.habboroleplay;\nimport com.eu.habbo.habbohotel.users.Habbo;')

            c = re.sub(r'RoleplayManager\.shout\((.*?), (.*?), (.*?)\);', r'\1.getHabbo().shout(\2, RoomChatMessageBubbles.getBubble(\3));', c)
            c = re.sub(r'GameClient targetClient = .*?\.getGameClient\((.*?)\);',
                       r'Habbo targetHabbo = Emulator.getGameServer().getGameClientManager().getHabbo(\1);\n        if (targetHabbo == null) { gameClient.getHabbo().whisper("Usuario no encontrado.", RoomChatMessageBubbles.getBubble(1)); return true; }\n        GameClient targetClient = targetHabbo.getClient();', c)
            write_file(p, c)
