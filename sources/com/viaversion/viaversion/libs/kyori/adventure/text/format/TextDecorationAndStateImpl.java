package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/TextDecorationAndStateImpl.class */
public final class TextDecorationAndStateImpl implements TextDecorationAndState {
    private final TextDecoration decoration;
    private final TextDecoration.State state;

    public TextDecorationAndStateImpl(final TextDecoration decoration, final TextDecoration.State state) {
        this.decoration = decoration;
        this.state = state;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecorationAndState
    @NotNull
    public TextDecoration decoration() {
        return this.decoration;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecorationAndState
    public TextDecoration.State state() {
        return this.state;
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
        TextDecorationAndStateImpl that = (TextDecorationAndStateImpl) other;
        return this.decoration == that.decoration && this.state == that.state;
    }

    public int hashCode() {
        int result = this.decoration.hashCode();
        return (31 * result) + this.state.hashCode();
    }
}
