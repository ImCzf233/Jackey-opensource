package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.EntityNameRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/providers/blockentities/SpawnerHandler.class */
public class SpawnerHandler implements BlockEntityProvider.BlockEntityHandler {
    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider.BlockEntityHandler
    public int transform(UserConnection user, CompoundTag tag) {
        if (tag.contains("SpawnData") && (tag.get("SpawnData") instanceof CompoundTag)) {
            CompoundTag data = (CompoundTag) tag.get("SpawnData");
            if (data.contains("id") && (data.get("id") instanceof StringTag)) {
                StringTag s = (StringTag) data.get("id");
                s.setValue(EntityNameRewriter.rewrite(s.getValue()));
                return -1;
            }
            return -1;
        }
        return -1;
    }
}
