package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_14_4to1_15/data/EntityTypeMapping.class */
public class EntityTypeMapping {
    public static int getOldEntityId(int entityId) {
        return entityId == 4 ? Entity1_14Types.PUFFERFISH.getId() : entityId >= 5 ? entityId - 1 : entityId;
    }
}
