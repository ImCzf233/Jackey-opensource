package de.enzaxd.viaforge.handler;

import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.reflect.InvocationTargetException;

/* loaded from: Jackey Client b2.jar:de/enzaxd/viaforge/handler/CommonTransformer.class */
public class CommonTransformer {
    public static final String HANDLER_DECODER_NAME = "via-decoder";
    public static final String HANDLER_ENCODER_NAME = "via-encoder";

    public static void decompress(ChannelHandlerContext ctx, ByteBuf buf) throws InvocationTargetException {
        ByteBuf byteBuf;
        MessageToMessageDecoder messageToMessageDecoder = ctx.pipeline().get("decompress");
        if (messageToMessageDecoder instanceof MessageToMessageDecoder) {
            byteBuf = (ByteBuf) PipelineUtil.callDecode(messageToMessageDecoder, ctx, buf).get(0);
        } else {
            byteBuf = (ByteBuf) PipelineUtil.callDecode((ByteToMessageDecoder) messageToMessageDecoder, ctx, buf).get(0);
        }
        ByteBuf decompressed = byteBuf;
        try {
            buf.clear().writeBytes(decompressed);
            decompressed.release();
        } catch (Throwable th) {
            decompressed.release();
            throw th;
        }
    }

    public static void compress(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        ByteBuf compressed = ctx.alloc().buffer();
        try {
            PipelineUtil.callEncode(ctx.pipeline().get("compress"), ctx, buf, compressed);
            buf.clear().writeBytes(compressed);
        } finally {
            compressed.release();
        }
    }
}
