package com.eu.habbo.habbohotel.habboroleplay.groups;

import java.util.Date;

public class RoleplayGroupLog {
    private int groupId;
    private int userId;
    private String action;
    private int cant;
    private Date timestamp;

    public RoleplayGroupLog(int groupId, int userId, String action, int cant, Date timestamp) {
        this.groupId = groupId;
        this.userId = userId;
        this.action = action;
        this.cant = cant;
        this.timestamp = timestamp;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    public int getCant() {
        return cant;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
