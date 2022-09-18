package jdk.nashorn.api.scripting;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import jdk.Exported;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import jdk.nashorn.internal.runtime.JSType;

@Exported
/* loaded from: Jackey Client b2.jar:jdk/nashorn/api/scripting/AbstractJSObject.class */
public abstract class AbstractJSObject implements JSObject {
    @Override // jdk.nashorn.api.scripting.JSObject
    public Object call(Object thiz, Object... args) {
        throw new UnsupportedOperationException("call");
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object newObject(Object... args) {
        throw new UnsupportedOperationException("newObject");
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object eval(String s) {
        throw new UnsupportedOperationException("eval");
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object getMember(String name) {
        Objects.requireNonNull(name);
        return null;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object getSlot(int index) {
        return null;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean hasMember(String name) {
        Objects.requireNonNull(name);
        return false;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean hasSlot(int slot) {
        return false;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public void removeMember(String name) {
        Objects.requireNonNull(name);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public void setMember(String name, Object value) {
        Objects.requireNonNull(name);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public void setSlot(int index, Object value) {
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Set<String> keySet() {
        return Collections.emptySet();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Collection<Object> values() {
        return Collections.emptySet();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isInstance(Object instance) {
        return false;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isInstanceOf(Object clazz) {
        if (clazz instanceof JSObject) {
            return ((JSObject) clazz).isInstance(this);
        }
        return false;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public String getClassName() {
        return getClass().getName();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isFunction() {
        return false;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isStrictFunction() {
        return false;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isArray() {
        return false;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    @Deprecated
    public double toNumber() {
        return JSType.toNumber(JSType.toPrimitive((JSObject) this, (Class<?>) Number.class));
    }

    public Object getDefaultValue(Class<?> hint) {
        return DefaultValueImpl.getDefaultValue(this, hint);
    }

    public static Object getDefaultValue(JSObject jsobj, Class<?> hint) {
        if (jsobj instanceof AbstractJSObject) {
            return ((AbstractJSObject) jsobj).getDefaultValue(hint);
        }
        if (jsobj instanceof JSONListAdapter) {
            return ((JSONListAdapter) jsobj).getDefaultValue(hint);
        }
        return DefaultValueImpl.getDefaultValue(jsobj, hint);
    }
}
