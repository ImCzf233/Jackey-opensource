package de.enzaxd.viaforge.handler;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: Jackey Client b2.jar:de/enzaxd/viaforge/handler/EncodeHandler.class */
public class EncodeHandler extends MessageToMessageEncoder<ByteBuf> {
    private final UserConnection info;
    private boolean handledCompression;

    protected /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, Object obj, List list) throws Exception {
        encode(channelHandlerContext, (ByteBuf) obj, (List<Object>) list);
    }

    public EncodeHandler(UserConnection info) {
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
            boolean needsCompress = handleCompressionOrder(ctx, transformedBuf);
            this.info.transformOutgoing(transformedBuf, CancelEncoderException::generate);
            if (needsCompress) {
                CommonTransformer.compress(ctx, transformedBuf);
            }
            out.add(transformedBuf.retain());
            transformedBuf.release();
        } catch (Throwable th) {
            transformedBuf.release();
            throw th;
        }
    }

    private boolean handleCompressionOrder(ChannelHandlerContext ctx, ByteBuf buf) throws InvocationTargetException {
        int encoderIndex;
        if (!this.handledCompression && (encoderIndex = ctx.pipeline().names().indexOf("compress")) != -1) {
            this.handledCompression = true;
            if (encoderIndex > ctx.pipeline().names().indexOf("via-encoder")) {
                CommonTransformer.decompress(ctx, buf);
                ChannelHandler encoder = ctx.pipeline().get("via-encoder");
                ChannelHandler decoder = ctx.pipeline().get("via-decoder");
                ctx.pipeline().remove(encoder);
                ctx.pipeline().remove(decoder);
                ctx.pipeline().addAfter("compress", "via-encoder", encoder);
                ctx.pipeline().addAfter("decompress", "via-decoder", decoder);
                return true;
            }
            return false;
        }
        return false;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (PipelineUtil.containsCause(cause, CancelCodecException.class)) {
            return;
        }
        super.exceptionCaught(ctx, cause);
    }
}
