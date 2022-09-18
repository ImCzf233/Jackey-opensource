package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIntMutablePair.class */
public class ObjectIntMutablePair<K> implements ObjectIntPair<K>, Serializable {
    private static final long serialVersionUID = 0;
    protected K left;
    protected int right;

    public ObjectIntMutablePair(K left, int right) {
        this.left = left;
        this.right = right;
    }

    /* renamed from: of */
    public static <K> ObjectIntMutablePair<K> m173of(K left, int right) {
        return new ObjectIntMutablePair<>(left, right);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    public K left() {
        return this.left;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    public ObjectIntMutablePair<K> left(K l) {
        this.left = l;
        return this;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIntPair
    public int rightInt() {
        return this.right;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIntPair
    public ObjectIntMutablePair<K> right(int r) {
        this.right = r;
        return this;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        return other instanceof ObjectIntPair ? Objects.equals(this.left, ((ObjectIntPair) other).left()) && this.right == ((ObjectIntPair) other).rightInt() : (other instanceof Pair) && Objects.equals(this.left, ((Pair) other).left()) && Objects.equals(Integer.valueOf(this.right), ((Pair) other).right());
    }

    public int hashCode() {
        return ((this.left == null ? 0 : this.left.hashCode()) * 19) + this.right;
    }

    public String toString() {
        return "<" + left() + "," + rightInt() + ">";
    }
}
