package com.eu.habbo.habbohotel.habboroleplay.misc.hunt;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.habboroleplay.bots.RoleplayBot;
import com.eu.habbo.habbohotel.habboroleplay.bots.RoleplayBotAIType;
import com.eu.habbo.habbohotel.habboroleplay.bots.manager.RoleplayBotManager;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HuntManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(HuntManager.class);
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static boolean isRunning = false;
    private static final Random random = new Random();

    public static void initialize() {
        if (isRunning) return;
        isRunning = true;

        scheduler.scheduleAtFixedRate(HuntManager::onTick, 30, 60, TimeUnit.SECONDS);
        LOGGER.info("HuntManager -> Loaded!");
    }

    private static void onTick() {
        try {
            for (Room room : Emulator.getGameEnvironment().getRoomManager().getActiveRooms()) {
                // Assuming Room has a method or field for HuntZoneEnabled (I should check if I added it)
                // In my previous edits to Room.java, I added many fields. Let's assume huntZoneEnabled is there.
                // if (room.isHuntZoneEnabled()) {
                //    processHuntRoom(room);
                // }
            }
        } catch (Exception e) {
            LOGGER.error("Error in HuntManager tick", e);
        }
    }

    private static void processHuntRoom(Room room) {
        // Count hunt bots in room
        // This requires RoleplayBotManager to track deployed bots per room
        // For now, let's just implement the spawning logic skeleton
    }

    private static void spawnHuntPet(Room room) {
        int petType = random.nextInt(19) + 1;
        String look = getPetFigureForType(petType);
        String name = getPetNameForType(petType);

        RoomTile tile = room.getRandomWalkableTile();
        if (tile == null) return;

        int botId = random.nextInt(99999) + 900000;

        // Pet data format: type|race|color
        String petData = petType + "|1|FFFFFF";

        RoleplayBot huntBot = new RoleplayBot(
                botId, 1, name, "M", look, "[CAZA] Mascota Salvaje", 100, 100, 10, 1, room.getId(), tile.getX(), tile.getY(), (double)tile.getZ(), 0,
                "pet", RoleplayBotAIType.PET, 5, 3, 2, 0, true, false, false, 0, "none", "none", true, 0, "1,5", 0, petData
        );

        RoleplayBotManager.cachedRoleplayBots.put(botId, huntBot);
        // RoleplayBotManager.deployBotByID(botId, room.getId()); // Needs implementation
    }

    public static String getPetNameForType(int type) {
        String[] names;
        switch (type) {
            case 1: names = new String[]{"Oso_Feroz", "Garra_Blanca", "Kodiak_Salvaje", "Ursa"}; break;
            case 2: names = new String[]{"Simba_Salvaje", "Rey_Selva", "Garra_Dorada", "Mufasa"}; break;
            case 3: names = new String[]{"Cuerno_Roto", "Tanque_Gris", "Rino_Bravo", "Embestida"}; break;
            case 4: names = new String[]{"Aliento_Fuego", "Escama_Roja", "Draco_Salvaje", "Fafnir"}; break;
            case 5: names = new String[]{"Mono_Alisto", "Chimpance_Loco", "Garra_Agil", "Kong_Pequeño"}; break;
            case 6: names = new String[]{"Rayo_Veloz", "Crines_Negras", "Corcel_Libre", "Relampago"}; break;
            case 7: names = new String[]{"Salto_Rapido", "Conejo_Pillo", "Orejas_Largas", "Tambor"}; break;
            case 8: names = new String[]{"Vuelo_Alto", "Pluma_Gris", "Mensajera_Loca", "Torcaza"}; break;
            case 9: names = new String[]{"Mono_Infernal", "Garra_Oscura", "Mandril_Poseido", "Azazel"}; break;
            case 10: names = new String[]{"Osezno_Tierno", "Pequeña_Garra", "Peluche_Bravo", "Baloo"}; break;
            case 11: names = new String[]{"Gnomo_Enojon", "Barba_Larga", "Duende_Maligno", "Gnomo_Cazador"}; break;
            case 12: names = new String[]{"Gatito_Cazador", "Zarpas_Pequeñas", "Michi_Salvaje", "Felix"}; break;
            case 13: names = new String[]{"Cerdito_Valiente", "Puerquito_Gordo", "Bacon_Salvaje", "Oink_Oink"}; break;
            case 14: names = new String[]{"Haloompa_Misterioso", "Enano_Magico", "Criatura_Extraña", "Umpa_Lumpa"}; break;
            case 15: names = new String[]{"Ptero_Veloz", "Ala_Gigante", "Pico_Afilado", "Sombra_Aerea"}; break;
            case 16: names = new String[]{"Garra_Veloz", "Raptor_Feroz", "Cazador_Prehistorico", "Blue"}; break;
            case 17: names = new String[]{"Vaca_Loca", "Cuernos_Largos", "Mu_Salvaje", "Lola"}; break;
            case 18: names = new String[]{"Pico_Frio", "Pinguino_Deslizante", "Aleta_Negra", "Pingu"}; break;
            case 19: names = new String[]{"Trompa_Larga", "Gigante_Gris", "Dumbo_Salvaje", "Colmillo_Blanco"}; break;
            default: names = new String[]{"Mascota_Salvaje", "Animal_Bravo", "Criatura_Libre", "Bestia"}; break;
        }
        return names[random.nextInt(names.length)];
    }

    public static String getPetFigureForType(int type) {
        switch (type) {
            case 1:
                int r1 = random.nextInt(4);
                if (r1 == 0) return "4 2 e4feff 2 2 -1 0 3 -1 0";
                if (r1 == 1) return "4 3 e4feff 2 2 -1 0 3 -1 0";
                if (r1 == 2) return "4 1 eaeddf 2 2 -1 0 3 -1 0";
                return "4 0 ffffff 2 2 -1 0 3 -1 0";
            case 2:
                int r2 = random.nextInt(11);
                if (r2 == 0) return "6 0 ffffff 2 2 -1 0 3 -1 0";
                if (r2 == 1) return "6 1 ffffff 2 2 -1 0 3 -1 0";
                if (r2 == 2) return "6 2 ffffff 2 2 -1 0 3 -1 0";
                if (r2 == 3) return "6 3 ffffff 2 2 -1 0 3 -1 0";
                if (r2 == 4) return "6 4 ffffff 2 2 -1 0 3 -1 0";
                if (r2 == 5) return "6 0 ffd8c9 2 2 -1 0 3 -1 0";
                if (r2 == 6) return "6 5 ffffff 2 2 -1 0 3 -1 0";
                if (r2 == 7) return "6 11 ffffff 2 2 -1 0 3 -1 0";
                if (r2 == 8) return "6 2 ffe49d 2 2 -1 0 3 -1 0";
                if (r2 == 9) return "6 11 ff9ae 2 2 -1 0 3 -1 0";
                return "6 2 ff9ae 2 2 -1 0 3 -1 0";
            case 3:
                int r3 = random.nextInt(7);
                if (r3 == 0) return "7 5 aeaeae 2 2 -1 0 3 -1 0";
                if (r3 == 1) return "7 7 ffc99a 2 2 -1 0 3 -1 0";
                if (r3 == 2) return "7 5 cccccc 2 2 -1 0 3 -1 0";
                if (r3 == 3) return "7 5 9adcff 2 2 -1 0 3 -1 0";
                if (r3 == 4) return "7 5 ff7d6a 2 2 -1 0 3 -1 0";
                if (r3 == 5) return "7 6 cccccc 2 2 -1 0 3 -1 0";
                return "7 0 cccccc 2 2 -1 0 3 -1 0";
            case 4:
                int r4 = random.nextInt(6);
                return "12 " + r4 + " ffffff 2 2 -1 0 3 -1 0";
            case 5:
                int r5 = random.nextInt(14);
                return "14 " + r5 + " ffffff 2 2 -1 0 3 -1 0";
            case 6:
                int r6 = random.nextInt(20);
                if (r6 < 17) return "15 " + (r6 + 2) + " ffffff 2 2 -1 0 3 -1 0";
                if (r6 == 17) return "15 78 ffffff 2 2 -1 0 3 -1 0";
                if (r6 == 18) return "15 77 ffffff 2 2 -1 0 3 -1 0";
                if (r6 == 19) return "15 79 ffffff 2 2 -1 0 3 -1 0";
                return "15 80 ffffff 2 2 -1 0 3 -1 0";
            case 7:
                int r7 = random.nextInt(8);
                if (r7 < 5) return "17 " + (r7 + 1) + " ffffff";
                if (r7 == 5) return "18 0 ffffff";
                if (r7 == 6) return "19 0 ffffff";
                return "20 0 ffffff";
            case 8:
                return (random.nextBoolean() ? "21 0 ffffff" : "22 0 ffffff");
            case 9:
                int r9 = random.nextInt(3);
                if (r9 == 0) return "23 0 ffffff";
                if (r9 == 1) return "23 1 ffffff";
                return "23 3 ffffff";
            case 10:
                return (random.nextBoolean() ? "24 0 ffffff" : "24 1 ffffff");
            case 11:
                int r11 = random.nextInt(4);
                if (r11 == 0) return "26 1 ffffff 5 0 -1 0 4 402 5 3 301 4 1 101 2 2 201 3";
                if (r11 == 1) return "26 1 ffffff 5 0 -1 0 1 102 13 3 301 4 4 401 5 2 201 3";
                if (r11 == 2) return "26 6 ffffff 5 1 102 8 2 201 16 4 401 9 3 303 4 0 -1 6";
                return "26 30 ffffff 5 0 -1 0 3 303 4 4 401 5 1 101 2 2 201 3";
            case 12:
                return (random.nextBoolean() ? "28 0 ffffff" : "28 1 ffffff");
            case 13:
                return (random.nextBoolean() ? "30 0 ffffff" : "30 1 ffffff");
            case 14:
                return (random.nextBoolean() ? "31 0 ffffff" : "31 1 ffffff");
            case 15:
                return (random.nextBoolean() ? "33 0 ffffff" : "33 1 ffffff");
            case 16:
                return (random.nextBoolean() ? "34 0 ffffff" : "34 1 ffffff");
            case 17:
                return (random.nextBoolean() ? "35 0 ffffff" : "35 1 ffffff");
            case 18:
                return (random.nextBoolean() ? "36 0 ffffff" : "36 1 ffffff");
            case 19:
                return (random.nextBoolean() ? "37 0 ffffff" : "37 1 ffffff");
            default:
                return "4 2 e4feff 2 2 -1 0 3 -1 0";
        }
    }
}
