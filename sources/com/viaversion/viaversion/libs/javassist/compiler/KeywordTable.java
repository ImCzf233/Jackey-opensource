package com.viaversion.viaversion.libs.javassist.compiler;

import java.util.HashMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/KeywordTable.class */
public final class KeywordTable extends HashMap<String, Integer> {
    private static final long serialVersionUID = 1;

    public int lookup(String name) {
        if (containsKey(name)) {
            return get(name).intValue();
        }
        return -1;
    }

    public void append(String name, int t) {
        put(name, Integer.valueOf(t));
    }
}
