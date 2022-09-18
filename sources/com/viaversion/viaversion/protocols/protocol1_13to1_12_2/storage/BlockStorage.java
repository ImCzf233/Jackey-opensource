package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.flare.SyncMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage.class */
public class BlockStorage implements StorableObject {
    private static final IntSet WHITELIST = new IntOpenHashSet(46, 0.99f);
    private final Map<Position, ReplacementData> blocks = SyncMap.hashmap();

    static {
        WHITELIST.add(5266);
        for (int i = 0; i < 16; i++) {
            WHITELIST.add(972 + i);
        }
        for (int i2 = 0; i2 < 20; i2++) {
            WHITELIST.add(6854 + i2);
        }
        for (int i3 = 0; i3 < 4; i3++) {
            WHITELIST.add(7110 + i3);
        }
        for (int i4 = 0; i4 < 5; i4++) {
            WHITELIST.add(5447 + i4);
        }
    }

    public void store(Position position, int block) {
        store(position, block, -1);
    }

    public void store(Position position, int block, int replacementId) {
        if (!WHITELIST.contains(block)) {
            return;
        }
        this.blocks.put(position, new ReplacementData(block, replacementId));
    }

    public boolean isWelcome(int block) {
        return WHITELIST.contains(block);
    }

    public boolean contains(Position position) {
        return this.blocks.containsKey(position);
    }

    public ReplacementData get(Position position) {
        return this.blocks.get(position);
    }

    public ReplacementData remove(Position position) {
        return this.blocks.remove(position);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage$ReplacementData.class */
    public static final class ReplacementData {
        private final int original;
        private int replacement;

        public ReplacementData(int original, int replacement) {
            this.original = original;
            this.replacement = replacement;
        }

        public int getOriginal() {
            return this.original;
        }

        public int getReplacement() {
            return this.replacement;
        }

        public void setReplacement(int replacement) {
            this.replacement = replacement;
        }
    }
}
