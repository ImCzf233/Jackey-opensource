package com.viaversion.viaversion.libs.fastutil.ints;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractIntIterator.class */
public abstract class AbstractIntIterator implements IntIterator {
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator
    public final void forEachRemaining(IntConsumer action) {
        forEachRemaining((java.util.function.IntConsumer) action);
    }
}
