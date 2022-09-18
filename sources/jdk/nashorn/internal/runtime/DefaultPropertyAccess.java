package jdk.nashorn.internal.runtime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/DefaultPropertyAccess.class */
public abstract class DefaultPropertyAccess implements PropertyAccess {
    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public abstract Object get(Object obj);

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public abstract void set(Object obj, Object obj2, int i);

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public abstract boolean has(Object obj);

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public abstract boolean hasOwnProperty(Object obj);

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(Object key, int programPoint) {
        return JSType.toInt32(get(key));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(double key, int programPoint) {
        return getInt(JSType.toObject(key), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(int key, int programPoint) {
        return getInt(JSType.toObject(key), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(Object key, int programPoint) {
        return JSType.toNumber(get(key));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(double key, int programPoint) {
        return getDouble(JSType.toObject(key), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(int key, int programPoint) {
        return getDouble(JSType.toObject(key), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(double key) {
        return get(JSType.toObject(key));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(int key) {
        return get(JSType.toObject(key));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double key, int value, int flags) {
        set(JSType.toObject(key), JSType.toObject(value), flags);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double key, double value, int flags) {
        set(JSType.toObject(key), JSType.toObject(value), flags);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double key, Object value, int flags) {
        set(JSType.toObject(key), JSType.toObject(value), flags);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int key, int value, int flags) {
        set(JSType.toObject(key), JSType.toObject(value), flags);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int key, double value, int flags) {
        set(JSType.toObject(key), JSType.toObject(value), flags);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int key, Object value, int flags) {
        set(JSType.toObject(key), value, flags);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object key, int value, int flags) {
        set(key, JSType.toObject(value), flags);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object key, double value, int flags) {
        set(key, JSType.toObject(value), flags);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(int key) {
        return has(JSType.toObject(key));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(double key) {
        return has(JSType.toObject(key));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean hasOwnProperty(int key) {
        return hasOwnProperty(JSType.toObject(key));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean hasOwnProperty(double key) {
        return hasOwnProperty(JSType.toObject(key));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(int key, boolean strict) {
        return delete(JSType.toObject(key), strict);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(double key, boolean strict) {
        return delete(JSType.toObject(key), strict);
    }
}
