package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/AlwaysMerger.class */
public final class AlwaysMerger implements Merger {
    static final AlwaysMerger INSTANCE = new AlwaysMerger();

    private AlwaysMerger() {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Merger
    public void mergeColor(final StyleImpl.BuilderImpl target, @Nullable final TextColor color) {
        target.color(color);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Merger
    public void mergeDecoration(final StyleImpl.BuilderImpl target, @NotNull final TextDecoration decoration, final TextDecoration.State state) {
        target.decoration(decoration, state);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Merger
    public void mergeClickEvent(final StyleImpl.BuilderImpl target, @Nullable final ClickEvent event) {
        target.clickEvent(event);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Merger
    public void mergeHoverEvent(final StyleImpl.BuilderImpl target, @Nullable final HoverEvent<?> event) {
        target.hoverEvent(event);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Merger
    public void mergeInsertion(final StyleImpl.BuilderImpl target, @Nullable final String insertion) {
        target.insertion(insertion);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.Merger
    public void mergeFont(final StyleImpl.BuilderImpl target, @Nullable final Key font) {
        target.font(font);
    }
}
