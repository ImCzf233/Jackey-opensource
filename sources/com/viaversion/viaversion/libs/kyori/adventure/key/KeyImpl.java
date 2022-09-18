package com.viaversion.viaversion.libs.kyori.adventure.key;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/key/KeyImpl.class */
public final class KeyImpl implements Key {
    static final String NAMESPACE_PATTERN = "[a-z0-9_\\-.]+";
    static final String VALUE_PATTERN = "[a-z0-9_\\-./]+";
    private static final IntPredicate NAMESPACE_PREDICATE = value -> {
        return value == 95 || value == 45 || (value >= 97 && value <= 122) || ((value >= 48 && value <= 57) || value == 46);
    };
    private static final IntPredicate VALUE_PREDICATE = value -> {
        return value == 95 || value == 45 || (value >= 97 && value <= 122) || ((value >= 48 && value <= 57) || value == 47 || value == 46);
    };
    private final String namespace;
    private final String value;

    public KeyImpl(@NotNull final String namespace, @NotNull final String value) {
        if (!namespaceValid(namespace)) {
            throw new InvalidKeyException(namespace, value, String.format("Non [a-z0-9_.-] character in namespace of Key[%s]", asString(namespace, value)));
        }
        if (!valueValid(value)) {
            throw new InvalidKeyException(namespace, value, String.format("Non [a-z0-9/._-] character in value of Key[%s]", asString(namespace, value)));
        }
        this.namespace = (String) Objects.requireNonNull(namespace, "namespace");
        this.value = (String) Objects.requireNonNull(value, "value");
    }

    @VisibleForTesting
    static boolean namespaceValid(@NotNull final String namespace) {
        int length = namespace.length();
        for (int i = 0; i < length; i++) {
            if (!NAMESPACE_PREDICATE.test(namespace.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @VisibleForTesting
    static boolean valueValid(@NotNull final String value) {
        int length = value.length();
        for (int i = 0; i < length; i++) {
            if (!VALUE_PREDICATE.test(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.key.Key
    @NotNull
    public String namespace() {
        return this.namespace;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.key.Key
    @NotNull
    public String value() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.key.Key
    @NotNull
    public String asString() {
        return asString(this.namespace, this.value);
    }

    @NotNull
    private static String asString(@NotNull final String namespace, @NotNull final String value) {
        return namespace + ':' + value;
    }

    @NotNull
    public String toString() {
        return asString();
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.key.Key, com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m90of("namespace", this.namespace), ExaminableProperty.m90of("value", this.value)});
    }

    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Key)) {
            return false;
        }
        Key that = (Key) other;
        return Objects.equals(this.namespace, that.namespace()) && Objects.equals(this.value, that.value());
    }

    public int hashCode() {
        int result = this.namespace.hashCode();
        return (31 * result) + this.value.hashCode();
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.key.Key
    public int compareTo(@NotNull final Key that) {
        return super.compareTo(that);
    }

    public static int clampCompare(final int value) {
        if (value < 0) {
            return -1;
        }
        if (value <= 0) {
            return value;
        }
        return 1;
    }
}
