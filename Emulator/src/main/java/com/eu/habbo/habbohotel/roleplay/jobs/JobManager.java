package com.eu.habbo.habbohotel.roleplay.jobs;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.RoleplayManager;
import com.eu.habbo.habbohotel.users.Habbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * JobManager: Manages job assignments and salary distributions.
 */
public class JobManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobManager.class);
    private static final ConcurrentHashMap<Integer, Job> JOBS = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Integer, Integer> USER_JOBS = new ConcurrentHashMap<>();

    /**
     * Initialize default jobs
     */
    public static void initialize() {
        addJob(1, "Police Officer", "Enforce the law and protect citizens", 500, 5);
        addJob(2, "Miner", "Extract valuable minerals from mines", 300, 3);
        addJob(3, "Farmer", "Cultivate crops for food production", 250, 2);
        addJob(4, "Chef", "Prepare food for the community", 400, 4);
        addJob(5, "Fisherman", "Catch fish for food and profit", 280, 3);
        addJob(6, "Merchant", "Buy and sell goods for profit", 600, 6);
        LOGGER.info("Job system initialized with {} jobs", JOBS.size());
    }

    /**
     * Add a job
     */
    private static void addJob(int jobId, String name, String description, int salary, int levelRequired) {
        Job job = new Job(jobId, name, description, salary, levelRequired);
        JOBS.put(jobId, job);
    }

    /**
     * Assign job to user
     */
    public static void assignJobToUser(Habbo habbo, int jobId) {
        if (habbo == null) return;

        Job job = JOBS.get(jobId);
        if (job == null) {
            LOGGER.warn("Attempt to assign non-existent job: {}", jobId);
            return;
        }

        USER_JOBS.put(habbo.getId(), jobId);
        LOGGER.info("Job assigned to user {}: {} (Salary: {})", habbo.getUsername(), job.getName(), job.getSalary());
    }

    /**
     * Remove job from user
     */
    public static void removeJobFromUser(Habbo habbo) {
        if (habbo == null) return;
        USER_JOBS.remove(habbo.getId());
        LOGGER.info("Job removed from user {}", habbo.getUsername());
    }

    /**
     * Get user's job
     */
    public static Job getUserJob(Habbo habbo) {
        if (habbo == null) return null;
        Integer jobId = USER_JOBS.get(habbo.getId());
        return jobId != null ? JOBS.get(jobId) : null;
    }

    /**
     * Pay salary to user
     */
    public static void paySalary(Habbo habbo) {
        if (habbo == null) return;
        Job job = getUserJob(habbo);
        if (job != null) {
            RoleplayManager.addMoney(habbo, job.getSalary());
            LOGGER.debug("Salary paid to {}: {}", habbo.getUsername(), job.getSalary());
        }
    }

    /**
     * Get job by ID
     */
    public static Job getJob(int jobId) {
        return JOBS.get(jobId);
    }

    /**
     * Job class
     */
    public static class Job {
        private final int id;
        private final String name;
        private final String description;
        private final int salary;
        private final int levelRequired;

        public Job(int id, String name, String description, int salary, int levelRequired) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.salary = salary;
            this.levelRequired = levelRequired;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public int getSalary() { return salary; }
        public int getLevelRequired() { return levelRequired; }
    }
}
