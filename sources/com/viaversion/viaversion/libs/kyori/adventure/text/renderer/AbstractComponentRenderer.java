package com.viaversion.viaversion.libs.kyori.adventure.text.renderer;

import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/renderer/AbstractComponentRenderer.class */
public abstract class AbstractComponentRenderer<C> implements ComponentRenderer<C> {
    @NotNull
    protected abstract Component renderBlockNbt(@NotNull final BlockNBTComponent component, @NotNull final C context);

    @NotNull
    protected abstract Component renderEntityNbt(@NotNull final EntityNBTComponent component, @NotNull final C context);

    @NotNull
    protected abstract Component renderStorageNbt(@NotNull final StorageNBTComponent component, @NotNull final C context);

    @NotNull
    protected abstract Component renderKeybind(@NotNull final KeybindComponent component, @NotNull final C context);

    @NotNull
    protected abstract Component renderScore(@NotNull final ScoreComponent component, @NotNull final C context);

    @NotNull
    protected abstract Component renderSelector(@NotNull final SelectorComponent component, @NotNull final C context);

    @NotNull
    protected abstract Component renderText(@NotNull final TextComponent component, @NotNull final C context);

    @NotNull
    protected abstract Component renderTranslatable(@NotNull final TranslatableComponent component, @NotNull final C context);

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.renderer.ComponentRenderer
    @NotNull
    public Component render(@NotNull final Component component, @NotNull final C context) {
        if (component instanceof TextComponent) {
            return renderText((TextComponent) component, context);
        }
        if (component instanceof TranslatableComponent) {
            return renderTranslatable((TranslatableComponent) component, context);
        }
        if (component instanceof KeybindComponent) {
            return renderKeybind((KeybindComponent) component, context);
        }
        if (component instanceof ScoreComponent) {
            return renderScore((ScoreComponent) component, context);
        }
        if (component instanceof SelectorComponent) {
            return renderSelector((SelectorComponent) component, context);
        }
        if (component instanceof NBTComponent) {
            if (component instanceof BlockNBTComponent) {
                return renderBlockNbt((BlockNBTComponent) component, context);
            }
            if (component instanceof EntityNBTComponent) {
                return renderEntityNbt((EntityNBTComponent) component, context);
            }
            if (component instanceof StorageNBTComponent) {
                return renderStorageNbt((StorageNBTComponent) component, context);
            }
        }
        return component;
    }
}
