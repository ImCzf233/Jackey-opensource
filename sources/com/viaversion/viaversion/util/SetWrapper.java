package com.viaversion.viaversion.util;

import com.google.common.collect.ForwardingSet;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/SetWrapper.class */
public class SetWrapper<E> extends ForwardingSet<E> {
    private final Set<E> set;
    private final Consumer<E> addListener;

    public SetWrapper(Set<E> set, Consumer<E> addListener) {
        this.set = set;
        this.addListener = addListener;
    }

    public boolean add(E element) {
        this.addListener.accept(element);
        return super.add(element);
    }

    public boolean addAll(Collection<? extends E> collection) {
        for (E element : collection) {
            this.addListener.accept(element);
        }
        return super.addAll(collection);
    }

    public Set<E> delegate() {
        return originalSet();
    }

    public Set<E> originalSet() {
        return this.set;
    }
}
