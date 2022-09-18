package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntPredicate.class */
public interface IntPredicate extends Predicate<Integer>, java.util.function.IntPredicate {
    @Deprecated
    default boolean test(Integer t) {
        return test(t.intValue());
    }

    @Override // java.util.function.IntPredicate
    default IntPredicate and(java.util.function.IntPredicate other) {
        Objects.requireNonNull(other);
        return t -> {
            return test(other) && other.test(other);
        };
    }

    default IntPredicate and(IntPredicate other) {
        return and((java.util.function.IntPredicate) other);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Integer> and(Predicate<? super Integer> other) {
        return super.and(other);
    }

    @Override // java.util.function.Predicate, java.util.function.IntPredicate
    default IntPredicate negate() {
        return t -> {
            return !test(t);
        };
    }

    @Override // java.util.function.IntPredicate
    default IntPredicate or(java.util.function.IntPredicate other) {
        Objects.requireNonNull(other);
        return t -> {
            return test(other) || other.test(other);
        };
    }

    /* renamed from: or */
    default IntPredicate m188or(IntPredicate other) {
        return or((java.util.function.IntPredicate) other);
    }

    @Override // java.util.function.Predicate
    @Deprecated
    default Predicate<Integer> or(Predicate<? super Integer> other) {
        return super.or(other);
    }
}
