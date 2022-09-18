package org.apache.log4j;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* compiled from: PropertyConfigurator.java */
/* loaded from: Jackey Client b2.jar:org/apache/log4j/SortedKeyEnumeration.class */
class SortedKeyEnumeration implements Enumeration {

    /* renamed from: e */
    private Enumeration f386e;

    public SortedKeyEnumeration(Hashtable ht) {
        Enumeration f = ht.keys();
        Vector keys = new Vector(ht.size());
        int last = 0;
        while (f.hasMoreElements()) {
            String key = (String) f.nextElement();
            int i = 0;
            while (i < last) {
                String s = (String) keys.get(i);
                if (key.compareTo(s) <= 0) {
                    break;
                }
                i++;
            }
            keys.add(i, key);
            last++;
        }
        this.f386e = keys.elements();
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return this.f386e.hasMoreElements();
    }

    @Override // java.util.Enumeration
    public Object nextElement() {
        return this.f386e.nextElement();
    }
}
