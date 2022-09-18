package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/block_entity_handlers/BannerHandler.class */
public class BannerHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    private static final int WALL_BANNER_START = 7110;
    private static final int WALL_BANNER_STOP = 7173;
    private static final int BANNER_START = 6854;
    private static final int BANNER_STOP = 7109;

    @Override // com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
    public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
        if (blockId >= BANNER_START && blockId <= BANNER_STOP) {
            int color = (blockId - BANNER_START) >> 4;
            tag.put("Base", new IntTag(15 - color));
        } else if (blockId >= WALL_BANNER_START && blockId <= WALL_BANNER_STOP) {
            int color2 = (blockId - WALL_BANNER_START) >> 2;
            tag.put("Base", new IntTag(15 - color2));
        } else {
            ViaBackwards.getPlatform().getLogger().warning("Why does this block have the banner block entity? :(" + tag);
        }
        Tag patternsTag = tag.get("Patterns");
        if (patternsTag instanceof ListTag) {
            Iterator<Tag> it = ((ListTag) patternsTag).iterator();
            while (it.hasNext()) {
                Tag pattern = it.next();
                if (pattern instanceof CompoundTag) {
                    IntTag c = (IntTag) ((CompoundTag) pattern).get("Color");
                    c.setValue(15 - c.asInt());
                }
            }
        }
        return tag;
    }
}
