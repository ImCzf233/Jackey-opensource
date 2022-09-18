package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.type.types.minecraft.ModernMetaType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/version/Metadata1_9Type.class */
public class Metadata1_9Type extends ModernMetaType {
    @Override // com.viaversion.viaversion.api.type.types.minecraft.ModernMetaType
    protected MetaType getType(int index) {
        return MetaType1_9.byId(index);
    }
}
