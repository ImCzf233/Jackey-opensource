package com.viaversion.viaversion.bungee.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/handlers/BungeeDecodeHandler.class */
public class BungeeDecodeHandler extends MessageToMessageDecoder<ByteBuf> {
    private final UserConnection info;

    protected /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, Object obj, List list) throws Exception {
        decode(channelHandlerContext, (ByteBuf) obj, (List<Object>) list);
    }

    public BungeeDecodeHandler(UserConnection info) {
        this.info = info;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
        if (!ctx.channel().isActive()) {
            throw CancelDecoderException.generate(null);
        }
        if (!this.info.checkServerboundPacket()) {
            throw CancelDecoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }
        ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            this.info.transformServerbound(transformedBuf, CancelDecoderException::generate);
            out.add(transformedBuf.retain());
            transformedBuf.release();
        } catch (Throwable th) {
            transformedBuf.release();
            throw th;
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof CancelCodecException) {
            return;
        }
        super.exceptionCaught(ctx, cause);
    }
}
