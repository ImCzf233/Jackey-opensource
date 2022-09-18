package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/providers/CompressionProvider.class */
public class CompressionProvider implements Provider {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/providers/CompressionProvider$CompressionHandler.class */
    public interface CompressionHandler extends ChannelHandler {
        void setCompressionThreshold(int i);
    }

    public void handlePlayCompression(UserConnection user, int threshold) {
        if (!user.isClientSide()) {
            throw new IllegalStateException("PLAY state Compression packet is unsupported");
        }
        ChannelPipeline pipe = user.getChannel().pipeline();
        if (threshold < 0) {
            if (pipe.get("compress") != null) {
                pipe.remove("compress");
                pipe.remove("decompress");
            }
        } else if (pipe.get("compress") == null) {
            pipe.addBefore(Via.getManager().getInjector().getEncoderName(), "compress", getEncoder(threshold));
            pipe.addBefore(Via.getManager().getInjector().getDecoderName(), "decompress", getDecoder(threshold));
        } else {
            ((CompressionHandler) pipe.get("compress")).setCompressionThreshold(threshold);
            ((CompressionHandler) pipe.get("decompress")).setCompressionThreshold(threshold);
        }
    }

    protected CompressionHandler getEncoder(int threshold) {
        return new Compressor(threshold);
    }

    protected CompressionHandler getDecoder(int threshold) {
        return new Decompressor(threshold);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/providers/CompressionProvider$Decompressor.class */
    public static class Decompressor extends MessageToMessageDecoder<ByteBuf> implements CompressionHandler {
        private final Inflater inflater = new Inflater();
        private int threshold;

        protected /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, Object obj, List list) throws Exception {
            decode(channelHandlerContext, (ByteBuf) obj, (List<Object>) list);
        }

        public Decompressor(int var1) {
            this.threshold = var1;
        }

        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            if (!in.isReadable()) {
                return;
            }
            int outLength = Type.VAR_INT.readPrimitive(in);
            if (outLength == 0) {
                out.add(in.readBytes(in.readableBytes()));
            } else if (outLength < this.threshold) {
                throw new DecoderException("Badly compressed packet - size of " + outLength + " is below server threshold of " + this.threshold);
            } else {
                if (outLength > 2097152) {
                    throw new DecoderException("Badly compressed packet - size of " + outLength + " is larger than protocol maximum of 2097152");
                }
                ByteBuf temp = in;
                if (!in.hasArray()) {
                    temp = ctx.alloc().heapBuffer().writeBytes(in);
                } else {
                    in.retain();
                }
                ByteBuf output = ctx.alloc().heapBuffer(outLength, outLength);
                try {
                    this.inflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
                    output.writerIndex(output.writerIndex() + this.inflater.inflate(output.array(), output.arrayOffset(), outLength));
                    out.add(output.retain());
                    output.release();
                    temp.release();
                    this.inflater.reset();
                } catch (Throwable th) {
                    output.release();
                    temp.release();
                    this.inflater.reset();
                    throw th;
                }
            }
        }

        @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CompressionProvider.CompressionHandler
        public void setCompressionThreshold(int threshold) {
            this.threshold = threshold;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/providers/CompressionProvider$Compressor.class */
    public static class Compressor extends MessageToByteEncoder<ByteBuf> implements CompressionHandler {
        private final Deflater deflater = new Deflater();
        private int threshold;

        public Compressor(int var1) {
            this.threshold = var1;
        }

        public void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
            int frameLength = in.readableBytes();
            if (frameLength < this.threshold) {
                out.writeByte(0);
                out.writeBytes(in);
                return;
            }
            Type.VAR_INT.writePrimitive(out, frameLength);
            ByteBuf temp = in;
            if (!in.hasArray()) {
                temp = ctx.alloc().heapBuffer().writeBytes(in);
            } else {
                in.retain();
            }
            ByteBuf output = ctx.alloc().heapBuffer();
            try {
                this.deflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
                this.deflater.finish();
                while (!this.deflater.finished()) {
                    output.ensureWritable(4096);
                    output.writerIndex(output.writerIndex() + this.deflater.deflate(output.array(), output.arrayOffset() + output.writerIndex(), output.writableBytes()));
                }
                out.writeBytes(output);
                output.release();
                temp.release();
                this.deflater.reset();
            } catch (Throwable th) {
                output.release();
                temp.release();
                this.deflater.reset();
                throw th;
            }
        }

        @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CompressionProvider.CompressionHandler
        public void setCompressionThreshold(int threshold) {
            this.threshold = threshold;
        }
    }
}
