package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.type.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/BaseChunkType.class */
public abstract class BaseChunkType extends Type<Chunk> {
    protected BaseChunkType() {
        super(Chunk.class);
    }

    protected BaseChunkType(String typeName) {
        super(typeName, Chunk.class);
    }

    @Override // com.viaversion.viaversion.api.type.Type
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }
}
