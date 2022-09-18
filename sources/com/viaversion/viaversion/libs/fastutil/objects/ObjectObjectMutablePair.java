package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectObjectMutablePair.class */
public class ObjectObjectMutablePair<K, V> implements Pair<K, V>, Serializable {
    private static final long serialVersionUID = 0;
    protected K left;
    protected V right;

    public ObjectObjectMutablePair(K left, V right) {
        this.left = left;
        this.right = right;
    }

    /* renamed from: of */
    public static <K, V> ObjectObjectMutablePair<K, V> m159of(K left, V right) {
        return new ObjectObjectMutablePair<>(left, right);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    public K left() {
        return this.left;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    public ObjectObjectMutablePair<K, V> left(K l) {
        this.left = l;
        return this;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    public V right() {
        return this.right;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    public ObjectObjectMutablePair<K, V> right(V r) {
        this.right = r;
        return this;
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
