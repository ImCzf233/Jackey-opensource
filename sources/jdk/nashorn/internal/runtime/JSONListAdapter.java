package jdk.nashorn.internal.runtime;

import java.util.Collection;
import java.util.Set;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.Global;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/JSONListAdapter.class */
public final class JSONListAdapter extends ListAdapter implements JSObject {
    public JSONListAdapter(JSObject obj, Global global) {
        super(obj, global);
    }

    public Object unwrap(Object homeGlobal) {
        Object unwrapped = ScriptObjectMirror.unwrap(this.obj, homeGlobal);
        return unwrapped != this.obj ? unwrapped : this;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object call(Object thiz, Object... args) {
        return this.obj.call(thiz, args);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object newObject(Object... args) {
        return this.obj.newObject(args);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object eval(String s) {
        return this.obj.eval(s);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object getMember(String name) {
        return this.obj.getMember(name);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object getSlot(int index) {
        return this.obj.getSlot(index);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean hasMember(String name) {
        return this.obj.hasMember(name);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean hasSlot(int slot) {
        return this.obj.hasSlot(slot);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public void removeMember(String name) {
        this.obj.removeMember(name);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public void setMember(String name, Object value) {
        this.obj.setMember(name, value);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public void setSlot(int index, Object value) {
        this.obj.setSlot(index, value);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Set<String> keySet() {
        return this.obj.keySet();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Collection<Object> values() {
        return this.obj.values();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isInstance(Object instance) {
        return this.obj.isInstance(instance);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isInstanceOf(Object clazz) {
        return this.obj.isInstanceOf(clazz);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public String getClassName() {
        return this.obj.getClassName();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isFunction() {
        return this.obj.isFunction();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isStrictFunction() {
        return this.obj.isStrictFunction();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isArray() {
        return this.obj.isArray();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    @Deprecated
    public double toNumber() {
        return this.obj.toNumber();
    }

    public Object getDefaultValue(Class<?> hint) {
        return AbstractJSObject.getDefaultValue(this.obj, hint);
    }
}
