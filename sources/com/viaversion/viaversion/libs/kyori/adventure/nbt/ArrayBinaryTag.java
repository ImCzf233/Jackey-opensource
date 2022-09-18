package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ArrayBinaryTag.class */
public interface ArrayBinaryTag extends BinaryTag {
    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    BinaryTagType<? extends ArrayBinaryTag> type();
}
