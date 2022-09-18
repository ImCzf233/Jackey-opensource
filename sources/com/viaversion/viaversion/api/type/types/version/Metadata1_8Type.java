package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.type.types.minecraft.OldMetaType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/version/Metadata1_8Type.class */
public class Metadata1_8Type extends OldMetaType {
    @Override // com.viaversion.viaversion.api.type.types.minecraft.OldMetaType
    protected MetaType getType(int index) {
        return MetaType1_8.byId(index);
    }
}
