package com.eu.habbo.networking.gameserver;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.gameclients.GameClientManager;
import com.eu.habbo.habbohotel.roleplay.HabboRoleplayManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebEventHandler: Handles WebSocket connections to /events endpoint.
 * Routes events to HabboRoleplayManager for authenticated users.
 */
public class WebEventHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebEventHandler.class);
    private WebSocketServerHandshaker handshaker;
    private GameClient linkedGameClient;
    private HabboRoleplayManager roleplayManager;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();

        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    /**
     * Handle initial HTTP upgrade request
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        try {
            // Check if this is a WebSocket upgrade request
            String uri = req.uri();

            if (!uri.equals("/events")) {
                LOGGER.warn("Invalid WebEvent URI: {}", uri);
                ctx.close();
                return;
            }

            // Try to authenticate the user from the channel
            authenticateUser(ctx);

            if (linkedGameClient == null || linkedGameClient.getHabbo() == null) {
                LOGGER.warn("WebEvent connection attempt from unauthenticated client");
                ctx.close();
                return;
            }

            // Perform WebSocket handshake
            WebSocketServerHandshakerFactory wsFactory =
                    new WebSocketServerHandshakerFactory("ws://localhost:30000/events", null, false);
            handshaker = wsFactory.newHandshaker(req);

            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), req);
                LOGGER.info("WebEvent connection established for user {}", linkedGameClient.getHabbo().getUsername());
            }
        } catch (Exception e) {
            LOGGER.error("Error handling HTTP request for WebEvent", e);
            ctx.close();
        }
    }

    /**
     * Handle WebSocket frames (messages)
     */
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        try {
            if (frame instanceof TextWebSocketFrame) {
                String message = ((TextWebSocketFrame) frame).text();
                handleWebEventMessage(message);
            }
        } catch (Exception e) {
            LOGGER.error("Error handling WebSocket frame", e);
            ctx.close();
        }
    }

    /**
     * Handle incoming WebEvent message
     */
    private void handleWebEventMessage(String message) {
        if (roleplayManager == null || !roleplayManager.isActive()) {
            LOGGER.warn("Received WebEvent for inactive roleplay session");
            return;
        }

        try {
            // Parse JSON message: {"event": "event_type", "data": {...}}
            // For now, assume message format: event_type:payload
            String[] parts = message.split(":", 2);
            if (parts.length >= 2) {
                String eventType = parts[0];
                String payload = parts[1];
                roleplayManager.handleWebEvent(eventType, payload);
            }
        } catch (Exception e) {
            LOGGER.error("Error handling WebEvent message", e);
        }
    }

    /**
     * Authenticate user by linking to existing GameClient
     * This assumes the WebSocket connection comes from the same client already connected
     */
    private void authenticateUser(ChannelHandlerContext ctx) {
        try {
            // Get all connected clients and find the one with matching remote address
            GameClientManager clientManager = Emulator.getGameServer().getGameClientManager();

            String remoteAddress = ctx.channel().remoteAddress().toString();

            // TODO: Implement proper authentication mechanism
            // For now, this is a placeholder. In production, you'd:
            // 1. Extract authentication token from WebSocket headers
            // 2. Validate token against session
            // 3. Link to corresponding GameClient

            // Search for client with matching address (temporary solution)
            // linkedGameClient = clientManager.getClientByRemoteAddress(remoteAddress);

            if (linkedGameClient != null && linkedGameClient.getHabbo() != null) {
                roleplayManager = HabboRoleplayManager.getInstance(linkedGameClient.getHabbo());
                if (roleplayManager == null) {
                    roleplayManager = new HabboRoleplayManager(linkedGameClient, linkedGameClient.getHabbo());
                    roleplayManager.initialize();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error authenticating WebEvent user", e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("WebEventHandler exception", cause);
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        LOGGER.debug("WebEvent connection closed");
        if (roleplayManager != null) {
            roleplayManager.dispose();
        }
    }
}
