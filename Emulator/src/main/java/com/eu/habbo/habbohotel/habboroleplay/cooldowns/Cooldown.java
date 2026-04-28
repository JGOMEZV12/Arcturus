package com.eu.habbo.habbohotel.habboroleplay.cooldowns;
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
