package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/providers/blockentities/BannerHandler.class */
public class BannerHandler implements BlockEntityProvider.BlockEntityHandler {
    private static final int WALL_BANNER_START = 7110;
    private static final int WALL_BANNER_STOP = 7173;
    private static final int BANNER_START = 6854;
    private static final int BANNER_STOP = 7109;

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider.BlockEntityHandler
    public int transform(UserConnection user, CompoundTag tag) {
        BlockStorage storage = (BlockStorage) user.get(BlockStorage.class);
        Position position = new Position((int) getLong((NumberTag) tag.get("x")), (short) getLong((NumberTag) tag.get("y")), (int) getLong((NumberTag) tag.get("z")));
        if (!storage.contains(position)) {
            Via.getPlatform().getLogger().warning("Received an banner color update packet, but there is no banner! O_o " + tag);
            return -1;
        }
        int blockId = storage.get(position).getOriginal();
        Tag base = tag.get("Base");
        int color = 0;
        if (base != null) {
            color = ((NumberTag) tag.get("Base")).asInt();
        }
        if (blockId >= BANNER_START && blockId <= BANNER_STOP) {
            blockId += (15 - color) * 16;
        } else if (blockId >= WALL_BANNER_START && blockId <= WALL_BANNER_STOP) {
            blockId += (15 - color) * 4;
        } else {
            Via.getPlatform().getLogger().warning("Why does this block have the banner block entity? :(" + tag);
        }
        if (tag.get("Patterns") instanceof ListTag) {
            Iterator<Tag> it = ((ListTag) tag.get("Patterns")).iterator();
            while (it.hasNext()) {
                Tag pattern = it.next();
                if (pattern instanceof CompoundTag) {
                    Tag c = ((CompoundTag) pattern).get("Color");
                    if (c instanceof IntTag) {
                        ((IntTag) c).setValue(15 - ((Integer) c.getValue()).intValue());
                    }
                }
            }
        }
        Tag name = tag.get("CustomName");
        if (name instanceof StringTag) {
            ((StringTag) name).setValue(ChatRewriter.legacyTextToJsonString(((StringTag) name).getValue()));
        }
        return blockId;
    }

    private long getLong(NumberTag tag) {
        return tag.asLong();
    }
}
