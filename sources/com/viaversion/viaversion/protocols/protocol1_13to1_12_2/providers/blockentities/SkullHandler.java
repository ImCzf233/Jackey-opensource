package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/providers/blockentities/SkullHandler.class */
public class SkullHandler implements BlockEntityProvider.BlockEntityHandler {
    private static final int SKULL_WALL_START = 5447;
    private static final int SKULL_END = 5566;

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider.BlockEntityHandler
    public int transform(UserConnection user, CompoundTag tag) {
        BlockStorage storage = (BlockStorage) user.get(BlockStorage.class);
        Position position = new Position((int) getLong((NumberTag) tag.get("x")), (short) getLong((NumberTag) tag.get("y")), (int) getLong((NumberTag) tag.get("z")));
        if (!storage.contains(position)) {
            Via.getPlatform().getLogger().warning("Received an head update packet, but there is no head! O_o " + tag);
            return -1;
        }
        int id = storage.get(position).getOriginal();
        if (id >= SKULL_WALL_START && id <= SKULL_END) {
            Tag skullType = tag.get("SkullType");
            if (skullType != null) {
                id += ((NumberTag) tag.get("SkullType")).asInt() * 20;
            }
            if (tag.contains("Rot")) {
                id += ((NumberTag) tag.get("Rot")).asInt();
            }
            return id;
        }
        Via.getPlatform().getLogger().warning("Why does this block have the skull block entity? " + tag);
        return -1;
    }

    private long getLong(NumberTag tag) {
        return tag.asLong();
    }
}
