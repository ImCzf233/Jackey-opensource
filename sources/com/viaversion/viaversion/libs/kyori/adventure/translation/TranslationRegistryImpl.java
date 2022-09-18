package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.github.benmanes.caffeine.cache.LocalCacheFactory;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/translation/TranslationRegistryImpl.class */
public final class TranslationRegistryImpl implements Examinable, TranslationRegistry {
    private final Key name;
    private final Map<String, Translation> translations = new ConcurrentHashMap();
    private Locale defaultLocale = Locale.US;

    public TranslationRegistryImpl(final Key name) {
        this.name = name;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.translation.TranslationRegistry
    public void register(@NotNull final String key, @NotNull final Locale locale, @NotNull final MessageFormat format) {
        this.translations.computeIfAbsent(key, x$0 -> {
            return new Translation(x$0);
        }).register(locale, format);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.translation.TranslationRegistry
    public void unregister(@NotNull final String key) {
        this.translations.remove(key);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.translation.Translator
    @NotNull
    public Key name() {
        return this.name;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.translation.TranslationRegistry
    public boolean contains(@NotNull final String key) {
        return this.translations.containsKey(key);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.translation.TranslationRegistry, com.viaversion.viaversion.libs.kyori.adventure.translation.Translator
    @Nullable
    public MessageFormat translate(@NotNull final String key, @NotNull final Locale locale) {
        Translation translation = this.translations.get(key);
        if (translation == null) {
            return null;
        }
        return translation.translate(locale);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.translation.TranslationRegistry
    public void defaultLocale(@NotNull final Locale defaultLocale) {
        this.defaultLocale = (Locale) Objects.requireNonNull(defaultLocale, "defaultLocale");
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m91of("translations", this.translations));
    }

    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TranslationRegistryImpl)) {
            return false;
        }
        TranslationRegistryImpl that = (TranslationRegistryImpl) other;
        return this.name.equals(that.name) && this.translations.equals(that.translations) && this.defaultLocale.equals(that.defaultLocale);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.translations, this.defaultLocale);
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/translation/TranslationRegistryImpl$Translation.class */
    public final class Translation implements Examinable {
        private final String key;
        private final Map<Locale, MessageFormat> formats = new ConcurrentHashMap();

        Translation(@NotNull final String key) {
            TranslationRegistryImpl.this = this$0;
            this.key = (String) Objects.requireNonNull(key, "translation key");
        }

        void register(@NotNull final Locale locale, @NotNull final MessageFormat format) {
            if (this.formats.putIfAbsent((Locale) Objects.requireNonNull(locale, "locale"), (MessageFormat) Objects.requireNonNull(format, "message format")) != null) {
                throw new IllegalArgumentException(String.format("Translation already exists: %s for %s", this.key, locale));
            }
        }

        @Nullable
        MessageFormat translate(@NotNull final Locale locale) {
            MessageFormat format = this.formats.get(Objects.requireNonNull(locale, "locale"));
            if (format == null) {
                format = this.formats.get(new Locale(locale.getLanguage()));
                if (format == null) {
                    format = this.formats.get(TranslationRegistryImpl.this.defaultLocale);
                    if (format == null) {
                        format = this.formats.get(TranslationLocales.global());
                    }
                }
            }
            return format;
        }

        @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
        @NotNull
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m90of(LocalCacheFactory.KEY, this.key), ExaminableProperty.m91of("formats", this.formats)});
        }

        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Translation)) {
                return false;
            }
            Translation that = (Translation) other;
            return this.key.equals(that.key) && this.formats.equals(that.formats);
        }

        public int hashCode() {
            return Objects.hash(this.key, this.formats);
        }

        public String toString() {
            return (String) examine(StringExaminer.simpleEscaping());
        }
    }
}
