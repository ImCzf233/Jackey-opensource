package org.apache.log4j.jmx;

import java.lang.reflect.Method;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/jmx/MethodUnion.class */
class MethodUnion {
    Method readMethod;
    Method writeMethod;

    public MethodUnion(Method readMethod, Method writeMethod) {
        this.readMethod = readMethod;
        this.writeMethod = writeMethod;
    }
}
