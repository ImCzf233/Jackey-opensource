package jdk.nashorn.internal.objects;

import java.util.Objects;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/GenericPropertyDescriptor.class */
public final class GenericPropertyDescriptor extends ScriptObject implements PropertyDescriptor {
    public Object configurable;
    public Object enumerable;
    private static PropertyMap $nasgenmap$;

    static {
        $clinit$();
    }

    /*  JADX ERROR: Method load error
        jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.GenericPropertyDescriptor.$clinit$():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/GenericPropertyDescriptor.class
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
        // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.GenericPropertyDescriptor.$clinit$():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/GenericPropertyDescriptor.class
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.GenericPropertyDescriptor.$clinit$():void");
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

    public GenericPropertyDescriptor(boolean configurable, boolean enumerable, Global global) {
        super(global.getObjectPrototype(), $nasgenmap$);
        this.configurable = Boolean.valueOf(configurable);
        this.enumerable = Boolean.valueOf(enumerable);
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
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public Object getValue() {
        throw new UnsupportedOperationException("value");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public ScriptFunction getGetter() {
        throw new UnsupportedOperationException(PropertyDescriptor.GET);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public ScriptFunction getSetter() {
        throw new UnsupportedOperationException(PropertyDescriptor.SET);
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
        throw new UnsupportedOperationException("set getter");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setSetter(Object setter) {
        throw new UnsupportedOperationException("set setter");
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
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public int type() {
        return 0;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public boolean hasAndEquals(PropertyDescriptor other) {
        if (has(PropertyDescriptor.CONFIGURABLE) && other.has(PropertyDescriptor.CONFIGURABLE) && isConfigurable() != other.isConfigurable()) {
            return false;
        }
        if (has(PropertyDescriptor.ENUMERABLE) && other.has(PropertyDescriptor.ENUMERABLE) && isEnumerable() != other.isEnumerable()) {
            return false;
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GenericPropertyDescriptor)) {
            return false;
        }
        GenericPropertyDescriptor other = (GenericPropertyDescriptor) obj;
        return ScriptRuntime.sameValue(this.configurable, other.configurable) && ScriptRuntime.sameValue(this.enumerable, other.enumerable);
    }

    public String toString() {
        return '[' + getClass().getSimpleName() + " {configurable=" + this.configurable + " enumerable=" + this.enumerable + "}]";
    }

    public int hashCode() {
        int hash = (97 * 7) + Objects.hashCode(this.configurable);
        return (97 * hash) + Objects.hashCode(this.enumerable);
    }
}
