package com.eu.habbo.habbohotel.habboroleplay.groups;

import java.util.Arrays;

public class RoleplayGroupRank {
    private int groupId;
    private int rankId;
    private String name;
    private String maleFigure;
    private String femaleFigure;
    private int pay;
    private String[] commands;
    private String[] workRooms;
    private int limit;

    public RoleplayGroupRank(int groupId, int rankId, String name, String maleFigure, String femaleFigure, int pay, String[] commands, String[] workRooms, int limit) {
        this.groupId = groupId;
        this.rankId = rankId;
        this.name = name;
        this.maleFigure = maleFigure;
        this.femaleFigure = femaleFigure;
        this.pay = pay;
        this.commands = commands;
        this.workRooms = workRooms;
        this.limit = limit;
    }

    public boolean hasCommand(String command) {
        return Arrays.stream(commands).anyMatch(c -> c.equalsIgnoreCase(command));
    }

    public boolean canWorkHere(int roomId) {
        String roomIdStr = String.valueOf(roomId);
        return Arrays.stream(workRooms).anyMatch(r -> r.equals("*") || r.equals(roomIdStr));
    }

    public int getGroupId() {
        return groupId;
    }

    public int getRankId() {
        return rankId;
    }

    public String getName() {
        return name;
    }

    public String getMaleFigure() {
        return maleFigure;
    }

    public String getFemaleFigure() {
        return femaleFigure;
    }

    public int getPay() {
        return pay;
    }

    public int getLimit() {
        return limit;
    }
}
