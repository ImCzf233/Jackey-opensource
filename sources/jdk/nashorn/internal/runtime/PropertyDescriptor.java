package jdk.nashorn.internal.runtime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/PropertyDescriptor.class */
public interface PropertyDescriptor {
    public static final int GENERIC = 0;
    public static final int DATA = 1;
    public static final int ACCESSOR = 2;
    public static final String CONFIGURABLE = "configurable";
    public static final String ENUMERABLE = "enumerable";
    public static final String WRITABLE = "writable";
    public static final String VALUE = "value";
    public static final String GET = "get";
    public static final String SET = "set";

    boolean isConfigurable();

    boolean isEnumerable();

    boolean isWritable();

    Object getValue();

    ScriptFunction getGetter();

    ScriptFunction getSetter();

    void setConfigurable(boolean z);

    void setEnumerable(boolean z);

    void setWritable(boolean z);

    void setValue(Object obj);

    void setGetter(Object obj);

    void setSetter(Object obj);

    PropertyDescriptor fillFrom(ScriptObject scriptObject);

    int type();

    boolean has(Object obj);

    boolean hasAndEquals(PropertyDescriptor propertyDescriptor);
}
