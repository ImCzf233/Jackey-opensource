package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/IfAbsentOnTargetMerger.class */
public final class IfAbsentOnTargetMerger implements Merger {
    static final IfAbsentOnTargetMerger INSTANCE = new IfAbsentOnTargetMerger();

    private IfAbsentOnTargetMerger() {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Merger
    public void mergeColor(final StyleImpl.BuilderImpl target, @Nullable final TextColor color) {
        if (target.color == null) {
            target.color(color);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Merger
    public void mergeDecoration(final StyleImpl.BuilderImpl target, @NotNull final TextDecoration decoration, final TextDecoration.State state) {
        target.decorationIfAbsent(decoration, state);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Merger
    public void mergeClickEvent(final StyleImpl.BuilderImpl target, @Nullable final ClickEvent event) {
        if (target.clickEvent == null) {
            target.clickEvent(event);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Merger
    public void mergeHoverEvent(final StyleImpl.BuilderImpl target, @Nullable final HoverEvent<?> event) {
        if (target.hoverEvent == null) {
            target.hoverEvent(event);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Merger
    public void mergeInsertion(final StyleImpl.BuilderImpl target, @Nullable final String insertion) {
        if (target.insertion == null) {
            target.insertion(insertion);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Merger
    public void mergeFont(final StyleImpl.BuilderImpl target, @Nullable final Key font) {
        if (target.font == null) {
            target.font(font);
        }
    }
}
