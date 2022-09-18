package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.util.Comparator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIntPair.class */
public interface IntIntPair extends Pair<Integer, Integer> {
    int leftInt();

    int rightInt();

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    @Deprecated
    default Integer left() {
        return Integer.valueOf(leftInt());
    }

    default IntIntPair left(int l) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default IntIntPair left(Integer l) {
        return left(l.intValue());
    }

    default int firstInt() {
        return leftInt();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    @Deprecated
    default Integer first() {
        return Integer.valueOf(firstInt());
    }

    default IntIntPair first(int l) {
        return left(l);
    }

    @Deprecated
    default IntIntPair first(Integer l) {
        return first(l.intValue());
    }

    default int keyInt() {
        return firstInt();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    @Deprecated
    default Integer key() {
        return Integer.valueOf(keyInt());
    }

    default IntIntPair key(int l) {
        return left(l);
    }

    @Deprecated
    default IntIntPair key(Integer l) {
        return key(l.intValue());
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    @Deprecated
    default Integer right() {
        return Integer.valueOf(rightInt());
    }

    default IntIntPair right(int r) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default IntIntPair right(Integer l) {
        return right(l.intValue());
    }

    default int secondInt() {
        return rightInt();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    @Deprecated
    default Integer second() {
        return Integer.valueOf(secondInt());
    }

    default IntIntPair second(int r) {
        return right(r);
    }

    @Deprecated
    default IntIntPair second(Integer l) {
        return second(l.intValue());
    }

    default int valueInt() {
        return rightInt();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    @Deprecated
    default Integer value() {
        return Integer.valueOf(valueInt());
    }

    default IntIntPair value(int r) {
        return right(r);
    }

    @Deprecated
    default IntIntPair value(Integer l) {
        return value(l.intValue());
    }

    /* renamed from: of */
    static IntIntPair m208of(int left, int right) {
        return new IntIntImmutablePair(left, right);
    }

    static Comparator<IntIntPair> lexComparator() {
        return x, y -> {
            int t = Integer.compare(x.leftInt(), y.leftInt());
            if (t != 0) {
                return t;
            }
            return Integer.compare(x.rightInt(), y.rightInt());
        };
    }
}
