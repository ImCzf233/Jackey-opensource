package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.ComponentFlattener;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.ComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializerImpl;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/legacy/LegacyComponentSerializer.class */
public interface LegacyComponentSerializer extends ComponentSerializer<Component, TextComponent, String>, Buildable<LegacyComponentSerializer, Builder> {
    public static final char SECTION_CHAR = 167;
    public static final char AMPERSAND_CHAR = '&';
    public static final char HEX_CHAR = '#';

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/legacy/LegacyComponentSerializer$Builder.class */
    public interface Builder extends Buildable.Builder<LegacyComponentSerializer> {
        @NotNull
        Builder character(final char legacyCharacter);

        @NotNull
        Builder hexCharacter(final char legacyHexCharacter);

        @NotNull
        Builder extractUrls();

        @NotNull
        Builder extractUrls(@NotNull final Pattern pattern);

        @NotNull
        Builder extractUrls(@Nullable final Style style);

        @NotNull
        Builder extractUrls(@NotNull final Pattern pattern, @Nullable final Style style);

        @NotNull
        Builder hexColors();

        @NotNull
        Builder useUnusualXRepeatedCharacterHexFormat();

        @NotNull
        Builder flattener(@NotNull final ComponentFlattener flattener);

        @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        LegacyComponentSerializer build();
    }

    @ApiStatus.Internal
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/legacy/LegacyComponentSerializer$Provider.class */
    public interface Provider {
        @ApiStatus.Internal
        @NotNull
        LegacyComponentSerializer legacyAmpersand();

        @ApiStatus.Internal
        @NotNull
        LegacyComponentSerializer legacySection();

        @ApiStatus.Internal
        @NotNull
        Consumer<Builder> legacy();
    }

    @NotNull
    TextComponent deserialize(@NotNull final String input);

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.ComponentSerializer
    @NotNull
    String serialize(@NotNull final Component component);

    @NotNull
    static LegacyComponentSerializer legacySection() {
        return LegacyComponentSerializerImpl.Instances.SECTION;
    }

    @NotNull
    static LegacyComponentSerializer legacyAmpersand() {
        return LegacyComponentSerializerImpl.Instances.AMPERSAND;
    }

    @NotNull
    static LegacyComponentSerializer legacy(final char legacyCharacter) {
        if (legacyCharacter == 167) {
            return legacySection();
        }
        if (legacyCharacter == '&') {
            return legacyAmpersand();
        }
        return builder().character(legacyCharacter).build();
    }

    @Nullable
    static LegacyFormat parseChar(final char character) {
        return LegacyComponentSerializerImpl.legacyFormat(character);
    }

    @NotNull
    static Builder builder() {
        return new LegacyComponentSerializerImpl.BuilderImpl();
    }
}
