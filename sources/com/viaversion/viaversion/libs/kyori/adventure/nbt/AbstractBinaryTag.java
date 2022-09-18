package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/AbstractBinaryTag.class */
abstract class AbstractBinaryTag implements BinaryTag {
    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public final String examinableName() {
        return type().toString();
    }

    public final String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }
}
