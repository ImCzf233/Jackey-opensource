package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.type.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/BaseChunkBulkType.class */
public abstract class BaseChunkBulkType extends Type<Chunk[]> {
    protected BaseChunkBulkType() {
        super(Chunk[].class);
    }

    protected BaseChunkBulkType(String typeName) {
        super(typeName, Chunk[].class);
    }

    @Override // com.viaversion.viaversion.api.type.Type
    public Class<? extends Type> getBaseClass() {
        return BaseChunkBulkType.class;
    }
}
