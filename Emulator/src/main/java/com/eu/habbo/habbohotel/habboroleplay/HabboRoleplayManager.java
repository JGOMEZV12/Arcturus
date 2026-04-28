package com.eu.habbo.habbohotel.habboroleplay;

import com.eu.habbo.habbohotel.habboroleplay.apartments.ApartmentManager;
import com.eu.habbo.habbohotel.habboroleplay.combat.CombatManager;
import com.eu.habbo.habbohotel.habboroleplay.comodin.ComodinManager;
import com.eu.habbo.habbohotel.habboroleplay.events.EventManager;
import com.eu.habbo.habbohotel.habboroleplay.farming.FarmingManager;
import com.eu.habbo.habbohotel.habboroleplay.groups.RoleplayGroupManager;
import com.eu.habbo.habbohotel.habboroleplay.hechizos.HechizosManager;
import com.eu.habbo.habbohotel.habboroleplay.phones.apps.PhoneAppManager;
import com.eu.habbo.habbohotel.habboroleplay.phones.chat.PhoneChatManager;
import com.eu.habbo.habbohotel.habboroleplay.phones.owned.PhoneOwnedManager;
import com.eu.habbo.habbohotel.habboroleplay.misc.RoleplayData;
import com.eu.habbo.habbohotel.habboroleplay.misc.WorkManager;
import com.eu.habbo.habbohotel.habboroleplay.misc.hunt.HuntManager;
import com.eu.habbo.habbohotel.habboroleplay.phones.PhoneManager;
import com.eu.habbo.habbohotel.habboroleplay.products.ProductsManager;
import com.eu.habbo.habbohotel.habboroleplay.rproom.RPRoomManager;
import com.eu.habbo.habbohotel.habboroleplay.vehicles.VehicleManager;
import com.eu.habbo.habbohotel.habboroleplay.weapons.WeaponManager;
import com.eu.habbo.habbohotel.habboroleplay.web.WebEventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HabboRoleplayManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(HabboRoleplayManager.class);

    private RPRoomManager rpRoomManager;
    private WebEventManager webEventManager;
    private RoleplayGroupManager roleplayGroupManager;
    private PhoneAppManager phoneAppManager;
    private PhoneOwnedManager phoneOwnedManager;
    private PhoneChatManager phoneChatManager;

    public HabboRoleplayManager() {
    }

    public void init() {
        RoleplayData.initialize();
        EventManager.initialize();
        CombatManager.initialize();

        this.rpRoomManager = new RPRoomManager();
        this.rpRoomManager.init();

        this.webEventManager = new WebEventManager();
        this.webEventManager.init();

        this.roleplayGroupManager = new RoleplayGroupManager();
        this.roleplayGroupManager.initialize();

        this.phoneAppManager = new PhoneAppManager();
        this.phoneAppManager.initialize();

        this.phoneOwnedManager = new PhoneOwnedManager();
        this.phoneOwnedManager.initialize();

        this.phoneChatManager = new PhoneChatManager();
        this.phoneChatManager.initialize();

        WeaponManager.initialize();
        VehicleManager.initialize();
        PhoneManager.initialize();
        ProductsManager.initialize();
        ApartmentManager.initialize();
        FarmingManager.initialize();
        HuntManager.initialize();
        WorkManager.initialize();
        ComodinManager.initialize();
        HechizosManager.initialize();

        LOGGER.info("HabboRoleplayManager -> Loaded!");
    }

    public RPRoomManager getRpRoomManager() {
        return rpRoomManager;
    }

    public WebEventManager getWebEventManager() {
        return webEventManager;
    }

    public void dispose() {
        LOGGER.info("HabboRoleplayManager -> Disposed!");
    }
}
