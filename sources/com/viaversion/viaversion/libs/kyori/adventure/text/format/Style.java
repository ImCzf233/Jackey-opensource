package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.adventure.util.MonkeyBars;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.NonExtendable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/Style.class */
public interface Style extends Buildable<Style, Builder>, Examinable {
    public static final Key DEFAULT_FONT = Key.key("default");

    @Nullable
    Key font();

    @NotNull
    Style font(@Nullable final Key font);

    @Nullable
    TextColor color();

    @NotNull
    Style color(@Nullable final TextColor color);

    @NotNull
    Style colorIfAbsent(@Nullable final TextColor color);

    TextDecoration.State decoration(@NotNull final TextDecoration decoration);

    @NotNull
    Style decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state);

    @NotNull
    Map<TextDecoration, TextDecoration.State> decorations();

    @NotNull
    Style decorations(@NotNull final Map<TextDecoration, TextDecoration.State> decorations);

    @Nullable
    ClickEvent clickEvent();

    @NotNull
    Style clickEvent(@Nullable final ClickEvent event);

    @Nullable
    HoverEvent<?> hoverEvent();

    @NotNull
    Style hoverEvent(@Nullable final HoverEventSource<?> source);

    @Nullable
    String insertion();

    @NotNull
    Style insertion(@Nullable final String insertion);

    @NotNull
    Style merge(@NotNull final Style that, final Merge.Strategy strategy, @NotNull final Set<Merge> merges);

    boolean isEmpty();

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    @NotNull
    Builder toBuilder();

    @NotNull
    static Style empty() {
        return StyleImpl.EMPTY;
    }

    @NotNull
    static Builder style() {
        return new StyleImpl.BuilderImpl();
    }

    @NotNull
    static Style style(@NotNull final Consumer<Builder> consumer) {
        return (Style) Buildable.configureAndBuild(style(), consumer);
    }

    @NotNull
    static Style style(@Nullable final TextColor color) {
        return color == null ? empty() : new StyleImpl(null, color, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, null, null, null);
    }

    @NotNull
    static Style style(@NotNull final TextDecoration decoration) {
        return style().decoration(decoration, true).build();
    }

    @NotNull
    static Style style(@Nullable final TextColor color, final TextDecoration... decorations) {
        Builder builder = style();
        builder.color(color);
        StyleImpl.decorate(builder, decorations);
        return builder.build();
    }

    @NotNull
    static Style style(@Nullable final TextColor color, final Set<TextDecoration> decorations) {
        Builder builder = style();
        builder.color(color);
        if (!decorations.isEmpty()) {
            for (TextDecoration decoration : decorations) {
                builder.decoration(decoration, true);
            }
        }
        return builder.build();
    }

    @NotNull
    static Style style(final StyleBuilderApplicable... applicables) {
        if (applicables.length == 0) {
            return empty();
        }
        Builder builder = style();
        for (StyleBuilderApplicable styleBuilderApplicable : applicables) {
            styleBuilderApplicable.styleApply(builder);
        }
        return builder.build();
    }

    @NotNull
    static Style style(@NotNull final Iterable<? extends StyleBuilderApplicable> applicables) {
        Builder builder = style();
        for (StyleBuilderApplicable applicable : applicables) {
            applicable.styleApply(builder);
        }
        return builder.build();
    }

    @NotNull
    default Style edit(@NotNull final Consumer<Builder> consumer) {
        return edit(consumer, Merge.Strategy.ALWAYS);
    }

    @NotNull
    default Style edit(@NotNull final Consumer<Builder> consumer, final Merge.Strategy strategy) {
        return style(style -> {
            if (strategy == Merge.Strategy.ALWAYS) {
                consumer.merge(this, strategy);
            }
            strategy.accept(consumer);
            if (strategy == Merge.Strategy.IF_ABSENT_ON_TARGET) {
                consumer.merge(this, strategy);
            }
        });
    }

    default boolean hasDecoration(@NotNull final TextDecoration decoration) {
        return decoration(decoration) == TextDecoration.State.TRUE;
    }

    @NotNull
    default Style decorate(@NotNull final TextDecoration decoration) {
        return decoration(decoration, TextDecoration.State.TRUE);
    }

    @NotNull
    default Style decoration(@NotNull final TextDecoration decoration, final boolean flag) {
        return decoration(decoration, TextDecoration.State.byBoolean(flag));
    }

    @NotNull
    default Style merge(@NotNull final Style that) {
        return merge(that, Merge.all());
    }

    @NotNull
    default Style merge(@NotNull final Style that, final Merge.Strategy strategy) {
        return merge(that, strategy, Merge.all());
    }

    @NotNull
    default Style merge(@NotNull final Style that, @NotNull final Merge merge) {
        return merge(that, Collections.singleton(merge));
    }

    @NotNull
    default Style merge(@NotNull final Style that, final Merge.Strategy strategy, @NotNull final Merge merge) {
        return merge(that, strategy, Collections.singleton(merge));
    }

    @NotNull
    default Style merge(@NotNull final Style that, @NotNull final Merge... merges) {
        return merge(that, Merge.m107of(merges));
    }

    @NotNull
    default Style merge(@NotNull final Style that, final Merge.Strategy strategy, @NotNull final Merge... merges) {
        return merge(that, strategy, Merge.m107of(merges));
    }

    @NotNull
    default Style merge(@NotNull final Style that, @NotNull final Set<Merge> merges) {
        return merge(that, Merge.Strategy.ALWAYS, merges);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge.class */
    public enum Merge {
        COLOR,
        DECORATIONS,
        EVENTS,
        INSERTION,
        FONT;
        
        static final Set<Merge> ALL = m107of(values());
        static final Set<Merge> COLOR_AND_DECORATIONS = m107of(COLOR, DECORATIONS);

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Merge$Strategy.class */
        public enum Strategy {
            ALWAYS,
            NEVER,
            IF_ABSENT_ON_TARGET
        }

        @NotNull
        public static Set<Merge> all() {
            return ALL;
        }

        @NotNull
        public static Set<Merge> colorAndDecorations() {
            return COLOR_AND_DECORATIONS;
        }

        @NotNull
        /* renamed from: of */
        public static Set<Merge> m107of(final Merge... merges) {
            return MonkeyBars.enumSet(Merge.class, merges);
        }

        public static boolean hasAll(@NotNull final Set<Merge> merges) {
            return merges.size() == ALL.size();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/Style$Builder.class */
    public interface Builder extends Buildable.Builder<Style> {
        @Contract("_ -> this")
        @NotNull
        Builder font(@Nullable final Key font);

        @Contract("_ -> this")
        @NotNull
        Builder color(@Nullable final TextColor color);

        @Contract("_ -> this")
        @NotNull
        Builder colorIfAbsent(@Nullable final TextColor color);

        @Contract("_, _ -> this")
        @NotNull
        Builder decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state);

        @Contract("_ -> this")
        @NotNull
        Builder clickEvent(@Nullable final ClickEvent event);

        @Contract("_ -> this")
        @NotNull
        Builder hoverEvent(@Nullable final HoverEventSource<?> source);

        @Contract("_ -> this")
        @NotNull
        Builder insertion(@Nullable final String insertion);

        @Contract("_, _, _ -> this")
        @NotNull
        Builder merge(@NotNull final Style that, final Merge.Strategy strategy, @NotNull final Set<Merge> merges);

        @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        Style build();

        @Contract("_ -> this")
        @NotNull
        default Builder decorate(@NotNull final TextDecoration decoration) {
            return decoration(decoration, TextDecoration.State.TRUE);
        }

        @Contract("_ -> this")
        @NotNull
        default Builder decorate(@NotNull final TextDecoration... decorations) {
            for (TextDecoration textDecoration : decorations) {
                decorate(textDecoration);
            }
            return this;
        }

        @Contract("_, _ -> this")
        @NotNull
        default Builder decoration(@NotNull final TextDecoration decoration, final boolean flag) {
            return decoration(decoration, TextDecoration.State.byBoolean(flag));
        }

        @Contract("_ -> this")
        @NotNull
        default Builder merge(@NotNull final Style that) {
            return merge(that, Merge.all());
        }

        @Contract("_, _ -> this")
        @NotNull
        default Builder merge(@NotNull final Style that, final Merge.Strategy strategy) {
            return merge(that, strategy, Merge.all());
        }

        @Contract("_, _ -> this")
        @NotNull
        default Builder merge(@NotNull final Style that, @NotNull final Merge... merges) {
            return merges.length == 0 ? this : merge(that, Merge.m107of(merges));
        }

        @Contract("_, _, _ -> this")
        @NotNull
        default Builder merge(@NotNull final Style that, final Merge.Strategy strategy, @NotNull final Merge... merges) {
            return merges.length == 0 ? this : merge(that, strategy, Merge.m107of(merges));
        }

        @Contract("_, _ -> this")
        @NotNull
        default Builder merge(@NotNull final Style that, @NotNull final Set<Merge> merges) {
            return merge(that, Merge.Strategy.ALWAYS, merges);
        }

        @Contract("_ -> this")
        @NotNull
        default Builder apply(@NotNull final StyleBuilderApplicable applicable) {
            applicable.styleApply(this);
            return this;
        }
    }
}
