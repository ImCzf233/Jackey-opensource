package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/translation/GlobalTranslator.class */
public interface GlobalTranslator extends Translator, Examinable {
    @NotNull
    Iterable<? extends Translator> sources();

    boolean addSource(@NotNull final Translator source);

    boolean removeSource(@NotNull final Translator source);

    @NotNull
    static GlobalTranslator get() {
        return GlobalTranslatorImpl.INSTANCE;
    }

    @NotNull
    static TranslatableComponentRenderer<Locale> renderer() {
        return GlobalTranslatorImpl.INSTANCE.renderer;
    }

    @NotNull
    static Component render(@NotNull final Component component, @NotNull final Locale locale) {
        return renderer().render(component, locale);
    }
}
