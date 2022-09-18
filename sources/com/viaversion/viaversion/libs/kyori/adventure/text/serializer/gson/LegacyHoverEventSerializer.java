package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/LegacyHoverEventSerializer.class */
public interface LegacyHoverEventSerializer {
    HoverEvent.ShowItem deserializeShowItem(@NotNull final Component input) throws IOException;

    HoverEvent.ShowEntity deserializeShowEntity(@NotNull final Component input, final Codec.Decoder<Component, String, ? extends RuntimeException> componentDecoder) throws IOException;

    @NotNull
    Component serializeShowItem(final HoverEvent.ShowItem input) throws IOException;

    @NotNull
    Component serializeShowEntity(final HoverEvent.ShowEntity input, final Codec.Encoder<Component, String, ? extends RuntimeException> componentEncoder) throws IOException;
}
