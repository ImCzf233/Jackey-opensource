package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIntMutablePair.class */
public class IntIntMutablePair implements IntIntPair, Serializable {
    private static final long serialVersionUID = 0;
    protected int left;
    protected int right;

    public IntIntMutablePair(int left, int right) {
        this.left = left;
        this.right = right;
    }

    /* renamed from: of */
    public static IntIntMutablePair m209of(int left, int right) {
        return new IntIntMutablePair(left, right);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIntPair
    public int leftInt() {
        return this.left;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIntPair
    public IntIntMutablePair left(int l) {
        this.left = l;
        return this;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIntPair
    public int rightInt() {
        return this.right;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIntPair
    public IntIntMutablePair right(int r) {
        this.right = r;
        return this;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        return other instanceof IntIntPair ? this.left == ((IntIntPair) other).leftInt() && this.right == ((IntIntPair) other).rightInt() : (other instanceof Pair) && Objects.equals(Integer.valueOf(this.left), ((Pair) other).left()) && Objects.equals(Integer.valueOf(this.right), ((Pair) other).right());
    }

    public int hashCode() {
        return (this.left * 19) + this.right;
    }

    public String toString() {
        return "<" + leftInt() + "," + rightInt() + ">";
    }
}
