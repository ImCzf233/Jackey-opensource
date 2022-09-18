package com.viaversion.viaversion.libs.kyori.adventure.text;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/ComponentIterator.class */
public final class ComponentIterator implements Iterator<Component> {
    private Component component;
    private final ComponentIteratorType type;
    private final Set<ComponentIteratorFlag> flags;
    private final Deque<Component> deque = new ArrayDeque();

    public ComponentIterator(@NotNull final Component component, @NotNull final ComponentIteratorType type, @NotNull final Set<ComponentIteratorFlag> flags) {
        this.component = component;
        this.type = type;
        this.flags = flags;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.component != null || !this.deque.isEmpty();
    }

    @Override // java.util.Iterator
    public Component next() {
        if (this.component != null) {
            Component next = this.component;
            this.component = null;
            this.type.populate(next, this.deque, this.flags);
            return next;
        } else if (this.deque.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            this.component = this.deque.poll();
            return next();
        }
    }
}
