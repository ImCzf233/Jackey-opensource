package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.translation.Translatable;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.adventure.util.ForwardingIterator;
import com.viaversion.viaversion.libs.kyori.adventure.util.IntFunction2;
import com.viaversion.viaversion.libs.kyori.adventure.util.MonkeyBars;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.NonExtendable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/Component.class */
public interface Component extends ComponentBuilderApplicable, ComponentLike, Examinable, HoverEventSource<Component> {
    public static final BiPredicate<? super Component, ? super Component> EQUALS = (v0, v1) -> {
        return Objects.equals(v0, v1);
    };
    public static final BiPredicate<? super Component, ? super Component> EQUALS_IDENTITY = a, b -> {
        return a == b;
    };

    @NotNull
    List<Component> children();

    @Contract(pure = true)
    @NotNull
    Component children(@NotNull final List<? extends ComponentLike> children);

    @Contract(pure = true)
    @NotNull
    Component append(@NotNull final Component component);

    @NotNull
    Style style();

    @Contract(pure = true)
    @NotNull
    Component style(@NotNull final Style style);

    @Contract(pure = true)
    @NotNull
    Component replaceText(@NotNull final Consumer<TextReplacementConfig.Builder> configurer);

    @Contract(pure = true)
    @NotNull
    Component replaceText(@NotNull final TextReplacementConfig config);

    @NotNull
    Component compact();

    @NotNull
    static TextComponent empty() {
        return TextComponentImpl.EMPTY;
    }

    @NotNull
    static TextComponent newline() {
        return TextComponentImpl.NEWLINE;
    }

    @NotNull
    static TextComponent space() {
        return TextComponentImpl.SPACE;
    }

    @Contract(value = "_, _ -> new", pure = true)
    @Deprecated
    @NotNull
    static TextComponent join(@NotNull final ComponentLike separator, @NotNull final ComponentLike... components) {
        return join(separator, Arrays.asList(components));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @Deprecated
    @NotNull
    static TextComponent join(@NotNull final ComponentLike separator, final Iterable<? extends ComponentLike> components) {
        Component component = join(JoinConfiguration.separator(separator), components);
        return component instanceof TextComponent ? (TextComponent) component : text().append(component).build();
    }

    @Contract(pure = true)
    @NotNull
    static Component join(@NotNull final JoinConfiguration config, @NotNull final ComponentLike... components) {
        return join(config, Arrays.asList(components));
    }

    @Contract(pure = true)
    @NotNull
    static Component join(@NotNull final JoinConfiguration config, @NotNull final Iterable<? extends ComponentLike> components) {
        return JoinConfigurationImpl.join(config, components);
    }

    @NotNull
    static Collector<Component, ? extends ComponentBuilder<?, ?>, Component> toComponent() {
        return toComponent(empty());
    }

    @NotNull
    static Collector<Component, ? extends ComponentBuilder<?, ?>, Component> toComponent(@NotNull final Component separator) {
        return Collector.of(Component::text, builder, add -> {
            if (separator != empty() && !builder.children().isEmpty()) {
                builder.append(separator);
            }
            builder.append(add);
        }, a, b -> {
            List<Component> aChildren = a.children();
            TextComponent.Builder ret = text().append(aChildren);
            if (!aChildren.isEmpty()) {
                ret.append(separator);
            }
            ret.append(b.children());
            return ret;
        }, (v0) -> {
            return v0.build();
        }, new Collector.Characteristics[0]);
    }

    @Contract(pure = true)
    static BlockNBTComponent.Builder blockNBT() {
        return new BlockNBTComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static BlockNBTComponent blockNBT(@NotNull final Consumer<? super BlockNBTComponent.Builder> consumer) {
        return (BlockNBTComponent) Buildable.configureAndBuild(blockNBT(), consumer);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static BlockNBTComponent blockNBT(@NotNull final String nbtPath, final BlockNBTComponent.Pos pos) {
        return blockNBT(nbtPath, false, pos);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static BlockNBTComponent blockNBT(@NotNull final String nbtPath, final boolean interpret, final BlockNBTComponent.Pos pos) {
        return blockNBT(nbtPath, interpret, null, pos);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static BlockNBTComponent blockNBT(@NotNull final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, final BlockNBTComponent.Pos pos) {
        return new BlockNBTComponentImpl(Collections.emptyList(), Style.empty(), nbtPath, interpret, separator, pos);
    }

    @Contract(pure = true)
    static EntityNBTComponent.Builder entityNBT() {
        return new EntityNBTComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static EntityNBTComponent entityNBT(@NotNull final Consumer<? super EntityNBTComponent.Builder> consumer) {
        return (EntityNBTComponent) Buildable.configureAndBuild(entityNBT(), consumer);
    }

    @Contract("_, _ -> new")
    @NotNull
    static EntityNBTComponent entityNBT(@NotNull final String nbtPath, @NotNull final String selector) {
        return (EntityNBTComponent) entityNBT().nbtPath(nbtPath).selector(selector).build();
    }

    @Contract(pure = true)
    static KeybindComponent.Builder keybind() {
        return new KeybindComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static KeybindComponent keybind(@NotNull final Consumer<? super KeybindComponent.Builder> consumer) {
        return (KeybindComponent) Buildable.configureAndBuild(keybind(), consumer);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(@NotNull final String keybind) {
        return keybind(keybind, Style.empty());
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(final KeybindComponent.KeybindLike keybind) {
        return keybind(((KeybindComponent.KeybindLike) Objects.requireNonNull(keybind, "keybind")).asKeybind(), Style.empty());
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(@NotNull final String keybind, @NotNull final Style style) {
        return new KeybindComponentImpl(Collections.emptyList(), style, keybind);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(final KeybindComponent.KeybindLike keybind, @NotNull final Style style) {
        return new KeybindComponentImpl(Collections.emptyList(), style, ((KeybindComponent.KeybindLike) Objects.requireNonNull(keybind, "keybind")).asKeybind());
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(@NotNull final String keybind, @Nullable final TextColor color) {
        return keybind(keybind, Style.style(color));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(final KeybindComponent.KeybindLike keybind, @Nullable final TextColor color) {
        return keybind(((KeybindComponent.KeybindLike) Objects.requireNonNull(keybind, "keybind")).asKeybind(), Style.style(color));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(@NotNull final String keybind, @Nullable final TextColor color, final TextDecoration... decorations) {
        return keybind(keybind, Style.style(color, decorations));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(final KeybindComponent.KeybindLike keybind, @Nullable final TextColor color, final TextDecoration... decorations) {
        return keybind(((KeybindComponent.KeybindLike) Objects.requireNonNull(keybind, "keybind")).asKeybind(), Style.style(color, decorations));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(@NotNull final String keybind, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations) {
        return keybind(keybind, Style.style(color, decorations));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(final KeybindComponent.KeybindLike keybind, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations) {
        return keybind(((KeybindComponent.KeybindLike) Objects.requireNonNull(keybind, "keybind")).asKeybind(), Style.style(color, decorations));
    }

    @Contract(pure = true)
    static ScoreComponent.Builder score() {
        return new ScoreComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static ScoreComponent score(@NotNull final Consumer<? super ScoreComponent.Builder> consumer) {
        return (ScoreComponent) Buildable.configureAndBuild(score(), consumer);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static ScoreComponent score(@NotNull final String name, @NotNull final String objective) {
        return score(name, objective, null);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @Deprecated
    @NotNull
    static ScoreComponent score(@NotNull final String name, @NotNull final String objective, @Nullable final String value) {
        return new ScoreComponentImpl(Collections.emptyList(), Style.empty(), name, objective, value);
    }

    @Contract(pure = true)
    static SelectorComponent.Builder selector() {
        return new SelectorComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static SelectorComponent selector(@NotNull final Consumer<? super SelectorComponent.Builder> consumer) {
        return (SelectorComponent) Buildable.configureAndBuild(selector(), consumer);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static SelectorComponent selector(@NotNull final String pattern) {
        return selector(pattern, null);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static SelectorComponent selector(@NotNull final String pattern, @Nullable final ComponentLike separator) {
        return new SelectorComponentImpl(Collections.emptyList(), Style.empty(), pattern, separator);
    }

    @Contract(pure = true)
    static StorageNBTComponent.Builder storageNBT() {
        return new StorageNBTComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static StorageNBTComponent storageNBT(@NotNull final Consumer<? super StorageNBTComponent.Builder> consumer) {
        return (StorageNBTComponent) Buildable.configureAndBuild(storageNBT(), consumer);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static StorageNBTComponent storageNBT(@NotNull final String nbtPath, @NotNull final Key storage) {
        return storageNBT(nbtPath, false, storage);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static StorageNBTComponent storageNBT(@NotNull final String nbtPath, final boolean interpret, @NotNull final Key storage) {
        return storageNBT(nbtPath, interpret, null, storage);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static StorageNBTComponent storageNBT(@NotNull final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, @NotNull final Key storage) {
        return new StorageNBTComponentImpl(Collections.emptyList(), Style.empty(), nbtPath, interpret, separator, storage);
    }

    @Contract(pure = true)
    static TextComponent.Builder text() {
        return new TextComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static TextComponent text(@NotNull final Consumer<? super TextComponent.Builder> consumer) {
        return (TextComponent) Buildable.configureAndBuild(text(), consumer);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TextComponent text(@NotNull final String content) {
        return content.isEmpty() ? empty() : new TextComponentImpl(Collections.emptyList(), Style.empty(), content);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(@NotNull final String content, @NotNull final Style style) {
        return new TextComponentImpl(Collections.emptyList(), style, content);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(@NotNull final String content, @Nullable final TextColor color) {
        return new TextComponentImpl(Collections.emptyList(), Style.style(color), content);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(@NotNull final String content, @Nullable final TextColor color, final TextDecoration... decorations) {
        return new TextComponentImpl(Collections.emptyList(), Style.style(color, decorations), content);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(@NotNull final String content, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations) {
        return new TextComponentImpl(Collections.emptyList(), Style.style(color, decorations), content);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TextComponent text(final boolean value) {
        return text(String.valueOf(value));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final boolean value, @NotNull final Style style) {
        return text(String.valueOf(value), style);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final boolean value, @Nullable final TextColor color) {
        return text(String.valueOf(value), color);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final boolean value, @Nullable final TextColor color, final TextDecoration... decorations) {
        return text(String.valueOf(value), color, decorations);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final boolean value, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations) {
        return text(String.valueOf(value), color, decorations);
    }

    @Contract(pure = true)
    @NotNull
    static TextComponent text(final char value) {
        return value == '\n' ? newline() : value == ' ' ? space() : text(String.valueOf(value));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final char value, @NotNull final Style style) {
        return text(String.valueOf(value), style);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final char value, @Nullable final TextColor color) {
        return text(String.valueOf(value), color);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final char value, @Nullable final TextColor color, final TextDecoration... decorations) {
        return text(String.valueOf(value), color, decorations);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final char value, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations) {
        return text(String.valueOf(value), color, decorations);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TextComponent text(final double value) {
        return text(String.valueOf(value));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final double value, @NotNull final Style style) {
        return text(String.valueOf(value), style);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final double value, @Nullable final TextColor color) {
        return text(String.valueOf(value), color);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final double value, @Nullable final TextColor color, final TextDecoration... decorations) {
        return text(String.valueOf(value), color, decorations);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final double value, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations) {
        return text(String.valueOf(value), color, decorations);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TextComponent text(final float value) {
        return text(String.valueOf(value));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final float value, @NotNull final Style style) {
        return text(String.valueOf(value), style);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final float value, @Nullable final TextColor color) {
        return text(String.valueOf(value), color);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final float value, @Nullable final TextColor color, final TextDecoration... decorations) {
        return text(String.valueOf(value), color, decorations);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final float value, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations) {
        return text(String.valueOf(value), color, decorations);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TextComponent text(final int value) {
        return text(String.valueOf(value));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final int value, @NotNull final Style style) {
        return text(String.valueOf(value), style);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final int value, @Nullable final TextColor color) {
        return text(String.valueOf(value), color);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final int value, @Nullable final TextColor color, final TextDecoration... decorations) {
        return text(String.valueOf(value), color, decorations);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final int value, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations) {
        return text(String.valueOf(value), color, decorations);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TextComponent text(final long value) {
        return text(String.valueOf(value));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final long value, @NotNull final Style style) {
        return text(String.valueOf(value), style);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final long value, @Nullable final TextColor color) {
        return text(String.valueOf(value), color);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final long value, @Nullable final TextColor color, final TextDecoration... decorations) {
        return text(String.valueOf(value), color, decorations);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(final long value, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations) {
        return text(String.valueOf(value), color, decorations);
    }

    @Contract(pure = true)
    static TranslatableComponent.Builder translatable() {
        return new TranslatableComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static TranslatableComponent translatable(@NotNull final Consumer<? super TranslatableComponent.Builder> consumer) {
        return (TranslatableComponent) Buildable.configureAndBuild(translatable(), consumer);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final String key) {
        return translatable(key, Style.empty());
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final Translatable translatable) {
        return translatable(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey(), Style.empty());
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final String key, @NotNull final Style style) {
        return new TranslatableComponentImpl(Collections.emptyList(), style, key, Collections.emptyList());
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final Style style) {
        return translatable(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey(), style);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color) {
        return translatable(key, Style.style(color));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color) {
        return translatable(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey(), color);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, final TextDecoration... decorations) {
        return translatable(key, Style.style(color, decorations));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, final TextDecoration... decorations) {
        return translatable(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey(), color, decorations);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations) {
        return translatable(key, Style.style(color, decorations));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations) {
        return translatable(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey(), color, decorations);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final String key, @NotNull final ComponentLike... args) {
        return translatable(key, Style.empty(), args);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final ComponentLike... args) {
        return translatable(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey(), args);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final String key, @NotNull final Style style, @NotNull final ComponentLike... args) {
        return new TranslatableComponentImpl(Collections.emptyList(), style, key, args);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final Style style, @NotNull final ComponentLike... args) {
        return translatable(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey(), style, args);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final ComponentLike... args) {
        return translatable(key, Style.style(color), args);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final ComponentLike... args) {
        return translatable(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey(), color, args);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations, @NotNull final ComponentLike... args) {
        return translatable(key, Style.style(color, decorations), args);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations, @NotNull final ComponentLike... args) {
        return translatable(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey(), color, decorations, args);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final String key, @NotNull final List<? extends ComponentLike> args) {
        return new TranslatableComponentImpl(Collections.emptyList(), Style.empty(), key, args);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final List<? extends ComponentLike> args) {
        return translatable(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey(), args);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final String key, @NotNull final Style style, @NotNull final List<? extends ComponentLike> args) {
        return new TranslatableComponentImpl(Collections.emptyList(), style, key, args);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final Translatable translatable, @NotNull final Style style, @NotNull final List<? extends ComponentLike> args) {
        return translatable(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey(), style, args);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final List<? extends ComponentLike> args) {
        return translatable(key, Style.style(color), args);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final List<? extends ComponentLike> args) {
        return translatable(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey(), color, args);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final String key, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations, @NotNull final List<? extends ComponentLike> args) {
        return translatable(key, Style.style(color, decorations), args);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull final Translatable translatable, @Nullable final TextColor color, @NotNull final Set<TextDecoration> decorations, @NotNull final List<? extends ComponentLike> args) {
        return translatable(((Translatable) Objects.requireNonNull(translatable, "translatable")).translationKey(), color, decorations, args);
    }

    default boolean contains(@NotNull final Component that) {
        return contains(that, EQUALS_IDENTITY);
    }

    default boolean contains(@NotNull final Component that, @NotNull final BiPredicate<? super Component, ? super Component> equals) {
        if (equals.test(this, that)) {
            return true;
        }
        for (Component child : children()) {
            if (child.contains(that, equals)) {
                return true;
            }
        }
        HoverEvent<?> hoverEvent = hoverEvent();
        if (hoverEvent != null) {
            Object value = hoverEvent.value();
            Component component = null;
            if (value instanceof Component) {
                component = (Component) hoverEvent.value();
            } else if (value instanceof HoverEvent.ShowEntity) {
                component = ((HoverEvent.ShowEntity) value).name();
            }
            if (component != null) {
                if (equals.test(that, component)) {
                    return true;
                }
                for (Component child2 : component.children()) {
                    if (child2.contains(that, equals)) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Deprecated
    default void detectCycle(@NotNull final Component that) {
        if (that.contains(this)) {
            throw new IllegalStateException("Component cycle detected between " + this + " and " + that);
        }
    }

    @NotNull
    default Component append(@NotNull final ComponentLike component) {
        return append(component.asComponent());
    }

    @Contract(pure = true)
    @NotNull
    default Component append(@NotNull final ComponentBuilder<?, ?> builder) {
        return append(builder.build());
    }

    @Contract(pure = true)
    @NotNull
    default Component style(@NotNull final Consumer<Style.Builder> consumer) {
        return style(style().edit(consumer));
    }

    @Contract(pure = true)
    @NotNull
    default Component style(@NotNull final Consumer<Style.Builder> consumer, final Style.Merge.Strategy strategy) {
        return style(style().edit(consumer, strategy));
    }

    @Contract(pure = true)
    @NotNull
    default Component style(final Style.Builder style) {
        return style(style.build());
    }

    @Contract(pure = true)
    @NotNull
    default Component mergeStyle(@NotNull final Component that) {
        return mergeStyle(that, Style.Merge.all());
    }

    @Contract(pure = true)
    @NotNull
    default Component mergeStyle(@NotNull final Component that, final Style.Merge... merges) {
        return mergeStyle(that, Style.Merge.m107of(merges));
    }

    @Contract(pure = true)
    @NotNull
    default Component mergeStyle(@NotNull final Component that, @NotNull final Set<Style.Merge> merges) {
        return style(style().merge(that.style(), merges));
    }

    @Nullable
    default TextColor color() {
        return style().color();
    }

    @Contract(pure = true)
    @NotNull
    default Component color(@Nullable final TextColor color) {
        return style(style().color(color));
    }

    @Contract(pure = true)
    @NotNull
    default Component colorIfAbsent(@Nullable final TextColor color) {
        return color() == null ? color(color) : this;
    }

    default boolean hasDecoration(@NotNull final TextDecoration decoration) {
        return decoration(decoration) == TextDecoration.State.TRUE;
    }

    @Contract(pure = true)
    @NotNull
    default Component decorate(@NotNull final TextDecoration decoration) {
        return decoration(decoration, TextDecoration.State.TRUE);
    }

    default TextDecoration.State decoration(@NotNull final TextDecoration decoration) {
        return style().decoration(decoration);
    }

    @Contract(pure = true)
    @NotNull
    default Component decoration(@NotNull final TextDecoration decoration, final boolean flag) {
        return decoration(decoration, TextDecoration.State.byBoolean(flag));
    }

    @Contract(pure = true)
    @NotNull
    default Component decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state) {
        return style(style().decoration(decoration, state));
    }

    @NotNull
    default Map<TextDecoration, TextDecoration.State> decorations() {
        return style().decorations();
    }

    @Contract(pure = true)
    @NotNull
    default Component decorations(@NotNull final Map<TextDecoration, TextDecoration.State> decorations) {
        return style(style().decorations(decorations));
    }

    @Nullable
    default ClickEvent clickEvent() {
        return style().clickEvent();
    }

    @Contract(pure = true)
    @NotNull
    default Component clickEvent(@Nullable final ClickEvent event) {
        return style(style().clickEvent(event));
    }

    @Nullable
    default HoverEvent<?> hoverEvent() {
        return style().hoverEvent();
    }

    @Contract(pure = true)
    @NotNull
    default Component hoverEvent(@Nullable final HoverEventSource<?> source) {
        return style(style().hoverEvent(source));
    }

    @Nullable
    default String insertion() {
        return style().insertion();
    }

    @Contract(pure = true)
    @NotNull
    default Component insertion(@Nullable final String insertion) {
        return style(style().insertion(insertion));
    }

    default boolean hasStyling() {
        return !style().isEmpty();
    }

    @NotNull
    default Iterable<Component> iterable(@NotNull final ComponentIteratorType type, @NotNull final ComponentIteratorFlag... flags) {
        return iterable(type, flags == null ? Collections.emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, flags));
    }

    @NotNull
    default Iterable<Component> iterable(@NotNull final ComponentIteratorType type, @NotNull final Set<ComponentIteratorFlag> flags) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(flags, "flags");
        return new ForwardingIterator(()
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0027: RETURN  
              (wrap: com.viaversion.viaversion.libs.kyori.adventure.util.ForwardingIterator : 0x0024: CONSTRUCTOR  (r0v4 com.viaversion.viaversion.libs.kyori.adventure.util.ForwardingIterator A[REMOVE]) = 
              (wrap: java.util.function.Supplier : 0x0017: INVOKE_CUSTOM (r2v1 java.util.function.Supplier A[REMOVE]) = 
              (r7v0 'this' com.viaversion.viaversion.libs.kyori.adventure.text.Component A[D('this' com.viaversion.viaversion.libs.kyori.adventure.text.Component), DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r8v0 'type' com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorType A[D('type' com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorType), DONT_INLINE])
              (r9v0 'flags' java.util.Set<com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorFlag> A[D('flags' java.util.Set<com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorFlag>), DONT_INLINE])
            
             handle type: INVOKE_DIRECT
             lambda: java.util.function.Supplier.get():java.lang.Object
             call insn: ?: INVOKE  
              (r2 I:com.viaversion.viaversion.libs.kyori.adventure.text.Component)
              (r3 I:com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorType)
              (r4 I:java.util.Set)
             type: DIRECT call: com.viaversion.viaversion.libs.kyori.adventure.text.Component.lambda$iterable$3(com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorType, java.util.Set):java.util.Iterator)
              (wrap: java.util.function.Supplier : 0x001f: INVOKE_CUSTOM (r3v2 java.util.function.Supplier A[REMOVE]) = 
              (r7v0 'this' com.viaversion.viaversion.libs.kyori.adventure.text.Component A[D('this' com.viaversion.viaversion.libs.kyori.adventure.text.Component), DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r8v0 'type' com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorType A[D('type' com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorType), DONT_INLINE])
              (r9v0 'flags' java.util.Set<com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorFlag> A[D('flags' java.util.Set<com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorFlag>), DONT_INLINE])
            
             handle type: INVOKE_DIRECT
             lambda: java.util.function.Supplier.get():java.lang.Object
             call insn: ?: INVOKE  
              (r3 I:com.viaversion.viaversion.libs.kyori.adventure.text.Component)
              (r4 I:com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorType)
              (r5 I:java.util.Set)
             type: DIRECT call: com.viaversion.viaversion.libs.kyori.adventure.text.Component.lambda$iterable$4(com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorType, java.util.Set):java.util.Spliterator)
             call: com.viaversion.viaversion.libs.kyori.adventure.util.ForwardingIterator.<init>(java.util.function.Supplier, java.util.function.Supplier):void type: CONSTRUCTOR)
             in method: com.viaversion.viaversion.libs.kyori.adventure.text.Component.iterable(com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorType, java.util.Set<com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorFlag>):java.lang.Iterable<com.viaversion.viaversion.libs.kyori.adventure.text.Component>, file: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/Component.class
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:287)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:286)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:265)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:369)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
            	at java.base/java.util.ArrayList.forEach(Unknown Source)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
            Caused by: java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 2
            	at java.base/jdk.internal.util.Preconditions.outOfBounds(Unknown Source)
            	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Unknown Source)
            	at java.base/jdk.internal.util.Preconditions.checkIndex(Unknown Source)
            	at java.base/java.util.Objects.checkIndex(Unknown Source)
            	at java.base/java.util.ArrayList.get(Unknown Source)
            	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:952)
            	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:857)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:791)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1029)
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:745)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:395)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:345)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
            	... 15 more
            */
        /*
            this = this;
            r0 = r8
            java.lang.String r1 = "type"
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0, r1)
            r0 = r9
            java.lang.String r1 = "flags"
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0, r1)
            com.viaversion.viaversion.libs.kyori.adventure.util.ForwardingIterator r0 = new com.viaversion.viaversion.libs.kyori.adventure.util.ForwardingIterator
            r1 = r0
            r2 = r7
            r3 = r8
            r4 = r9
            java.lang.Iterable<com.viaversion.viaversion.libs.kyori.adventure.text.Component> r2 = () -> { // java.util.function.Supplier.get():java.lang.Object
                return r2.lambda$iterable$3(r3, r4);
            }
            r3 = r7
            r4 = r8
            r5 = r9
            java.lang.Iterable<com.viaversion.viaversion.libs.kyori.adventure.text.Component> r3 = () -> { // java.util.function.Supplier.get():java.lang.Object
                return r3.lambda$iterable$4(r4, r5);
            }
            r1.<init>(r2, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.libs.kyori.adventure.text.Component.iterable(com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorType, java.util.Set):java.lang.Iterable");
    }

    @NotNull
    default Iterator<Component> iterator(@NotNull final ComponentIteratorType type, @NotNull final ComponentIteratorFlag... flags) {
        return iterator(type, flags == null ? Collections.emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, flags));
    }

    @NotNull
    default Iterator<Component> iterator(@NotNull final ComponentIteratorType type, @NotNull final Set<ComponentIteratorFlag> flags) {
        return new ComponentIterator(this, (ComponentIteratorType) Objects.requireNonNull(type, "type"), (Set) Objects.requireNonNull(flags, "flags"));
    }

    @NotNull
    default Spliterator<Component> spliterator(@NotNull final ComponentIteratorType type, @NotNull final ComponentIteratorFlag... flags) {
        return spliterator(type, flags == null ? Collections.emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, flags));
    }

    @NotNull
    default Spliterator<Component> spliterator(@NotNull final ComponentIteratorType type, @NotNull final Set<ComponentIteratorFlag> flags) {
        return Spliterators.spliteratorUnknownSize(iterator(type, flags), 0);
    }

    @Contract(pure = true)
    @Deprecated
    @NotNull
    @ApiStatus.ScheduledForRemoval
    default Component replaceText(@NotNull final String search, @Nullable final ComponentLike replacement) {
        return replaceText(b -> {
            b.matchLiteral(search).replacement(replacement);
        });
    }

    @Contract(pure = true)
    @Deprecated
    @NotNull
    @ApiStatus.ScheduledForRemoval
    default Component replaceText(@NotNull final Pattern pattern, @NotNull final Function<TextComponent.Builder, ComponentLike> replacement) {
        return replaceText(b -> {
            b.match(pattern).replacement(replacement);
        });
    }

    @Contract(pure = true)
    @Deprecated
    @NotNull
    @ApiStatus.ScheduledForRemoval
    default Component replaceFirstText(@NotNull final String search, @Nullable final ComponentLike replacement) {
        return replaceText(b -> {
            b.matchLiteral(search).once().replacement(replacement);
        });
    }

    @Contract(pure = true)
    @Deprecated
    @NotNull
    @ApiStatus.ScheduledForRemoval
    default Component replaceFirstText(@NotNull final Pattern pattern, @NotNull final Function<TextComponent.Builder, ComponentLike> replacement) {
        return replaceText(b -> {
            b.match(pattern).once().replacement(replacement);
        });
    }

    @Contract(pure = true)
    @Deprecated
    @NotNull
    @ApiStatus.ScheduledForRemoval
    default Component replaceText(@NotNull final String search, @Nullable final ComponentLike replacement, final int numberOfReplacements) {
        return replaceText(b -> {
            b.matchLiteral(search).times(numberOfReplacements).replacement(replacement);
        });
    }

    @Contract(pure = true)
    @Deprecated
    @NotNull
    @ApiStatus.ScheduledForRemoval
    default Component replaceText(@NotNull final Pattern pattern, @NotNull final Function<TextComponent.Builder, ComponentLike> replacement, final int numberOfReplacements) {
        return replaceText(b -> {
            b.match(pattern).times(numberOfReplacements).replacement(replacement);
        });
    }

    @Contract(pure = true)
    @Deprecated
    @NotNull
    @ApiStatus.ScheduledForRemoval
    default Component replaceText(@NotNull final String search, @Nullable final ComponentLike replacement, @NotNull final IntFunction2<PatternReplacementResult> fn) {
        return replaceText(b -> {
            b.matchLiteral(search).replacement(replacement).condition(fn);
        });
    }

    @Contract(pure = true)
    @Deprecated
    @NotNull
    @ApiStatus.ScheduledForRemoval
    default Component replaceText(@NotNull final Pattern pattern, @NotNull final Function<TextComponent.Builder, ComponentLike> replacement, @NotNull final IntFunction2<PatternReplacementResult> fn) {
        return replaceText(b -> {
            b.match(pattern).replacement(replacement).condition(fn);
        });
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilderApplicable
    default void componentBuilderApply(@NotNull final ComponentBuilder<?, ?> component) {
        component.append(this);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike
    @NotNull
    default Component asComponent() {
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource
    @NotNull
    default HoverEvent<Component> asHoverEvent(@NotNull final UnaryOperator<Component> op) {
        return HoverEvent.showText((Component) op.apply(this));
    }
}
