package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.Type;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/MetaListTypeTemplate.class */
public abstract class MetaListTypeTemplate extends Type<List<Metadata>> {
    public MetaListTypeTemplate() {
        super("MetaData List", List.class);
    }

    @Override // com.viaversion.viaversion.api.type.Type
    public Class<? extends Type> getBaseClass() {
        return MetaListTypeTemplate.class;
    }
}
