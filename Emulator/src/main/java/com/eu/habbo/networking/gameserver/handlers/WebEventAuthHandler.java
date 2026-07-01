package com.eu.habbo.networking.gameserver.handlers;

import com.eu.habbo.Emulator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebEventAuthHandler: Authenticates WebEvent connections from /events endpoint.
 */
public class WebEventAuthHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebEventAuthHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String authHeader = request.headers().get("Authorization");
        String uri = request.uri();

        if (!uri.equals("/events")) {
            ctx.fireChannelRead(request.retain());
            return;
        }

        // Validate authentication token
        if (authHeader == null || authHeader.isEmpty()) {
            LOGGER.warn("WebEvent connection attempt without authentication token");
            sendUnauthorizedResponse(ctx);
            return;
        }

        try {
            // Parse token format: "Bearer <token>"
            String[] parts = authHeader.split(" ");
            if (parts.length != 2 || !parts[0].equals("Bearer")) {
                LOGGER.warn("Invalid authorization header format");
                sendUnauthorizedResponse(ctx);
                return;
            }

            String token = parts[1];
            // TODO: Validate token against session tokens
            // For now, pass through to next handler
            ctx.fireChannelRead(request.retain());
        } catch (Exception e) {
            LOGGER.error("Error parsing authorization header", e);
            sendUnauthorizedResponse(ctx);
        }
    }

    private void sendUnauthorizedResponse(ChannelHandlerContext ctx) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.UNAUTHORIZED,
                Unpooled.wrappedBuffer("401 Unauthorized".getBytes())
        );
        ctx.writeAndFlush(response).addListener(future -> ctx.close());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("WebEventAuthHandler exception", cause);
        ctx.close();
    }
}
