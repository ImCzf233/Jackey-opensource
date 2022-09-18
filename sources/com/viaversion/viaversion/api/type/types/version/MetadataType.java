package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaTypes;
import com.viaversion.viaversion.api.type.types.minecraft.ModernMetaType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/version/MetadataType.class */
public final class MetadataType extends ModernMetaType {
    private final MetaTypes metaTypes;

    public MetadataType(MetaTypes metaTypes) {
        this.metaTypes = metaTypes;
    }

    @Override // com.viaversion.viaversion.api.type.types.minecraft.ModernMetaType
    protected MetaType getType(int index) {
        return this.metaTypes.byId(index);
    }
}
