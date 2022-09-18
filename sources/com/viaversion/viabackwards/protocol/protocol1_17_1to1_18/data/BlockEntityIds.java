package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data;

import java.util.Arrays;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_17_1to1_18/data/BlockEntityIds.class */
public final class BlockEntityIds {
    private static final int[] IDS;

    static {
        int[] ids = com.viaversion.viaversion.protocols.protocol1_18to1_17_1.BlockEntityIds.getIds();
        IDS = new int[Arrays.stream(ids).max().getAsInt() + 1];
        Arrays.fill(IDS, -1);
        for (int i = 0; i < ids.length; i++) {
            int id = ids[i];
            if (id != -1) {
                IDS[id] = i;
            }
        }
    }

    public static int mappedId(int id) {
        if (id < 0 || id > IDS.length) {
            return -1;
        }
        return IDS[id];
    }
}
