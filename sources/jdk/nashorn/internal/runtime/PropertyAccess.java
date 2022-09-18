package jdk.nashorn.internal.runtime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/PropertyAccess.class */
public interface PropertyAccess {
    int getInt(Object obj, int i);

    int getInt(double d, int i);

    int getInt(int i, int i2);

    double getDouble(Object obj, int i);

    double getDouble(double d, int i);

    double getDouble(int i, int i2);

    Object get(Object obj);

    Object get(double d);

    Object get(int i);

    void set(Object obj, int i, int i2);

    void set(Object obj, double d, int i);

    void set(Object obj, Object obj2, int i);

    void set(double d, int i, int i2);

    void set(double d, double d2, int i);

    void set(double d, Object obj, int i);

    void set(int i, int i2, int i3);

    void set(int i, double d, int i2);

    void set(int i, Object obj, int i2);

    boolean has(Object obj);

    boolean has(int i);

    boolean has(double d);

    boolean hasOwnProperty(Object obj);

    boolean hasOwnProperty(int i);

    boolean hasOwnProperty(double d);

    boolean delete(int i, boolean z);

    boolean delete(double d, boolean z);

    boolean delete(Object obj, boolean z);
}
