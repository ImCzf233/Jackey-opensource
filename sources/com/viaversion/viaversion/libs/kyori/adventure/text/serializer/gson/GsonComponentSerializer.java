package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.GsonBuilder;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.ComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializerImpl;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/GsonComponentSerializer.class */
public interface GsonComponentSerializer extends ComponentSerializer<Component, Component, String>, Buildable<GsonComponentSerializer, Builder> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/GsonComponentSerializer$Builder.class */
    public interface Builder extends Buildable.Builder<GsonComponentSerializer> {
        @NotNull
        Builder downsampleColors();

        @NotNull
        Builder legacyHoverEventSerializer(@Nullable final LegacyHoverEventSerializer serializer);

        @NotNull
        Builder emitLegacyHoverEvent();

        @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        GsonComponentSerializer build();
    }

    @ApiStatus.Internal
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/GsonComponentSerializer$Provider.class */
    public interface Provider {
        @ApiStatus.Internal
        @NotNull
        GsonComponentSerializer gson();

        @ApiStatus.Internal
        @NotNull
        GsonComponentSerializer gsonLegacy();

        @ApiStatus.Internal
        @NotNull
        Consumer<Builder> builder();
    }

    @NotNull
    Gson serializer();

    @NotNull
    UnaryOperator<GsonBuilder> populator();

    @NotNull
    Component deserializeFromTree(@NotNull final JsonElement input);

    @NotNull
    JsonElement serializeToTree(@NotNull final Component component);

    @NotNull
    static GsonComponentSerializer gson() {
        return GsonComponentSerializerImpl.Instances.INSTANCE;
    }

    @NotNull
    static GsonComponentSerializer colorDownsamplingGson() {
        return GsonComponentSerializerImpl.Instances.LEGACY_INSTANCE;
    }

    static Builder builder() {
        return new GsonComponentSerializerImpl.BuilderImpl();
    }
}
