package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.GsonBuilder;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.util.Services;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/GsonComponentSerializerImpl.class */
public final class GsonComponentSerializerImpl implements GsonComponentSerializer {
    private static final Optional<GsonComponentSerializer.Provider> SERVICE = Services.service(GsonComponentSerializer.Provider.class);
    static final Consumer<GsonComponentSerializer.Builder> BUILDER = (Consumer) SERVICE.map((v0) -> {
        return v0.builder();
    }).orElseGet(() -> {
        return builder -> {
        };
    });
    private final Gson serializer;
    private final UnaryOperator<GsonBuilder> populator;
    private final boolean downsampleColor;
    @Nullable
    private final LegacyHoverEventSerializer legacyHoverSerializer;
    private final boolean emitLegacyHover;

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/GsonComponentSerializerImpl$Instances.class */
    public static final class Instances {
        static final GsonComponentSerializer INSTANCE = (GsonComponentSerializer) GsonComponentSerializerImpl.SERVICE.map((v0) -> {
            return v0.gson();
        }).orElseGet(() -> {
            return new GsonComponentSerializerImpl(false, null, false);
        });
        static final GsonComponentSerializer LEGACY_INSTANCE = (GsonComponentSerializer) GsonComponentSerializerImpl.SERVICE.map((v0) -> {
            return v0.gsonLegacy();
        }).orElseGet(() -> {
            return new GsonComponentSerializerImpl(true, null, true);
        });

        Instances() {
        }
    }

    GsonComponentSerializerImpl(final boolean downsampleColor, @Nullable final LegacyHoverEventSerializer legacyHoverSerializer, final boolean emitLegacyHover) {
        this.downsampleColor = downsampleColor;
        this.legacyHoverSerializer = legacyHoverSerializer;
        this.emitLegacyHover = emitLegacyHover;
        this.populator = builder -> {
            builder.registerTypeAdapterFactory(new SerializerFactory(downsampleColor, legacyHoverSerializer, emitLegacyHover));
            return builder;
        };
        this.serializer = ((GsonBuilder) this.populator.apply(new GsonBuilder())).create();
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer
    @NotNull
    public Gson serializer() {
        return this.serializer;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer
    @NotNull
    public UnaryOperator<GsonBuilder> populator() {
        return this.populator;
    }

    @NotNull
    public Component deserialize(@NotNull final String string) {
        Component component = (Component) serializer().fromJson(string, (Class<Object>) Component.class);
        if (component == null) {
            throw ComponentSerializerImpl.notSureHowToDeserialize(string);
        }
        return component;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.ComponentSerializer
    @NotNull
    public String serialize(@NotNull final Component component) {
        return serializer().toJson(component);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer
    @NotNull
    public Component deserializeFromTree(@NotNull final JsonElement input) {
        Component component = (Component) serializer().fromJson(input, (Class<Object>) Component.class);
        if (component == null) {
            throw ComponentSerializerImpl.notSureHowToDeserialize(input);
        }
        return component;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer
    @NotNull
    public JsonElement serializeToTree(@NotNull final Component component) {
        return serializer().toJsonTree(component);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    @NotNull
    public GsonComponentSerializer.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/GsonComponentSerializerImpl$BuilderImpl.class */
    public static final class BuilderImpl implements GsonComponentSerializer.Builder {
        private boolean downsampleColor;
        @Nullable
        private LegacyHoverEventSerializer legacyHoverSerializer;
        private boolean emitLegacyHover;

        public BuilderImpl() {
            this.downsampleColor = false;
            this.emitLegacyHover = false;
            GsonComponentSerializerImpl.BUILDER.accept(this);
        }

        BuilderImpl(final GsonComponentSerializerImpl serializer) {
            this();
            this.downsampleColor = serializer.downsampleColor;
            this.emitLegacyHover = serializer.emitLegacyHover;
            this.legacyHoverSerializer = serializer.legacyHoverSerializer;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer.Builder
        @NotNull
        public GsonComponentSerializer.Builder downsampleColors() {
            this.downsampleColor = true;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer.Builder
        @NotNull
        public GsonComponentSerializer.Builder legacyHoverEventSerializer(@Nullable final LegacyHoverEventSerializer serializer) {
            this.legacyHoverSerializer = serializer;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer.Builder
        @NotNull
        public GsonComponentSerializer.Builder emitLegacyHoverEvent() {
            this.emitLegacyHover = true;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer.Builder, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public GsonComponentSerializer build() {
            if (this.legacyHoverSerializer == null) {
                return this.downsampleColor ? Instances.LEGACY_INSTANCE : Instances.INSTANCE;
            }
            return new GsonComponentSerializerImpl(this.downsampleColor, this.legacyHoverSerializer, this.emitLegacyHover);
        }
    }
}
