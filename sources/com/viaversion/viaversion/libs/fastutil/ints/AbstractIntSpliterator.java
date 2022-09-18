package com.viaversion.viaversion.libs.fastutil.ints;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractIntSpliterator.class */
public abstract class AbstractIntSpliterator implements IntSpliterator {
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator
    public final boolean tryAdvance(IntConsumer action) {
        return tryAdvance((java.util.function.IntConsumer) action);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator
    public final void forEachRemaining(IntConsumer action) {
        forEachRemaining((java.util.function.IntConsumer) action);
    }
}
