package com.viaversion.viaversion.protocols.protocol1_9to1_8.types;

import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/types/Chunk1_8Type.class */
public class Chunk1_8Type extends PartialType<Chunk, ClientWorld> {
    public Chunk1_8Type(ClientWorld param) {
        super(param, Chunk.class);
    }

    @Override // com.viaversion.viaversion.api.type.Type
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }

    public Chunk read(ByteBuf input, ClientWorld world) throws Exception {
        int chunkX = input.readInt();
        int chunkZ = input.readInt();
        boolean fullChunk = input.readBoolean();
        int bitmask = input.readUnsignedShort();
        int dataLength = Type.VAR_INT.readPrimitive(input);
        byte[] data = new byte[dataLength];
        input.readBytes(data);
        if (fullChunk && bitmask == 0) {
            return new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], null, new ArrayList());
        }
        return deserialize(chunkX, chunkZ, fullChunk, world.getEnvironment() == Environment.NORMAL, bitmask, data);
    }

    public void write(ByteBuf output, ClientWorld world, Chunk chunk) throws Exception {
        output.writeInt(chunk.getX());
        output.writeInt(chunk.getZ());
        output.writeBoolean(chunk.isFullChunk());
        output.writeShort(chunk.getBitmask());
        byte[] data = serialize(chunk);
        Type.VAR_INT.writePrimitive(output, data.length);
        output.writeBytes(data);
    }

    public static Chunk deserialize(int chunkX, int chunkZ, boolean fullChunk, boolean skyLight, int bitmask, byte[] data) throws Exception {
        ByteBuf input = Unpooled.wrappedBuffer(data);
        ChunkSection[] sections = new ChunkSection[16];
        int[] biomeData = null;
        for (int i = 0; i < sections.length; i++) {
            if ((bitmask & (1 << i)) != 0) {
                sections[i] = Types1_8.CHUNK_SECTION.read(input);
            }
        }
        for (int i2 = 0; i2 < sections.length; i2++) {
            if ((bitmask & (1 << i2)) != 0) {
                sections[i2].getLight().readBlockLight(input);
            }
        }
        if (skyLight) {
            for (int i3 = 0; i3 < sections.length; i3++) {
                if ((bitmask & (1 << i3)) != 0) {
                    sections[i3].getLight().readSkyLight(input);
                }
            }
        }
        if (fullChunk) {
            biomeData = new int[256];
            for (int i4 = 0; i4 < 256; i4++) {
                biomeData[i4] = input.readUnsignedByte();
            }
        }
        input.release();
        return new BaseChunk(chunkX, chunkZ, fullChunk, false, bitmask, sections, biomeData, new ArrayList());
    }

    public static byte[] serialize(Chunk chunk) throws Exception {
        int[] biomeData;
        ByteBuf output = Unpooled.buffer();
        for (int i = 0; i < chunk.getSections().length; i++) {
            if ((chunk.getBitmask() & (1 << i)) != 0) {
                Types1_8.CHUNK_SECTION.write(output, chunk.getSections()[i]);
            }
        }
        for (int i2 = 0; i2 < chunk.getSections().length; i2++) {
            if ((chunk.getBitmask() & (1 << i2)) != 0) {
                chunk.getSections()[i2].getLight().writeBlockLight(output);
            }
        }
        for (int i3 = 0; i3 < chunk.getSections().length; i3++) {
            if ((chunk.getBitmask() & (1 << i3)) != 0 && chunk.getSections()[i3].getLight().hasSkyLight()) {
                chunk.getSections()[i3].getLight().writeSkyLight(output);
            }
        }
        if (chunk.isFullChunk() && chunk.getBiomeData() != null) {
            for (int biome : chunk.getBiomeData()) {
                output.writeByte((byte) biome);
            }
        }
        byte[] data = new byte[output.readableBytes()];
        output.readBytes(data);
        output.release();
        return data;
    }
}
