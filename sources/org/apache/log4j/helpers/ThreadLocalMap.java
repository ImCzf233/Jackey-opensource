package org.apache.log4j.helpers;

import java.util.Hashtable;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/ThreadLocalMap.class */
public final class ThreadLocalMap extends InheritableThreadLocal {
    @Override // java.lang.InheritableThreadLocal
    public final Object childValue(Object parentValue) {
        Hashtable ht = (Hashtable) parentValue;
        if (ht != null) {
            return ht.clone();
        }
        return null;
    }
}
