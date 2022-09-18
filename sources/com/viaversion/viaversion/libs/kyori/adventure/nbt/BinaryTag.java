package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTag.class */
public interface BinaryTag extends BinaryTagLike, Examinable {
    @NotNull
    BinaryTagType<? extends BinaryTag> type();

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagLike
    @NotNull
    default BinaryTag asBinaryTag() {
        return this;
    }
}
