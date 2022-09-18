package com.viaversion.viaversion.util;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/EntityTypeUtil.class */
public class EntityTypeUtil {
    public static EntityType[] toOrderedArray(EntityType[] values) {
        List<EntityType> types = new ArrayList<>();
        for (EntityType type : values) {
            if (type.getId() != -1) {
                types.add(type);
            }
        }
        types.sort(Comparator.comparingInt((v0) -> {
            return v0.getId();
        }));
        return (EntityType[]) types.toArray(new EntityType[0]);
    }

    public static EntityType getTypeFromId(EntityType[] values, int typeId, EntityType fallback) {
        EntityType type;
        if (typeId < 0 || typeId >= values.length || (type = values[typeId]) == null) {
            Via.getPlatform().getLogger().severe("Could not find " + fallback.getClass().getSimpleName() + " type id " + typeId);
            return fallback;
        }
        return type;
    }
}
