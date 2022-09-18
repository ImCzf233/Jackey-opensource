package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.util.Comparator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIntPair.class */
public interface ObjectIntPair<K> extends Pair<K, Integer> {
    int rightInt();

    @Override // com.viaversion.viaversion.libs.fastutil.Pair
    @Deprecated
    default Integer right() {
        return Integer.valueOf(rightInt());
    }

    default ObjectIntPair<K> right(int r) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default ObjectIntPair<K> right(Integer l) {
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

    default ObjectIntPair<K> second(int r) {
        return right(r);
    }

    @Deprecated
    default ObjectIntPair<K> second(Integer l) {
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

    default ObjectIntPair<K> value(int r) {
        return right(r);
    }

    @Deprecated
    default ObjectIntPair<K> value(Integer l) {
        return value(l.intValue());
    }

    /* renamed from: of */
    static <K> ObjectIntPair<K> m172of(K left, int right) {
        return new ObjectIntImmutablePair(left, right);
    }

    static <K> Comparator<ObjectIntPair<K>> lexComparator() {
        return x, y -> {
            int t = ((Comparable) x.left()).compareTo(y.left());
            if (t != 0) {
                return t;
            }
            return Integer.compare(x.rightInt(), y.rightInt());
        };
    }
}
