package com.eu.habbo.habbohotel.habboroleplay.misc;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.habboroleplay.groups.RoleplayGroup;
import com.eu.habbo.habbohotel.habboroleplay.groups.RoleplayGroupManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class WorkManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkManager.class);
    public static final ConcurrentHashMap<Integer, List<Integer>> workingUsersList = new ConcurrentHashMap<>();

    public static void initialize() {
        LOGGER.info("WorkManager -> Loaded!");
    }

    public static void addWorkerToList(GameClient session) {
        int jobId = session.getHabbo().getHabboRoleplay().getJobId();
        if (jobId <= 1) return;

        RoleplayGroup job = RoleplayGroupManager.getJob(jobId);
        if (job == null) return;

        workingUsersList.computeIfAbsent(jobId, k -> new ArrayList<>()).add(session.getHabbo().getHabboInfo().getId());

        // Deactivate bots for this job if they are working
        // TODO: Implement bot deactivation logic
    }

    public static void removeWorkerFromList(GameClient session) {
        int jobId = session.getHabbo().getHabboRoleplay().getJobId();
        if (jobId <= 1) return;

        List<Integer> workers = workingUsersList.get(jobId);
        if (workers != null) {
            workers.remove((Integer)session.getHabbo().getHabboInfo().getId());
            if (workers.isEmpty()) {
                // Reactivate bots for this job
                // TODO: Implement bot reactivation logic
            }
        }
    }
}
