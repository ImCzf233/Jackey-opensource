package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.UserAccessorProperty;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.linker.NashornGuards;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/ScriptObject.class */
public abstract class ScriptObject implements PropertyAccess, Cloneable {
    public static final String PROTO_PROPERTY_NAME = "__proto__";
    public static final String NO_SUCH_METHOD_NAME = "__noSuchMethod__";
    public static final String NO_SUCH_PROPERTY_NAME = "__noSuchProperty__";
    public static final int IS_ARRAY = 1;
    public static final int IS_ARGUMENTS = 2;
    public static final int IS_LENGTH_NOT_WRITABLE = 4;
    public static final int IS_BUILTIN = 8;
    public static final int SPILL_RATE = 8;
    private PropertyMap map;
    private ScriptObject proto;
    private int flags;
    protected long[] primitiveSpill;
    protected Object[] objectSpill;
    private ArrayData arrayData;
    public static final MethodHandle GETPROTO;
    static final MethodHandle MEGAMORPHIC_GET;
    static final MethodHandle GLOBALFILTER;
    static final MethodHandle DECLARE_AND_SET;
    private static final MethodHandle TRUNCATINGFILTER;
    private static final MethodHandle KNOWNFUNCPROPGUARDSELF;
    private static final MethodHandle KNOWNFUNCPROPGUARDPROTO;
    private static final ArrayList<MethodHandle> PROTO_FILTERS;
    public static final CompilerConstants.Call GET_ARRAY;
    public static final CompilerConstants.Call GET_ARGUMENT;
    public static final CompilerConstants.Call SET_ARGUMENT;
    public static final CompilerConstants.Call GET_PROTO;
    public static final CompilerConstants.Call GET_PROTO_DEPTH;
    public static final CompilerConstants.Call SET_GLOBAL_OBJECT_PROTO;
    public static final CompilerConstants.Call SET_PROTO_FROM_LITERAL;
    public static final CompilerConstants.Call SET_USER_ACCESSORS;
    static final MethodHandle[] SET_SLOW;
    public static final CompilerConstants.Call SET_MAP;
    static final MethodHandle CAS_MAP;
    static final MethodHandle EXTENSION_CHECK;
    static final MethodHandle ENSURE_SPILL_SIZE;
    private static LongAdder count;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ScriptObject.class.desiredAssertionStatus();
        GETPROTO = findOwnMH_V("getProto", ScriptObject.class, new Class[0]);
        MEGAMORPHIC_GET = findOwnMH_V("megamorphicGet", Object.class, String.class, Boolean.TYPE, Boolean.TYPE);
        GLOBALFILTER = findOwnMH_S("globalFilter", Object.class, Object.class);
        DECLARE_AND_SET = findOwnMH_V("declareAndSet", Void.TYPE, String.class, Object.class);
        TRUNCATINGFILTER = findOwnMH_S("truncatingFilter", Object[].class, Integer.TYPE, Object[].class);
        KNOWNFUNCPROPGUARDSELF = findOwnMH_S("knownFunctionPropertyGuardSelf", Boolean.TYPE, Object.class, PropertyMap.class, MethodHandle.class, ScriptFunction.class);
        KNOWNFUNCPROPGUARDPROTO = findOwnMH_S("knownFunctionPropertyGuardProto", Boolean.TYPE, Object.class, PropertyMap.class, MethodHandle.class, Integer.TYPE, ScriptFunction.class);
        PROTO_FILTERS = new ArrayList<>();
        GET_ARRAY = CompilerConstants.virtualCall(MethodHandles.lookup(), ScriptObject.class, "getArray", ArrayData.class, new Class[0]);
        GET_ARGUMENT = CompilerConstants.virtualCall(MethodHandles.lookup(), ScriptObject.class, "getArgument", Object.class, Integer.TYPE);
        SET_ARGUMENT = CompilerConstants.virtualCall(MethodHandles.lookup(), ScriptObject.class, "setArgument", Void.TYPE, Integer.TYPE, Object.class);
        GET_PROTO = CompilerConstants.virtualCallNoLookup(ScriptObject.class, "getProto", ScriptObject.class, new Class[0]);
        GET_PROTO_DEPTH = CompilerConstants.virtualCallNoLookup(ScriptObject.class, "getProto", ScriptObject.class, Integer.TYPE);
        SET_GLOBAL_OBJECT_PROTO = CompilerConstants.staticCallNoLookup(ScriptObject.class, "setGlobalObjectProto", Void.TYPE, ScriptObject.class);
        SET_PROTO_FROM_LITERAL = CompilerConstants.virtualCallNoLookup(ScriptObject.class, "setProtoFromLiteral", Void.TYPE, Object.class);
        SET_USER_ACCESSORS = CompilerConstants.virtualCall(MethodHandles.lookup(), ScriptObject.class, "setUserAccessors", Void.TYPE, String.class, ScriptFunction.class, ScriptFunction.class);
        SET_SLOW = new MethodHandle[]{findOwnMH_V(PropertyDescriptor.SET, Void.TYPE, Object.class, Integer.TYPE, Integer.TYPE), findOwnMH_V(PropertyDescriptor.SET, Void.TYPE, Object.class, Double.TYPE, Integer.TYPE), findOwnMH_V(PropertyDescriptor.SET, Void.TYPE, Object.class, Object.class, Integer.TYPE)};
        SET_MAP = CompilerConstants.virtualCallNoLookup(ScriptObject.class, "setMap", Void.TYPE, PropertyMap.class);
        CAS_MAP = findOwnMH_V("compareAndSetMap", Boolean.TYPE, PropertyMap.class, PropertyMap.class);
        EXTENSION_CHECK = findOwnMH_V("extensionCheck", Boolean.TYPE, Boolean.TYPE, String.class);
        ENSURE_SPILL_SIZE = findOwnMH_V("ensureSpillSize", Object.class, Integer.TYPE);
        if (Context.DEBUG) {
            count = new LongAdder();
        }
    }

    public ScriptObject() {
        this(null);
    }

    public ScriptObject(PropertyMap map) {
        if (Context.DEBUG) {
            count.increment();
        }
        this.arrayData = ArrayData.EMPTY_ARRAY;
        setMap(map == null ? PropertyMap.newMap() : map);
    }

    public ScriptObject(ScriptObject proto, PropertyMap map) {
        this(map);
        this.proto = proto;
    }

    public ScriptObject(PropertyMap map, long[] primitiveSpill, Object[] objectSpill) {
        this(map);
        this.primitiveSpill = primitiveSpill;
        this.objectSpill = objectSpill;
        if ($assertionsDisabled || primitiveSpill == null || primitiveSpill.length == objectSpill.length) {
            return;
        }
        throw new AssertionError(" primitive spill pool size is not the same length as object spill pool size");
    }

    public boolean isGlobal() {
        return false;
    }

    private static int alignUp(int size, int alignment) {
        return ((size + alignment) - 1) & ((alignment - 1) ^ (-1));
    }

    public static int spillAllocationLength(int nProperties) {
        return alignUp(nProperties, 8);
    }

    public void addBoundProperties(ScriptObject source) {
        addBoundProperties(source, source.getMap().getProperties());
    }

    public void addBoundProperties(ScriptObject source, Property[] properties) {
        PropertyMap newMap = getMap();
        boolean extensible = newMap.isExtensible();
        for (Property property : properties) {
            newMap = addBoundProperty(newMap, source, property, extensible);
        }
        setMap(newMap);
    }

    public PropertyMap addBoundProperty(PropertyMap propMap, ScriptObject source, Property property, boolean extensible) {
        PropertyMap newMap = propMap;
        String key = property.getKey();
        Property oldProp = newMap.findProperty(key);
        if (oldProp == null) {
            if (!extensible) {
                throw ECMAErrors.typeError("object.non.extensible", key, ScriptRuntime.safeToString(this));
            }
            if (property instanceof UserAccessorProperty) {
                UserAccessorProperty prop = newUserAccessors(key, property.getFlags(), property.getGetterFunction(source), property.getSetterFunction(source));
                newMap = newMap.addPropertyNoHistory(prop);
            } else {
                newMap = newMap.addPropertyBind((AccessorProperty) property, source);
            }
        } else if (property.isFunctionDeclaration() && !oldProp.isConfigurable() && ((oldProp instanceof UserAccessorProperty) || !oldProp.isWritable() || !oldProp.isEnumerable())) {
            throw ECMAErrors.typeError("cant.redefine.property", key, ScriptRuntime.safeToString(this));
        }
        return newMap;
    }

    public void addBoundProperties(Object source, AccessorProperty[] properties) {
        PropertyMap newMap = getMap();
        boolean extensible = newMap.isExtensible();
        for (AccessorProperty property : properties) {
            String key = property.getKey();
            if (newMap.findProperty(key) == null) {
                if (!extensible) {
                    throw ECMAErrors.typeError("object.non.extensible", key, ScriptRuntime.safeToString(this));
                }
                newMap = newMap.addPropertyBind(property, source);
            }
        }
        setMap(newMap);
    }

    static MethodHandle bindTo(MethodHandle methodHandle, Object receiver) {
        return Lookup.f248MH.dropArguments(Lookup.f248MH.bindTo(methodHandle, receiver), 0, methodHandle.type().parameterType(0));
    }

    public Iterator<String> propertyIterator() {
        return new KeyIterator(this);
    }

    public Iterator<Object> valueIterator() {
        return new ValueIterator(this);
    }

    public final boolean isAccessorDescriptor() {
        return has(PropertyDescriptor.GET) || has(PropertyDescriptor.SET);
    }

    public final boolean isDataDescriptor() {
        return has("value") || has(PropertyDescriptor.WRITABLE);
    }

    public final PropertyDescriptor toPropertyDescriptor() {
        PropertyDescriptor desc;
        Global global = Context.getGlobal();
        if (isDataDescriptor()) {
            if (has(PropertyDescriptor.SET) || has(PropertyDescriptor.GET)) {
                throw ECMAErrors.typeError(global, "inconsistent.property.descriptor", new String[0]);
            }
            desc = global.newDataDescriptor(ScriptRuntime.UNDEFINED, false, false, false);
        } else if (isAccessorDescriptor()) {
            if (has("value") || has(PropertyDescriptor.WRITABLE)) {
                throw ECMAErrors.typeError(global, "inconsistent.property.descriptor", new String[0]);
            }
            desc = global.newAccessorDescriptor(ScriptRuntime.UNDEFINED, ScriptRuntime.UNDEFINED, false, false);
        } else {
            desc = global.newGenericDescriptor(false, false);
        }
        return desc.fillFrom(this);
    }

    public static PropertyDescriptor toPropertyDescriptor(Global global, Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).toPropertyDescriptor();
        }
        throw ECMAErrors.typeError(global, "not.an.object", ScriptRuntime.safeToString(obj));
    }

    public Object getOwnPropertyDescriptor(String key) {
        Property property = getMap().findProperty(key);
        Global global = Context.getGlobal();
        if (property != null) {
            ScriptFunction get = property.getGetterFunction(this);
            ScriptFunction set = property.getSetterFunction(this);
            boolean configurable = property.isConfigurable();
            boolean enumerable = property.isEnumerable();
            boolean writable = property.isWritable();
            if (property instanceof UserAccessorProperty) {
                return global.newAccessorDescriptor(get != null ? get : ScriptRuntime.UNDEFINED, set != null ? set : ScriptRuntime.UNDEFINED, configurable, enumerable);
            }
            return global.newDataDescriptor(getWithProperty(property), configurable, enumerable, writable);
        }
        int index = ArrayIndex.getArrayIndex(key);
        ArrayData array = getArray();
        if (array.has(index)) {
            return array.getDescriptor(global, index);
        }
        return ScriptRuntime.UNDEFINED;
    }

    public Object getPropertyDescriptor(String key) {
        Object res = getOwnPropertyDescriptor(key);
        if (res != ScriptRuntime.UNDEFINED) {
            return res;
        }
        if (getProto() != null) {
            return getProto().getOwnPropertyDescriptor(key);
        }
        return ScriptRuntime.UNDEFINED;
    }

    public void invalidateGlobalConstant(String key) {
        GlobalConstants globalConstants = getGlobalConstants();
        if (globalConstants != null) {
            globalConstants.delete(key);
        }
    }

    public boolean defineOwnProperty(String key, Object propertyDesc, boolean reject) {
        Global global = Context.getGlobal();
        PropertyDescriptor desc = toPropertyDescriptor(global, propertyDesc);
        Object current = getOwnPropertyDescriptor(key);
        String name = JSType.toString(key);
        invalidateGlobalConstant(key);
        if (current == ScriptRuntime.UNDEFINED) {
            if (isExtensible()) {
                addOwnProperty(key, desc);
                return true;
            } else if (reject) {
                throw ECMAErrors.typeError(global, "object.non.extensible", name, ScriptRuntime.safeToString(this));
            } else {
                return false;
            }
        }
        PropertyDescriptor currentDesc = (PropertyDescriptor) current;
        if ((desc.type() == 0 && !desc.has(PropertyDescriptor.CONFIGURABLE) && !desc.has(PropertyDescriptor.ENUMERABLE)) || desc.hasAndEquals(currentDesc)) {
            return true;
        }
        if (!currentDesc.isConfigurable()) {
            if (desc.has(PropertyDescriptor.CONFIGURABLE) && desc.isConfigurable()) {
                if (reject) {
                    throw ECMAErrors.typeError(global, "cant.redefine.property", name, ScriptRuntime.safeToString(this));
                }
                return false;
            } else if (desc.has(PropertyDescriptor.ENUMERABLE) && currentDesc.isEnumerable() != desc.isEnumerable()) {
                if (reject) {
                    throw ECMAErrors.typeError(global, "cant.redefine.property", name, ScriptRuntime.safeToString(this));
                }
                return false;
            }
        }
        int propFlags = Property.mergeFlags(currentDesc, desc);
        Property property = getMap().findProperty(key);
        if (currentDesc.type() == 1 && (desc.type() == 1 || desc.type() == 0)) {
            if (!currentDesc.isConfigurable() && !currentDesc.isWritable() && ((desc.has(PropertyDescriptor.WRITABLE) && desc.isWritable()) || (desc.has("value") && !ScriptRuntime.sameValue(currentDesc.getValue(), desc.getValue())))) {
                if (reject) {
                    throw ECMAErrors.typeError(global, "cant.redefine.property", name, ScriptRuntime.safeToString(this));
                }
                return false;
            }
            boolean newValue = desc.has("value");
            Object value = newValue ? desc.getValue() : currentDesc.getValue();
            if (newValue && property != null) {
                modifyOwnProperty(property, 0);
                set(key, value, 0);
                property = getMap().findProperty(key);
            }
            if (property == null) {
                addOwnProperty(key, propFlags, value);
                checkIntegerKey(key);
            } else {
                modifyOwnProperty(property, propFlags);
            }
        } else if (currentDesc.type() == 2 && (desc.type() == 2 || desc.type() == 0)) {
            if (!currentDesc.isConfigurable() && ((desc.has(PropertyDescriptor.GET) && !ScriptRuntime.sameValue(currentDesc.getGetter(), desc.getGetter())) || (desc.has(PropertyDescriptor.SET) && !ScriptRuntime.sameValue(currentDesc.getSetter(), desc.getSetter())))) {
                if (reject) {
                    throw ECMAErrors.typeError(global, "cant.redefine.property", name, ScriptRuntime.safeToString(this));
                }
                return false;
            }
            modifyOwnProperty(property, propFlags, desc.has(PropertyDescriptor.GET) ? desc.getGetter() : currentDesc.getGetter(), desc.has(PropertyDescriptor.SET) ? desc.getSetter() : currentDesc.getSetter());
        } else if (!currentDesc.isConfigurable()) {
            if (reject) {
                throw ECMAErrors.typeError(global, "cant.redefine.property", name, ScriptRuntime.safeToString(this));
            }
            return false;
        } else {
            int propFlags2 = 0;
            boolean value2 = desc.has(PropertyDescriptor.CONFIGURABLE) ? desc.isConfigurable() : currentDesc.isConfigurable();
            if (!value2) {
                propFlags2 = 0 | 4;
            }
            boolean value3 = desc.has(PropertyDescriptor.ENUMERABLE) ? desc.isEnumerable() : currentDesc.isEnumerable();
            if (!value3) {
                propFlags2 |= 2;
            }
            int type = desc.type();
            if (type == 1) {
                boolean value4 = desc.has(PropertyDescriptor.WRITABLE) && desc.isWritable();
                if (!value4) {
                    propFlags2 |= 1;
                }
                deleteOwnProperty(property);
                addOwnProperty(key, propFlags2, desc.getValue());
            } else if (type == 2) {
                if (property == null) {
                    addOwnProperty(key, propFlags2, desc.has(PropertyDescriptor.GET) ? desc.getGetter() : null, desc.has(PropertyDescriptor.SET) ? desc.getSetter() : null);
                } else {
                    modifyOwnProperty(property, propFlags2, desc.has(PropertyDescriptor.GET) ? desc.getGetter() : null, desc.has(PropertyDescriptor.SET) ? desc.getSetter() : null);
                }
            }
        }
        checkIntegerKey(key);
        return true;
    }

    public void defineOwnProperty(int index, Object value) {
        if ($assertionsDisabled || ArrayIndex.isValidArrayIndex(index)) {
            long longIndex = ArrayIndex.toLongIndex(index);
            long oldLength = getArray().length();
            if (longIndex >= oldLength) {
                setArray(getArray().ensure(longIndex).safeDelete(oldLength, longIndex - 1, false));
            }
            setArray(getArray().set(index, value, false));
            return;
        }
        throw new AssertionError("invalid array index");
    }

    private void checkIntegerKey(String key) {
        int index = ArrayIndex.getArrayIndex(key);
        if (ArrayIndex.isValidArrayIndex(index)) {
            ArrayData data = getArray();
            if (data.has(index)) {
                setArray(data.delete(index));
            }
        }
    }

    public final void addOwnProperty(String key, PropertyDescriptor propertyDesc) {
        PropertyDescriptor pdesc = propertyDesc;
        int propFlags = Property.toFlags(pdesc);
        if (pdesc.type() == 0) {
            Global global = Context.getGlobal();
            PropertyDescriptor dDesc = global.newDataDescriptor(ScriptRuntime.UNDEFINED, false, false, false);
            dDesc.fillFrom((ScriptObject) pdesc);
            pdesc = dDesc;
        }
        int type = pdesc.type();
        if (type == 1) {
            addOwnProperty(key, propFlags, pdesc.getValue());
        } else if (type == 2) {
            addOwnProperty(key, propFlags, pdesc.has(PropertyDescriptor.GET) ? pdesc.getGetter() : null, pdesc.has(PropertyDescriptor.SET) ? pdesc.getSetter() : null);
        }
        checkIntegerKey(key);
    }

    public final FindProperty findProperty(String key, boolean deep) {
        return findProperty(key, deep, this);
    }

    public FindProperty findProperty(String key, boolean deep, ScriptObject start) {
        PropertyMap selfMap = getMap();
        Property property = selfMap.findProperty(key);
        if (property != null) {
            return new FindProperty(start, this, property);
        }
        if (deep) {
            ScriptObject myProto = getProto();
            FindProperty find = myProto == null ? null : myProto.findProperty(key, true, start);
            checkSharedProtoMap();
            return find;
        }
        return null;
    }

    boolean hasProperty(String key, boolean deep) {
        ScriptObject myProto;
        if (getMap().findProperty(key) != null) {
            return true;
        }
        if (deep && (myProto = getProto()) != null) {
            return myProto.hasProperty(key, true);
        }
        return false;
    }

    private SwitchPoint findBuiltinSwitchPoint(String key) {
        SwitchPoint sp;
        ScriptObject proto = getProto();
        while (true) {
            ScriptObject myProto = proto;
            if (myProto != null) {
                Property prop = myProto.getMap().findProperty(key);
                if (prop == null || (sp = prop.getBuiltinSwitchPoint()) == null || sp.hasBeenInvalidated()) {
                    proto = myProto.getProto();
                } else {
                    return sp;
                }
            } else {
                return null;
            }
        }
    }

    public final Property addOwnProperty(String key, int propertyFlags, ScriptFunction getter, ScriptFunction setter) {
        return addOwnProperty(newUserAccessors(key, propertyFlags, getter, setter));
    }

    public final Property addOwnProperty(String key, int propertyFlags, Object value) {
        return addSpillProperty(key, propertyFlags, value, true);
    }

    /* JADX WARN: Incorrect condition in loop: B:4:0x0011 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final jdk.nashorn.internal.runtime.Property addOwnProperty(jdk.nashorn.internal.runtime.Property r5) {
        /*
            r4 = this;
            r0 = r4
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.getMap()
            r6 = r0
        L5:
            r0 = r6
            r1 = r5
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.addProperty(r1)
            r7 = r0
            r0 = r4
            r1 = r6
            r2 = r7
            boolean r0 = r0.compareAndSetMap(r1, r2)
            if (r0 != 0) goto L2e
            r0 = r4
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.getMap()
            r6 = r0
            r0 = r6
            r1 = r5
            java.lang.String r1 = r1.getKey()
            jdk.nashorn.internal.runtime.Property r0 = r0.findProperty(r1)
            r8 = r0
            r0 = r8
            if (r0 == 0) goto L2b
            r0 = r8
            return r0
        L2b:
            goto L30
        L2e:
            r0 = r5
            return r0
        L30:
            goto L5
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.ScriptObject.addOwnProperty(jdk.nashorn.internal.runtime.Property):jdk.nashorn.internal.runtime.Property");
    }

    private void erasePropertyValue(Property property) {
        if (!(property instanceof UserAccessorProperty)) {
            if (!$assertionsDisabled && property == null) {
                throw new AssertionError();
            }
            property.setValue(this, this, (Object) ScriptRuntime.UNDEFINED, false);
        }
    }

    public final boolean deleteOwnProperty(Property property) {
        erasePropertyValue(property);
        PropertyMap map = getMap();
        while (true) {
            PropertyMap oldMap = map;
            PropertyMap newMap = oldMap.deleteProperty(property);
            if (newMap == null) {
                return false;
            }
            if (!compareAndSetMap(oldMap, newMap)) {
                map = getMap();
            } else {
                if (property instanceof UserAccessorProperty) {
                    ((UserAccessorProperty) property).setAccessors(this, getMap(), null);
                }
                invalidateGlobalConstant(property.getKey());
                return true;
            }
        }
    }

    public final void initUserAccessors(String key, int propertyFlags, ScriptFunction getter, ScriptFunction setter) {
        PropertyMap newMap;
        PropertyMap oldMap = getMap();
        int slot = oldMap.getFreeSpillSlot();
        ensureSpillSize(slot);
        this.objectSpill[slot] = new UserAccessorProperty.Accessors(getter, setter);
        do {
            Property newProperty = new UserAccessorProperty(key, propertyFlags, slot);
            newMap = oldMap.addProperty(newProperty);
        } while (!compareAndSetMap(oldMap, newMap));
    }

    public final Property modifyOwnProperty(Property oldProperty, int propertyFlags, ScriptFunction getter, ScriptFunction setter) {
        Property newProperty;
        if (oldProperty instanceof UserAccessorProperty) {
            UserAccessorProperty uc = (UserAccessorProperty) oldProperty;
            int slot = uc.getSlot();
            if (!$assertionsDisabled && uc.getLocalType() != Object.class) {
                throw new AssertionError();
            }
            UserAccessorProperty.Accessors gs = uc.getAccessors(this);
            if (!$assertionsDisabled && gs == null) {
                throw new AssertionError();
            }
            gs.set(getter, setter);
            if (uc.getFlags() == propertyFlags) {
                return oldProperty;
            }
            newProperty = new UserAccessorProperty(uc.getKey(), propertyFlags, slot);
        } else {
            erasePropertyValue(oldProperty);
            newProperty = newUserAccessors(oldProperty.getKey(), propertyFlags, getter, setter);
        }
        return modifyOwnProperty(oldProperty, newProperty);
    }

    public final Property modifyOwnProperty(Property oldProperty, int propertyFlags) {
        return modifyOwnProperty(oldProperty, oldProperty.setFlags(propertyFlags));
    }

    /* JADX WARN: Incorrect condition in loop: B:14:0x003a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private jdk.nashorn.internal.runtime.Property modifyOwnProperty(jdk.nashorn.internal.runtime.Property r5, jdk.nashorn.internal.runtime.Property r6) {
        /*
            r4 = this;
            r0 = r5
            r1 = r6
            if (r0 != r1) goto L7
            r0 = r6
            return r0
        L7:
            boolean r0 = jdk.nashorn.internal.runtime.ScriptObject.$assertionsDisabled
            if (r0 != 0) goto L26
            r0 = r6
            java.lang.String r0 = r0.getKey()
            r1 = r5
            java.lang.String r1 = r1.getKey()
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L26
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r1 = r0
            java.lang.String r2 = "replacing property with different key"
            r1.<init>(r2)
            throw r0
        L26:
            r0 = r4
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.getMap()
            r7 = r0
        L2b:
            r0 = r7
            r1 = r5
            r2 = r6
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.replaceProperty(r1, r2)
            r8 = r0
            r0 = r4
            r1 = r7
            r2 = r8
            boolean r0 = r0.compareAndSetMap(r1, r2)
            if (r0 != 0) goto L60
            r0 = r4
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.getMap()
            r7 = r0
            r0 = r7
            r1 = r5
            java.lang.String r1 = r1.getKey()
            jdk.nashorn.internal.runtime.Property r0 = r0.findProperty(r1)
            r9 = r0
            r0 = r9
            if (r0 == 0) goto L5d
            r0 = r9
            r1 = r6
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L5d
            r0 = r9
            return r0
        L5d:
            goto L62
        L60:
            r0 = r6
            return r0
        L62:
            goto L2b
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.ScriptObject.modifyOwnProperty(jdk.nashorn.internal.runtime.Property, jdk.nashorn.internal.runtime.Property):jdk.nashorn.internal.runtime.Property");
    }

    public final void setUserAccessors(String key, ScriptFunction getter, ScriptFunction setter) {
        Property oldProperty = getMap().findProperty(key);
        if (oldProperty instanceof UserAccessorProperty) {
            modifyOwnProperty(oldProperty, oldProperty.getFlags(), getter, setter);
        } else {
            addOwnProperty(newUserAccessors(key, oldProperty != null ? oldProperty.getFlags() : 0, getter, setter));
        }
    }

    private static int getIntValue(FindProperty find, int programPoint) {
        MethodHandle getter = find.getGetter(Integer.TYPE, programPoint, null);
        if (getter != null) {
            try {
                return getter.invokeExact(find.getGetterReceiver());
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable e2) {
                throw new RuntimeException(e2);
            }
        }
        return 0;
    }

    private static double getDoubleValue(FindProperty find, int programPoint) {
        MethodHandle getter = find.getGetter(Double.TYPE, programPoint, null);
        if (getter != null) {
            try {
                return getter.invokeExact(find.getGetterReceiver());
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable e2) {
                throw new RuntimeException(e2);
            }
        }
        return Double.NaN;
    }

    public MethodHandle getCallMethodHandle(FindProperty find, MethodType type, String bindName) {
        return getCallMethodHandle(find.getObjectValue(), type, bindName);
    }

    protected static MethodHandle getCallMethodHandle(Object value, MethodType type, String bindName) {
        if (value instanceof ScriptFunction) {
            return ((ScriptFunction) value).getCallMethodHandle(type, bindName);
        }
        return null;
    }

    public final Object getWithProperty(Property property) {
        return new FindProperty(this, this, property).getObjectValue();
    }

    public final Property getProperty(String key) {
        return getMap().findProperty(key);
    }

    public Object getArgument(int key) {
        return get(key);
    }

    public void setArgument(int key, Object value) {
        set(key, value, 0);
    }

    public Context getContext() {
        return Context.fromClass(getClass());
    }

    public final PropertyMap getMap() {
        return this.map;
    }

    public final void setMap(PropertyMap map) {
        this.map = map;
    }

    protected final boolean compareAndSetMap(PropertyMap oldMap, PropertyMap newMap) {
        if (oldMap == this.map) {
            this.map = newMap;
            return true;
        }
        return false;
    }

    public final ScriptObject getProto() {
        return this.proto;
    }

    public final ScriptObject getProto(int n) {
        if ($assertionsDisabled || n > 0) {
            ScriptObject p = getProto();
            int i = n;
            while (true) {
                int i2 = i;
                i--;
                if (i2 > 0) {
                    p = p.getProto();
                } else {
                    return p;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    public final void setProto(ScriptObject newProto) {
        ScriptObject oldProto = this.proto;
        if (oldProto != newProto) {
            this.proto = newProto;
            getMap().protoChanged(true);
            setMap(getMap().changeProto(newProto));
        }
    }

    public void setInitialProto(ScriptObject initialProto) {
        this.proto = initialProto;
    }

    public static void setGlobalObjectProto(ScriptObject obj) {
        obj.setInitialProto(Global.objectPrototype());
    }

    public final void setPrototypeOf(Object newProto) {
        if (newProto == null || (newProto instanceof ScriptObject)) {
            if (!isExtensible()) {
                if (newProto == getProto()) {
                    return;
                }
                throw ECMAErrors.typeError("__proto__.set.non.extensible", ScriptRuntime.safeToString(this));
            }
            ScriptObject scriptObject = (ScriptObject) newProto;
            while (true) {
                ScriptObject p = scriptObject;
                if (p != null) {
                    if (p == this) {
                        throw ECMAErrors.typeError("circular.__proto__.set", ScriptRuntime.safeToString(this));
                    }
                    scriptObject = p.getProto();
                } else {
                    setProto((ScriptObject) newProto);
                    return;
                }
            }
        } else {
            throw ECMAErrors.typeError("cant.set.proto.to.non.object", ScriptRuntime.safeToString(this), ScriptRuntime.safeToString(newProto));
        }
    }

    public final void setProtoFromLiteral(Object newProto) {
        if (newProto == null || (newProto instanceof ScriptObject)) {
            setPrototypeOf(newProto);
        } else {
            setPrototypeOf(Global.objectPrototype());
        }
    }

    public final String[] getOwnKeys(boolean all) {
        return getOwnKeys(all, null);
    }

    public String[] getOwnKeys(boolean all, Set<String> nonEnumerable) {
        Property[] properties;
        List<Object> keys = new ArrayList<>();
        PropertyMap selfMap = getMap();
        ArrayData array = getArray();
        Iterator<Long> iter = array.indexIterator();
        while (iter.hasNext()) {
            keys.add(JSType.toString(iter.next().longValue()));
        }
        for (Property property : selfMap.getProperties()) {
            boolean enumerable = property.isEnumerable();
            String key = property.getKey();
            if (all) {
                keys.add(key);
            } else if (enumerable) {
                if (nonEnumerable == null || !nonEnumerable.contains(key)) {
                    keys.add(key);
                }
            } else if (nonEnumerable != null) {
                nonEnumerable.add(key);
            }
        }
        return (String[]) keys.toArray(new String[keys.size()]);
    }

    public boolean hasArrayEntries() {
        return getArray().length() > 0 || getMap().containsArrayKeys();
    }

    public String getClassName() {
        return "Object";
    }

    public Object getLength() {
        return get("length");
    }

    public String safeToString() {
        return "[object " + getClassName() + "]";
    }

    public Object getDefaultValue(Class<?> typeHint) {
        return Context.getGlobal().getDefaultValue(this, typeHint);
    }

    public boolean isInstance(ScriptObject instance) {
        return false;
    }

    /* JADX WARN: Incorrect condition in loop: B:4:0x0011 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public jdk.nashorn.internal.runtime.ScriptObject preventExtensions() {
        /*
            r4 = this;
            r0 = r4
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.getMap()
            r5 = r0
        L5:
            r0 = r4
            r1 = r5
            r2 = r4
            jdk.nashorn.internal.runtime.PropertyMap r2 = r2.getMap()
            jdk.nashorn.internal.runtime.PropertyMap r2 = r2.preventExtensions()
            boolean r0 = r0.compareAndSetMap(r1, r2)
            if (r0 != 0) goto L1c
            r0 = r4
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.getMap()
            r5 = r0
            goto L5
        L1c:
            r0 = r4
            jdk.nashorn.internal.runtime.arrays.ArrayData r0 = r0.getArray()
            r6 = r0
            boolean r0 = jdk.nashorn.internal.runtime.ScriptObject.$assertionsDisabled
            if (r0 != 0) goto L33
            r0 = r6
            if (r0 != 0) goto L33
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r1 = r0
            r1.<init>()
            throw r0
        L33:
            r0 = r4
            r1 = r6
            jdk.nashorn.internal.runtime.arrays.ArrayData r1 = jdk.nashorn.internal.runtime.arrays.ArrayData.preventExtension(r1)
            r0.setArray(r1)
            r0 = r4
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.ScriptObject.preventExtensions():jdk.nashorn.internal.runtime.ScriptObject");
    }

    public static boolean isArray(Object obj) {
        return (obj instanceof ScriptObject) && ((ScriptObject) obj).isArray();
    }

    public final boolean isArray() {
        return (this.flags & 1) != 0;
    }

    public final void setIsArray() {
        this.flags |= 1;
    }

    public final boolean isArguments() {
        return (this.flags & 2) != 0;
    }

    public final void setIsArguments() {
        this.flags |= 2;
    }

    public boolean isLengthNotWritable() {
        return (this.flags & 4) != 0;
    }

    public void setIsLengthNotWritable() {
        this.flags |= 4;
    }

    public final ArrayData getArray(Class<?> elementType) {
        if (elementType == null) {
            return this.arrayData;
        }
        ArrayData newArrayData = this.arrayData.convert(elementType);
        if (newArrayData != this.arrayData) {
            this.arrayData = newArrayData;
        }
        return newArrayData;
    }

    public final ArrayData getArray() {
        return this.arrayData;
    }

    public final void setArray(ArrayData arrayData) {
        this.arrayData = arrayData;
    }

    public boolean isExtensible() {
        return getMap().isExtensible();
    }

    /* JADX WARN: Incorrect condition in loop: B:4:0x0013 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public jdk.nashorn.internal.runtime.ScriptObject seal() {
        /*
            r4 = this;
            r0 = r4
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.getMap()
            r5 = r0
        L5:
            r0 = r4
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.getMap()
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.seal()
            r6 = r0
            r0 = r4
            r1 = r5
            r2 = r6
            boolean r0 = r0.compareAndSetMap(r1, r2)
            if (r0 != 0) goto L1e
            r0 = r4
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.getMap()
            r5 = r0
            goto L2b
        L1e:
            r0 = r4
            r1 = r4
            jdk.nashorn.internal.runtime.arrays.ArrayData r1 = r1.getArray()
            jdk.nashorn.internal.runtime.arrays.ArrayData r1 = jdk.nashorn.internal.runtime.arrays.ArrayData.seal(r1)
            r0.setArray(r1)
            r0 = r4
            return r0
        L2b:
            goto L5
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.ScriptObject.seal():jdk.nashorn.internal.runtime.ScriptObject");
    }

    public boolean isSealed() {
        return getMap().isSealed();
    }

    /* JADX WARN: Incorrect condition in loop: B:4:0x0013 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public jdk.nashorn.internal.runtime.ScriptObject freeze() {
        /*
            r4 = this;
            r0 = r4
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.getMap()
            r5 = r0
        L5:
            r0 = r4
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.getMap()
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.freeze()
            r6 = r0
            r0 = r4
            r1 = r5
            r2 = r6
            boolean r0 = r0.compareAndSetMap(r1, r2)
            if (r0 != 0) goto L1e
            r0 = r4
            jdk.nashorn.internal.runtime.PropertyMap r0 = r0.getMap()
            r5 = r0
            goto L2b
        L1e:
            r0 = r4
            r1 = r4
            jdk.nashorn.internal.runtime.arrays.ArrayData r1 = r1.getArray()
            jdk.nashorn.internal.runtime.arrays.ArrayData r1 = jdk.nashorn.internal.runtime.arrays.ArrayData.freeze(r1)
            r0.setArray(r1)
            r0 = r4
            return r0
        L2b:
            goto L5
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.ScriptObject.freeze():jdk.nashorn.internal.runtime.ScriptObject");
    }

    public boolean isFrozen() {
        return getMap().isFrozen();
    }

    public boolean isScope() {
        return false;
    }

    public final void setIsBuiltin() {
        this.flags |= 8;
    }

    public final boolean isBuiltin() {
        return (this.flags & 8) != 0;
    }

    public void clear(boolean strict) {
        Iterator<String> iter = propertyIterator();
        while (iter.hasNext()) {
            delete(iter.next(), strict);
        }
    }

    public boolean containsKey(Object key) {
        return has(key);
    }

    public boolean containsValue(Object value) {
        Iterator<Object> iter = valueIterator();
        while (iter.hasNext()) {
            if (iter.next().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        Iterator<String> iter = propertyIterator();
        Set<Map.Entry<Object, Object>> entries = new HashSet<>();
        while (iter.hasNext()) {
            Object key = iter.next();
            entries.add(new AbstractMap.SimpleImmutableEntry<>(key, get(key)));
        }
        return Collections.unmodifiableSet(entries);
    }

    public boolean isEmpty() {
        return !propertyIterator().hasNext();
    }

    public Set<Object> keySet() {
        Iterator<String> iter = propertyIterator();
        Set<Object> keySet = new HashSet<>();
        while (iter.hasNext()) {
            keySet.add(iter.next());
        }
        return Collections.unmodifiableSet(keySet);
    }

    public Object put(Object key, Object value, boolean strict) {
        Object oldValue = get(key);
        int scriptObjectFlags = strict ? 2 : 0;
        set(key, value, scriptObjectFlags);
        return oldValue;
    }

    public void putAll(Map<?, ?> otherMap, boolean strict) {
        int scriptObjectFlags = strict ? 2 : 0;
        for (Map.Entry<?, ?> entry : otherMap.entrySet()) {
            set(entry.getKey(), entry.getValue(), scriptObjectFlags);
        }
    }

    public Object remove(Object key, boolean strict) {
        Object oldValue = get(key);
        delete(key, strict);
        return oldValue;
    }

    public int size() {
        int n = 0;
        Iterator<String> iter = propertyIterator();
        while (iter.hasNext()) {
            n++;
            iter.next();
        }
        return n;
    }

    public Collection<Object> values() {
        List<Object> values = new ArrayList<>(size());
        Iterator<Object> iter = valueIterator();
        while (iter.hasNext()) {
            values.add(iter.next());
        }
        return Collections.unmodifiableList(values);
    }

    public GuardedInvocation lookup(CallSiteDescriptor desc, LinkRequest request) {
        int c = desc.getNameTokenCount();
        String operator = CallSiteDescriptorFactory.tokenizeOperators(desc).get(0);
        boolean z = true;
        switch (operator.hashCode()) {
            case -75566075:
                if (operator.equals("getElem")) {
                    z = true;
                    break;
                }
                break;
            case -75232295:
                if (operator.equals("getProp")) {
                    z = false;
                    break;
                }
                break;
            case 108960:
                if (operator.equals("new")) {
                    z = true;
                    break;
                }
                break;
            case 3045982:
                if (operator.equals("call")) {
                    z = true;
                    break;
                }
                break;
            case 618460119:
                if (operator.equals("getMethod")) {
                    z = true;
                    break;
                }
                break;
            case 1402960095:
                if (operator.equals("callMethod")) {
                    z = true;
                    break;
                }
                break;
            case 1984543505:
                if (operator.equals("setElem")) {
                    z = true;
                    break;
                }
                break;
            case 1984877285:
                if (operator.equals("setProp")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
            case true:
            case true:
                return c > 2 ? findGetMethod(desc, request, operator) : findGetIndexMethod(desc, request);
            case true:
            case true:
                return c > 2 ? findSetMethod(desc, request) : findSetIndexMethod(desc, request);
            case true:
                return findCallMethod(desc, request);
            case true:
                return findNewMethod(desc, request);
            case true:
                return findCallMethodMethod(desc, request);
            default:
                return null;
        }
    }

    protected GuardedInvocation findNewMethod(CallSiteDescriptor desc, LinkRequest request) {
        return notAFunction(desc);
    }

    protected GuardedInvocation findCallMethod(CallSiteDescriptor desc, LinkRequest request) {
        return notAFunction(desc);
    }

    private GuardedInvocation notAFunction(CallSiteDescriptor desc) {
        throw ECMAErrors.typeError("not.a.function", NashornCallSiteDescriptor.getFunctionErrorMessage(desc, this));
    }

    public GuardedInvocation findCallMethodMethod(CallSiteDescriptor desc, LinkRequest request) {
        MethodType callType = desc.getMethodType();
        CallSiteDescriptor getterType = desc.changeMethodType(MethodType.methodType(Object.class, callType.parameterType(0)));
        GuardedInvocation getter = findGetMethod(getterType, request, "getMethod");
        MethodHandle argDroppingGetter = Lookup.f248MH.dropArguments(getter.getInvocation(), 1, callType.parameterList().subList(1, callType.parameterCount()));
        MethodHandle invoker = Bootstrap.createDynamicInvoker("dyn:call", callType.insertParameterTypes(0, argDroppingGetter.type().returnType()));
        return getter.replaceMethods(Lookup.f248MH.foldArguments(invoker, argDroppingGetter), getter.getGuard());
    }

    boolean hasWithScope() {
        return false;
    }

    public static MethodHandle addProtoFilter(MethodHandle methodHandle, int depth) {
        if (depth == 0) {
            return methodHandle;
        }
        int listIndex = depth - 1;
        MethodHandle filter = listIndex < PROTO_FILTERS.size() ? PROTO_FILTERS.get(listIndex) : null;
        if (filter == null) {
            filter = addProtoFilter(GETPROTO, depth - 1);
            PROTO_FILTERS.add(null);
            PROTO_FILTERS.set(listIndex, filter);
        }
        return Lookup.f248MH.filterArguments(methodHandle, 0, filter.asType(filter.type().changeReturnType(methodHandle.type().parameterType(0))));
    }

    public GuardedInvocation findGetMethod(CallSiteDescriptor desc, LinkRequest request, String operator) {
        SwitchPoint[] protoSwitchPoints;
        GuardedInvocation cinv;
        boolean explicitInstanceOfCheck = NashornGuards.explicitInstanceOfCheck(desc, request);
        String name = desc.getNameToken(2);
        if (NashornCallSiteDescriptor.isApplyToCall(desc) && Global.isBuiltinFunctionPrototypeApply()) {
            name = "call";
        }
        if (request.isCallSiteUnstable() || hasWithScope()) {
            return findMegaMorphicGetMethod(desc, name, "getMethod".equals(operator));
        }
        FindProperty find = findProperty(name, true);
        if (find == null) {
            boolean z = true;
            switch (operator.hashCode()) {
                case -75566075:
                    if (operator.equals("getElem")) {
                        z = false;
                        break;
                    }
                    break;
                case -75232295:
                    if (operator.equals("getProp")) {
                        z = true;
                        break;
                    }
                    break;
                case 618460119:
                    if (operator.equals("getMethod")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                case true:
                    return noSuchProperty(desc, request);
                case true:
                    return noSuchMethod(desc, request);
                default:
                    throw new AssertionError(operator);
            }
        }
        GlobalConstants globalConstants = getGlobalConstants();
        if (globalConstants != null && (cinv = globalConstants.findGetMethod(find, this, desc)) != null) {
            return cinv;
        }
        Class<?> returnType = desc.getMethodType().returnType();
        Property property = find.getProperty();
        int programPoint = NashornCallSiteDescriptor.isOptimistic(desc) ? NashornCallSiteDescriptor.getProgramPoint(desc) : -1;
        MethodHandle mh = find.getGetter(returnType, programPoint, request);
        MethodHandle guard = NashornGuards.getGuard(this, property, desc, explicitInstanceOfCheck);
        ScriptObject owner = find.getOwner();
        Class<ClassCastException> exception = explicitInstanceOfCheck ? null : ClassCastException.class;
        if (mh == null) {
            mh = Lookup.emptyGetter(returnType);
            protoSwitchPoints = getProtoSwitchPoints(name, owner);
        } else if (!find.isSelf()) {
            if (!$assertionsDisabled && !mh.type().returnType().equals(returnType)) {
                throw new AssertionError("return type mismatch for getter " + mh.type().returnType() + " != " + returnType);
            }
            if (!(property instanceof UserAccessorProperty)) {
                mh = addProtoFilter(mh, find.getProtoChainLength());
            }
            protoSwitchPoints = getProtoSwitchPoints(name, owner);
        } else {
            protoSwitchPoints = null;
        }
        GuardedInvocation inv = new GuardedInvocation(mh, guard, protoSwitchPoints, exception);
        return inv.addSwitchPoint(findBuiltinSwitchPoint(name));
    }

    private static GuardedInvocation findMegaMorphicGetMethod(CallSiteDescriptor desc, String name, boolean isMethod) {
        Context.getContextTrusted().getLogger(ObjectClassGenerator.class).warning("Megamorphic getter: " + desc + " " + name + " " + isMethod);
        MethodHandle invoker = Lookup.f248MH.insertArguments(MEGAMORPHIC_GET, 1, name, Boolean.valueOf(isMethod), Boolean.valueOf(NashornCallSiteDescriptor.isScope(desc)));
        MethodHandle guard = getScriptObjectGuard(desc.getMethodType(), true);
        return new GuardedInvocation(invoker, guard);
    }

    private Object megamorphicGet(String key, boolean isMethod, boolean isScope) {
        FindProperty find = findProperty(key, true);
        if (find != null) {
            return find.getObjectValue();
        }
        return isMethod ? getNoSuchMethod(key, isScope, -1) : invokeNoSuchProperty(key, isScope, -1);
    }

    private void declareAndSet(String key, Object value) {
        PropertyMap oldMap = getMap();
        FindProperty find = findProperty(key, false);
        if ($assertionsDisabled || find != null) {
            Property property = find.getProperty();
            if (!$assertionsDisabled && property == null) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && !property.needsDeclaration()) {
                throw new AssertionError();
            }
            PropertyMap newMap = oldMap.replaceProperty(property, property.removeFlags(512));
            setMap(newMap);
            set(key, value, 0);
            return;
        }
        throw new AssertionError();
    }

    public GuardedInvocation findGetIndexMethod(CallSiteDescriptor desc, LinkRequest request) {
        String name;
        MethodType callType = desc.getMethodType();
        Class<?> returnType = callType.returnType();
        Class<?> returnClass = returnType.isPrimitive() ? returnType : Object.class;
        Class<?> keyClass = callType.parameterType(1);
        boolean explicitInstanceOfCheck = NashornGuards.explicitInstanceOfCheck(desc, request);
        if (returnClass.isPrimitive()) {
            String returnTypeName = returnClass.getName();
            name = PropertyDescriptor.GET + Character.toUpperCase(returnTypeName.charAt(0)) + returnTypeName.substring(1, returnTypeName.length());
        } else {
            name = PropertyDescriptor.GET;
        }
        MethodHandle mh = findGetIndexMethodHandle(returnClass, name, keyClass, desc);
        return new GuardedInvocation(mh, getScriptObjectGuard(callType, explicitInstanceOfCheck), (SwitchPoint) null, explicitInstanceOfCheck ? null : ClassCastException.class);
    }

    private static MethodHandle getScriptObjectGuard(MethodType type, boolean explicitInstanceOfCheck) {
        if (ScriptObject.class.isAssignableFrom(type.parameterType(0))) {
            return null;
        }
        return NashornGuards.getScriptObjectGuard(explicitInstanceOfCheck);
    }

    protected MethodHandle findGetIndexMethodHandle(Class<?> returnType, String name, Class<?> elementType, CallSiteDescriptor desc) {
        if (!returnType.isPrimitive()) {
            return findOwnMH_V(getClass(), name, returnType, elementType);
        }
        MethodHandleFunctionality methodHandleFunctionality = Lookup.f248MH;
        MethodHandle findOwnMH_V = findOwnMH_V(getClass(), name, returnType, elementType, Integer.TYPE);
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(NashornCallSiteDescriptor.isOptimistic(desc) ? NashornCallSiteDescriptor.getProgramPoint(desc) : -1);
        return methodHandleFunctionality.insertArguments(findOwnMH_V, 2, objArr);
    }

    public final SwitchPoint[] getProtoSwitchPoints(String name, ScriptObject owner) {
        if (owner == this || getProto() == null) {
            return null;
        }
        List<SwitchPoint> switchPoints = new ArrayList<>();
        ScriptObject scriptObject = this;
        while (true) {
            ScriptObject obj = scriptObject;
            if (obj == owner || obj.getProto() == null) {
                break;
            }
            ScriptObject parent = obj.getProto();
            parent.getMap().addListener(name, obj.getMap());
            SwitchPoint sp = parent.getMap().getSharedProtoSwitchPoint();
            if (sp != null && !sp.hasBeenInvalidated()) {
                switchPoints.add(sp);
            }
            scriptObject = obj.getProto();
        }
        switchPoints.add(getMap().getSwitchPoint(name));
        return (SwitchPoint[]) switchPoints.toArray(new SwitchPoint[switchPoints.size()]);
    }

    private void checkSharedProtoMap() {
        if (getMap().isInvalidSharedMapFor(getProto())) {
            setMap(getMap().makeUnsharedCopy());
        }
    }

    public GuardedInvocation findSetMethod(CallSiteDescriptor desc, LinkRequest request) {
        GuardedInvocation cinv;
        String name = desc.getNameToken(2);
        if (request.isCallSiteUnstable() || hasWithScope()) {
            return findMegaMorphicSetMethod(desc, name);
        }
        boolean explicitInstanceOfCheck = NashornGuards.explicitInstanceOfCheck(desc, request);
        FindProperty find = findProperty(name, true, this);
        if (find != null && find.isInherited() && !(find.getProperty() instanceof UserAccessorProperty)) {
            if (isExtensible() && !find.getProperty().isWritable()) {
                return createEmptySetMethod(desc, explicitInstanceOfCheck, "property.not.writable", true);
            }
            if (!NashornCallSiteDescriptor.isScope(desc) || !find.getOwner().isScope()) {
                find = null;
            }
        }
        if (find != null) {
            if (!find.getProperty().isWritable() && !NashornCallSiteDescriptor.isDeclaration(desc)) {
                if (NashornCallSiteDescriptor.isScope(desc) && find.getProperty().isLexicalBinding()) {
                    throw ECMAErrors.typeError("assign.constant", name);
                }
                return createEmptySetMethod(desc, explicitInstanceOfCheck, "property.not.writable", true);
            }
        } else if (!isExtensible()) {
            return createEmptySetMethod(desc, explicitInstanceOfCheck, "object.non.extensible", false);
        }
        GuardedInvocation inv = new SetMethodCreator(this, find, desc, request).createGuardedInvocation(findBuiltinSwitchPoint(name));
        GlobalConstants globalConstants = getGlobalConstants();
        if (globalConstants != null && (cinv = globalConstants.findSetMethod(find, this, inv, desc, request)) != null) {
            return cinv;
        }
        return inv;
    }

    private GlobalConstants getGlobalConstants() {
        if (!isGlobal()) {
            return null;
        }
        return getContext().getGlobalConstants();
    }

    private GuardedInvocation createEmptySetMethod(CallSiteDescriptor desc, boolean explicitInstanceOfCheck, String strictErrorMessage, boolean canBeFastScope) {
        String name = desc.getNameToken(2);
        if (NashornCallSiteDescriptor.isStrict(desc)) {
            throw ECMAErrors.typeError(strictErrorMessage, name, ScriptRuntime.safeToString(this));
        }
        if (!$assertionsDisabled && !canBeFastScope && NashornCallSiteDescriptor.isFastScope(desc)) {
            throw new AssertionError();
        }
        return new GuardedInvocation(Lookup.EMPTY_SETTER, NashornGuards.getMapGuard(getMap(), explicitInstanceOfCheck), getProtoSwitchPoints(name, null), explicitInstanceOfCheck ? null : ClassCastException.class);
    }

    private boolean extensionCheck(boolean isStrict, String name) {
        if (isExtensible()) {
            return true;
        }
        if (isStrict) {
            throw ECMAErrors.typeError("object.non.extensible", name, ScriptRuntime.safeToString(this));
        }
        return false;
    }

    private GuardedInvocation findMegaMorphicSetMethod(CallSiteDescriptor desc, String name) {
        MethodType type = desc.getMethodType().insertParameterTypes(1, Object.class);
        GuardedInvocation inv = findSetIndexMethod(getClass(), desc, false, type);
        return inv.replaceMethods(Lookup.f248MH.insertArguments(inv.getInvocation(), 1, name), inv.getGuard());
    }

    private static Object globalFilter(Object object) {
        ScriptObject sobj;
        ScriptObject scriptObject = (ScriptObject) object;
        while (true) {
            sobj = scriptObject;
            if (sobj == null || (sobj instanceof Global)) {
                break;
            }
            scriptObject = sobj.getProto();
        }
        return sobj;
    }

    public GuardedInvocation findSetIndexMethod(CallSiteDescriptor desc, LinkRequest request) {
        return findSetIndexMethod(getClass(), desc, NashornGuards.explicitInstanceOfCheck(desc, request), desc.getMethodType());
    }

    private static GuardedInvocation findSetIndexMethod(Class<? extends ScriptObject> clazz, CallSiteDescriptor desc, boolean explicitInstanceOfCheck, MethodType callType) {
        if ($assertionsDisabled || callType.parameterCount() == 3) {
            Class<?> keyClass = callType.parameterType(1);
            Class<?> valueClass = callType.parameterType(2);
            MethodHandle methodHandle = findOwnMH_V(clazz, PropertyDescriptor.SET, Void.TYPE, keyClass, valueClass, Integer.TYPE);
            return new GuardedInvocation(Lookup.f248MH.insertArguments(methodHandle, 3, Integer.valueOf(NashornCallSiteDescriptor.getFlags(desc))), getScriptObjectGuard(callType, explicitInstanceOfCheck), (SwitchPoint) null, explicitInstanceOfCheck ? null : ClassCastException.class);
        }
        throw new AssertionError();
    }

    public GuardedInvocation noSuchMethod(CallSiteDescriptor desc, LinkRequest request) {
        String name = desc.getNameToken(2);
        FindProperty find = findProperty(NO_SUCH_METHOD_NAME, true);
        boolean scopeCall = isScope() && NashornCallSiteDescriptor.isScope(desc);
        if (find == null) {
            return noSuchProperty(desc, request);
        }
        boolean explicitInstanceOfCheck = NashornGuards.explicitInstanceOfCheck(desc, request);
        Object value = find.getObjectValue();
        if (!(value instanceof ScriptFunction)) {
            return createEmptyGetter(desc, explicitInstanceOfCheck, name);
        }
        ScriptFunction func = (ScriptFunction) value;
        Object thiz = (!scopeCall || !func.isStrict()) ? this : ScriptRuntime.UNDEFINED;
        return new GuardedInvocation(Lookup.f248MH.dropArguments(Lookup.f248MH.constant(ScriptFunction.class, func.createBound(thiz, new Object[]{name})), 0, Object.class), NashornGuards.combineGuards(NashornGuards.getIdentityGuard(this), NashornGuards.getMapGuard(getMap(), true)));
    }

    public GuardedInvocation noSuchProperty(CallSiteDescriptor desc, LinkRequest request) {
        MethodHandle methodHandle;
        String name = desc.getNameToken(2);
        FindProperty find = findProperty(NO_SUCH_PROPERTY_NAME, true);
        boolean scopeAccess = isScope() && NashornCallSiteDescriptor.isScope(desc);
        if (find != null) {
            Object value = find.getObjectValue();
            ScriptFunction func = null;
            MethodHandle mh = null;
            if (value instanceof ScriptFunction) {
                func = (ScriptFunction) value;
                mh = getCallMethodHandle(func, desc.getMethodType(), name);
            }
            if (mh != null) {
                if (!$assertionsDisabled && func == null) {
                    throw new AssertionError();
                }
                if (scopeAccess && func.isStrict()) {
                    mh = bindTo(mh, ScriptRuntime.UNDEFINED);
                }
                MethodHandle methodHandle2 = mh;
                if (find.isSelf()) {
                    methodHandle = getKnownFunctionPropertyGuardSelf(getMap(), find.getGetter(Object.class, -1, request), func);
                } else {
                    methodHandle = getKnownFunctionPropertyGuardProto(getMap(), find.getGetter(Object.class, -1, request), find.getProtoChainLength(), func);
                }
                return new GuardedInvocation(methodHandle2, methodHandle, getProtoSwitchPoints(NO_SUCH_PROPERTY_NAME, find.getOwner()), (Class<? extends Throwable>) null);
            }
        }
        if (scopeAccess) {
            throw ECMAErrors.referenceError("not.defined", name);
        }
        return createEmptyGetter(desc, NashornGuards.explicitInstanceOfCheck(desc, request), name);
    }

    public Object invokeNoSuchProperty(String name, boolean isScope, int programPoint) {
        FindProperty find = findProperty(NO_SUCH_PROPERTY_NAME, true);
        Object func = find != null ? find.getObjectValue() : null;
        Object ret = ScriptRuntime.UNDEFINED;
        if (func instanceof ScriptFunction) {
            ScriptFunction sfunc = (ScriptFunction) func;
            Object self = (!isScope || !sfunc.isStrict()) ? this : ScriptRuntime.UNDEFINED;
            ret = ScriptRuntime.apply(sfunc, self, name);
        } else if (isScope) {
            throw ECMAErrors.referenceError("not.defined", name);
        }
        if (UnwarrantedOptimismException.isValid(programPoint)) {
            throw new UnwarrantedOptimismException(ret, programPoint);
        }
        return ret;
    }

    private Object getNoSuchMethod(String name, boolean isScope, int programPoint) {
        FindProperty find = findProperty(NO_SUCH_METHOD_NAME, true);
        if (find == null) {
            return invokeNoSuchProperty(name, isScope, programPoint);
        }
        Object value = find.getObjectValue();
        if (!(value instanceof ScriptFunction)) {
            if (isScope) {
                throw ECMAErrors.referenceError("not.defined", name);
            }
            return ScriptRuntime.UNDEFINED;
        }
        ScriptFunction func = (ScriptFunction) value;
        Object self = (!isScope || !func.isStrict()) ? this : ScriptRuntime.UNDEFINED;
        return func.createBound(self, new Object[]{name});
    }

    private GuardedInvocation createEmptyGetter(CallSiteDescriptor desc, boolean explicitInstanceOfCheck, String name) {
        if (NashornCallSiteDescriptor.isOptimistic(desc)) {
            throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, NashornCallSiteDescriptor.getProgramPoint(desc), Type.OBJECT);
        }
        return new GuardedInvocation(Lookup.emptyGetter(desc.getMethodType().returnType()), NashornGuards.getMapGuard(getMap(), explicitInstanceOfCheck), getProtoSwitchPoints(name, null), explicitInstanceOfCheck ? null : ClassCastException.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/ScriptObject$ScriptObjectIterator.class */
    public static abstract class ScriptObjectIterator<T> implements Iterator<T> {
        protected T[] values;
        protected final ScriptObject object;
        private int index;

        protected abstract void init();

        ScriptObjectIterator(ScriptObject object) {
            this.object = object;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.values == null) {
                init();
            }
            return this.index < this.values.length;
        }

        @Override // java.util.Iterator
        public T next() {
            if (this.values == null) {
                init();
            }
            T[] tArr = this.values;
            int i = this.index;
            this.index = i + 1;
            return tArr[i];
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/ScriptObject$KeyIterator.class */
    public static class KeyIterator extends ScriptObjectIterator<String> {
        KeyIterator(ScriptObject object) {
            super(object);
        }

        /* JADX WARN: Type inference failed for: r1v3, types: [T[], java.lang.Object[]] */
        @Override // jdk.nashorn.internal.runtime.ScriptObject.ScriptObjectIterator
        protected void init() {
            Set<String> keys = new LinkedHashSet<>();
            Set<String> nonEnumerable = new HashSet<>();
            ScriptObject scriptObject = this.object;
            while (true) {
                ScriptObject self = scriptObject;
                if (self != null) {
                    keys.addAll(Arrays.asList(self.getOwnKeys(false, nonEnumerable)));
                    scriptObject = self.getProto();
                } else {
                    this.values = keys.toArray(new String[keys.size()]);
                    return;
                }
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/ScriptObject$ValueIterator.class */
    public static class ValueIterator extends ScriptObjectIterator<Object> {
        ValueIterator(ScriptObject object) {
            super(object);
        }

        /* JADX WARN: Type inference failed for: r1v3, types: [T[], java.lang.Object[]] */
        @Override // jdk.nashorn.internal.runtime.ScriptObject.ScriptObjectIterator
        protected void init() {
            String[] ownKeys;
            ArrayList<Object> valueList = new ArrayList<>();
            Set<String> nonEnumerable = new HashSet<>();
            ScriptObject scriptObject = this.object;
            while (true) {
                ScriptObject self = scriptObject;
                if (self != null) {
                    for (String key : self.getOwnKeys(false, nonEnumerable)) {
                        valueList.add(self.get(key));
                    }
                    scriptObject = self.getProto();
                } else {
                    this.values = valueList.toArray(new Object[valueList.size()]);
                    return;
                }
            }
        }
    }

    private Property addSpillProperty(String key, int flags, Object value, boolean hasInitialValue) {
        Property property;
        PropertyMap propertyMap = getMap();
        int fieldSlot = propertyMap.getFreeFieldSlot();
        int propertyFlags = flags | (useDualFields() ? 2048 : 0);
        if (fieldSlot > -1) {
            Property property2 = hasInitialValue ? new AccessorProperty(key, propertyFlags, fieldSlot, this, value) : new AccessorProperty(key, propertyFlags, getClass(), fieldSlot);
            property = addOwnProperty(property2);
        } else {
            int spillSlot = propertyMap.getFreeSpillSlot();
            Property property3 = hasInitialValue ? new SpillProperty(key, propertyFlags, spillSlot, this, value) : new SpillProperty(key, propertyFlags, spillSlot);
            property = addOwnProperty(property3);
            ensureSpillSize(property.getSlot());
        }
        return property;
    }

    public MethodHandle addSpill(Class<?> type, String key) {
        return addSpillProperty(key, 0, null, false).getSetter(type, getMap());
    }

    public static MethodHandle pairArguments(MethodHandle methodHandle, MethodType callType) {
        return pairArguments(methodHandle, callType, null);
    }

    public static MethodHandle pairArguments(MethodHandle methodHandle, MethodType callType, Boolean callerVarArg) {
        boolean z;
        MethodType methodType = methodHandle.type();
        if (methodType.equals(callType.changeReturnType(methodType.returnType()))) {
            return methodHandle;
        }
        int parameterCount = methodType.parameterCount();
        int callCount = callType.parameterCount();
        boolean isCalleeVarArg = parameterCount > 0 && methodType.parameterType(parameterCount - 1).isArray();
        if (callerVarArg != null) {
            z = callerVarArg.booleanValue();
        } else {
            z = callCount > 0 && callType.parameterType(callCount - 1).isArray();
        }
        boolean isCallerVarArg = z;
        if (isCalleeVarArg) {
            return isCallerVarArg ? methodHandle : Lookup.f248MH.asCollector(methodHandle, Object[].class, (callCount - parameterCount) + 1);
        } else if (isCallerVarArg) {
            return adaptHandleToVarArgCallSite(methodHandle, callCount);
        } else {
            if (callCount < parameterCount) {
                int missingArgs = parameterCount - callCount;
                Object[] fillers = new Object[missingArgs];
                Arrays.fill(fillers, ScriptRuntime.UNDEFINED);
                if (isCalleeVarArg) {
                    fillers[missingArgs - 1] = ScriptRuntime.EMPTY_ARRAY;
                }
                return Lookup.f248MH.insertArguments(methodHandle, parameterCount - missingArgs, fillers);
            } else if (callCount > parameterCount) {
                int discardedArgs = callCount - parameterCount;
                Class<?>[] discards = new Class[discardedArgs];
                Arrays.fill(discards, Object.class);
                return Lookup.f248MH.dropArguments(methodHandle, callCount - discardedArgs, discards);
            } else {
                return methodHandle;
            }
        }
    }

    public static MethodHandle adaptHandleToVarArgCallSite(MethodHandle mh, int callSiteParamCount) {
        int spreadArgs = (mh.type().parameterCount() - callSiteParamCount) + 1;
        return Lookup.f248MH.filterArguments(Lookup.f248MH.asSpreader(mh, Object[].class, spreadArgs), callSiteParamCount - 1, Lookup.f248MH.insertArguments(TRUNCATINGFILTER, 0, Integer.valueOf(spreadArgs)));
    }

    private static Object[] truncatingFilter(int n, Object[] array) {
        int length = array == null ? 0 : array.length;
        if (n == length) {
            return array == null ? ScriptRuntime.EMPTY_ARRAY : array;
        }
        Object[] newArray = new Object[n];
        if (array != null) {
            System.arraycopy(array, 0, newArray, 0, Math.min(n, length));
        }
        if (length < n) {
            Object fill = ScriptRuntime.UNDEFINED;
            for (int i = length; i < n; i++) {
                newArray[i] = fill;
            }
        }
        return newArray;
    }

    public final void setLength(long newLength) {
        ArrayData data = getArray();
        long arrayLength = data.length();
        if (newLength == arrayLength) {
            return;
        }
        if (newLength > arrayLength) {
            setArray(data.ensure(newLength - 1).safeDelete(arrayLength, newLength - 1, false));
        } else if (newLength < arrayLength) {
            long actualLength = newLength;
            if (getMap().containsArrayKeys()) {
                long j = arrayLength;
                while (true) {
                    long l = j - 1;
                    if (l < newLength) {
                        break;
                    }
                    FindProperty find = findProperty(JSType.toString(l), false);
                    if (find != null) {
                        if (find.getProperty().isConfigurable()) {
                            deleteOwnProperty(find.getProperty());
                        } else {
                            actualLength = l + 1;
                            break;
                        }
                    }
                    j = l;
                }
            }
            setArray(data.shrink(actualLength));
            data.setLength(actualLength);
        }
    }

    private int getInt(int index, String key, int programPoint) {
        ArrayData array;
        FindProperty find;
        if (ArrayIndex.isValidArrayIndex(index)) {
            ScriptObject object = this;
            do {
                if (object.getMap().containsArrayKeys() && (find = object.findProperty(key, false, this)) != null) {
                    return getIntValue(find, programPoint);
                }
                ScriptObject proto = object.getProto();
                object = proto;
                if (proto != null) {
                    array = object.getArray();
                }
            } while (!array.has(index));
            if (UnwarrantedOptimismException.isValid(programPoint)) {
                return array.getIntOptimistic(index, programPoint);
            }
            return array.getInt(index);
        }
        FindProperty find2 = findProperty(key, true);
        if (find2 != null) {
            return getIntValue(find2, programPoint);
        }
        return JSType.toInt32(invokeNoSuchProperty(key, false, programPoint));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(Object key, int programPoint) {
        Object primitiveKey = JSType.toPrimitive(key, String.class);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        ArrayData array = getArray();
        if (array.has(index)) {
            return UnwarrantedOptimismException.isValid(programPoint) ? array.getIntOptimistic(index, programPoint) : array.getInt(index);
        }
        return getInt(index, JSType.toString(primitiveKey), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(double key, int programPoint) {
        int index = ArrayIndex.getArrayIndex(key);
        ArrayData array = getArray();
        if (array.has(index)) {
            return UnwarrantedOptimismException.isValid(programPoint) ? array.getIntOptimistic(index, programPoint) : array.getInt(index);
        }
        return getInt(index, JSType.toString(key), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(int key, int programPoint) {
        int index = ArrayIndex.getArrayIndex(key);
        ArrayData array = getArray();
        if (array.has(index)) {
            return UnwarrantedOptimismException.isValid(programPoint) ? array.getIntOptimistic(key, programPoint) : array.getInt(key);
        }
        return getInt(index, JSType.toString(key), programPoint);
    }

    private double getDouble(int index, String key, int programPoint) {
        ArrayData array;
        FindProperty find;
        if (ArrayIndex.isValidArrayIndex(index)) {
            ScriptObject object = this;
            do {
                if (object.getMap().containsArrayKeys() && (find = object.findProperty(key, false, this)) != null) {
                    return getDoubleValue(find, programPoint);
                }
                ScriptObject proto = object.getProto();
                object = proto;
                if (proto != null) {
                    array = object.getArray();
                }
            } while (!array.has(index));
            if (UnwarrantedOptimismException.isValid(programPoint)) {
                return array.getDoubleOptimistic(index, programPoint);
            }
            return array.getDouble(index);
        }
        FindProperty find2 = findProperty(key, true);
        if (find2 != null) {
            return getDoubleValue(find2, programPoint);
        }
        return JSType.toNumber(invokeNoSuchProperty(key, false, -1));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(Object key, int programPoint) {
        Object primitiveKey = JSType.toPrimitive(key, String.class);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        ArrayData array = getArray();
        if (array.has(index)) {
            return UnwarrantedOptimismException.isValid(programPoint) ? array.getDoubleOptimistic(index, programPoint) : array.getDouble(index);
        }
        return getDouble(index, JSType.toString(primitiveKey), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(double key, int programPoint) {
        int index = ArrayIndex.getArrayIndex(key);
        ArrayData array = getArray();
        if (array.has(index)) {
            return UnwarrantedOptimismException.isValid(programPoint) ? array.getDoubleOptimistic(index, programPoint) : array.getDouble(index);
        }
        return getDouble(index, JSType.toString(key), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(int key, int programPoint) {
        int index = ArrayIndex.getArrayIndex(key);
        ArrayData array = getArray();
        if (array.has(index)) {
            return UnwarrantedOptimismException.isValid(programPoint) ? array.getDoubleOptimistic(key, programPoint) : array.getDouble(key);
        }
        return getDouble(index, JSType.toString(key), programPoint);
    }

    private Object get(int index, String key) {
        ArrayData array;
        FindProperty find;
        if (ArrayIndex.isValidArrayIndex(index)) {
            ScriptObject object = this;
            do {
                if (object.getMap().containsArrayKeys() && (find = object.findProperty(key, false, this)) != null) {
                    return find.getObjectValue();
                }
                ScriptObject proto = object.getProto();
                object = proto;
                if (proto != null) {
                    array = object.getArray();
                }
            } while (!array.has(index));
            return array.getObject(index);
        }
        FindProperty find2 = findProperty(key, true);
        if (find2 != null) {
            return find2.getObjectValue();
        }
        return invokeNoSuchProperty(key, false, -1);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(Object key) {
        Object primitiveKey = JSType.toPrimitive(key, String.class);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        ArrayData array = getArray();
        if (array.has(index)) {
            return array.getObject(index);
        }
        return get(index, JSType.toString(primitiveKey));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(double key) {
        int index = ArrayIndex.getArrayIndex(key);
        ArrayData array = getArray();
        if (array.has(index)) {
            return array.getObject(index);
        }
        return get(index, JSType.toString(key));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(int key) {
        int index = ArrayIndex.getArrayIndex(key);
        ArrayData array = getArray();
        if (array.has(index)) {
            return array.getObject(index);
        }
        return get(index, JSType.toString(key));
    }

    private boolean doesNotHaveCheckArrayKeys(long longIndex, int value, int callSiteFlags) {
        String key;
        FindProperty find;
        if (getMap().containsArrayKeys() && (find = findProperty((key = JSType.toString(longIndex)), true)) != null) {
            setObject(find, callSiteFlags, key, Integer.valueOf(value));
            return true;
        }
        return false;
    }

    private boolean doesNotHaveCheckArrayKeys(long longIndex, long value, int callSiteFlags) {
        String key;
        FindProperty find;
        if (getMap().containsArrayKeys() && (find = findProperty((key = JSType.toString(longIndex)), true)) != null) {
            setObject(find, callSiteFlags, key, Long.valueOf(value));
            return true;
        }
        return false;
    }

    private boolean doesNotHaveCheckArrayKeys(long longIndex, double value, int callSiteFlags) {
        String key;
        FindProperty find;
        if (getMap().containsArrayKeys() && (find = findProperty((key = JSType.toString(longIndex)), true)) != null) {
            setObject(find, callSiteFlags, key, Double.valueOf(value));
            return true;
        }
        return false;
    }

    private boolean doesNotHaveCheckArrayKeys(long longIndex, Object value, int callSiteFlags) {
        String key;
        FindProperty find;
        if (getMap().containsArrayKeys() && (find = findProperty((key = JSType.toString(longIndex)), true)) != null) {
            setObject(find, callSiteFlags, key, value);
            return true;
        }
        return false;
    }

    private boolean doesNotHaveEnsureLength(long longIndex, long oldLength, int callSiteFlags) {
        if (longIndex >= oldLength) {
            if (!isExtensible()) {
                if (NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)) {
                    throw ECMAErrors.typeError("object.non.extensible", JSType.toString(longIndex), ScriptRuntime.safeToString(this));
                }
                return true;
            }
            setArray(getArray().ensure(longIndex));
            return false;
        }
        return false;
    }

    private void doesNotHave(int index, int value, int callSiteFlags) {
        long oldLength = getArray().length();
        long longIndex = ArrayIndex.toLongIndex(index);
        if (!doesNotHaveCheckArrayKeys(longIndex, value, callSiteFlags) && !doesNotHaveEnsureLength(longIndex, oldLength, callSiteFlags)) {
            boolean strict = NashornCallSiteDescriptor.isStrictFlag(callSiteFlags);
            setArray(getArray().set(index, value, strict).safeDelete(oldLength, longIndex - 1, strict));
        }
    }

    private void doesNotHave(int index, double value, int callSiteFlags) {
        long oldLength = getArray().length();
        long longIndex = ArrayIndex.toLongIndex(index);
        if (!doesNotHaveCheckArrayKeys(longIndex, value, callSiteFlags) && !doesNotHaveEnsureLength(longIndex, oldLength, callSiteFlags)) {
            boolean strict = NashornCallSiteDescriptor.isStrictFlag(callSiteFlags);
            setArray(getArray().set(index, value, strict).safeDelete(oldLength, longIndex - 1, strict));
        }
    }

    private void doesNotHave(int index, Object value, int callSiteFlags) {
        long oldLength = getArray().length();
        long longIndex = ArrayIndex.toLongIndex(index);
        if (!doesNotHaveCheckArrayKeys(longIndex, value, callSiteFlags) && !doesNotHaveEnsureLength(longIndex, oldLength, callSiteFlags)) {
            boolean strict = NashornCallSiteDescriptor.isStrictFlag(callSiteFlags);
            setArray(getArray().set(index, value, strict).safeDelete(oldLength, longIndex - 1, strict));
        }
    }

    public final void setObject(FindProperty find, int callSiteFlags, String key, Object value) {
        FindProperty f = find;
        invalidateGlobalConstant(key);
        if (f != null && f.isInherited() && !(f.getProperty() instanceof UserAccessorProperty)) {
            boolean isScope = NashornCallSiteDescriptor.isScopeFlag(callSiteFlags);
            if (isScope && f.getSelf() != this) {
                f.getSelf().setObject(null, 0, key, value);
                return;
            } else if (!isScope || !f.getOwner().isScope()) {
                f = null;
            }
        }
        if (f != null) {
            if (!f.getProperty().isWritable()) {
                if (NashornCallSiteDescriptor.isScopeFlag(callSiteFlags) && f.getProperty().isLexicalBinding()) {
                    throw ECMAErrors.typeError("assign.constant", key);
                }
                if (NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)) {
                    throw ECMAErrors.typeError("property.not.writable", key, ScriptRuntime.safeToString(this));
                }
                return;
            }
            f.setValue(value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags));
        } else if (!isExtensible()) {
            if (NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)) {
                throw ECMAErrors.typeError("object.non.extensible", key, ScriptRuntime.safeToString(this));
            }
        } else {
            ScriptObject sobj = this;
            if (isScope()) {
                while (sobj != null && !(sobj instanceof Global)) {
                    sobj = sobj.getProto();
                }
                if (!$assertionsDisabled && sobj == null) {
                    throw new AssertionError("no parent global object in scope");
                }
            }
            sobj.addSpillProperty(key, 0, value, true);
        }
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object key, int value, int callSiteFlags) {
        Object primitiveKey = JSType.toPrimitive(key, String.class);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        if (ArrayIndex.isValidArrayIndex(index)) {
            ArrayData data = getArray();
            if (data.has(index)) {
                setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
                return;
            } else {
                doesNotHave(index, value, callSiteFlags);
                return;
            }
        }
        String propName = JSType.toString(primitiveKey);
        setObject(findProperty(propName, true), callSiteFlags, propName, JSType.toObject(value));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object key, double value, int callSiteFlags) {
        Object primitiveKey = JSType.toPrimitive(key, String.class);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        if (ArrayIndex.isValidArrayIndex(index)) {
            ArrayData data = getArray();
            if (data.has(index)) {
                setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
                return;
            } else {
                doesNotHave(index, value, callSiteFlags);
                return;
            }
        }
        String propName = JSType.toString(primitiveKey);
        setObject(findProperty(propName, true), callSiteFlags, propName, JSType.toObject(value));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object key, Object value, int callSiteFlags) {
        Object primitiveKey = JSType.toPrimitive(key, String.class);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        if (ArrayIndex.isValidArrayIndex(index)) {
            ArrayData data = getArray();
            if (data.has(index)) {
                setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
                return;
            } else {
                doesNotHave(index, value, callSiteFlags);
                return;
            }
        }
        String propName = JSType.toString(primitiveKey);
        setObject(findProperty(propName, true), callSiteFlags, propName, value);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double key, int value, int callSiteFlags) {
        int index = ArrayIndex.getArrayIndex(key);
        if (ArrayIndex.isValidArrayIndex(index)) {
            ArrayData data = getArray();
            if (data.has(index)) {
                setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
                return;
            } else {
                doesNotHave(index, value, callSiteFlags);
                return;
            }
        }
        String propName = JSType.toString(key);
        setObject(findProperty(propName, true), callSiteFlags, propName, JSType.toObject(value));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double key, double value, int callSiteFlags) {
        int index = ArrayIndex.getArrayIndex(key);
        if (ArrayIndex.isValidArrayIndex(index)) {
            ArrayData data = getArray();
            if (data.has(index)) {
                setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
                return;
            } else {
                doesNotHave(index, value, callSiteFlags);
                return;
            }
        }
        String propName = JSType.toString(key);
        setObject(findProperty(propName, true), callSiteFlags, propName, JSType.toObject(value));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double key, Object value, int callSiteFlags) {
        int index = ArrayIndex.getArrayIndex(key);
        if (ArrayIndex.isValidArrayIndex(index)) {
            ArrayData data = getArray();
            if (data.has(index)) {
                setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
                return;
            } else {
                doesNotHave(index, value, callSiteFlags);
                return;
            }
        }
        String propName = JSType.toString(key);
        setObject(findProperty(propName, true), callSiteFlags, propName, value);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int key, int value, int callSiteFlags) {
        int index = ArrayIndex.getArrayIndex(key);
        if (ArrayIndex.isValidArrayIndex(index)) {
            if (getArray().has(index)) {
                ArrayData data = getArray();
                setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
                return;
            }
            doesNotHave(index, value, callSiteFlags);
            return;
        }
        String propName = JSType.toString(key);
        setObject(findProperty(propName, true), callSiteFlags, propName, JSType.toObject(value));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int key, double value, int callSiteFlags) {
        int index = ArrayIndex.getArrayIndex(key);
        if (ArrayIndex.isValidArrayIndex(index)) {
            ArrayData data = getArray();
            if (data.has(index)) {
                setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
                return;
            } else {
                doesNotHave(index, value, callSiteFlags);
                return;
            }
        }
        String propName = JSType.toString(key);
        setObject(findProperty(propName, true), callSiteFlags, propName, JSType.toObject(value));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int key, Object value, int callSiteFlags) {
        int index = ArrayIndex.getArrayIndex(key);
        if (ArrayIndex.isValidArrayIndex(index)) {
            ArrayData data = getArray();
            if (data.has(index)) {
                setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
                return;
            } else {
                doesNotHave(index, value, callSiteFlags);
                return;
            }
        }
        String propName = JSType.toString(key);
        setObject(findProperty(propName, true), callSiteFlags, propName, value);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(Object key) {
        Object primitiveKey = JSType.toPrimitive(key);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        return ArrayIndex.isValidArrayIndex(index) ? hasArrayProperty(index) : hasProperty(JSType.toString(primitiveKey), true);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(double key) {
        int index = ArrayIndex.getArrayIndex(key);
        return ArrayIndex.isValidArrayIndex(index) ? hasArrayProperty(index) : hasProperty(JSType.toString(key), true);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(int key) {
        int index = ArrayIndex.getArrayIndex(key);
        return ArrayIndex.isValidArrayIndex(index) ? hasArrayProperty(index) : hasProperty(JSType.toString(key), true);
    }

    private boolean hasArrayProperty(int index) {
        boolean hasArrayKeys = false;
        ScriptObject scriptObject = this;
        while (true) {
            ScriptObject self = scriptObject;
            if (self == null) {
                return hasArrayKeys && hasProperty(ArrayIndex.toKey(index), true);
            } else if (self.getArray().has(index)) {
                return true;
            } else {
                hasArrayKeys = hasArrayKeys || self.getMap().containsArrayKeys();
                scriptObject = self.getProto();
            }
        }
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean hasOwnProperty(Object key) {
        Object primitiveKey = JSType.toPrimitive(key, String.class);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        return ArrayIndex.isValidArrayIndex(index) ? hasOwnArrayProperty(index) : hasProperty(JSType.toString(primitiveKey), false);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean hasOwnProperty(int key) {
        int index = ArrayIndex.getArrayIndex(key);
        return ArrayIndex.isValidArrayIndex(index) ? hasOwnArrayProperty(index) : hasProperty(JSType.toString(key), false);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean hasOwnProperty(double key) {
        int index = ArrayIndex.getArrayIndex(key);
        return ArrayIndex.isValidArrayIndex(index) ? hasOwnArrayProperty(index) : hasProperty(JSType.toString(key), false);
    }

    private boolean hasOwnArrayProperty(int index) {
        return getArray().has(index) || (getMap().containsArrayKeys() && hasProperty(ArrayIndex.toKey(index), false));
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(int key, boolean strict) {
        int index = ArrayIndex.getArrayIndex(key);
        ArrayData array = getArray();
        if (array.has(index)) {
            if (array.canDelete(index, strict)) {
                setArray(array.delete(index));
                return true;
            }
            return false;
        }
        return deleteObject(JSType.toObject(key), strict);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(double key, boolean strict) {
        int index = ArrayIndex.getArrayIndex(key);
        ArrayData array = getArray();
        if (array.has(index)) {
            if (array.canDelete(index, strict)) {
                setArray(array.delete(index));
                return true;
            }
            return false;
        }
        return deleteObject(JSType.toObject(key), strict);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(Object key, boolean strict) {
        Object primitiveKey = JSType.toPrimitive(key, String.class);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        ArrayData array = getArray();
        if (array.has(index)) {
            if (array.canDelete(index, strict)) {
                setArray(array.delete(index));
                return true;
            }
            return false;
        }
        return deleteObject(primitiveKey, strict);
    }

    private boolean deleteObject(Object key, boolean strict) {
        String propName = JSType.toString(key);
        FindProperty find = findProperty(propName, false);
        if (find == null) {
            return true;
        }
        if (!find.getProperty().isConfigurable()) {
            if (strict) {
                throw ECMAErrors.typeError("cant.delete.property", propName, ScriptRuntime.safeToString(this));
            }
            return false;
        }
        Property prop = find.getProperty();
        deleteOwnProperty(prop);
        return true;
    }

    public final ScriptObject copy() {
        try {
            return clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public ScriptObject clone() throws CloneNotSupportedException {
        ScriptObject clone = (ScriptObject) super.clone();
        if (this.objectSpill != null) {
            clone.objectSpill = (Object[]) this.objectSpill.clone();
            if (this.primitiveSpill != null) {
                clone.primitiveSpill = (long[]) this.primitiveSpill.clone();
            }
        }
        clone.arrayData = this.arrayData.copy();
        return clone;
    }

    protected final UserAccessorProperty newUserAccessors(String key, int propertyFlags, ScriptFunction getter, ScriptFunction setter) {
        UserAccessorProperty uc = getMap().newUserAccessors(key, propertyFlags);
        uc.setAccessors(this, getMap(), new UserAccessorProperty.Accessors(getter, setter));
        return uc;
    }

    public boolean useDualFields() {
        return !StructureLoader.isSingleFieldStructure(getClass().getName());
    }

    Object ensureSpillSize(int slot) {
        int oldLength = this.objectSpill == null ? 0 : this.objectSpill.length;
        if (slot < oldLength) {
            return this;
        }
        int newLength = alignUp(slot + 1, 8);
        Object[] newObjectSpill = new Object[newLength];
        long[] newPrimitiveSpill = useDualFields() ? new long[newLength] : null;
        if (this.objectSpill != null) {
            System.arraycopy(this.objectSpill, 0, newObjectSpill, 0, oldLength);
            if (this.primitiveSpill != null && newPrimitiveSpill != null) {
                System.arraycopy(this.primitiveSpill, 0, newPrimitiveSpill, 0, oldLength);
            }
        }
        this.primitiveSpill = newPrimitiveSpill;
        this.objectSpill = newObjectSpill;
        return this;
    }

    private static MethodHandle findOwnMH_V(Class<? extends ScriptObject> clazz, String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findVirtual(MethodHandles.lookup(), ScriptObject.class, name, Lookup.f248MH.type(rtype, types));
    }

    private static MethodHandle findOwnMH_V(String name, Class<?> rtype, Class<?>... types) {
        return findOwnMH_V(ScriptObject.class, name, rtype, types);
    }

    private static MethodHandle findOwnMH_S(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), ScriptObject.class, name, Lookup.f248MH.type(rtype, types));
    }

    private static MethodHandle getKnownFunctionPropertyGuardSelf(PropertyMap map, MethodHandle getter, ScriptFunction func) {
        return Lookup.f248MH.insertArguments(KNOWNFUNCPROPGUARDSELF, 1, map, getter, func);
    }

    private static boolean knownFunctionPropertyGuardSelf(Object self, PropertyMap map, MethodHandle getter, ScriptFunction func) {
        if ((self instanceof ScriptObject) && ((ScriptObject) self).getMap() == map) {
            try {
                return getter.invokeExact(self) == func;
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
        return false;
    }

    private static MethodHandle getKnownFunctionPropertyGuardProto(PropertyMap map, MethodHandle getter, int depth, ScriptFunction func) {
        return Lookup.f248MH.insertArguments(KNOWNFUNCPROPGUARDPROTO, 1, map, getter, Integer.valueOf(depth), func);
    }

    private static ScriptObject getProto(ScriptObject self, int depth) {
        ScriptObject proto = self;
        for (int d = 0; d < depth; d++) {
            proto = proto.getProto();
            if (proto == null) {
                return null;
            }
        }
        return proto;
    }

    private static boolean knownFunctionPropertyGuardProto(Object self, PropertyMap map, MethodHandle getter, int depth, ScriptFunction func) {
        ScriptObject proto;
        if (!(self instanceof ScriptObject) || ((ScriptObject) self).getMap() != map || (proto = getProto((ScriptObject) self, depth)) == null) {
            return false;
        }
        try {
            return getter.invokeExact(proto) == func;
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static long getCount() {
        return count.longValue();
    }
}
