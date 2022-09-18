package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/ForwardingIterator.class */
public final class ForwardingIterator<T> implements Iterable<T> {
    private final Supplier<Iterator<T>> iterator;
    private final Supplier<Spliterator<T>> spliterator;

    public ForwardingIterator(@NotNull final Supplier<Iterator<T>> iterator, @NotNull final Supplier<Spliterator<T>> spliterator) {
        this.iterator = (Supplier) Objects.requireNonNull(iterator, "iterator");
        this.spliterator = (Supplier) Objects.requireNonNull(spliterator, "spliterator");
    }

    @Override // java.lang.Iterable
    @NotNull
    public Iterator<T> iterator() {
        return this.iterator.get();
    }

    @Override // java.lang.Iterable
    @NotNull
    public Spliterator<T> spliterator() {
        return this.spliterator.get();
    }
}
