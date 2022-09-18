package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/block_entity_handlers/BedHandler.class */
public class BedHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    @Override // com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
    public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
        int offset = blockId - 748;
        int color = offset >> 4;
        tag.put("color", new IntTag(color));
        return tag;
    }
}
