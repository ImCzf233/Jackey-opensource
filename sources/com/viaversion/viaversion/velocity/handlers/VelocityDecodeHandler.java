package com.viaversion.viaversion.velocity.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/velocity/handlers/VelocityDecodeHandler.class */
public class VelocityDecodeHandler extends MessageToMessageDecoder<ByteBuf> {
    private final UserConnection info;

    protected /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, Object obj, List list) throws Exception {
        decode(channelHandlerContext, (ByteBuf) obj, (List<Object>) list);
    }

    public VelocityDecodeHandler(UserConnection info) {
        this.info = info;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
        if (!this.info.checkIncomingPacket()) {
            throw CancelDecoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }
        ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            this.info.transformIncoming(transformedBuf, CancelDecoderException::generate);
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

    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        if (event != VelocityChannelInitializer.COMPRESSION_ENABLED_EVENT) {
            super.userEventTriggered(ctx, event);
            return;
        }
        ChannelPipeline pipeline = ctx.pipeline();
        ChannelHandler encoder = pipeline.get("via-encoder");
        pipeline.remove(encoder);
        pipeline.addBefore(VelocityChannelInitializer.MINECRAFT_ENCODER, "via-encoder", encoder);
        ChannelHandler decoder = pipeline.get("via-decoder");
        pipeline.remove(decoder);
        pipeline.addBefore(VelocityChannelInitializer.MINECRAFT_DECODER, "via-decoder", decoder);
        super.userEventTriggered(ctx, event);
    }
}
