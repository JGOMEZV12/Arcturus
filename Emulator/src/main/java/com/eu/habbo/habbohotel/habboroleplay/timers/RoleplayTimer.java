package com.eu.habbo.habbohotel.habboroleplay.timers;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class RoleplayTimer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayTimer.class);
    protected final String type;
    protected final GameClient client;
    protected int time;
    protected boolean forever;
    protected Object[] params;
    protected int timeLeft = 0;
    protected int timeCount = 0;
    protected ScheduledFuture<?> timer;

    public RoleplayTimer(String type, GameClient client, int time, boolean forever, Object[] params) {
        this.type = type;
        this.client = client;
        this.time = time;
        this.forever = forever;
        this.params = params;

        this.timer = Emulator.getThreading().getService().scheduleAtFixedRate(() -> {
            try {
                execute();
                if (!forever && timeLeft <= 0) {
                    endTimer();
                }
            } catch (Exception e) {
                LOGGER.error("Error in RoleplayTimer execute", e);
                endTimer();
            }
        }, time, time, TimeUnit.MILLISECONDS);
    }

    public void endTimer() {
        if (timer != null) {
            timer.cancel(false);
            timer = null;
        }
        if (client != null && client.getRoleplay() != null) {
            client.getRoleplay().getTimerManager().activeTimers.remove(type);
        }
    }

    public abstract void execute();
}
