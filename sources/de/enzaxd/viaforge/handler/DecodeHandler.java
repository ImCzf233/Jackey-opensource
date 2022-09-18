package de.enzaxd.viaforge.handler;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: Jackey Client b2.jar:de/enzaxd/viaforge/handler/DecodeHandler.class */
public class DecodeHandler extends MessageToMessageDecoder<ByteBuf> {
    private final UserConnection info;
    private boolean handledCompression;
    private boolean skipDoubleTransform;

    protected /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, Object obj, List list) throws Exception {
        decode(channelHandlerContext, (ByteBuf) obj, (List<Object>) list);
    }

    public DecodeHandler(UserConnection info) {
        this.info = info;
    }

    public UserConnection getInfo() {
        return this.info;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
        if (this.skipDoubleTransform) {
            this.skipDoubleTransform = false;
            out.add(bytebuf.retain());
        } else if (!this.info.checkIncomingPacket()) {
            throw CancelDecoderException.generate(null);
        } else {
            if (!this.info.shouldTransformPacket()) {
                out.add(bytebuf.retain());
                return;
            }
            ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
            try {
                boolean needsCompress = handleCompressionOrder(ctx, transformedBuf);
                this.info.transformIncoming(transformedBuf, CancelDecoderException::generate);
                if (needsCompress) {
                    CommonTransformer.compress(ctx, transformedBuf);
                    this.skipDoubleTransform = true;
                }
                out.add(transformedBuf.retain());
                transformedBuf.release();
            } catch (Throwable th) {
                transformedBuf.release();
                throw th;
            }
        }
    }

    private boolean handleCompressionOrder(ChannelHandlerContext ctx, ByteBuf buf) throws InvocationTargetException {
        int decoderIndex;
        if (!this.handledCompression && (decoderIndex = ctx.pipeline().names().indexOf("decompress")) != -1) {
            this.handledCompression = true;
            if (decoderIndex > ctx.pipeline().names().indexOf("via-decoder")) {
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
