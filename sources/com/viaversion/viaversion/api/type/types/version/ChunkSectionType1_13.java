package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.BiIntConsumer;
import com.viaversion.viaversion.util.CompactArrayUtil;
import io.netty.buffer.ByteBuf;
import java.util.function.IntToLongFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/version/ChunkSectionType1_13.class */
public class ChunkSectionType1_13 extends Type<ChunkSection> {
    private static final int GLOBAL_PALETTE = 14;

    public ChunkSectionType1_13() {
        super("Chunk Section Type", ChunkSection.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public ChunkSection read(ByteBuf buffer) throws Exception {
        ChunkSection chunkSection;
        BiIntConsumer biIntConsumer;
        int bitsPerBlock = buffer.readUnsignedByte();
        if (bitsPerBlock == 0 || bitsPerBlock > 8) {
            bitsPerBlock = 14;
        }
        if (bitsPerBlock != 14) {
            int paletteLength = Type.VAR_INT.readPrimitive(buffer);
            chunkSection = new ChunkSectionImpl(true, paletteLength);
            for (int i = 0; i < paletteLength; i++) {
                chunkSection.addPaletteEntry(Type.VAR_INT.readPrimitive(buffer));
            }
        } else {
            chunkSection = new ChunkSectionImpl(true);
        }
        long[] blockData = new long[Type.VAR_INT.readPrimitive(buffer)];
        if (blockData.length > 0) {
            int expectedLength = (int) Math.ceil((4096 * bitsPerBlock) / 64.0d);
            if (blockData.length != expectedLength) {
                throw new IllegalStateException("Block data length (" + blockData.length + ") does not match expected length (" + expectedLength + ")! bitsPerBlock=" + bitsPerBlock + ", originalBitsPerBlock=" + bitsPerBlock);
            }
            for (int i2 = 0; i2 < blockData.length; i2++) {
                blockData[i2] = buffer.readLong();
            }
            int i3 = bitsPerBlock;
            if (bitsPerBlock == 14) {
                ChunkSection chunkSection2 = chunkSection;
                chunkSection2.getClass();
                biIntConsumer = this::setFlatBlock;
            } else {
                ChunkSection chunkSection3 = chunkSection;
                chunkSection3.getClass();
                biIntConsumer = this::setPaletteIndex;
            }
            CompactArrayUtil.iterateCompactArray(i3, 4096, blockData, biIntConsumer);
        }
        return chunkSection;
    }

    public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
        IntToLongFunction intToLongFunction;
        int bitsPerBlock = 4;
        while (chunkSection.getPaletteSize() > (1 << bitsPerBlock)) {
            bitsPerBlock++;
        }
        if (bitsPerBlock > 8) {
            bitsPerBlock = 14;
        }
        buffer.writeByte(bitsPerBlock);
        if (bitsPerBlock != 14) {
            Type.VAR_INT.writePrimitive(buffer, chunkSection.getPaletteSize());
            for (int i = 0; i < chunkSection.getPaletteSize(); i++) {
                Type.VAR_INT.writePrimitive(buffer, chunkSection.getPaletteEntry(i));
            }
        }
        int i2 = bitsPerBlock;
        if (bitsPerBlock == 14) {
            chunkSection.getClass();
            intToLongFunction = this::getFlatBlock;
        } else {
            chunkSection.getClass();
            intToLongFunction = this::getPaletteIndex;
        }
        long[] data = CompactArrayUtil.createCompactArray(i2, 4096, intToLongFunction);
        Type.VAR_INT.writePrimitive(buffer, data.length);
        for (long l : data) {
            buffer.writeLong(l);
        }
    }
}
