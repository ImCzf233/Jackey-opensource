package org.spongepowered.asm.mixin.injection.invoke.arg;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/invoke/arg/Args.class */
public abstract class Args {
    protected final Object[] values;

    public abstract <T> void set(int i, T t);

    public abstract void setAll(Object... objArr);

    protected Args(Object[] values) {
        this.values = values;
    }

    public int size() {
        return this.values.length;
    }

    public <T> T get(int index) {
        return (T) this.values[index];
    }
}
