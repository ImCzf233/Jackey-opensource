package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Stack;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntStack.class */
public interface IntStack extends Stack<Integer> {
    void push(int i);

    int popInt();

    int topInt();

    int peekInt(int i);

    @Deprecated
    default void push(Integer o) {
        push(o.intValue());
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Stack
    @Deprecated
    default Integer pop() {
        return Integer.valueOf(popInt());
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Stack
    @Deprecated
    default Integer top() {
        return Integer.valueOf(topInt());
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Stack
    @Deprecated
    default Integer peek(int i) {
        return Integer.valueOf(peekInt(i));
    }
}
