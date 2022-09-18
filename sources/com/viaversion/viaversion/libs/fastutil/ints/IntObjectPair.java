package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.util.Comparator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntObjectPair.class */
public interface IntObjectPair<V> extends Pair<Integer, V> {
    int leftInt();

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    @Deprecated
    default Integer left() {
        return Integer.valueOf(leftInt());
    }

    default IntObjectPair<V> left(int l) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default IntObjectPair<V> left(Integer l) {
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

    default IntObjectPair<V> first(int l) {
        return left(l);
    }

    @Deprecated
    default IntObjectPair<V> first(Integer l) {
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

    default IntObjectPair<V> key(int l) {
        return left(l);
    }

    @Deprecated
    default IntObjectPair<V> key(Integer l) {
        return key(l.intValue());
    }

    /* renamed from: of */
    static <V> IntObjectPair<V> m194of(int left, V right) {
        return new IntObjectImmutablePair(left, right);
    }

    static <V> Comparator<IntObjectPair<V>> lexComparator() {
        return x, y -> {
            int t = Integer.compare(x.leftInt(), y.leftInt());
            if (t != 0) {
                return t;
            }
            return ((Comparable) x.right()).compareTo(y.right());
        };
    }
}
