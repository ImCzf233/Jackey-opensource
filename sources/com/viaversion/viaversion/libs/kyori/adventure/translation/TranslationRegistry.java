package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/translation/TranslationRegistry.class */
public interface TranslationRegistry extends Translator {
    public static final Pattern SINGLE_QUOTE_PATTERN = Pattern.compile("'");

    boolean contains(@NotNull final String key);

    @Override // com.viaversion.viaversion.libs.kyori.adventure.translation.Translator
    @Nullable
    MessageFormat translate(@NotNull final String key, @NotNull final Locale locale);

    void defaultLocale(@NotNull final Locale locale);

    void register(@NotNull final String key, @NotNull final Locale locale, @NotNull final MessageFormat format);

    void unregister(@NotNull final String key);

    @NotNull
    static TranslationRegistry create(final Key name) {
        return new TranslationRegistryImpl((Key) Objects.requireNonNull(name, "name"));
    }

    default void registerAll(@NotNull final Locale locale, @NotNull final Map<String, MessageFormat> formats) {
        Set<String> keySet = formats.keySet();
        Objects.requireNonNull(formats);
        registerAll(locale, keySet, (v1) -> {
            return r3.get(v1);
        });
    }

    default void registerAll(@NotNull final Locale locale, @NotNull final Path path, final boolean escapeSingleQuotes) {
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            registerAll(locale, new PropertyResourceBundle(reader), escapeSingleQuotes);
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
        }
    }

    default void registerAll(@NotNull final Locale locale, @NotNull final ResourceBundle bundle, final boolean escapeSingleQuotes) {
        registerAll(locale, bundle.keySet(), key -> {
            String str;
            String format = bundle.getString(key);
            if (escapeSingleQuotes) {
                str = SINGLE_QUOTE_PATTERN.matcher(format).replaceAll("''");
            } else {
                str = format;
            }
            return new MessageFormat(str, locale);
        });
    }

    default void registerAll(@NotNull final Locale locale, @NotNull final Set<String> keys, final Function<String, MessageFormat> function) {
        List<IllegalArgumentException> errors = null;
        for (String key : keys) {
            try {
                register(key, locale, function.apply(key));
            } catch (IllegalArgumentException e) {
                if (errors == null) {
                    errors = new LinkedList<>();
                }
                errors.add(e);
            }
        }
        if (errors != null) {
            int size = errors.size();
            if (size == 1) {
                throw errors.get(0);
            }
            if (size > 1) {
                throw new IllegalArgumentException(String.format("Invalid key (and %d more)", Integer.valueOf(size - 1)), errors.get(0));
            }
        }
    }
}
