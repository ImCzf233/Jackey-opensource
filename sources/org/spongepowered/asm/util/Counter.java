package org.spongepowered.asm.util;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/Counter.class */
public final class Counter {
    public int value;

    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == Counter.class && ((Counter) obj).value == this.value;
    }

    public int hashCode() {
        return this.value;
    }
}
