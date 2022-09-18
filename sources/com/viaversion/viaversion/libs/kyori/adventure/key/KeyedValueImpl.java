package com.viaversion.viaversion.libs.kyori.adventure.key;

import com.github.benmanes.caffeine.cache.LocalCacheFactory;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/key/KeyedValueImpl.class */
final class KeyedValueImpl<T> implements Examinable, KeyedValue<T> {
    private final Key key;
    private final T value;

    public KeyedValueImpl(final Key key, final T value) {
        this.key = key;
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.key.Keyed
    @NotNull
    public Key key() {
        return this.key;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.key.KeyedValue
    @NotNull
    public T value() {
        return this.value;
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        KeyedValueImpl<?> that = (KeyedValueImpl) other;
        return this.key.equals(that.key) && this.value.equals(that.value);
    }

    public int hashCode() {
        int result = this.key.hashCode();
        return (31 * result) + this.value.hashCode();
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of(LocalCacheFactory.KEY, this.key), ExaminableProperty.m91of("value", this.value)});
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }
}
