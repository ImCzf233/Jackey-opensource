package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ListTagBuilder.class */
public final class ListTagBuilder<T extends BinaryTag> implements ListBinaryTag.Builder<T> {
    @Nullable
    private List<BinaryTag> tags;
    private BinaryTagType<? extends BinaryTag> elementType;

    public ListTagBuilder() {
        this(BinaryTagTypes.END);
    }

    public ListTagBuilder(final BinaryTagType<? extends BinaryTag> type) {
        this.elementType = type;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ListTagSetter
    public ListBinaryTag.Builder<T> add(final BinaryTag tag) {
        ListBinaryTagImpl.noAddEnd(tag);
        if (this.elementType == BinaryTagTypes.END) {
            this.elementType = tag.type();
        }
        ListBinaryTagImpl.mustBeSameType(tag, this.elementType);
        if (this.tags == null) {
            this.tags = new ArrayList();
        }
        this.tags.add(tag);
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ListTagSetter
    public ListBinaryTag.Builder<T> add(final Iterable<? extends T> tagsToAdd) {
        for (T tag : tagsToAdd) {
            add((BinaryTag) tag);
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag.Builder
    @NotNull
    public ListBinaryTag build() {
        return this.tags == null ? ListBinaryTag.empty() : new ListBinaryTagImpl(this.elementType, new ArrayList(this.tags));
    }
}
