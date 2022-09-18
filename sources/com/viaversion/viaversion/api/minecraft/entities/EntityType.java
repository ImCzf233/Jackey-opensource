package com.viaversion.viaversion.api.minecraft.entities;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/entities/EntityType.class */
public interface EntityType {
    int getId();

    EntityType getParent();

    String name();

    /* renamed from: is */
    default boolean m223is(EntityType... types) {
        for (EntityType type : types) {
            if (this == type) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: is */
    default boolean m224is(EntityType type) {
        return this == type;
    }

    default boolean isOrHasParent(EntityType type) {
        EntityType parent = this;
        while (parent != type) {
            parent = parent.getParent();
            if (parent == null) {
                return false;
            }
        }
        return true;
    }
}
