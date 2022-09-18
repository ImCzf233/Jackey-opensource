package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.github.benmanes.caffeine.cache.LocalCacheFactory;
import com.viaversion.viaversion.libs.kyori.adventure.Adventure;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/translation/GlobalTranslatorImpl.class */
public final class GlobalTranslatorImpl implements GlobalTranslator {
    private static final Key NAME = Key.key(Adventure.NAMESPACE, "global");
    static final GlobalTranslatorImpl INSTANCE = new GlobalTranslatorImpl();
    final TranslatableComponentRenderer<Locale> renderer = TranslatableComponentRenderer.usingTranslationSource(this);
    private final Set<Translator> sources = Collections.newSetFromMap(new ConcurrentHashMap());

    private GlobalTranslatorImpl() {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.translation.Translator
    @NotNull
    public Key name() {
        return NAME;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.translation.GlobalTranslator
    @NotNull
    public Iterable<? extends Translator> sources() {
        return Collections.unmodifiableSet(this.sources);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.translation.GlobalTranslator
    public boolean addSource(@NotNull final Translator source) {
        Objects.requireNonNull(source, "source");
        if (source == this) {
            throw new IllegalArgumentException("GlobalTranslationSource");
        }
        return this.sources.add(source);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.translation.GlobalTranslator
    public boolean removeSource(@NotNull final Translator source) {
        Objects.requireNonNull(source, "source");
        return this.sources.remove(source);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.translation.Translator
    @Nullable
    public MessageFormat translate(@NotNull final String key, @NotNull final Locale locale) {
        Objects.requireNonNull(key, LocalCacheFactory.KEY);
        Objects.requireNonNull(locale, "locale");
        for (Translator source : this.sources) {
            MessageFormat translation = source.translate(key, locale);
            if (translation != null) {
                return translation;
            }
        }
        return null;
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m91of("sources", this.sources));
    }
}
