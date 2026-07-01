package com.eu.habbo.networking.gameserver;

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
 * WebEventRouter: Routes HTTP requests to appropriate handlers.
 * Distinguishes between /ws (traditional WebSocket) and /events (RolePlay WebSocket).
 */
public class WebEventRouter extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebEventRouter.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String uri = request.uri();

        if (uri.equals("/events")) {
            // Route to WebEventHandler (RolePlay events)
            LOGGER.debug("Routing request to /events");
            ctx.fireChannelRead(request.retain());
        } else if (uri.equals("/ws") || uri.contains("?authtoken=")) {
            // Route to standard WebSocket handler
            LOGGER.debug("Routing request to /ws");
            ctx.fireChannelRead(request.retain());
        } else {
            // Reject unknown routes
            LOGGER.warn("Unknown WebSocket route: {}", uri);
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.NOT_FOUND,
                    Unpooled.wrappedBuffer("404 Not Found".getBytes())
            );
            ctx.writeAndFlush(response).addListener(future -> ctx.close());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("WebEventRouter exception", cause);
        ctx.close();
    }
}
