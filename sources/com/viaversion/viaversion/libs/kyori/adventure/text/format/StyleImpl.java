package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/StyleImpl.class */
public final class StyleImpl implements Style {
    static final StyleImpl EMPTY = new StyleImpl(null, null, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, null, null, null);
    private static final TextDecoration[] DECORATIONS = TextDecoration.values();
    @Nullable
    final Key font;
    @Nullable
    final TextColor color;
    final TextDecoration.State obfuscated;
    final TextDecoration.State bold;
    final TextDecoration.State strikethrough;
    final TextDecoration.State underlined;
    final TextDecoration.State italic;
    @Nullable
    final ClickEvent clickEvent;
    @Nullable
    final HoverEvent<?> hoverEvent;
    @Nullable
    final String insertion;

    public static void decorate(final Style.Builder builder, final TextDecoration[] decorations) {
        for (TextDecoration decoration : decorations) {
            builder.decoration(decoration, true);
        }
    }

    public StyleImpl(@Nullable final Key font, @Nullable final TextColor color, final TextDecoration.State obfuscated, final TextDecoration.State bold, final TextDecoration.State strikethrough, final TextDecoration.State underlined, final TextDecoration.State italic, @Nullable final ClickEvent clickEvent, @Nullable final HoverEvent<?> hoverEvent, @Nullable final String insertion) {
        this.font = font;
        this.color = color;
        this.obfuscated = obfuscated;
        this.bold = bold;
        this.strikethrough = strikethrough;
        this.underlined = underlined;
        this.italic = italic;
        this.clickEvent = clickEvent;
        this.hoverEvent = hoverEvent;
        this.insertion = insertion;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @Nullable
    public Key font() {
        return this.font;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @NotNull
    public Style font(@Nullable final Key font) {
        return Objects.equals(this.font, font) ? this : new StyleImpl(font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @Nullable
    public TextColor color() {
        return this.color;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @NotNull
    public Style color(@Nullable final TextColor color) {
        return Objects.equals(this.color, color) ? this : new StyleImpl(this.font, color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @NotNull
    public Style colorIfAbsent(@Nullable final TextColor color) {
        if (this.color == null) {
            return color(color);
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    public TextDecoration.State decoration(@NotNull final TextDecoration decoration) {
        if (decoration == TextDecoration.BOLD) {
            return this.bold;
        }
        if (decoration == TextDecoration.ITALIC) {
            return this.italic;
        }
        if (decoration == TextDecoration.UNDERLINED) {
            return this.underlined;
        }
        if (decoration == TextDecoration.STRIKETHROUGH) {
            return this.strikethrough;
        }
        if (decoration == TextDecoration.OBFUSCATED) {
            return this.obfuscated;
        }
        throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @NotNull
    public Style decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state) {
        Objects.requireNonNull(state, "state");
        if (decoration == TextDecoration.BOLD) {
            return new StyleImpl(this.font, this.color, this.obfuscated, state, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
        }
        if (decoration == TextDecoration.ITALIC) {
            return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, state, this.clickEvent, this.hoverEvent, this.insertion);
        }
        if (decoration == TextDecoration.UNDERLINED) {
            return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, state, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
        }
        if (decoration == TextDecoration.STRIKETHROUGH) {
            return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, state, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
        }
        if (decoration == TextDecoration.OBFUSCATED) {
            return new StyleImpl(this.font, this.color, state, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
        }
        throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @NotNull
    public Map<TextDecoration, TextDecoration.State> decorations() {
        Map<TextDecoration, TextDecoration.State> decorations = new EnumMap<>(TextDecoration.class);
        int length = DECORATIONS.length;
        for (int i = 0; i < length; i++) {
            TextDecoration decoration = DECORATIONS[i];
            TextDecoration.State value = decoration(decoration);
            decorations.put(decoration, value);
        }
        return decorations;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @NotNull
    public Style decorations(@NotNull final Map<TextDecoration, TextDecoration.State> decorations) {
        TextDecoration.State obfuscated = decorations.getOrDefault(TextDecoration.OBFUSCATED, this.obfuscated);
        TextDecoration.State bold = decorations.getOrDefault(TextDecoration.BOLD, this.bold);
        TextDecoration.State strikethrough = decorations.getOrDefault(TextDecoration.STRIKETHROUGH, this.strikethrough);
        TextDecoration.State underlined = decorations.getOrDefault(TextDecoration.UNDERLINED, this.underlined);
        TextDecoration.State italic = decorations.getOrDefault(TextDecoration.ITALIC, this.italic);
        return new StyleImpl(this.font, this.color, obfuscated, bold, strikethrough, underlined, italic, this.clickEvent, this.hoverEvent, this.insertion);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @Nullable
    public ClickEvent clickEvent() {
        return this.clickEvent;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @NotNull
    public Style clickEvent(@Nullable final ClickEvent event) {
        return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, event, this.hoverEvent, this.insertion);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @Nullable
    public HoverEvent<?> hoverEvent() {
        return this.hoverEvent;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @NotNull
    public Style hoverEvent(@Nullable final HoverEventSource<?> source) {
        return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, HoverEventSource.unbox(source), this.insertion);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @Nullable
    public String insertion() {
        return this.insertion;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @NotNull
    public Style insertion(@Nullable final String insertion) {
        return Objects.equals(this.insertion, insertion) ? this : new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, insertion);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    @NotNull
    public Style merge(@NotNull final Style that, final Style.Merge.Strategy strategy, @NotNull final Set<Style.Merge> merges) {
        if (that.isEmpty() || strategy == Style.Merge.Strategy.NEVER || merges.isEmpty()) {
            return this;
        }
        if (isEmpty() && Style.Merge.hasAll(merges)) {
            return that;
        }
        Style.Builder builder = toBuilder();
        builder.merge(that, strategy, merges);
        return builder.build();
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style
    public boolean isEmpty() {
        return this == EMPTY;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    @NotNull
    public Style.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("color", this.color), ExaminableProperty.m91of("obfuscated", this.obfuscated), ExaminableProperty.m91of("bold", this.bold), ExaminableProperty.m91of("strikethrough", this.strikethrough), ExaminableProperty.m91of("underlined", this.underlined), ExaminableProperty.m91of("italic", this.italic), ExaminableProperty.m91of("clickEvent", this.clickEvent), ExaminableProperty.m91of("hoverEvent", this.hoverEvent), ExaminableProperty.m90of("insertion", this.insertion), ExaminableProperty.m91of("font", this.font)});
    }

    @NotNull
    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StyleImpl)) {
            return false;
        }
        StyleImpl that = (StyleImpl) other;
        return Objects.equals(this.color, that.color) && this.obfuscated == that.obfuscated && this.bold == that.bold && this.strikethrough == that.strikethrough && this.underlined == that.underlined && this.italic == that.italic && Objects.equals(this.clickEvent, that.clickEvent) && Objects.equals(this.hoverEvent, that.hoverEvent) && Objects.equals(this.insertion, that.insertion) && Objects.equals(this.font, that.font);
    }

    public int hashCode() {
        int result = Objects.hashCode(this.color);
        return (31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * result) + this.obfuscated.hashCode())) + this.bold.hashCode())) + this.strikethrough.hashCode())) + this.underlined.hashCode())) + this.italic.hashCode())) + Objects.hashCode(this.clickEvent))) + Objects.hashCode(this.hoverEvent))) + Objects.hashCode(this.insertion))) + Objects.hashCode(this.font);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/StyleImpl$BuilderImpl.class */
    public static final class BuilderImpl implements Style.Builder {
        @Nullable
        Key font;
        @Nullable
        TextColor color;
        TextDecoration.State obfuscated;
        TextDecoration.State bold;
        TextDecoration.State strikethrough;
        TextDecoration.State underlined;
        TextDecoration.State italic;
        @Nullable
        ClickEvent clickEvent;
        @Nullable
        HoverEvent<?> hoverEvent;
        @Nullable
        String insertion;

        public BuilderImpl() {
            this.obfuscated = TextDecoration.State.NOT_SET;
            this.bold = TextDecoration.State.NOT_SET;
            this.strikethrough = TextDecoration.State.NOT_SET;
            this.underlined = TextDecoration.State.NOT_SET;
            this.italic = TextDecoration.State.NOT_SET;
        }

        BuilderImpl(@NotNull final StyleImpl style) {
            this.obfuscated = TextDecoration.State.NOT_SET;
            this.bold = TextDecoration.State.NOT_SET;
            this.strikethrough = TextDecoration.State.NOT_SET;
            this.underlined = TextDecoration.State.NOT_SET;
            this.italic = TextDecoration.State.NOT_SET;
            this.color = style.color;
            this.obfuscated = style.obfuscated;
            this.bold = style.bold;
            this.strikethrough = style.strikethrough;
            this.underlined = style.underlined;
            this.italic = style.italic;
            this.clickEvent = style.clickEvent;
            this.hoverEvent = style.hoverEvent;
            this.insertion = style.insertion;
            this.font = style.font;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style.Builder
        @NotNull
        public Style.Builder font(@Nullable final Key font) {
            this.font = font;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style.Builder
        @NotNull
        public Style.Builder color(@Nullable final TextColor color) {
            this.color = color;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style.Builder
        @NotNull
        public Style.Builder colorIfAbsent(@Nullable final TextColor color) {
            if (this.color == null) {
                this.color = color;
            }
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style.Builder
        @NotNull
        public Style.Builder decorate(@NotNull final TextDecoration decoration) {
            return decoration(decoration, TextDecoration.State.TRUE);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style.Builder
        @NotNull
        public Style.Builder decorate(@NotNull final TextDecoration... decorations) {
            for (TextDecoration textDecoration : decorations) {
                decorate(textDecoration);
            }
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style.Builder
        @NotNull
        public Style.Builder decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state) {
            Objects.requireNonNull(state, "state");
            if (decoration == TextDecoration.BOLD) {
                this.bold = state;
                return this;
            } else if (decoration == TextDecoration.ITALIC) {
                this.italic = state;
                return this;
            } else if (decoration == TextDecoration.UNDERLINED) {
                this.underlined = state;
                return this;
            } else if (decoration == TextDecoration.STRIKETHROUGH) {
                this.strikethrough = state;
                return this;
            } else if (decoration == TextDecoration.OBFUSCATED) {
                this.obfuscated = state;
                return this;
            } else {
                throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
            }
        }

        @NotNull
        public Style.Builder decorationIfAbsent(@NotNull final TextDecoration decoration, final TextDecoration.State state) {
            Objects.requireNonNull(state, "state");
            if (decoration == TextDecoration.BOLD) {
                if (this.bold == TextDecoration.State.NOT_SET) {
                    this.bold = state;
                }
                return this;
            } else if (decoration == TextDecoration.ITALIC) {
                if (this.italic == TextDecoration.State.NOT_SET) {
                    this.italic = state;
                }
                return this;
            } else if (decoration == TextDecoration.UNDERLINED) {
                if (this.underlined == TextDecoration.State.NOT_SET) {
                    this.underlined = state;
                }
                return this;
            } else if (decoration == TextDecoration.STRIKETHROUGH) {
                if (this.strikethrough == TextDecoration.State.NOT_SET) {
                    this.strikethrough = state;
                }
                return this;
            } else if (decoration == TextDecoration.OBFUSCATED) {
                if (this.obfuscated == TextDecoration.State.NOT_SET) {
                    this.obfuscated = state;
                }
                return this;
            } else {
                throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
            }
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style.Builder
        @NotNull
        public Style.Builder clickEvent(@Nullable final ClickEvent event) {
            this.clickEvent = event;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style.Builder
        @NotNull
        public Style.Builder hoverEvent(@Nullable final HoverEventSource<?> source) {
            this.hoverEvent = HoverEventSource.unbox(source);
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style.Builder
        @NotNull
        public Style.Builder insertion(@Nullable final String insertion) {
            this.insertion = insertion;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style.Builder
        @NotNull
        public Style.Builder merge(@NotNull final Style that, final Style.Merge.Strategy strategy, @NotNull final Set<Style.Merge> merges) {
            Key font;
            String insertion;
            TextColor color;
            if (strategy == Style.Merge.Strategy.NEVER || that.isEmpty() || merges.isEmpty()) {
                return this;
            }
            Merger merger = merger(strategy);
            if (merges.contains(Style.Merge.COLOR) && (color = that.color()) != null) {
                merger.mergeColor(this, color);
            }
            if (merges.contains(Style.Merge.DECORATIONS)) {
                int length = StyleImpl.DECORATIONS.length;
                for (int i = 0; i < length; i++) {
                    TextDecoration decoration = StyleImpl.DECORATIONS[i];
                    TextDecoration.State state = that.decoration(decoration);
                    if (state != TextDecoration.State.NOT_SET) {
                        merger.mergeDecoration(this, decoration, state);
                    }
                }
            }
            if (merges.contains(Style.Merge.EVENTS)) {
                ClickEvent clickEvent = that.clickEvent();
                if (clickEvent != null) {
                    merger.mergeClickEvent(this, clickEvent);
                }
                HoverEvent<?> hoverEvent = that.hoverEvent();
                if (hoverEvent != null) {
                    merger.mergeHoverEvent(this, hoverEvent);
                }
            }
            if (merges.contains(Style.Merge.INSERTION) && (insertion = that.insertion()) != null) {
                merger.mergeInsertion(this, insertion);
            }
            if (merges.contains(Style.Merge.FONT) && (font = that.font()) != null) {
                merger.mergeFont(this, font);
            }
            return this;
        }

        private static Merger merger(final Style.Merge.Strategy strategy) {
            if (strategy == Style.Merge.Strategy.ALWAYS) {
                return AlwaysMerger.INSTANCE;
            }
            if (strategy == Style.Merge.Strategy.NEVER) {
                throw new UnsupportedOperationException();
            }
            if (strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET) {
                return IfAbsentOnTargetMerger.INSTANCE;
            }
            throw new IllegalArgumentException(strategy.name());
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Style.Builder, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public Style build() {
            if (isEmpty()) {
                return StyleImpl.EMPTY;
            }
            return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
        }

        private boolean isEmpty() {
            return this.color == null && this.obfuscated == TextDecoration.State.NOT_SET && this.bold == TextDecoration.State.NOT_SET && this.strikethrough == TextDecoration.State.NOT_SET && this.underlined == TextDecoration.State.NOT_SET && this.italic == TextDecoration.State.NOT_SET && this.clickEvent == null && this.hoverEvent == null && this.insertion == null && this.font == null;
        }
    }
}
