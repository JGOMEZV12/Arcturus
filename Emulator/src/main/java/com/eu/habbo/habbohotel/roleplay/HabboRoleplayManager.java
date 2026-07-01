package com.eu.habbo.habbohotel.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * HabboRoleplayManager: Manages roleplay state and events for each connected user.
 * Initialized when user logs in (setHabbo called in GameClient).
 * Handles WebEvent communication through the same WebSocket connection.
 */
public class HabboRoleplayManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(HabboRoleplayManager.class);
    private static final ConcurrentHashMap<Integer, HabboRoleplayManager> ROLEPLAY_MANAGERS = new ConcurrentHashMap<>();

    private final GameClient gameClient;
    private final Habbo habbo;
    private final int userId;
    private final String sessionToken;
    private final long createdAt;
    private final AtomicBoolean isActive;
    private final RoleplayEventDispatcher eventDispatcher;
    private final RoleplayUserData roleplayData;

    public HabboRoleplayManager(GameClient gameClient, Habbo habbo) {
        this.gameClient = gameClient;
        this.habbo = habbo;
        this.userId = habbo.getId();
        this.sessionToken = generateSessionToken();
        this.createdAt = System.currentTimeMillis();
        this.isActive = new AtomicBoolean(true);
        this.eventDispatcher = new RoleplayEventDispatcher(this);
        this.roleplayData = new RoleplayUserData(userId);

        ROLEPLAY_MANAGERS.put(userId, this);
        LOGGER.info("HabboRoleplayManager initialized for user {} (session: {})", habbo.getUsername(), sessionToken);
    }

    /**
     * Initialize roleplay systems when user logs in
     */
    public void initialize() {
        try {
            // Load user roleplay data from database
            roleplayData.load();

            // Initialize event listeners
            eventDispatcher.initialize();

            LOGGER.debug("Roleplay systems initialized for user {}", habbo.getUsername());
        } catch (Exception e) {
            LOGGER.error("Error initializing roleplay for user {}", habbo.getUsername(), e);
        }
    }

    /**
     * Handle incoming WebEvent from /events endpoint
     */
    public void handleWebEvent(String eventType, String payload) {
        if (!isActive.get()) {
            LOGGER.warn("Received event for inactive roleplay session: {}", userId);
            return;
        }

        try {
            eventDispatcher.dispatch(eventType, payload);
        } catch (Exception e) {
            LOGGER.error("Error handling web event for user {}", userId, e);
        }
    }

    /**
     * Send a roleplay event to the client via WebSocket
     */
    public void sendRoleplayEvent(String eventType, String payload) {
        if (!isActive.get() || gameClient == null || !gameClient.getChannel().isOpen()) {
            return;
        }

        try {
            RoleplayEventMessage message = new RoleplayEventMessage(eventType, payload);
            gameClient.sendResponse(message);
        } catch (Exception e) {
            LOGGER.error("Error sending roleplay event to user {}", userId, e);
        }
    }

    /**
     * Cleanup when user disconnects
     */
    public void dispose() {
        if (!isActive.compareAndSet(true, false)) {
            return; // Already disposed
        }

        try {
            // Save user roleplay data
            if (roleplayData != null) {
                roleplayData.save();
            }

            // Cleanup event dispatcher
            if (eventDispatcher != null) {
                eventDispatcher.dispose();
            }

            ROLEPLAY_MANAGERS.remove(userId);
            LOGGER.info("HabboRoleplayManager disposed for user {}", habbo.getUsername());
        } catch (Exception e) {
            LOGGER.error("Error disposing roleplay for user {}", userId, e);
        }
    }

    /**
     * Generate a unique session token for WebEvent authentication
     */
    private String generateSessionToken() {
        return String.format("%d_%s_%d",
                userId,
                habbo.getUsername(),
                System.currentTimeMillis()
        );
    }

    // ============ Getters ============

    public GameClient getGameClient() {
        return gameClient;
    }

    public Habbo getHabbo() {
        return habbo;
    }

    public int getUserId() {
        return userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public boolean isActive() {
        return isActive.get();
    }

    public RoleplayEventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public RoleplayUserData getRoleplayData() {
        return roleplayData;
    }

    public long getSessionDurationMs() {
        return System.currentTimeMillis() - createdAt;
    }

    // ============ Static Accessors ============

    public static HabboRoleplayManager getInstance(int userId) {
        return ROLEPLAY_MANAGERS.get(userId);
    }

    public static HabboRoleplayManager getInstance(Habbo habbo) {
        return habbo != null ? getInstance(habbo.getId()) : null;
    }

    public static boolean isUserInRoleplay(int userId) {
        HabboRoleplayManager manager = getInstance(userId);
        return manager != null && manager.isActive();
    }
}
