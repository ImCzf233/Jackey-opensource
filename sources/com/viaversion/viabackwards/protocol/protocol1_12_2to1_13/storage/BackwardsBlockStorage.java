package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/storage/BackwardsBlockStorage.class */
public class BackwardsBlockStorage implements StorableObject {
    private static final IntSet WHITELIST = new IntOpenHashSet(779);
    private final Map<Position, Integer> blocks = new ConcurrentHashMap();

    static {
        for (int i = 5265; i <= 5286; i++) {
            WHITELIST.add(i);
        }
        for (int i2 = 0; i2 < 256; i2++) {
            WHITELIST.add(748 + i2);
        }
        for (int i3 = 6854; i3 <= 7173; i3++) {
            WHITELIST.add(i3);
        }
        WHITELIST.add(1647);
        for (int i4 = 5447; i4 <= 5566; i4++) {
            WHITELIST.add(i4);
        }
        for (int i5 = 1028; i5 <= 1039; i5++) {
            WHITELIST.add(i5);
        }
        for (int i6 = 1047; i6 <= 1082; i6++) {
            WHITELIST.add(i6);
        }
        for (int i7 = 1099; i7 <= 1110; i7++) {
            WHITELIST.add(i7);
        }
    }

    public void checkAndStore(Position position, int block) {
        if (!WHITELIST.contains(block)) {
            this.blocks.remove(position);
        } else {
            this.blocks.put(position, Integer.valueOf(block));
        }
    }

    public boolean isWelcome(int block) {
        return WHITELIST.contains(block);
    }

    public Integer get(Position position) {
        return this.blocks.get(position);
    }

    public int remove(Position position) {
        return this.blocks.remove(position).intValue();
    }

    public void clear() {
        this.blocks.clear();
    }

    public Map<Position, Integer> getBlocks() {
        return this.blocks;
    }
}
