package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.type.types.minecraft.ModernMetaType;

@Deprecated
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/version/Metadata1_14Type.class */
public class Metadata1_14Type extends ModernMetaType {
    @Override // com.viaversion.viaversion.api.type.types.minecraft.ModernMetaType
    protected MetaType getType(int index) {
        return Types1_14.META_TYPES.byId(index);
    }
}