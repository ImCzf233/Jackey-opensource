package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntObjectImmutablePair.class */
public class IntObjectImmutablePair<V> implements IntObjectPair<V>, Serializable {
    private static final long serialVersionUID = 0;
    protected final int left;
    protected final V right;

    public IntObjectImmutablePair(int left, V right) {
        this.left = left;
        this.right = right;
    }

    /* renamed from: of */
    public static <V> IntObjectImmutablePair<V> m196of(int left, V right) {
        return new IntObjectImmutablePair<>(left, right);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntObjectPair
    public int leftInt() {
        return this.left;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    public V right() {
        return this.right;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        return other instanceof IntObjectPair ? this.left == ((IntObjectPair) other).leftInt() && Objects.equals(this.right, ((IntObjectPair) other).right()) : (other instanceof Pair) && Objects.equals(Integer.valueOf(this.left), ((Pair) other).left()) && Objects.equals(this.right, ((Pair) other).right());
    }

    public int hashCode() {
        return (this.left * 19) + (this.right == null ? 0 : this.right.hashCode());
    }

    public String toString() {
        return "<" + leftInt() + "," + right() + ">";
    }
}
