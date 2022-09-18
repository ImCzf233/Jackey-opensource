package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import io.netty.buffer.ByteBuf;
import java.util.zip.Deflater;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/types/Chunk1_7_10Type.class */
public class Chunk1_7_10Type extends PartialType<Chunk, ClientWorld> {
    public Chunk1_7_10Type(ClientWorld param) {
        super(param, Chunk.class);
    }

    public Chunk read(ByteBuf byteBuf, ClientWorld clientWorld) throws Exception {
        throw new UnsupportedOperationException();
    }

    public void write(ByteBuf output, ClientWorld clientWorld, Chunk chunk) throws Exception {
        int[] biomeData;
        output.writeInt(chunk.getX());
        output.writeInt(chunk.getZ());
        output.writeBoolean(chunk.isFullChunk());
        output.writeShort(chunk.getBitmask());
        output.writeShort(0);
        ByteBuf dataToCompress = output.alloc().buffer();
        ByteBuf blockData = output.alloc().buffer();
        for (int i = 0; i < chunk.getSections().length; i++) {
            if ((chunk.getBitmask() & (1 << i)) != 0) {
                ChunkSection section = chunk.getSections()[i];
                for (int y = 0; y < 16; y++) {
                    for (int z = 0; z < 16; z++) {
                        int previousData = 0;
                        for (int x = 0; x < 16; x++) {
                            int block = section.getFlatBlock(x, y, z);
                            dataToCompress.writeByte(block >> 4);
                            int data = block & 15;
                            if (x % 2 == 0) {
                                previousData = data;
                            } else {
                                blockData.writeByte((data << 4) | previousData);
                            }
                        }
                    }
                }
            }
        }
        dataToCompress.writeBytes(blockData);
        blockData.release();
        for (int i2 = 0; i2 < chunk.getSections().length; i2++) {
            if ((chunk.getBitmask() & (1 << i2)) != 0) {
                chunk.getSections()[i2].getLight().writeBlockLight(dataToCompress);
            }
        }
        boolean skyLight = clientWorld != null && clientWorld.getEnvironment() == Environment.NORMAL;
        if (skyLight) {
            for (int i3 = 0; i3 < chunk.getSections().length; i3++) {
                if ((chunk.getBitmask() & (1 << i3)) != 0) {
                    chunk.getSections()[i3].getLight().writeSkyLight(dataToCompress);
                }
            }
        }
        if (chunk.isFullChunk() && chunk.isBiomeData()) {
            for (int biome : chunk.getBiomeData()) {
                dataToCompress.writeByte((byte) biome);
            }
        }
        dataToCompress.readerIndex(0);
        byte[] data2 = new byte[dataToCompress.readableBytes()];
        dataToCompress.readBytes(data2);
        dataToCompress.release();
        Deflater deflater = new Deflater(4);
        try {
            deflater.setInput(data2, 0, data2.length);
            deflater.finish();
            byte[] compressedData = new byte[data2.length];
            int compressedSize = deflater.deflate(compressedData);
            deflater.end();
            output.writeInt(compressedSize);
            output.writeBytes(compressedData, 0, compressedSize);
        } catch (Throwable th) {
            deflater.end();
            throw th;
        }
    }
}
