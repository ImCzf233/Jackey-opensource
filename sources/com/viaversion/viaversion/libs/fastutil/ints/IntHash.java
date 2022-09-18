package com.viaversion.viaversion.libs.fastutil.ints;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntHash.class */
public interface IntHash {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntHash$Strategy.class */
    public interface Strategy {
        int hashCode(int i);

        boolean equals(int i, int i2);
    }
}
