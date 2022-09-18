package jdk.nashorn.internal.runtime.linker;

import java.util.Objects;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/JavaSuperAdapter.class */
class JavaSuperAdapter {
    private final Object adapter;

    public JavaSuperAdapter(Object adapter) {
        this.adapter = Objects.requireNonNull(adapter);
    }

    public Object getAdapter() {
        return this.adapter;
    }
}
