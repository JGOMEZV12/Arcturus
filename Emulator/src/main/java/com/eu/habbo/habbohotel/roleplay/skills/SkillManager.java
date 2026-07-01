package com.eu.habbo.habbohotel.roleplay.skills;

import com.eu.habbo.habbohotel.roleplay.HabboRoleplayManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * SkillManager: Manages user skills and leveling system.
 */
public class SkillManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkillManager.class);
    private final HabboRoleplayManager roleplayManager;
    private final ConcurrentHashMap<String, Skill> skills;

    public SkillManager(HabboRoleplayManager roleplayManager) {
        this.roleplayManager = roleplayManager;
        this.skills = new ConcurrentHashMap<>();
        initializeDefaultSkills();
    }

    /**
     * Initialize default skills
     */
    private void initializeDefaultSkills() {
        addSkill("combat", 1, 0);
        addSkill("farming", 1, 0);
        addSkill("fishing", 1, 0);
        addSkill("cooking", 1, 0);
        addSkill("mining", 1, 0);
    }

    /**
     * Add a skill to the user
     */
    public void addSkill(String skillName, int level, int experience) {
        Skill skill = new Skill(skillName, level, experience);
        skills.put(skillName, skill);
        LOGGER.debug("Skill added: {} (Level: {})", skillName, level);
    }

    /**
     * Add experience to a skill
     */
    public void addSkillExperience(String skillName, int experience) {
        Skill skill = skills.get(skillName);
        if (skill != null) {
            skill.addExperience(experience);
            
            // Check for level up
            if (skill.shouldLevelUp()) {
                skill.levelUp();
                LOGGER.info("Skill level up: {} - Level {}", skillName, skill.getLevel());
            }
        }
    }

    /**
     * Get skill
     */
    public Skill getSkill(String skillName) {
        return skills.get(skillName);
    }

    /**
     * Get all skills
     */
    public ConcurrentHashMap<String, Skill> getSkills() {
        return skills;
    }

    /**
     * Skill class
     */
    public static class Skill {
        private final String name;
        private int level;
        private int experience;
        private static final int EXP_PER_LEVEL = 1000;

        public Skill(String name, int level, int experience) {
            this.name = name;
            this.level = level;
            this.experience = experience;
        }

        public void addExperience(int amount) {
            this.experience += amount;
        }

        public boolean shouldLevelUp() {
            return experience >= (level * EXP_PER_LEVEL);
        }

        public void levelUp() {
            this.level++;
            this.experience = 0;
        }

        public String getName() { return name; }
        public int getLevel() { return level; }
        public int getExperience() { return experience; }
    }
}
