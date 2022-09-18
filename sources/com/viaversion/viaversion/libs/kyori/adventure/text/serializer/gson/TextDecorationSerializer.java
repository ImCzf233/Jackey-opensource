package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/gson/TextDecorationSerializer.class */
final class TextDecorationSerializer {
    static final TypeAdapter<TextDecoration> INSTANCE = IndexedSerializer.m104of("text decoration", TextDecoration.NAMES);

    private TextDecorationSerializer() {
    }
}
