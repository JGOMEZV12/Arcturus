package com.eu.habbo.habbohotel.habboroleplay.roleplayusers;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.cooldowns.CooldownManager;
import com.eu.habbo.habbohotel.habboroleplay.timers.TimerManager;
import com.eu.habbo.habbohotel.habboroleplay.roleplayusers.offers.OfferManager;
import com.eu.habbo.habbohotel.habboroleplay.farming.models.FarmingStats;
import com.eu.habbo.habbohotel.habboroleplay.phones.apps.PhoneAppOwned;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
public class RoleplayUser {
    private GameClient client;
    private final CooldownManager cooldownManager;
    private final TimerManager timerManager;
    private final OfferManager offerManager;
    private FarmingStats farmingStats;
    private final ConcurrentHashMap<Integer, PhoneAppOwned> ownedPhonesApps = new ConcurrentHashMap<>();
    private int curHealth, maxHealth, hunger, deadTimeLeft, jobId, jobRank, jailedTimeLeft, wantedLevel, wantedTimeLeft, probationTimeLeft, level, levelExp, loadingTimeLeft, fuelChargingCant, sida, animo, curEnergy, maxEnergy, bankChequings, bankSavings, bankAccount, phone, phoneModelId, mecParts;
    private boolean isDead, isJailed, isWanted, onProbation, cuffed, noob, breakGeneralTimer, fuelCharging, togglingPSV, turfCapturing, isStun, isWorking, isWorkingOut;
    private String wantedFor = "", phoneNumber = "";
    public RoleplayUser(GameClient client, ResultSet userRow) throws SQLException {
        this.client = client;
        this.cooldownManager = new CooldownManager(client);
        this.timerManager = new TimerManager(client);
        this.offerManager = new OfferManager(client);
    }
    public GameClient getClient() { return client; }
    public void setClient(GameClient client) { this.client = client; }
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
    public boolean isNoob() { return noob; }
    public void setNoob(boolean b) { noob = b; }
    public int getLevel() { return level; }
    public void setLevel(int l) { level = l; }
    public int getLevelExp() { return levelExp; }
    public void setLevelExp(int e) { levelExp = e; }
    public int getLoadingTimeLeft() { return loadingTimeLeft; }
    public void setLoadingTimeLeft(int t) { loadingTimeLeft = t; }
    public boolean isBreakGeneralTimer() { return breakGeneralTimer; }
    public void setBreakGeneralTimer(boolean b) { breakGeneralTimer = b; }
    public boolean isFuelCharging() { return fuelCharging; }
    public void setFuelCharging(boolean b) { fuelCharging = b; }
    public int getFuelChargingCant() { return fuelChargingCant; }
    public void setFuelChargingCant(int c) { fuelChargingCant = c; }
    public String getWantedFor() { return wantedFor; }
    public void setWantedFor(String s) { wantedFor = s; }
    public void setStunned(boolean b) { this.isStun = b; }
    public int getSida() { return sida; }
    public void setSida(int s) { sida = s; }
    public int getAnimo() { return animo; }
    public void setAnimo(int a) { animo = a; }
    public int getCurEnergy() { return curEnergy; }
    public void setCurEnergy(int e) { curEnergy = e; }
    public int getMaxEnergy() { return maxEnergy; }
    public void setMaxEnergy(int e) { maxEnergy = e; }
    public boolean isWorking() { return isWorking; }
    public void setWorking(boolean b) { isWorking = b; }
    public boolean isTurfCapturing() { return turfCapturing; }
    public void setTurfCapturing(boolean b) { turfCapturing = b; }
    public int getPhone() { return phone; }
    public void setPhone(int p) { phone = p; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String s) { phoneNumber = s; }
    public int getPhoneModelId() { return phoneModelId; }
    public void setPhoneModelId(int id) { phoneModelId = id; }
    public ConcurrentHashMap<Integer, PhoneAppOwned> getOwnedPhonesApps() { return ownedPhonesApps; }
    public void setUsingPhone(boolean b) {}
    public void setWorkingOut(boolean b) { isWorkingOut = b; }
    public void replenishStats() { this.curHealth = this.maxHealth; }
    public boolean nearItem(String s, int d) { return true; }
    public int getBankChequings() { return bankChequings; }
    public void setBankChequings(int b) { bankChequings = b; }
    public int getBankSavings() { return bankSavings; }
    public void setBankSavings(int b) { bankSavings = b; }
    public int getBankAccount() { return bankAccount; }
    public void setBankAccount(int b) { bankAccount = b; }
    public int getMecParts() { return mecParts; }
    public void setMecParts(int p) { mecParts = p; }
}
