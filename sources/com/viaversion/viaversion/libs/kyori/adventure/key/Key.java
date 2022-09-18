package com.viaversion.viaversion.libs.kyori.adventure.key;

import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/key/Key.class */
public interface Key extends Comparable<Key>, Examinable {
    public static final String MINECRAFT_NAMESPACE = "minecraft";

    @NotNull
    String namespace();

    @NotNull
    String value();

    @NotNull
    String asString();

    @NotNull
    static Key key(@Pattern("([a-z0-9_\\-.]+:)?[a-z0-9_\\-./]+") @NotNull final String string) {
        return key(string, ':');
    }

    @NotNull
    static Key key(@NotNull final String string, final char character) {
        int index = string.indexOf(character);
        String namespace = index >= 1 ? string.substring(0, index) : MINECRAFT_NAMESPACE;
        String value = index >= 0 ? string.substring(index + 1) : string;
        return key(namespace, value);
    }

    @NotNull
    static Key key(@NotNull final Namespaced namespaced, @Pattern("[a-z0-9_\\-./]+") @NotNull final String value) {
        return key(namespaced.namespace(), value);
    }

    @NotNull
    static Key key(@Pattern("[a-z0-9_\\-.]+") @NotNull final String namespace, @Pattern("[a-z0-9_\\-./]+") @NotNull final String value) {
        return new KeyImpl(namespace, value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m90of("namespace", namespace()), ExaminableProperty.m90of("value", value())});
    }

    default int compareTo(@NotNull final Key that) {
        int value = value().compareTo(that.value());
        if (value != 0) {
            return KeyImpl.clampCompare(value);
        }
        return KeyImpl.clampCompare(namespace().compareTo(that.namespace()));
    }
}
