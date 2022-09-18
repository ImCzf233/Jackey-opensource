package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectObjectImmutablePair.class */
public class ObjectObjectImmutablePair<K, V> implements Pair<K, V>, Serializable {
    private static final long serialVersionUID = 0;
    protected final K left;
    protected final V right;

    public ObjectObjectImmutablePair(K left, V right) {
        this.left = left;
        this.right = right;
    }

    /* renamed from: of */
    public static <K, V> ObjectObjectImmutablePair<K, V> m161of(K left, V right) {
        return new ObjectObjectImmutablePair<>(left, right);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    public K left() {
        return this.left;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    public V right() {
        return this.right;
    }

    public boolean equals(Object other) {
        return other != null && (other instanceof Pair) && Objects.equals(this.left, ((Pair) other).left()) && Objects.equals(this.right, ((Pair) other).right());
    }

    public int hashCode() {
        return ((this.left == null ? 0 : this.left.hashCode()) * 19) + (this.right == null ? 0 : this.right.hashCode());
    }

    public String toString() {
        return "<" + left() + "," + right() + ">";
    }
}
