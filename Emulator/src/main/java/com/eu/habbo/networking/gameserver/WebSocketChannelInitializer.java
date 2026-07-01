package com.eu.habbo.networking.gameserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import com.eu.habbo.messages.PacketManager;
import com.eu.habbo.networking.gameserver.codec.WebSocketCodec;
import com.eu.habbo.networking.gameserver.decoders.*;
import com.eu.habbo.networking.gameserver.encoders.GameServerMessageEncoder;
import com.eu.habbo.networking.gameserver.encoders.GameServerMessageLogger;
import com.eu.habbo.networking.gameserver.handlers.IdleTimeoutHandler;
import com.eu.habbo.networking.gameserver.handlers.WebSocketHttpHandler;
import com.eu.habbo.networking.gameserver.ssl.SSLCertificateLoader;
import javax.net.ssl.SSLEngine;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    private static final int MAX_FRAME_SIZE = 500000;

    private final SslContext sslContext;
    private final boolean sslEnabled;
    private final WebSocketServerProtocolConfig wsConfig;

    public WebSocketChannelInitializer() {
        this.sslContext = SSLCertificateLoader.getContext();
        this.sslEnabled = this.sslContext != null;
        this.wsConfig = WebSocketServerProtocolConfig.newBuilder()
                .websocketPath("/")
                .checkStartsWith(true)
                .maxFramePayloadLength(MAX_FRAME_SIZE)
                .build();
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        
        pipeline.addLast("logger", new LoggingHandler());

        if (this.sslEnabled) {
            SSLEngine engine = this.sslContext.newEngine(ch.alloc());
            pipeline.addLast(new SslHandler(engine));
        }

        pipeline.addLast("httpCodec", new HttpServerCodec());
        pipeline.addLast("httpAggregator", new HttpObjectAggregator(MAX_FRAME_SIZE));
        
        // Add WebEventRouter to distinguish between /events and /ws paths
        pipeline.addLast("webEventRouter", new WebEventRouter());
        
        // WebEvent handler for /events endpoint (RolePlay)
        pipeline.addLast("webEventHandler", new WebEventHandler());
        
        pipeline.addLast("wsHttpHandler", new WebSocketHttpHandler());
        pipeline.addLast("wsProtocolHandler", new WebSocketServerProtocolHandler(this.wsConfig));
        pipeline.addLast("wsCodec", new WebSocketCodec());

        // Standard game decoders
        pipeline.addLast(new GamePolicyDecoder());
        pipeline.addLast(new GameByteFrameDecoder());
        pipeline.addLast(new GameByteDecoder());

        if (PacketManager.DEBUG_SHOW_PACKETS) {
            pipeline.addLast(new GameClientMessageLogger());
        }

        pipeline.addLast("idleEventHandler", new IdleTimeoutHandler(30, 60));
        pipeline.addLast(new GameMessageRateLimit());
        pipeline.addLast(new GameMessageHandler());

        // Encoders
        pipeline.addLast("messageEncoder", new GameServerMessageEncoder());

        if (PacketManager.DEBUG_SHOW_PACKETS) {
            pipeline.addLast(new GameServerMessageLogger());
        }
    }

    public boolean isSslEnabled() {
        return this.sslEnabled;
    }
}
