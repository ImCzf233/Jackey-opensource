package jdk.nashorn.internal.runtime;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SwitchPoint;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Property.class */
public abstract class Property implements Serializable {
    public static final int WRITABLE_ENUMERABLE_CONFIGURABLE = 0;
    public static final int NOT_WRITABLE = 1;
    public static final int NOT_ENUMERABLE = 2;
    public static final int NOT_CONFIGURABLE = 4;
    private static final int MODIFY_MASK = 7;
    public static final int IS_PARAMETER = 8;
    public static final int HAS_ARGUMENTS = 16;
    public static final int IS_FUNCTION_DECLARATION = 32;
    public static final int IS_NASGEN_PRIMITIVE = 64;
    public static final int IS_BUILTIN = 128;
    public static final int IS_BOUND = 256;
    public static final int NEEDS_DECLARATION = 512;
    public static final int IS_LEXICAL_BINDING = 1024;
    public static final int DUAL_FIELDS = 2048;
    private final String key;
    private int flags;
    private final int slot;
    private Class<?> type;
    protected transient SwitchPoint builtinSwitchPoint;
    private static final long serialVersionUID = 2099814273074501176L;
    static final /* synthetic */ boolean $assertionsDisabled;

    public abstract Property copy();

    public abstract Property copy(Class<?> cls);

    public abstract MethodHandle getGetter(Class<?> cls);

    public abstract MethodHandle getOptimisticGetter(Class<?> cls, int i);

    public abstract void initMethodHandles(Class<?> cls);

    public abstract int getIntValue(ScriptObject scriptObject, ScriptObject scriptObject2);

    public abstract double getDoubleValue(ScriptObject scriptObject, ScriptObject scriptObject2);

    public abstract Object getObjectValue(ScriptObject scriptObject, ScriptObject scriptObject2);

    public abstract void setValue(ScriptObject scriptObject, ScriptObject scriptObject2, int i, boolean z);

    public abstract void setValue(ScriptObject scriptObject, ScriptObject scriptObject2, double d, boolean z);

    public abstract void setValue(ScriptObject scriptObject, ScriptObject scriptObject2, Object obj, boolean z);

    public abstract MethodHandle getSetter(Class<?> cls, PropertyMap propertyMap);

    static {
        $assertionsDisabled = !Property.class.desiredAssertionStatus();
    }

    public Property(String key, int flags, int slot) {
        if ($assertionsDisabled || key != null) {
            this.key = key;
            this.flags = flags;
            this.slot = slot;
            return;
        }
        throw new AssertionError();
    }

    public Property(Property property, int flags) {
        this.key = property.key;
        this.slot = property.slot;
        this.builtinSwitchPoint = property.builtinSwitchPoint;
        this.flags = flags;
    }

    public static int mergeFlags(PropertyDescriptor oldDesc, PropertyDescriptor newDesc) {
        int propFlags = 0;
        boolean value = newDesc.has(PropertyDescriptor.CONFIGURABLE) ? newDesc.isConfigurable() : oldDesc.isConfigurable();
        if (!value) {
            propFlags = 0 | 4;
        }
        boolean value2 = newDesc.has(PropertyDescriptor.ENUMERABLE) ? newDesc.isEnumerable() : oldDesc.isEnumerable();
        if (!value2) {
            propFlags |= 2;
        }
        boolean value3 = newDesc.has(PropertyDescriptor.WRITABLE) ? newDesc.isWritable() : oldDesc.isWritable();
        if (!value3) {
            propFlags |= 1;
        }
        return propFlags;
    }

    public final void setBuiltinSwitchPoint(SwitchPoint sp) {
        this.builtinSwitchPoint = sp;
    }

    public final SwitchPoint getBuiltinSwitchPoint() {
        return this.builtinSwitchPoint;
    }

    public boolean isBuiltin() {
        return this.builtinSwitchPoint != null && !this.builtinSwitchPoint.hasBeenInvalidated();
    }

    public static int toFlags(PropertyDescriptor desc) {
        int propFlags = 0;
        if (!desc.isConfigurable()) {
            propFlags = 0 | 4;
        }
        if (!desc.isEnumerable()) {
            propFlags |= 2;
        }
        if (!desc.isWritable()) {
            propFlags |= 1;
        }
        return propFlags;
    }

    public boolean hasGetterFunction(ScriptObject obj) {
        return false;
    }

    public boolean hasSetterFunction(ScriptObject obj) {
        return false;
    }

    public boolean isWritable() {
        return (this.flags & 1) == 0;
    }

    public boolean isConfigurable() {
        return (this.flags & 4) == 0;
    }

    public boolean isEnumerable() {
        return (this.flags & 2) == 0;
    }

    public boolean isParameter() {
        return (this.flags & 8) != 0;
    }

    public boolean hasArguments() {
        return (this.flags & 16) != 0;
    }

    public boolean isSpill() {
        return false;
    }

    public boolean isBound() {
        return (this.flags & 256) != 0;
    }

    public boolean needsDeclaration() {
        return (this.flags & 512) != 0;
    }

    public Property addFlags(int propertyFlags) {
        if ((this.flags & propertyFlags) != propertyFlags) {
            Property cloned = copy();
            cloned.flags |= propertyFlags;
            return cloned;
        }
        return this;
    }

    public int getFlags() {
        return this.flags;
    }

    public Property removeFlags(int propertyFlags) {
        if ((this.flags & propertyFlags) != 0) {
            Property cloned = copy();
            cloned.flags &= propertyFlags ^ (-1);
            return cloned;
        }
        return this;
    }

    public Property setFlags(int propertyFlags) {
        if (this.flags != propertyFlags) {
            Property cloned = copy();
            cloned.flags &= -8;
            cloned.flags |= propertyFlags & 7;
            return cloned;
        }
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public int getSlot() {
        return this.slot;
    }

    public ScriptFunction getGetterFunction(ScriptObject obj) {
        return null;
    }

    public ScriptFunction getSetterFunction(ScriptObject obj) {
        return null;
    }

    public int hashCode() {
        Class<?> t = getLocalType();
        return ((Objects.hashCode(this.key) ^ this.flags) ^ getSlot()) ^ (t == null ? 0 : t.hashCode());
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Property otherProperty = (Property) other;
        return equalsWithoutType(otherProperty) && getLocalType() == otherProperty.getLocalType();
    }

    public boolean equalsWithoutType(Property otherProperty) {
        return getFlags() == otherProperty.getFlags() && getSlot() == otherProperty.getSlot() && getKey().equals(otherProperty.getKey());
    }

    private static String type(Class<?> type) {
        if (type == null) {
            return "undef";
        }
        if (type == Integer.TYPE) {
            return "i";
        }
        if (type == Double.TYPE) {
            return "d";
        }
        return "o";
    }

    public final String toStringShort() {
        StringBuilder sb = new StringBuilder();
        Class<?> t = getLocalType();
        sb.append(getKey()).append(" (").append(type(t)).append(')');
        return sb.toString();
    }

    private static String indent(String str, int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        for (int i = 0; i < indent - str.length(); i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Class<?> t = getLocalType();
        sb.append(indent(getKey(), 20)).append(" id=").append(Debug.m67id(this)).append(" (0x").append(indent(Integer.toHexString(this.flags), 4)).append(") ").append(getClass().getSimpleName()).append(" {").append(indent(type(t), 5)).append('}');
        if (this.slot != -1) {
            sb.append(" [").append("slot=").append(this.slot).append(']');
        }
        return sb.toString();
    }

    public final Class<?> getType() {
        return this.type;
    }

    public final void setType(Class<?> type) {
        if ($assertionsDisabled || type != Boolean.TYPE) {
            this.type = type == null ? null : type.isPrimitive() ? type : Object.class;
            return;
        }
        throw new AssertionError("no boolean storage support yet - fix this");
    }

    public Class<?> getLocalType() {
        return getType();
    }

    public boolean canChangeType() {
        return false;
    }

    public boolean isFunctionDeclaration() {
        return (this.flags & 32) != 0;
    }

    public boolean isLexicalBinding() {
        return (this.flags & 1024) != 0;
    }

    public boolean hasDualFields() {
        return (this.flags & 2048) != 0;
    }
}
