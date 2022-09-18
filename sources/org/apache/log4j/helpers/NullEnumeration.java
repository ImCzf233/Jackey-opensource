package org.apache.log4j.helpers;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/NullEnumeration.class */
public class NullEnumeration implements Enumeration {
    private static final NullEnumeration instance = new NullEnumeration();

    private NullEnumeration() {
    }

    public static NullEnumeration getInstance() {
        return instance;
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return false;
    }

    @Override // java.util.Enumeration
    public Object nextElement() {
        throw new NoSuchElementException();
    }
}
