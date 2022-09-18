package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/tag/builtin/Tag.class */
public abstract class Tag implements Cloneable {
    public abstract Object getValue();

    public abstract void read(DataInput dataInput) throws IOException;

    public abstract void write(DataOutput dataOutput) throws IOException;

    public abstract int getTagId();

    public abstract Tag clone();

    public String toString() {
        String value = "";
        if (getValue() != null) {
            value = getValue().toString();
            if (getValue().getClass().isArray()) {
                StringBuilder build = new StringBuilder();
                build.append("[");
                for (int index = 0; index < Array.getLength(getValue()); index++) {
                    if (index > 0) {
                        build.append(", ");
                    }
                    build.append(Array.get(getValue(), index));
                }
                build.append("]");
                value = build.toString();
            }
        }
        return getClass().getSimpleName() + " { " + value + " }";
    }
}
