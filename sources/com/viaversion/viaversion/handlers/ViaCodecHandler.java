package com.viaversion.viaversion.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/handlers/ViaCodecHandler.class */
public interface ViaCodecHandler {
    void transform(ByteBuf byteBuf) throws Exception;

    void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception;
}
