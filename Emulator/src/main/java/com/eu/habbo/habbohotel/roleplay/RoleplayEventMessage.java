package com.eu.habbo.habbohotel.roleplay;

import com.eu.habbo.messages.ServerMessage;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * RoleplayEventMessage: Wraps roleplay events as ServerMessages for transmission.
 * Serializes RP events as JSON and sends via WebSocket.
 */
public class RoleplayEventMessage extends ServerMessage {

    public RoleplayEventMessage(String eventType, String payload) {
        super();
        createEventMessage(eventType, payload);
    }

    /**
     * Create event message as JSON
     */
    private void createEventMessage(String eventType, String payload) {
        try {
            JsonObject event = new JsonObject();
            event.addProperty("type", eventType);
            event.addProperty("data", payload);
            event.addProperty("timestamp", System.currentTimeMillis());

            String json = event.toString();
            byte[] bytes = json.getBytes();

            // Create buffer with JSON data
            ByteBuf buffer = Unpooled.buffer(bytes.length + 2);
            buffer.writeShort(bytes.length);
            buffer.writeBytes(bytes);

            // Set header for roleplay event (custom ID)
            setBody(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set message body
     */
    private void setBody(ByteBuf buffer) {
        // Override compose() to return this buffer
        this.buffer = buffer;
    }

    private ByteBuf buffer;

    @Override
    public ByteBuf compose() {
        return buffer;
    }
}
