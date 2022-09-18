package com.viaversion.viaversion.libs.fastutil;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/Stack.class */
public interface Stack<K> {
    void push(K k);

    K pop();

    boolean isEmpty();

    default K top() {
        return peek(0);
    }

    default K peek(int i) {
        throw new UnsupportedOperationException();
    }
}
