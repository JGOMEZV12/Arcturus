package com.eu.habbo.habbohotel.habboroleplay.timers.types;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser;
import com.eu.habbo.habbohotel.habboroleplay.timers.RoleplayTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TurfCaptureTimer extends RoleplayTimer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TurfCaptureTimer.class);

    public TurfCaptureTimer(GameClient client) {
        super("turfcapture", client, 1000, true, null);
        this.timeLeft = 60000;
    }

    @Override
    public void execute() {
        try {
            if (client.getHabbo() == null) return;
            RoleplayUser rpUser = client.getHabbo().getHabboRoleplay();
            if (rpUser == null || rpUser.isDead() || rpUser.isJailed() || client.getHabbo().getRoomUnit().isWalking()) {
                cancelTurfCapture();
                return;
            }

            timeCount++;
            timeLeft -= 1000;
            rpUser.setLoadingTimeLeft(timeLeft / 1000);

            if (timeLeft > 0) {
                if (timeCount == 60) {
                    client.getHabbo().talk("*Se acerca a capturar el barrio de pandillas [" + (timeLeft / 60000) + " Minutos restantes]*");
                    timeCount = 0;
                }
                return;
            }

            completeTurfCapture(rpUser);
        } catch (Exception e) {
            LOGGER.error("Error in TurfCaptureTimer", e);
            endTimer();
        }
    }

    private void cancelTurfCapture() {
        if (client.getHabbo() != null && client.getHabbo().getHabboRoleplay() != null) {
            client.getHabbo().getHabboRoleplay().setTurfCapturing(false);
        }
        endTimer();
    }

    private void completeTurfCapture(RoleplayUser rpUser) {
        if (client.getHabbo() != null) {
            client.getHabbo().talk("*Captura con éxito el barrio de pandillas*");
        }
        rpUser.setTurfCapturing(false);
        rpUser.setLoadingTimeLeft(0);
        endTimer();
    }
}
