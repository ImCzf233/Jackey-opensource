package com.viaversion.viaversion.libs.kyori.adventure.text.renderer;

import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.translation.Translator;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/renderer/TranslatableComponentRenderer.class */
public abstract class TranslatableComponentRenderer<C> extends AbstractComponentRenderer<C> {
    private static final Set<Style.Merge> MERGES = Style.Merge.m107of(Style.Merge.COLOR, Style.Merge.DECORATIONS, Style.Merge.INSERTION, Style.Merge.FONT);

    @Nullable
    protected abstract MessageFormat translate(@NotNull final String key, @NotNull final C context);

    @NotNull
    public static TranslatableComponentRenderer<Locale> usingTranslationSource(@NotNull final Translator source) {
        Objects.requireNonNull(source, "source");
        return new TranslatableComponentRenderer<Locale>() { // from class: com.viaversion.viaversion.libs.kyori.adventure.text.renderer.TranslatableComponentRenderer.1
            @Nullable
            public MessageFormat translate(@NotNull final String key, @NotNull final Locale context) {
                return source.translate(key, context);
            }
        };
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.renderer.AbstractComponentRenderer
    @NotNull
    protected Component renderBlockNbt(@NotNull final BlockNBTComponent component, @NotNull final C context) {
        BlockNBTComponent.Builder builder = ((BlockNBTComponent.Builder) nbt(Component.blockNBT(), component)).pos(component.pos());
        return mergeStyleAndOptionallyDeepRender(component, builder, context);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.renderer.AbstractComponentRenderer
    @NotNull
    protected Component renderEntityNbt(@NotNull final EntityNBTComponent component, @NotNull final C context) {
        EntityNBTComponent.Builder builder = ((EntityNBTComponent.Builder) nbt(Component.entityNBT(), component)).selector(component.selector());
        return mergeStyleAndOptionallyDeepRender(component, builder, context);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.renderer.AbstractComponentRenderer
    @NotNull
    protected Component renderStorageNbt(@NotNull final StorageNBTComponent component, @NotNull final C context) {
        StorageNBTComponent.Builder builder = ((StorageNBTComponent.Builder) nbt(Component.storageNBT(), component)).storage(component.storage());
        return mergeStyleAndOptionallyDeepRender(component, builder, context);
    }

    protected static NBTComponentBuilder nbt(final NBTComponentBuilder builder, final NBTComponent oldComponent) {
        return builder.nbtPath(oldComponent.nbtPath()).interpret(oldComponent.interpret());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.renderer.AbstractComponentRenderer
    @NotNull
    protected Component renderKeybind(@NotNull final KeybindComponent component, @NotNull final C context) {
        KeybindComponent.Builder builder = Component.keybind().keybind(component.keybind());
        return mergeStyleAndOptionallyDeepRender(component, builder, context);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.renderer.AbstractComponentRenderer
    @NotNull
    protected Component renderScore(@NotNull final ScoreComponent component, @NotNull final C context) {
        ScoreComponent.Builder builder = Component.score().name(component.name()).objective(component.objective()).value(component.value());
        return mergeStyleAndOptionallyDeepRender(component, builder, context);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.renderer.AbstractComponentRenderer
    @NotNull
    protected Component renderSelector(@NotNull final SelectorComponent component, @NotNull final C context) {
        SelectorComponent.Builder builder = Component.selector().pattern(component.pattern());
        return mergeStyleAndOptionallyDeepRender(component, builder, context);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.renderer.AbstractComponentRenderer
    @NotNull
    protected Component renderText(@NotNull final TextComponent component, @NotNull final C context) {
        TextComponent.Builder builder = Component.text().content(component.content());
        return mergeStyleAndOptionallyDeepRender(component, builder, context);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.renderer.AbstractComponentRenderer
    @NotNull
    protected Component renderTranslatable(@NotNull final TranslatableComponent component, @NotNull final C context) {
        MessageFormat format = translate(component.key(), context);
        if (format == null) {
            TranslatableComponent.Builder builder = Component.translatable().key(component.key());
            if (!component.args().isEmpty()) {
                List<Component> args = new ArrayList<>(component.args());
                int size = args.size();
                for (int i = 0; i < size; i++) {
                    args.set(i, render(args.get(i), context));
                }
                builder.args(args);
            }
            return mergeStyleAndOptionallyDeepRender(component, builder, context);
        }
        List<Component> args2 = component.args();
        TextComponent.Builder builder2 = Component.text();
        mergeStyle(component, builder2, context);
        if (args2.isEmpty()) {
            builder2.content(format.format((Object[]) null, new StringBuffer(), (FieldPosition) null).toString());
            return optionallyRenderChildrenAppendAndBuild(component.children(), builder2, context);
        }
        Object[] nulls = new Object[args2.size()];
        StringBuffer sb = format.format(nulls, new StringBuffer(), (FieldPosition) null);
        AttributedCharacterIterator it = format.formatToCharacterIterator(nulls);
        while (it.getIndex() < it.getEndIndex()) {
            int end = it.getRunLimit();
            Integer index = (Integer) it.getAttribute(MessageFormat.Field.ARGUMENT);
            if (index != null) {
                builder2.append(render(args2.get(index.intValue()), context));
            } else {
                builder2.append((Component) Component.text(sb.substring(it.getIndex(), end)));
            }
            it.setIndex(end);
        }
        return optionallyRenderChildrenAppendAndBuild(component.children(), builder2, context);
    }

    protected <O extends BuildableComponent<O, B>, B extends ComponentBuilder<O, B>> O mergeStyleAndOptionallyDeepRender(final Component component, final B builder, final C context) {
        mergeStyle(component, builder, context);
        return (O) optionallyRenderChildrenAppendAndBuild(component.children(), builder, context);
    }

    protected <O extends BuildableComponent<O, B>, B extends ComponentBuilder<O, B>> O optionallyRenderChildrenAppendAndBuild(final List<Component> children, final B builder, final C context) {
        if (!children.isEmpty()) {
            children.forEach(child -> {
                builder.append(render(context, builder));
            });
        }
        return (O) builder.build();
    }

    protected <B extends ComponentBuilder<?, ?>> void mergeStyle(final Component component, final B builder, final C context) {
        builder.mergeStyle(component, MERGES);
        builder.clickEvent(component.clickEvent());
        HoverEvent<?> hoverEvent = component.hoverEvent();
        if (hoverEvent != null) {
            builder.hoverEvent(hoverEvent.withRenderedValue(this, context));
        }
    }
}
