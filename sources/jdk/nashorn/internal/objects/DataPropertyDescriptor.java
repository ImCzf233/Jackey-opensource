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
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/DataPropertyDescriptor.class */
public final class DataPropertyDescriptor extends ScriptObject implements PropertyDescriptor {
    public Object configurable;
    public Object enumerable;
    public Object writable;
    public Object value;
    private static PropertyMap $nasgenmap$;

    static {
        $clinit$();
    }

    /*  JADX ERROR: Method load error
        jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.DataPropertyDescriptor.$clinit$():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/DataPropertyDescriptor.class
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
        // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.DataPropertyDescriptor.$clinit$():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/DataPropertyDescriptor.class
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.DataPropertyDescriptor.$clinit$():void");
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

    public Object G$writable() {
        return this.writable;
    }

    public void S$writable(Object obj) {
        this.writable = obj;
    }

    public Object G$value() {
        return this.value;
    }

    public void S$value(Object obj) {
        this.value = obj;
    }

    public DataPropertyDescriptor(boolean configurable, boolean enumerable, boolean writable, Object value, Global global) {
        super(global.getObjectPrototype(), $nasgenmap$);
        this.configurable = Boolean.valueOf(configurable);
        this.enumerable = Boolean.valueOf(enumerable);
        this.writable = Boolean.valueOf(writable);
        this.value = value;
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
        return JSType.toBoolean(this.writable);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public Object getValue() {
        return this.value;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public ScriptFunction getGetter() {
        throw new UnsupportedOperationException("getter");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public ScriptFunction getSetter() {
        throw new UnsupportedOperationException("setter");
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
        this.writable = Boolean.valueOf(flag);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setValue(Object value) {
        this.value = value;
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
        if (sobj.has(PropertyDescriptor.WRITABLE)) {
            this.writable = Boolean.valueOf(JSType.toBoolean(sobj.get(PropertyDescriptor.WRITABLE)));
        } else {
            delete(PropertyDescriptor.WRITABLE, false);
        }
        if (sobj.has("value")) {
            this.value = sobj.get("value");
        } else {
            delete("value", false);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public int type() {
        return 1;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public boolean hasAndEquals(PropertyDescriptor otherDesc) {
        if (!(otherDesc instanceof DataPropertyDescriptor)) {
            return false;
        }
        DataPropertyDescriptor other = (DataPropertyDescriptor) otherDesc;
        return (!has(PropertyDescriptor.CONFIGURABLE) || ScriptRuntime.sameValue(this.configurable, other.configurable)) && (!has(PropertyDescriptor.ENUMERABLE) || ScriptRuntime.sameValue(this.enumerable, other.enumerable)) && ((!has(PropertyDescriptor.WRITABLE) || ScriptRuntime.sameValue(this.writable, other.writable)) && (!has("value") || ScriptRuntime.sameValue(this.value, other.value)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataPropertyDescriptor)) {
            return false;
        }
        DataPropertyDescriptor other = (DataPropertyDescriptor) obj;
        return ScriptRuntime.sameValue(this.configurable, other.configurable) && ScriptRuntime.sameValue(this.enumerable, other.enumerable) && ScriptRuntime.sameValue(this.writable, other.writable) && ScriptRuntime.sameValue(this.value, other.value);
    }

    public String toString() {
        return '[' + getClass().getSimpleName() + " {configurable=" + this.configurable + " enumerable=" + this.enumerable + " writable=" + this.writable + " value=" + this.value + "}]";
    }

    public int hashCode() {
        int hash = (43 * 5) + Objects.hashCode(this.configurable);
        return (43 * ((43 * ((43 * hash) + Objects.hashCode(this.enumerable))) + Objects.hashCode(this.writable))) + Objects.hashCode(this.value);
    }
}
