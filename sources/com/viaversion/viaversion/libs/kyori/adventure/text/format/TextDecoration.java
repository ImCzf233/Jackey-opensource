package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/TextDecoration.class */
public enum TextDecoration implements StyleBuilderApplicable, TextFormat {
    OBFUSCATED("obfuscated"),
    BOLD("bold"),
    STRIKETHROUGH("strikethrough"),
    UNDERLINED("underlined"),
    ITALIC("italic");
    
    public static final Index<String, TextDecoration> NAMES = Index.create(TextDecoration.class, constant -> {
        return constant.name;
    });
    private final String name;

    TextDecoration(final String name) {
        this.name = name;
    }

    @NotNull
    /* renamed from: as */
    public final TextDecorationAndState m105as(final boolean state) {
        return m106as(State.byBoolean(state));
    }

    @NotNull
    /* renamed from: as */
    public final TextDecorationAndState m106as(@NotNull final State state) {
        return new TextDecorationAndStateImpl(this, state);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable
    public void styleApply(final Style.Builder style) {
        style.decorate(this);
    }

    @Override // java.lang.Enum
    @NotNull
    public String toString() {
        return this.name;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/TextDecoration$State.class */
    public enum State {
        NOT_SET("not_set"),
        FALSE("false"),
        TRUE("true");
        
        private final String name;

        State(final String name) {
            this.name = name;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.name;
        }

        @NotNull
        public static State byBoolean(final boolean flag) {
            return flag ? TRUE : FALSE;
        }

        @NotNull
        public static State byBoolean(@Nullable final Boolean flag) {
            return flag == null ? NOT_SET : byBoolean(flag.booleanValue());
        }
    }
}
