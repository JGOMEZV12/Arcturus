package com.eu.habbo.habbohotel.habboroleplay.groups;

public class RoleplayGroupMember {
    private int groupId;
    private int userId;
    private int userRank;
    private boolean isAdmin;

    public RoleplayGroupMember(int groupId, int userId, int userRank, boolean isAdmin) {
        this.groupId = groupId;
        this.userId = userId;
        this.userRank = userRank;
        this.isAdmin = isAdmin;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getUserId() {
        return userId;
    }

    public int getUserRank() {
        return userRank;
    }

    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
