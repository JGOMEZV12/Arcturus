package com.eu.habbo.habbohotel.habboroleplay.groups;
import com.eu.habbo.habbohotel.gameclients.GameClient;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class RoleplayGroupManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayGroupManager.class);

    public static final ConcurrentHashMap<Integer, RoleplayGroup> jobs = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Integer, RoleplayGroup> gangs = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Integer, RoleplayGroupRank> genericGangRanks = new ConcurrentHashMap<>();

    public void initialize() {
        jobs.clear();
        gangs.clear();
        genericGangRanks.clear();

        loadGenericGangRanks();
        loadJobs();
        loadGangs();

        LOGGER.info("RoleplayGroupManager -> Loaded {} Jobs and {} Gangs.", jobs.size(), gangs.size());
    }

    private void loadGenericGangRanks() {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_gangs_ranks` WHERE `gang` = '1000'")) {

            while (row.next()) {
                int rankId = row.getInt("rank");
                String name = row.getString("name");
                String[] commands = row.getString("commands").split(",");
                int limit = row.getInt("limit");

                RoleplayGroupRank rank = new RoleplayGroupRank(1000, rankId, name, "", "", 0, commands, new String[]{}, limit);
                genericGangRanks.put(rankId, rank);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading generic gang ranks", e);
        }
    }

    private void loadJobs() {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_jobs`")) {

            while (row.next()) {
                int id = row.getInt("id");
                String name = row.getString("name");
                String desc = row.getString("desc");
                String badge = row.getString("badge");
                int ownerId = row.getInt("owner_id");
                int roomId = row.getInt("room_id");
                int balance = row.getInt("bank_balance");
                int stock = row.getInt("stock");
                boolean isGang = row.getBoolean("isGang");

                RoleplayGroup job = new RoleplayGroup(id, name, desc, badge, roomId, ownerId, balance, stock, isGang, false);
                loadJobRanks(job);
                loadJobMembers(job);
                loadLogs(job);
                jobs.put(id, job);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading jobs", e);
        }
    }

    private void loadJobRanks(RoleplayGroup job) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_jobs_ranks` WHERE `job` = '" + job.getId() + "'")) {

            while (row.next()) {
                int rankId = row.getInt("rank");
                String name = row.getString("name");
                String maleFigure = row.getString("male_figure");
                String femaleFigure = row.getString("female_figure");
                int pay = row.getInt("pay");
                String[] commands = row.getString("commands").split(",");
                String[] workRooms = row.getString("workrooms").split(",");
                int limit = row.getInt("limit");

                RoleplayGroupRank rank = new RoleplayGroupRank(job.getId(), rankId, name, maleFigure, femaleFigure, pay, commands, workRooms, limit);
                job.getRanks().put(rankId, rank);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading ranks for job " + job.getId(), e);
        }
    }

    private void loadJobMembers(RoleplayGroup job) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT id, job_rank FROM `rp_stats` WHERE `job_id` = '" + job.getId() + "'")) {

            while (row.next()) {
                int userId = row.getInt("id");
                int rank = row.getInt("job_rank");
                boolean isAdmin = rank == 6;

                RoleplayGroupMember member = new RoleplayGroupMember(job.getId(), userId, rank, isAdmin);
                job.getMembers().put(userId, member);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading members for job " + job.getId(), e);
        }
    }

    private void loadGangs() {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `rp_gangs`")) {

            while (row.next()) {
                int id = row.getInt("id");
                String name = row.getString("name");
                String desc = row.getString("desc");
                String badge = row.getString("badge");
                int ownerId = row.getInt("owner_id");
                int roomId = row.getInt("room_id");
                int balance = row.getInt("bank_balance");
                int stock = row.getInt("stock");
                boolean isGang = row.getBoolean("isGang");
                boolean bankruptcy = row.getBoolean("bankruptcy");

                RoleplayGroup gang = new RoleplayGroup(id, name, desc, badge, roomId, ownerId, balance, stock, isGang, bankruptcy);
                gang.getRanks().putAll(genericGangRanks);
                loadGangMembers(gang);
                loadLogs(gang);
                gangs.put(id, gang);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading gangs", e);
        }
    }

    private void loadGangMembers(RoleplayGroup gang) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT id, gang_rank FROM `rp_stats` WHERE `gang_id` = '" + gang.getId() + "'")) {

            while (row.next()) {
                int userId = row.getInt("id");
                int rank = row.getInt("gang_rank");
                boolean isAdmin = rank >= 5;

                RoleplayGroupMember member = new RoleplayGroupMember(gang.getId(), userId, rank, isAdmin);
                gang.getMembers().put(userId, member);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading members for gang " + gang.getId(), e);
        }
    }

    private void loadLogs(RoleplayGroup group) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet row = statement.executeQuery("SELECT * FROM `groups_logs` WHERE `group_id` = '" + group.getId() + "' ORDER BY timestamp DESC LIMIT 30")) {

            int count = 0;
            while (row.next()) {
                int userId = row.getInt("user_id");
                String action = row.getString("action");
                int cant = row.getInt("cant");
                long timestamp = row.getLong("timestamp");

                RoleplayGroupLog log = new RoleplayGroupLog(group.getId(), userId, action, cant, new Date(timestamp * 1000L));
                group.getLogs().put(count++, log);
            }
        } catch (Exception e) {
            LOGGER.error("Error loading logs for group " + group.getId(), e);
        }
    }

    public static RoleplayGroup getJob(int id) {
        return jobs.get(id);
    }

    public static RoleplayGroup getGang(int id) {
        return gangs.get(id);
    }

    public static RoleplayGroupRank getJobRank(int jobId, int rankId) {
        RoleplayGroup job = getJob(jobId);
        if (job != null) {
            return job.getRanks().get(rankId);
        }
        return null;
    }

    public static RoleplayGroupRank getGangRank(int gangId, int rankId) {
        RoleplayGroup gang = getGang(gangId);
        if (gang != null) {
            return gang.getRanks().get(rankId);
        }
        return null;
    }

    public static boolean hasJobCommand(GameClient session, String command) {
        if (session == null || session.getHabbo().getHabboRoleplay() == null) return false;

        int jobId = session.getHabbo().getHabboRoleplay().getJobId();
        int jobRankId = session.getHabbo().getHabboRoleplay().getJobRank();

        if (jobId <= 1) return false;

        RoleplayGroupRank rank = getJobRank(jobId, jobRankId);
        if (rank == null) return false;

        return rank.hasCommand(command);
    }

    public static boolean hasGangCommand(GameClient session, String command) {
        if (session == null || session.getHabbo().getHabboRoleplay() == null) return false;

        int gangId = session.getHabbo().getHabboRoleplay().getJobId(); // In Polar, Gang info is often in rp_stats under different columns but here I might need to clarify
        int gangRankId = session.getHabbo().getHabboRoleplay().getJobRank();

        if (gangId <= 1000) return false;

        RoleplayGroupRank rank = getGangRank(gangId, gangRankId);
        if (rank == null) return false;

        return rank.hasCommand(command);
    }
}
