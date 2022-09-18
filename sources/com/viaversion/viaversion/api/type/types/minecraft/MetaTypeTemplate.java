package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/MetaTypeTemplate.class */
public abstract class MetaTypeTemplate extends Type<Metadata> {
    public MetaTypeTemplate() {
        super("Metadata type", Metadata.class);
    }

    @Override // com.viaversion.viaversion.api.type.Type
    public Class<? extends Type> getBaseClass() {
        return MetaTypeTemplate.class;
    }
}
