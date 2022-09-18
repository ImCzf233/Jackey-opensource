package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/pointer/PointerImpl.class */
public final class PointerImpl<T> implements Pointer<T> {
    private final Class<T> type;
    private final Key key;

    public PointerImpl(final Class<T> type, final Key key) {
        this.type = type;
        this.key = key;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer
    @NotNull
    public Class<T> type() {
        return this.type;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer
    @NotNull
    public Key key() {
        return this.key;
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        PointerImpl<?> that = (PointerImpl) other;
        return this.type.equals(that.type) && this.key.equals(that.key);
    }

    public int hashCode() {
        int result = this.type.hashCode();
        return (31 * result) + this.key.hashCode();
    }
}
