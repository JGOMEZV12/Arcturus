package com.eu.habbo.habbohotel.habboroleplay.web.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.phones.Phone;
import com.eu.habbo.habbohotel.habboroleplay.phones.PhoneManager;
import com.eu.habbo.habbohotel.habboroleplay.phones.apps.PhoneApp;
import com.eu.habbo.habbohotel.habboroleplay.phones.apps.PhoneAppManager;
import com.eu.habbo.habbohotel.habboroleplay.phones.apps.PhoneAppOwned;
import com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser;
import com.eu.habbo.habbohotel.habboroleplay.web.IWebEvent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PhoneWebEvent implements IWebEvent {
    @Override
    public void execute(GameClient client, String data) {
        RoleplayUser rpUser = client.getHabbo().getHabboRoleplay();
        if (rpUser == null) return;

        String action = data.contains(",") ? data.split(",")[0] : data;

        switch (action) {
            case "open_phone":
                rpUser.setUsingPhone(true);
                Emulator.getGameEnvironment().getHabboRoleplayManager().getWebEventManager().sendDataDirect(client, "compose_phone|open_phone|");
                break;
            case "close_phone":
                rpUser.setUsingPhone(false);
                Emulator.getGameEnvironment().getHabboRoleplayManager().getWebEventManager().sendDataDirect(client, "compose_phone|close_phone|");
                break;
            case "load_apps":
                loadApps(client, rpUser);
                break;
            // Add more cases from the C# code
        }
    }

    private void loadApps(GameClient client, RoleplayUser rpUser) {
        StringBuilder screenHtml = new StringBuilder();
        StringBuilder dockHtml = new StringBuilder();

        Phone phone = PhoneManager.phones.get(rpUser.getPhoneModelId()); // This might need a mapping from modelId to modelName
        // For simplicity, let's assume we can get it

        List<PhoneAppOwned> ownedApps = rpUser.getOwnedPhonesApps().values().stream()
                .sorted(Comparator.comparingInt(PhoneAppOwned::getScreenId).thenComparingInt(PhoneAppOwned::getSlotId))
                .collect(Collectors.toList());

        for (PhoneAppOwned owned : ownedApps) {
            // Logic to build HTML for each app based on screenId
            // screenId 0 is dock
            // ...
        }

        Emulator.getGameEnvironment().getHabboRoleplayManager().getWebEventManager().sendDataDirect(client, "compose_phone|load_apps|" + screenHtml + "|" + dockHtml);
    }
}
