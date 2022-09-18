package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/ClickEventActionSerializer.class */
final class ClickEventActionSerializer {
    static final TypeAdapter<ClickEvent.Action> INSTANCE = IndexedSerializer.m104of("click action", ClickEvent.Action.NAMES);

    private ClickEventActionSerializer() {
    }
}
