package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
@Debug.Renderer(text = "asHexString()")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/TextColorImpl.class */
public final class TextColorImpl implements TextColor {
    private final int value;

    public TextColorImpl(final int value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor
    public int value() {
        return this.value;
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextColorImpl)) {
            return false;
        }
        TextColorImpl that = (TextColorImpl) other;
        return this.value == that.value;
    }

    public int hashCode() {
        return this.value;
    }

    public String toString() {
        return asHexString();
    }
}
