package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.netty.EmptyChannelHandler;
import de.gerrygames.viarewind.netty.ForwardMessageToByteEncoder;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.CompressionSendStorage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/provider/CompressionHandlerProvider.class */
public class CompressionHandlerProvider implements Provider {
    public void handleSetCompression(UserConnection user, int threshold) {
        ChannelPipeline pipeline = user.getChannel().pipeline();
        if (user.isClientSide()) {
            pipeline.addBefore(Via.getManager().getInjector().getEncoderName(), "compress", getEncoder(threshold));
            pipeline.addBefore(Via.getManager().getInjector().getDecoderName(), "decompress", getDecoder(threshold));
            return;
        }
        CompressionSendStorage storage = (CompressionSendStorage) user.get(CompressionSendStorage.class);
        storage.setRemoveCompression(true);
    }

    public void handleTransform(UserConnection user) {
        CompressionSendStorage storage = (CompressionSendStorage) user.get(CompressionSendStorage.class);
        if (storage.isRemoveCompression()) {
            ChannelPipeline pipeline = user.getChannel().pipeline();
            String compressor = null;
            String decompressor = null;
            if (pipeline.get("compress") != null) {
                compressor = "compress";
                decompressor = "decompress";
            } else if (pipeline.get("compression-encoder") != null) {
                compressor = "compression-encoder";
                decompressor = "compression-decoder";
            }
            if (compressor != null) {
                pipeline.replace(decompressor, decompressor, new EmptyChannelHandler());
                pipeline.replace(compressor, compressor, new ForwardMessageToByteEncoder());
                storage.setRemoveCompression(false);
                return;
            }
            throw new IllegalStateException("Couldn't remove compression for 1.7!");
        }
    }

    protected ChannelHandler getEncoder(int threshold) {
        return new Compressor(threshold);
    }

    protected ChannelHandler getDecoder(int threshold) {
        return new Decompressor(threshold);
    }

    /* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/provider/CompressionHandlerProvider$Decompressor.class */
    public static class Decompressor extends MessageToMessageDecoder<ByteBuf> {
        private final Inflater inflater = new Inflater();
        private final int threshold;

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
                    temp = ByteBufAllocator.DEFAULT.heapBuffer().writeBytes(in);
                } else {
                    in.retain();
                }
                ByteBuf output = ByteBufAllocator.DEFAULT.heapBuffer(outLength, outLength);
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
    }

    /* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/provider/CompressionHandlerProvider$Compressor.class */
    public static class Compressor extends MessageToByteEncoder<ByteBuf> {
        private final Deflater deflater = new Deflater();
        private final int threshold;

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
                temp = ByteBufAllocator.DEFAULT.heapBuffer().writeBytes(in);
            } else {
                in.retain();
            }
            ByteBuf output = ByteBufAllocator.DEFAULT.heapBuffer();
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
    }
}
