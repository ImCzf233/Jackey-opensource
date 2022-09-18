package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.type.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/metadata/types/AbstractMetaTypes.class */
public abstract class AbstractMetaTypes implements MetaTypes {
    private final MetaType[] values;

    public AbstractMetaTypes(int values) {
        this.values = new MetaType[values];
    }

    @Override // com.viaversion.viaversion.api.minecraft.metadata.types.MetaTypes
    public MetaType byId(int id) {
        return this.values[id];
    }

    @Override // com.viaversion.viaversion.api.minecraft.metadata.types.MetaTypes
    public MetaType[] values() {
        return this.values;
    }

    public MetaType add(int typeId, Type<?> type) {
        MetaType metaType = MetaType.create(typeId, type);
        this.values[typeId] = metaType;
        return metaType;
    }
}
