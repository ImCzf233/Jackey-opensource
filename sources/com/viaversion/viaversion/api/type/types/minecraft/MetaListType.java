package com.viaversion.viaversion.api.type.types.minecraft;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/MetaListType.class */
public final class MetaListType extends MetaListTypeTemplate {
    private final Type<Metadata> type;

    public MetaListType(Type<Metadata> type) {
        Preconditions.checkNotNull(type);
        this.type = type;
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public List<Metadata> read(ByteBuf buffer) throws Exception {
        Metadata meta;
        List<Metadata> list = new ArrayList<>();
        do {
            meta = this.type.read(buffer);
            if (meta != null) {
                list.add(meta);
            }
        } while (meta != null);
        return list;
    }

    public void write(ByteBuf buffer, List<Metadata> object) throws Exception {
        for (Metadata metadata : object) {
            this.type.write(buffer, metadata);
        }
        this.type.write(buffer, null);
    }
}
