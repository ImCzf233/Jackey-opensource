package com.viaversion.viaversion.bungee.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bungee.util.BungeePipelineUtil;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/handlers/BungeeEncodeHandler.class */
public class BungeeEncodeHandler extends MessageToMessageEncoder<ByteBuf> {
    private final UserConnection info;
    private boolean handledCompression;

    protected /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, Object obj, List list) throws Exception {
        encode(channelHandlerContext, (ByteBuf) obj, (List<Object>) list);
    }

    public BungeeEncodeHandler(UserConnection info) {
        this.info = info;
    }

    protected void encode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
        if (!ctx.channel().isActive()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.checkClientboundPacket()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }
        ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            boolean needsCompress = handleCompressionOrder(ctx, transformedBuf);
            this.info.transformClientbound(transformedBuf, CancelEncoderException::generate);
            if (needsCompress) {
                recompress(ctx, transformedBuf);
            }
            out.add(transformedBuf.retain());
            transformedBuf.release();
        } catch (Throwable th) {
            transformedBuf.release();
            throw th;
        }
    }

    private boolean handleCompressionOrder(ChannelHandlerContext ctx, ByteBuf buf) {
        boolean needsCompress = false;
        if (!this.handledCompression && ctx.pipeline().names().indexOf("compress") > ctx.pipeline().names().indexOf("via-encoder")) {
            ByteBuf decompressed = BungeePipelineUtil.decompress(ctx, buf);
            if (buf != decompressed) {
                try {
                    buf.clear().writeBytes(decompressed);
                    decompressed.release();
                } catch (Throwable th) {
                    decompressed.release();
                    throw th;
                }
            }
            ChannelHandler dec = ctx.pipeline().get("via-decoder");
            ChannelHandler enc = ctx.pipeline().get("via-encoder");
            ctx.pipeline().remove(dec);
            ctx.pipeline().remove(enc);
            ctx.pipeline().addAfter("decompress", "via-decoder", dec);
            ctx.pipeline().addAfter("compress", "via-encoder", enc);
            needsCompress = true;
            this.handledCompression = true;
        }
        return needsCompress;
    }

    private void recompress(ChannelHandlerContext ctx, ByteBuf buf) {
        ByteBuf compressed = BungeePipelineUtil.compress(ctx, buf);
        try {
            buf.clear().writeBytes(compressed);
            compressed.release();
        } catch (Throwable th) {
            compressed.release();
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
