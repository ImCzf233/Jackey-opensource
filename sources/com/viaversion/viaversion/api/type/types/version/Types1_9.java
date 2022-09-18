package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.MetaListType;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/version/Types1_9.class */
public class Types1_9 {
    public static final Type<Metadata> METADATA = new Metadata1_9Type();
    public static final Type<List<Metadata>> METADATA_LIST = new MetaListType(METADATA);
    public static final Type<ChunkSection> CHUNK_SECTION = new ChunkSectionType1_9();
}
