package com.viaversion.viaversion.util;

import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/Pair.class */
public class Pair<X, Y> {
    private final X key;
    private Y value;

    public Pair(X key, Y value) {
        this.key = key;
        this.value = value;
    }

    public X key() {
        return this.key;
    }

    public Y value() {
        return this.value;
    }

    @Deprecated
    public X getKey() {
        return this.key;
    }

    @Deprecated
    public Y getValue() {
        return this.value;
    }

    @Deprecated
    public void setValue(Y value) {
        this.value = value;
    }

    public String toString() {
        return "Pair{" + this.key + ", " + this.value + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair) o;
        if (Objects.equals(this.key, pair.key)) {
            return Objects.equals(this.value, pair.value);
        }
        return false;
    }

    public int hashCode() {
        int result = this.key != null ? this.key.hashCode() : 0;
        return (31 * result) + (this.value != null ? this.value.hashCode() : 0);
    }
}
