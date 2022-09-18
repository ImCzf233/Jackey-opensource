package com.viaversion.viaversion.libs.kyori.adventure.translation;

import java.util.Locale;
import java.util.function.Supplier;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/translation/TranslationLocales.class */
final class TranslationLocales {
    private static final Supplier<Locale> GLOBAL;

    static {
        String property = System.getProperty("net.kyo".concat("ri.adventure.defaultTranslationLocale"));
        if (property == null || property.isEmpty()) {
            GLOBAL = () -> {
                return Locale.US;
            };
        } else if (property.equals("system")) {
            GLOBAL = Locale::getDefault;
        } else {
            Locale locale = Translator.parseLocale(property);
            GLOBAL = () -> {
                return locale;
            };
        }
    }

    private TranslationLocales() {
    }

    public static Locale global() {
        return GLOBAL.get();
    }
}
