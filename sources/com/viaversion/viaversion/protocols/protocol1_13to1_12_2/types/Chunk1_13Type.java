package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/types/Chunk1_13Type.class */
public class Chunk1_13Type extends PartialType<Chunk, ClientWorld> {
    public Chunk1_13Type(ClientWorld param) {
        super(param, Chunk.class);
    }

    public Chunk read(ByteBuf input, ClientWorld world) throws Exception {
        int chunkX = input.readInt();
        int chunkZ = input.readInt();
        boolean fullChunk = input.readBoolean();
        int primaryBitmask = Type.VAR_INT.readPrimitive(input);
        ByteBuf data = input.readSlice(Type.VAR_INT.readPrimitive(input));
        ChunkSection[] sections = new ChunkSection[16];
        for (int i = 0; i < 16; i++) {
            if ((primaryBitmask & (1 << i)) != 0) {
                ChunkSection section = Types1_13.CHUNK_SECTION.read(data);
                sections[i] = section;
                section.getLight().readBlockLight(data);
                if (world.getEnvironment() == Environment.NORMAL) {
                    section.getLight().readSkyLight(data);
                }
            }
        }
        int[] biomeData = fullChunk ? new int[256] : null;
        if (fullChunk) {
            if (data.readableBytes() >= 1024) {
                for (int i2 = 0; i2 < 256; i2++) {
                    biomeData[i2] = data.readInt();
                }
            } else {
                Via.getPlatform().getLogger().log(Level.WARNING, "Chunk x=" + chunkX + " z=" + chunkZ + " doesn't have biome data!");
            }
        }
        List<CompoundTag> nbtData = new ArrayList<>(Arrays.asList(Type.NBT_ARRAY.read(input)));
        if (input.readableBytes() > 0) {
            byte[] array = Type.REMAINING_BYTES.read(input);
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Found " + array.length + " more bytes than expected while reading the chunk: " + chunkX + "/" + chunkZ);
            }
        }
        return new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitmask, sections, biomeData, nbtData);
    }

    public void write(ByteBuf output, ClientWorld world, Chunk chunk) throws Exception {
        int[] biomeData;
        output.writeInt(chunk.getX());
        output.writeInt(chunk.getZ());
        output.writeBoolean(chunk.isFullChunk());
        Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
        ByteBuf buf = output.alloc().buffer();
        for (int i = 0; i < 16; i++) {
            try {
                ChunkSection section = chunk.getSections()[i];
                if (section != null) {
                    Types1_13.CHUNK_SECTION.write(buf, section);
                    section.getLight().writeBlockLight(buf);
                    if (section.getLight().hasSkyLight()) {
                        section.getLight().writeSkyLight(buf);
                    }
                }
            } catch (Throwable th) {
                buf.release();
                throw th;
            }
        }
        buf.readerIndex(0);
        Type.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.isBiomeData() ? 1024 : 0));
        output.writeBytes(buf);
        buf.release();
        if (chunk.isBiomeData()) {
            for (int value : chunk.getBiomeData()) {
                output.writeInt(value);
            }
        }
        Type.NBT_ARRAY.write(output, chunk.getBlockEntities().toArray(new CompoundTag[0]));
    }

    @Override // com.viaversion.viaversion.api.type.Type
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }
}
