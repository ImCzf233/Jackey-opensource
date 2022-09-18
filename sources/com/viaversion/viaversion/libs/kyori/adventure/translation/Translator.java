package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import java.text.MessageFormat;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/translation/Translator.class */
public interface Translator {
    @NotNull
    Key name();

    @Nullable
    MessageFormat translate(@NotNull final String key, @NotNull final Locale locale);

    @Nullable
    static Locale parseLocale(@NotNull final String string) {
        String[] segments = string.split("_", 3);
        int length = segments.length;
        if (length == 1) {
            return new Locale(string);
        }
        if (length == 2) {
            return new Locale(segments[0], segments[1]);
        }
        if (length == 3) {
            return new Locale(segments[0], segments[1], segments[2]);
        }
        return null;
    }
}
