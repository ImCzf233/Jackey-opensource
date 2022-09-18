package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectObjectImmutablePair;
import java.util.Comparator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/Pair.class */
public interface Pair<L, R> {
    L left();

    R right();

    default Pair<L, R> left(L l) {
        throw new UnsupportedOperationException();
    }

    default Pair<L, R> right(R r) {
        throw new UnsupportedOperationException();
    }

    default L first() {
        return left();
    }

    default R second() {
        return right();
    }

    default Pair<L, R> first(L l) {
        return left(l);
    }

    default Pair<L, R> second(R r) {
        return right(r);
    }

    default Pair<L, R> key(L l) {
        return left(l);
    }

    default Pair<L, R> value(R r) {
        return right(r);
    }

    default L key() {
        return left();
    }

    default R value() {
        return right();
    }

    /* renamed from: of */
    static <L, R> Pair<L, R> m220of(L l, R r) {
        return new ObjectObjectImmutablePair(l, r);
    }

    static <L, R> Comparator<Pair<L, R>> lexComparator() {
        return x, y -> {
            int t = ((Comparable) x.left()).compareTo(y.left());
            return t != 0 ? t : ((Comparable) x.right()).compareTo(y.right());
        };
    }
}
