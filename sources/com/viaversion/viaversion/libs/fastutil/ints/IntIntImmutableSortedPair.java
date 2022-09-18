package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SortedPair;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIntImmutableSortedPair.class */
public class IntIntImmutableSortedPair extends IntIntImmutablePair implements IntIntSortedPair, Serializable {
    private static final long serialVersionUID = 0;

    private IntIntImmutableSortedPair(int left, int right) {
        super(left, right);
    }

    /* renamed from: of */
    public static IntIntImmutableSortedPair m210of(int left, int right) {
        if (left <= right) {
            return new IntIntImmutableSortedPair(left, right);
        }
        return new IntIntImmutableSortedPair(right, left);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIntImmutablePair
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        return other instanceof IntIntSortedPair ? this.left == ((IntIntSortedPair) other).leftInt() && this.right == ((IntIntSortedPair) other).rightInt() : (other instanceof SortedPair) && Objects.equals(Integer.valueOf(this.left), ((SortedPair) other).left()) && Objects.equals(Integer.valueOf(this.right), ((SortedPair) other).right());
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIntImmutablePair
    public String toString() {
        return "{" + leftInt() + "," + rightInt() + "}";
    }
}
