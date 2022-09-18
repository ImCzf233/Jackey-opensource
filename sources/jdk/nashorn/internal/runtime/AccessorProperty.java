package jdk.nashorn.internal.runtime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.SwitchPoint;
import java.util.function.Supplier;
import java.util.logging.Level;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.objects.Global;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/AccessorProperty.class */
public class AccessorProperty extends Property {
    private static final MethodHandles.Lookup LOOKUP;
    private static final MethodHandle REPLACE_MAP;
    private static final MethodHandle INVALIDATE_SP;
    private static final int NOOF_TYPES;
    private static final long serialVersionUID = 3371720170182154920L;
    private static ClassValue<Accessors> GETTERS_SETTERS;
    private transient MethodHandle[] GETTER_CACHE;
    transient MethodHandle primitiveGetter;
    transient MethodHandle primitiveSetter;
    transient MethodHandle objectGetter;
    transient MethodHandle objectSetter;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !AccessorProperty.class.desiredAssertionStatus();
        LOOKUP = MethodHandles.lookup();
        REPLACE_MAP = findOwnMH_S("replaceMap", Object.class, Object.class, PropertyMap.class);
        INVALIDATE_SP = findOwnMH_S("invalidateSwitchPoint", Object.class, AccessorProperty.class, Object.class);
        NOOF_TYPES = JSType.getNumberOfAccessorTypes();
        GETTERS_SETTERS = new ClassValue<Accessors>() { // from class: jdk.nashorn.internal.runtime.AccessorProperty.1
            protected Accessors computeValue(Class<?> structure) {
                return new Accessors(structure);
            }
        };
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/AccessorProperty$Accessors.class */
    public static class Accessors {
        final MethodHandle[] objectGetters;
        final MethodHandle[] objectSetters;
        final MethodHandle[] primitiveGetters;
        final MethodHandle[] primitiveSetters;

        Accessors(Class<?> structure) {
            int fieldCount = ObjectClassGenerator.getFieldCount(structure);
            this.objectGetters = new MethodHandle[fieldCount];
            this.objectSetters = new MethodHandle[fieldCount];
            this.primitiveGetters = new MethodHandle[fieldCount];
            this.primitiveSetters = new MethodHandle[fieldCount];
            for (int i = 0; i < fieldCount; i++) {
                String fieldName = ObjectClassGenerator.getFieldName(i, Type.OBJECT);
                Class<?> typeClass = Type.OBJECT.getTypeClass();
                this.objectGetters[i] = Lookup.f248MH.asType(Lookup.f248MH.getter(AccessorProperty.LOOKUP, structure, fieldName, typeClass), Lookup.GET_OBJECT_TYPE);
                this.objectSetters[i] = Lookup.f248MH.asType(Lookup.f248MH.setter(AccessorProperty.LOOKUP, structure, fieldName, typeClass), Lookup.SET_OBJECT_TYPE);
            }
            if (!StructureLoader.isSingleFieldStructure(structure.getName())) {
                for (int i2 = 0; i2 < fieldCount; i2++) {
                    String fieldNamePrimitive = ObjectClassGenerator.getFieldName(i2, ObjectClassGenerator.PRIMITIVE_FIELD_TYPE);
                    Class<?> typeClass2 = ObjectClassGenerator.PRIMITIVE_FIELD_TYPE.getTypeClass();
                    this.primitiveGetters[i2] = Lookup.f248MH.asType(Lookup.f248MH.getter(AccessorProperty.LOOKUP, structure, fieldNamePrimitive, typeClass2), Lookup.GET_PRIMITIVE_TYPE);
                    this.primitiveSetters[i2] = Lookup.f248MH.asType(Lookup.f248MH.setter(AccessorProperty.LOOKUP, structure, fieldNamePrimitive, typeClass2), Lookup.SET_PRIMITIVE_TYPE);
                }
            }
        }
    }

    public static AccessorProperty create(String key, int propertyFlags, MethodHandle getter, MethodHandle setter) {
        return new AccessorProperty(key, propertyFlags, -1, getter, setter);
    }

    public AccessorProperty(AccessorProperty property, Object delegate) {
        super(property, property.getFlags() | 256);
        this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
        this.primitiveGetter = bindTo(property.primitiveGetter, delegate);
        this.primitiveSetter = bindTo(property.primitiveSetter, delegate);
        this.objectGetter = bindTo(property.objectGetter, delegate);
        this.objectSetter = bindTo(property.objectSetter, delegate);
        property.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
        setType(property.getType());
    }

    public AccessorProperty(String key, int flags, int slot, MethodHandle primitiveGetter, MethodHandle primitiveSetter, MethodHandle objectGetter, MethodHandle objectSetter) {
        super(key, flags, slot);
        this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
        if ($assertionsDisabled || getClass() != AccessorProperty.class) {
            this.primitiveGetter = primitiveGetter;
            this.primitiveSetter = primitiveSetter;
            this.objectGetter = objectGetter;
            this.objectSetter = objectSetter;
            initializeType();
            return;
        }
        throw new AssertionError();
    }

    private AccessorProperty(String key, int flags, int slot, MethodHandle getter, MethodHandle setter) {
        super(key, flags | 128 | 2048 | (getter.type().returnType().isPrimitive() ? 64 : 0), slot);
        this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
        if ($assertionsDisabled || !isSpill()) {
            Class<?> getterType = getter.type().returnType();
            Class<?> setterType = setter == null ? null : setter.type().parameterType(1);
            if (!$assertionsDisabled && setterType != null && setterType != getterType) {
                throw new AssertionError();
            }
            if (getterType == Integer.TYPE) {
                this.primitiveGetter = Lookup.f248MH.asType(getter, Lookup.GET_PRIMITIVE_TYPE);
                this.primitiveSetter = setter == null ? null : Lookup.f248MH.asType(setter, Lookup.SET_PRIMITIVE_TYPE);
            } else if (getterType == Double.TYPE) {
                this.primitiveGetter = Lookup.f248MH.asType(Lookup.f248MH.filterReturnValue(getter, ObjectClassGenerator.PACK_DOUBLE), Lookup.GET_PRIMITIVE_TYPE);
                this.primitiveSetter = setter == null ? null : Lookup.f248MH.asType(Lookup.f248MH.filterArguments(setter, 1, ObjectClassGenerator.UNPACK_DOUBLE), Lookup.SET_PRIMITIVE_TYPE);
            } else {
                this.primitiveSetter = null;
                this.primitiveGetter = null;
            }
            if (!$assertionsDisabled && this.primitiveGetter != null && this.primitiveGetter.type() != Lookup.GET_PRIMITIVE_TYPE) {
                throw new AssertionError(this.primitiveGetter + "!=" + Lookup.GET_PRIMITIVE_TYPE);
            }
            if (!$assertionsDisabled && this.primitiveSetter != null && this.primitiveSetter.type() != Lookup.SET_PRIMITIVE_TYPE) {
                throw new AssertionError(this.primitiveSetter);
            }
            this.objectGetter = getter.type() != Lookup.GET_OBJECT_TYPE ? Lookup.f248MH.asType(getter, Lookup.GET_OBJECT_TYPE) : getter;
            this.objectSetter = (setter == null || setter.type() == Lookup.SET_OBJECT_TYPE) ? setter : Lookup.f248MH.asType(setter, Lookup.SET_OBJECT_TYPE);
            setType(getterType);
            return;
        }
        throw new AssertionError();
    }

    public AccessorProperty(String key, int flags, Class<?> structure, int slot) {
        super(key, flags, slot);
        this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
        initGetterSetter(structure);
        initializeType();
    }

    private void initGetterSetter(Class<?> structure) {
        int slot = getSlot();
        if (isParameter() && hasArguments()) {
            MethodHandle arguments = Lookup.f248MH.getter(LOOKUP, structure, "arguments", ScriptObject.class);
            this.objectGetter = Lookup.f248MH.asType(Lookup.f248MH.insertArguments(Lookup.f248MH.filterArguments(ScriptObject.GET_ARGUMENT.methodHandle(), 0, arguments), 1, Integer.valueOf(slot)), Lookup.GET_OBJECT_TYPE);
            this.objectSetter = Lookup.f248MH.asType(Lookup.f248MH.insertArguments(Lookup.f248MH.filterArguments(ScriptObject.SET_ARGUMENT.methodHandle(), 0, arguments), 1, Integer.valueOf(slot)), Lookup.SET_OBJECT_TYPE);
            this.primitiveGetter = null;
            this.primitiveSetter = null;
        } else {
            Accessors gs = (Accessors) GETTERS_SETTERS.get(structure);
            this.objectGetter = gs.objectGetters[slot];
            this.primitiveGetter = gs.primitiveGetters[slot];
            this.objectSetter = gs.objectSetters[slot];
            this.primitiveSetter = gs.primitiveSetters[slot];
        }
        if ($assertionsDisabled || hasDualFields() != StructureLoader.isSingleFieldStructure(structure.getName())) {
            return;
        }
        throw new AssertionError();
    }

    public AccessorProperty(String key, int flags, int slot, ScriptObject owner, Object initialValue) {
        this(key, flags, owner.getClass(), slot);
        setInitialValue(owner, initialValue);
    }

    public AccessorProperty(String key, int flags, Class<?> structure, int slot, Class<?> initialType) {
        this(key, flags, structure, slot);
        setType(hasDualFields() ? initialType : Object.class);
    }

    public AccessorProperty(AccessorProperty property, Class<?> newType) {
        super(property, property.getFlags());
        this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
        this.GETTER_CACHE = newType != property.getLocalType() ? new MethodHandle[NOOF_TYPES] : property.GETTER_CACHE;
        this.primitiveGetter = property.primitiveGetter;
        this.primitiveSetter = property.primitiveSetter;
        this.objectGetter = property.objectGetter;
        this.objectSetter = property.objectSetter;
        setType(newType);
    }

    public AccessorProperty(AccessorProperty property) {
        this(property, property.getLocalType());
    }

    public final void setInitialValue(ScriptObject owner, Object initialValue) {
        setType(hasDualFields() ? JSType.unboxedFieldType(initialValue) : Object.class);
        if (initialValue instanceof Integer) {
            invokeSetter(owner, ((Integer) initialValue).intValue());
        } else if (initialValue instanceof Double) {
            invokeSetter(owner, ((Double) initialValue).doubleValue());
        } else {
            invokeSetter(owner, initialValue);
        }
    }

    protected final void initializeType() {
        setType(!hasDualFields() ? Object.class : null);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
    }

    private static MethodHandle bindTo(MethodHandle mh, Object receiver) {
        if (mh == null) {
            return null;
        }
        return Lookup.f248MH.dropArguments(Lookup.f248MH.bindTo(mh, receiver), 0, Object.class);
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public Property copy() {
        return new AccessorProperty(this);
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public Property copy(Class<?> newType) {
        return new AccessorProperty(this, newType);
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public int getIntValue(ScriptObject self, ScriptObject owner) {
        try {
            return getGetter(Integer.TYPE).invokeExact(self);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public double getDoubleValue(ScriptObject self, ScriptObject owner) {
        try {
            return getGetter(Double.TYPE).invokeExact(self);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public Object getObjectValue(ScriptObject self, ScriptObject owner) {
        try {
            return getGetter(Object.class).invokeExact(self);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    protected final void invokeSetter(ScriptObject self, int value) {
        try {
            getSetter(Integer.TYPE, self.getMap()).invokeExact(self, value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    protected final void invokeSetter(ScriptObject self, double value) {
        try {
            getSetter(Double.TYPE, self.getMap()).invokeExact(self, value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    protected final void invokeSetter(ScriptObject self, Object value) {
        try {
            getSetter(Object.class, self.getMap()).invokeExact(self, value);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public void setValue(ScriptObject self, ScriptObject owner, int value, boolean strict) {
        if ($assertionsDisabled || isConfigurable() || isWritable()) {
            invokeSetter(self, value);
            return;
        }
        throw new AssertionError(getKey() + " is not writable or configurable");
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public void setValue(ScriptObject self, ScriptObject owner, double value, boolean strict) {
        if ($assertionsDisabled || isConfigurable() || isWritable()) {
            invokeSetter(self, value);
            return;
        }
        throw new AssertionError(getKey() + " is not writable or configurable");
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public void setValue(ScriptObject self, ScriptObject owner, Object value, boolean strict) {
        invokeSetter(self, value);
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public void initMethodHandles(Class<?> structure) {
        if (!ScriptObject.class.isAssignableFrom(structure) || !StructureLoader.isStructureClass(structure.getName())) {
            throw new IllegalArgumentException();
        }
        if (!$assertionsDisabled && isSpill()) {
            throw new AssertionError();
        }
        initGetterSetter(structure);
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public MethodHandle getGetter(Class<?> type) {
        MethodHandle getter;
        int i = JSType.getAccessorTypeIndex(type);
        if (!$assertionsDisabled && type != Integer.TYPE && type != Double.TYPE && type != Object.class) {
            throw new AssertionError("invalid getter type " + type + " for " + getKey());
        }
        checkUndeclared();
        MethodHandle[] getterCache = this.GETTER_CACHE;
        MethodHandle cachedGetter = getterCache[i];
        if (cachedGetter != null) {
            getter = cachedGetter;
        } else {
            getter = debug(ObjectClassGenerator.createGetter(getLocalType(), type, this.primitiveGetter, this.objectGetter, -1), getLocalType(), type, PropertyDescriptor.GET);
            getterCache[i] = getter;
        }
        if (!$assertionsDisabled && (getter.type().returnType() != type || getter.type().parameterType(0) != Object.class)) {
            throw new AssertionError();
        }
        return getter;
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public MethodHandle getOptimisticGetter(Class<?> type, int programPoint) {
        if (this.objectGetter == null) {
            return getOptimisticPrimitiveGetter(type, programPoint);
        }
        checkUndeclared();
        return debug(ObjectClassGenerator.createGetter(getLocalType(), type, this.primitiveGetter, this.objectGetter, programPoint), getLocalType(), type, PropertyDescriptor.GET);
    }

    private MethodHandle getOptimisticPrimitiveGetter(Class<?> type, int programPoint) {
        MethodHandle g = getGetter(getLocalType());
        return Lookup.f248MH.asType(OptimisticReturnFilters.filterOptimisticReturnValue(g, type, programPoint), g.type().changeReturnType(type));
    }

    private Property getWiderProperty(Class<?> type) {
        return copy(type);
    }

    private PropertyMap getWiderMap(PropertyMap oldMap, Property newProperty) {
        PropertyMap newMap = oldMap.replaceProperty(this, newProperty);
        if ($assertionsDisabled || oldMap.size() > 0) {
            if (!$assertionsDisabled && newMap.size() != oldMap.size()) {
                throw new AssertionError();
            }
            return newMap;
        }
        throw new AssertionError();
    }

    private void checkUndeclared() {
        if ((getFlags() & 512) != 0) {
            throw ECMAErrors.referenceError("not.defined", getKey());
        }
    }

    private static Object replaceMap(Object sobj, PropertyMap newMap) {
        ((ScriptObject) sobj).setMap(newMap);
        return sobj;
    }

    private static Object invalidateSwitchPoint(AccessorProperty property, Object obj) {
        if (!property.builtinSwitchPoint.hasBeenInvalidated()) {
            SwitchPoint.invalidateAll(new SwitchPoint[]{property.builtinSwitchPoint});
        }
        return obj;
    }

    private MethodHandle generateSetter(Class<?> forType, Class<?> type) {
        return debug(ObjectClassGenerator.createSetter(forType, type, this.primitiveSetter, this.objectSetter), getLocalType(), type, PropertyDescriptor.SET);
    }

    protected final boolean isUndefined() {
        return getLocalType() == null;
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public MethodHandle getSetter(Class<?> type, PropertyMap currentMap) {
        MethodHandle mh;
        checkUndeclared();
        int typeIndex = JSType.getAccessorTypeIndex(type);
        int currentTypeIndex = JSType.getAccessorTypeIndex(getLocalType());
        if (needsInvalidator(typeIndex, currentTypeIndex)) {
            Property newProperty = getWiderProperty(type);
            PropertyMap newMap = getWiderMap(currentMap, newProperty);
            MethodHandle widerSetter = newProperty.getSetter(type, newMap);
            Class<?> ct = getLocalType();
            mh = Lookup.f248MH.filterArguments(widerSetter, 0, Lookup.f248MH.insertArguments(debugReplace(ct, type, currentMap, newMap), 1, newMap));
            if (ct != null && ct.isPrimitive() && !type.isPrimitive()) {
                mh = ObjectClassGenerator.createGuardBoxedPrimitiveSetter(ct, generateSetter(ct, ct), mh);
            }
        } else {
            Class<?> forType = isUndefined() ? type : getLocalType();
            mh = generateSetter(!forType.isPrimitive() ? Object.class : forType, type);
        }
        if (isBuiltin()) {
            mh = Lookup.f248MH.filterArguments(mh, 0, debugInvalidate(Lookup.f248MH.insertArguments(INVALIDATE_SP, 0, this), getKey()));
        }
        if ($assertionsDisabled || mh.type().returnType() == Void.TYPE) {
            return mh;
        }
        throw new AssertionError(mh.type());
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public final boolean canChangeType() {
        if (!hasDualFields()) {
            return false;
        }
        return getLocalType() == null || (getLocalType() != Object.class && (isConfigurable() || isWritable()));
    }

    private boolean needsInvalidator(int typeIndex, int currentTypeIndex) {
        return canChangeType() && typeIndex > currentTypeIndex;
    }

    private MethodHandle debug(MethodHandle mh, final Class<?> forType, final Class<?> type, final String tag) {
        if (!Context.DEBUG || !Global.hasInstance()) {
            return mh;
        }
        Context context = Context.getContextTrusted();
        if (!$assertionsDisabled && context == null) {
            throw new AssertionError();
        }
        return context.addLoggingToHandle(ObjectClassGenerator.class, Level.INFO, mh, 0, true, new Supplier<String>() { // from class: jdk.nashorn.internal.runtime.AccessorProperty.2
            @Override // java.util.function.Supplier
            public String get() {
                return tag + " '" + AccessorProperty.this.getKey() + "' (property=" + Debug.m67id(this) + ", slot=" + AccessorProperty.this.getSlot() + " " + getClass().getSimpleName() + " forType=" + MethodHandleFactory.stripName(forType) + ", type=" + MethodHandleFactory.stripName(type) + ')';
            }
        });
    }

    private MethodHandle debugReplace(final Class<?> oldType, final Class<?> newType, final PropertyMap oldMap, final PropertyMap newMap) {
        if (!Context.DEBUG || !Global.hasInstance()) {
            return REPLACE_MAP;
        }
        Context context = Context.getContextTrusted();
        if (!$assertionsDisabled && context == null) {
            throw new AssertionError();
        }
        MethodHandle mh = context.addLoggingToHandle(ObjectClassGenerator.class, REPLACE_MAP, new Supplier<String>() { // from class: jdk.nashorn.internal.runtime.AccessorProperty.3
            @Override // java.util.function.Supplier
            public String get() {
                return "Type change for '" + AccessorProperty.this.getKey() + "' " + oldType + "=>" + newType;
            }
        });
        return context.addLoggingToHandle(ObjectClassGenerator.class, Level.FINEST, mh, Integer.MAX_VALUE, false, new Supplier<String>() { // from class: jdk.nashorn.internal.runtime.AccessorProperty.4
            @Override // java.util.function.Supplier
            public String get() {
                return "Setting map " + Debug.m67id(oldMap) + " => " + Debug.m67id(newMap) + " " + oldMap + " => " + newMap;
            }
        });
    }

    private static MethodHandle debugInvalidate(MethodHandle invalidator, final String key) {
        if (!Context.DEBUG || !Global.hasInstance()) {
            return invalidator;
        }
        Context context = Context.getContextTrusted();
        if (!$assertionsDisabled && context == null) {
            throw new AssertionError();
        }
        return context.addLoggingToHandle(ObjectClassGenerator.class, invalidator, new Supplier<String>() { // from class: jdk.nashorn.internal.runtime.AccessorProperty.5
            @Override // java.util.function.Supplier
            public String get() {
                return "Field change callback for " + key + " triggered ";
            }
        });
    }

    private static MethodHandle findOwnMH_S(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(LOOKUP, AccessorProperty.class, name, Lookup.f248MH.type(rtype, types));
    }
}
