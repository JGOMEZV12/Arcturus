package com.eu.habbo.habbohotel.habboroleplay.bots;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.bots.manager.RoleplayBotManager;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RoleplayBot {
    private int id;
    private String name;
    private String figure;
    private String gender;
    private String motto;
    private int virtualId;
    private int maxHealth;
    private int curHealth;
    private int strength;
    private int level;
    private int spawnId;
    private boolean deployed;
    private boolean dead;
    private int x;
    private int y;
    private double z;
    private int spawnRot;

    private RoleplayBotAIType aiType;
    private String aiTypeString;

    private int ownerId;
    private String petData;

    private int roamInterval;
    private int attackInterval;
    private int followInterval;
    private int roomStayInterval;

    private boolean roamBot;
    private boolean roamCityBot;
    private boolean addableBot;
    private int corporationId;
    private String stopWorkItem;
    private String workUniform;
    private boolean canBeAttacked;
    private int attackPos;
    private String actionOdds;
    private int speechTimer;

    public RoleplayBot(int id, int ownerId, String name, String gender, String figure, String motto, int maxHealth, int curHealth, int strength, int level, int spawnId, int x, int y, double z, int spawnRot, String aiTypeString, RoleplayBotAIType aiType, int roamInterval, int attackInterval, int followInterval, int roomStayInterval, boolean roamBot, boolean roamCityBot, boolean addableBot, int corporationId, String stopWorkItem, String workUniform, boolean canBeAttacked, int attackPos, String actionOdds, int speechTimer, String petData) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.gender = gender;
        this.figure = figure;
        this.motto = motto;
        this.maxHealth = maxHealth;
        this.curHealth = curHealth;
        this.strength = strength;
        this.level = level;
        this.spawnId = spawnId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.spawnRot = spawnRot;
        this.aiTypeString = aiTypeString;
        this.aiType = aiType;
        this.roamInterval = roamInterval;
        this.attackInterval = attackInterval;
        this.followInterval = followInterval;
        this.roomStayInterval = roomStayInterval;
        this.roamBot = roamBot;
        this.roamCityBot = roamCityBot;
        this.addableBot = addableBot;
        this.corporationId = corporationId;
        this.stopWorkItem = stopWorkItem;
        this.workUniform = workUniform;
        this.canBeAttacked = canBeAttacked;
        this.attackPos = attackPos;
        this.actionOdds = actionOdds;
        this.speechTimer = speechTimer;
        this.petData = petData;

        this.deployed = false;
        this.dead = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSpawnId() {
        return spawnId;
    }

    public RoleplayBotAIType getAiType() {
        return aiType;
    }

    public boolean isRoamBot() {
        return roamBot;
    }

    public int getCorporationId() {
        return corporationId;
    }

    // Add other getters and setters as needed
}
