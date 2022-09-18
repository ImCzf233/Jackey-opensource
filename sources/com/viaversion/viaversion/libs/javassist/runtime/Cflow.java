package com.viaversion.viaversion.libs.javassist.runtime;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/runtime/Cflow.class */
public class Cflow extends ThreadLocal<Depth> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/runtime/Cflow$Depth.class */
    public static class Depth {
        private int depth = 0;

        Depth() {
        }

        int value() {
            return this.depth;
        }

        void inc() {
            this.depth++;
        }

        void dec() {
            this.depth--;
        }
    }

    @Override // java.lang.ThreadLocal
    public synchronized Depth initialValue() {
        return new Depth();
    }

    public void enter() {
        get().inc();
    }

    public void exit() {
        get().dec();
    }

    public int value() {
        return get().value();
    }
}
