package jdk.nashorn.internal.objects;

import java.util.Objects;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/AccessorPropertyDescriptor.class */
public final class AccessorPropertyDescriptor extends ScriptObject implements PropertyDescriptor {
    public Object configurable;
    public Object enumerable;
    public Object get;
    public Object set;
    private static PropertyMap $nasgenmap$;

    static {
        $clinit$();
    }

    /*  JADX ERROR: Method load error
        jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.AccessorPropertyDescriptor.$clinit$():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/AccessorPropertyDescriptor.class
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:154)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
        	at jadx.core.ProcessClass.process(ProcessClass.java:67)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
        Caused by: java.lang.ArrayIndexOutOfBoundsException
        */
    public static void $clinit$() {
        /*
        // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.AccessorPropertyDescriptor.$clinit$():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/AccessorPropertyDescriptor.class
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.AccessorPropertyDescriptor.$clinit$():void");
    }

    public Object G$configurable() {
        return this.configurable;
    }

    public void S$configurable(Object obj) {
        this.configurable = obj;
    }

    public Object G$enumerable() {
        return this.enumerable;
    }

    public void S$enumerable(Object obj) {
        this.enumerable = obj;
    }

    public Object G$get() {
        return this.get;
    }

    public void S$get(Object obj) {
        this.get = obj;
    }

    public Object G$set() {
        return this.set;
    }

    public void S$set(Object obj) {
        this.set = obj;
    }

    public AccessorPropertyDescriptor(boolean configurable, boolean enumerable, Object get, Object set, Global global) {
        super(global.getObjectPrototype(), $nasgenmap$);
        this.configurable = Boolean.valueOf(configurable);
        this.enumerable = Boolean.valueOf(enumerable);
        this.get = get;
        this.set = set;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public boolean isConfigurable() {
        return JSType.toBoolean(this.configurable);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public boolean isEnumerable() {
        return JSType.toBoolean(this.enumerable);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public boolean isWritable() {
        return true;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public Object getValue() {
        throw new UnsupportedOperationException("value");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public ScriptFunction getGetter() {
        if (this.get instanceof ScriptFunction) {
            return (ScriptFunction) this.get;
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public ScriptFunction getSetter() {
        if (this.set instanceof ScriptFunction) {
            return (ScriptFunction) this.set;
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setConfigurable(boolean flag) {
        this.configurable = Boolean.valueOf(flag);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setEnumerable(boolean flag) {
        this.enumerable = Boolean.valueOf(flag);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setWritable(boolean flag) {
        throw new UnsupportedOperationException("set writable");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setValue(Object value) {
        throw new UnsupportedOperationException("set value");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setGetter(Object getter) {
        this.get = getter;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setSetter(Object setter) {
        this.set = setter;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public PropertyDescriptor fillFrom(ScriptObject sobj) {
        if (sobj.has(PropertyDescriptor.CONFIGURABLE)) {
            this.configurable = Boolean.valueOf(JSType.toBoolean(sobj.get(PropertyDescriptor.CONFIGURABLE)));
        } else {
            delete(PropertyDescriptor.CONFIGURABLE, false);
        }
        if (sobj.has(PropertyDescriptor.ENUMERABLE)) {
            this.enumerable = Boolean.valueOf(JSType.toBoolean(sobj.get(PropertyDescriptor.ENUMERABLE)));
        } else {
            delete(PropertyDescriptor.ENUMERABLE, false);
        }
        if (sobj.has(PropertyDescriptor.GET)) {
            Object getter = sobj.get(PropertyDescriptor.GET);
            if (getter == ScriptRuntime.UNDEFINED || (getter instanceof ScriptFunction)) {
                this.get = getter;
            } else {
                throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(getter));
            }
        } else {
            delete(PropertyDescriptor.GET, false);
        }
        if (sobj.has(PropertyDescriptor.SET)) {
            Object setter = sobj.get(PropertyDescriptor.SET);
            if (setter == ScriptRuntime.UNDEFINED || (setter instanceof ScriptFunction)) {
                this.set = setter;
            } else {
                throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(setter));
            }
        } else {
            delete(PropertyDescriptor.SET, false);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public int type() {
        return 2;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public boolean hasAndEquals(PropertyDescriptor otherDesc) {
        if (!(otherDesc instanceof AccessorPropertyDescriptor)) {
            return false;
        }
        AccessorPropertyDescriptor other = (AccessorPropertyDescriptor) otherDesc;
        return (!has(PropertyDescriptor.CONFIGURABLE) || ScriptRuntime.sameValue(this.configurable, other.configurable)) && (!has(PropertyDescriptor.ENUMERABLE) || ScriptRuntime.sameValue(this.enumerable, other.enumerable)) && ((!has(PropertyDescriptor.GET) || ScriptRuntime.sameValue(this.get, other.get)) && (!has(PropertyDescriptor.SET) || ScriptRuntime.sameValue(this.set, other.set)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AccessorPropertyDescriptor)) {
            return false;
        }
        AccessorPropertyDescriptor other = (AccessorPropertyDescriptor) obj;
        return ScriptRuntime.sameValue(this.configurable, other.configurable) && ScriptRuntime.sameValue(this.enumerable, other.enumerable) && ScriptRuntime.sameValue(this.get, other.get) && ScriptRuntime.sameValue(this.set, other.set);
    }

    public String toString() {
        return '[' + getClass().getSimpleName() + " {configurable=" + this.configurable + " enumerable=" + this.enumerable + " getter=" + this.get + " setter=" + this.set + "}]";
    }

    public int hashCode() {
        int hash = (41 * 7) + Objects.hashCode(this.configurable);
        return (41 * ((41 * ((41 * hash) + Objects.hashCode(this.enumerable))) + Objects.hashCode(this.get))) + Objects.hashCode(this.set);
    }
}
