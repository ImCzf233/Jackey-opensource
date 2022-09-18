package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntConsumer.class */
public interface IntConsumer extends Consumer<Integer>, java.util.function.IntConsumer {
    @Deprecated
    default void accept(Integer t) {
        accept(t.intValue());
    }

    @Override // java.util.function.IntConsumer
    default IntConsumer andThen(java.util.function.IntConsumer after) {
        Objects.requireNonNull(after);
        return t -> {
            accept(after);
            after.accept(after);
        };
    }

    default IntConsumer andThen(IntConsumer after) {
        return andThen((java.util.function.IntConsumer) after);
    }

    @Override // java.util.function.Consumer
    @Deprecated
    default Consumer<Integer> andThen(Consumer<? super Integer> after) {
        return super.andThen(after);
    }
}
