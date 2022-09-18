package com.viaversion.viaversion.velocity.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/velocity/handlers/VelocityEncodeHandler.class */
public class VelocityEncodeHandler extends MessageToMessageEncoder<ByteBuf> {
    private final UserConnection info;

    protected /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, Object obj, List list) throws Exception {
        encode(channelHandlerContext, (ByteBuf) obj, (List<Object>) list);
    }

    public VelocityEncodeHandler(UserConnection info) {
        this.info = info;
    }

    protected void encode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
        if (!this.info.checkOutgoingPacket()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }
        ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            this.info.transformOutgoing(transformedBuf, CancelEncoderException::generate);
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
