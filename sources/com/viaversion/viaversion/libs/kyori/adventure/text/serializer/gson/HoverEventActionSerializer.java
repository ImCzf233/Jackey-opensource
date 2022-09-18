package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/HoverEventActionSerializer.class */
final class HoverEventActionSerializer {
    static final TypeAdapter<HoverEvent.Action<?>> INSTANCE = IndexedSerializer.m104of("hover action", HoverEvent.Action.NAMES);

    private HoverEventActionSerializer() {
    }
}
