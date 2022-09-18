package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
import com.viaversion.viaversion.util.Pair;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockConnectionStorage.class */
public class BlockConnectionStorage implements StorableObject {
    private static final short[] REVERSE_BLOCK_MAPPINGS = new short[8582];
    private static Constructor<?> fastUtilLongObjectHashMap;
    private final Map<Long, Pair<byte[], NibbleArray>> blockStorage = createLongObjectMap();

    static {
        try {
            String className = "it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap";
            fastUtilLongObjectHashMap = Class.forName(className).getConstructor(new Class[0]);
            Via.getPlatform().getLogger().info("Using FastUtil Long2ObjectOpenHashMap for block connections");
        } catch (ClassNotFoundException | NoSuchMethodException e) {
        }
        Arrays.fill(REVERSE_BLOCK_MAPPINGS, (short) -1);
        for (int i = 0; i < 4096; i++) {
            int newBlock = Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(i);
            if (newBlock != -1) {
                REVERSE_BLOCK_MAPPINGS[newBlock] = (short) i;
            }
        }
    }

    public void store(int x, int y, int z, int blockState) {
        short mapping = REVERSE_BLOCK_MAPPINGS[blockState];
        if (mapping == -1) {
            return;
        }
        long pair = getChunkSectionIndex(x, y, z);
        Pair<byte[], NibbleArray> map = getChunkSection(pair, (mapping & 15) != 0);
        int blockIndex = encodeBlockPos(x, y, z);
        map.key()[blockIndex] = (byte) (mapping >> 4);
        NibbleArray nibbleArray = map.value();
        if (nibbleArray != null) {
            nibbleArray.set(blockIndex, mapping);
        }
    }

    public int get(int x, int y, int z) {
        long pair = getChunkSectionIndex(x, y, z);
        Pair<byte[], NibbleArray> map = this.blockStorage.get(Long.valueOf(pair));
        if (map == null) {
            return 0;
        }
        short blockPosition = encodeBlockPos(x, y, z);
        NibbleArray nibbleArray = map.value();
        return WorldPackets.toNewId(((map.key()[blockPosition] & 255) << 4) | (nibbleArray == null ? (byte) 0 : nibbleArray.get(blockPosition)));
    }

    public void remove(int x, int y, int z) {
        byte[] key;
        long pair = getChunkSectionIndex(x, y, z);
        Pair<byte[], NibbleArray> map = this.blockStorage.get(Long.valueOf(pair));
        if (map == null) {
            return;
        }
        int blockIndex = encodeBlockPos(x, y, z);
        NibbleArray nibbleArray = map.value();
        if (nibbleArray != null) {
            nibbleArray.set(blockIndex, 0);
            boolean allZero = true;
            int i = 0;
            while (true) {
                if (i < 4096) {
                    if (nibbleArray.get(i) == 0) {
                        i++;
                    } else {
                        allZero = false;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (allZero) {
                map.setValue(null);
            }
        }
        map.key()[blockIndex] = 0;
        for (short entry : map.key()) {
            if (entry != 0) {
                return;
            }
        }
        this.blockStorage.remove(Long.valueOf(pair));
    }

    public void clear() {
        this.blockStorage.clear();
    }

    public void unloadChunk(int x, int z) {
        for (int y = 0; y < 256; y += 16) {
            this.blockStorage.remove(Long.valueOf(getChunkSectionIndex(x << 4, y, z << 4)));
        }
    }

    private Pair<byte[], NibbleArray> getChunkSection(long index, boolean requireNibbleArray) {
        Pair<byte[], NibbleArray> map = this.blockStorage.get(Long.valueOf(index));
        if (map == null) {
            map = new Pair<>(new byte[4096], null);
            this.blockStorage.put(Long.valueOf(index), map);
        }
        if (map.value() == null && requireNibbleArray) {
            map.setValue(new NibbleArray(4096));
        }
        return map;
    }

    private long getChunkSectionIndex(int x, int y, int z) {
        return (((x >> 4) & 67108863) << 38) | (((y >> 4) & 4095) << 26) | ((z >> 4) & 67108863);
    }

    private long getChunkSectionIndex(Position position) {
        return getChunkSectionIndex(position.m228x(), position.m227y(), position.m226z());
    }

    private short encodeBlockPos(int x, int y, int z) {
        return (short) (((y & 15) << 8) | ((x & 15) << 4) | (z & 15));
    }

    private short encodeBlockPos(Position pos) {
        return encodeBlockPos(pos.m228x(), pos.m227y(), pos.m226z());
    }

    private <T> Map<Long, T> createLongObjectMap() {
        if (fastUtilLongObjectHashMap != null) {
            try {
                return (Map) fastUtilLongObjectHashMap.newInstance(new Object[0]);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return new HashMap();
    }
}
