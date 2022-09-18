package com.viaversion.viaversion.util;

import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/Triple.class */
public class Triple<A, B, C> {
    private final A first;
    private final B second;
    private final C third;

    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A first() {
        return this.first;
    }

    public B second() {
        return this.second;
    }

    public C third() {
        return this.third;
    }

    @Deprecated
    public A getFirst() {
        return this.first;
    }

    @Deprecated
    public B getSecond() {
        return this.second;
    }

    @Deprecated
    public C getThird() {
        return this.third;
    }

    public String toString() {
        return "Triple{" + this.first + ", " + this.second + ", " + this.third + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Triple<?, ?, ?> triple = (Triple) o;
        if (!Objects.equals(this.first, triple.first) || !Objects.equals(this.second, triple.second)) {
            return false;
        }
        return Objects.equals(this.third, triple.third);
    }

    public int hashCode() {
        int result = this.first != null ? this.first.hashCode() : 0;
        return (31 * ((31 * result) + (this.second != null ? this.second.hashCode() : 0))) + (this.third != null ? this.third.hashCode() : 0);
    }
}
