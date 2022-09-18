package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.ref.WeakReference;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.options.Options;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/NashornGuards.class */
public final class NashornGuards {
    private static final MethodHandle IS_MAP = findOwnMH("isMap", Boolean.TYPE, ScriptObject.class, PropertyMap.class);
    private static final MethodHandle IS_MAP_SCRIPTOBJECT = findOwnMH("isMap", Boolean.TYPE, Object.class, PropertyMap.class);
    private static final MethodHandle IS_SCRIPTOBJECT = findOwnMH("isScriptObject", Boolean.TYPE, Object.class);
    private static final MethodHandle IS_NOT_JSOBJECT = findOwnMH("isNotJSObject", Boolean.TYPE, Object.class);
    private static final MethodHandle SAME_OBJECT = findOwnMH("sameObject", Boolean.TYPE, Object.class, WeakReference.class);
    private static final boolean CCE_ONLY = Options.getBooleanProperty("nashorn.cce");

    private NashornGuards() {
    }

    public static boolean explicitInstanceOfCheck(CallSiteDescriptor desc, LinkRequest request) {
        return !CCE_ONLY;
    }

    public static MethodHandle getScriptObjectGuard() {
        return IS_SCRIPTOBJECT;
    }

    public static MethodHandle getNotJSObjectGuard() {
        return IS_NOT_JSOBJECT;
    }

    public static MethodHandle getScriptObjectGuard(boolean explicitInstanceOfCheck) {
        if (explicitInstanceOfCheck) {
            return IS_SCRIPTOBJECT;
        }
        return null;
    }

    public static MethodHandle getMapGuard(PropertyMap map, boolean explicitInstanceOfCheck) {
        return Lookup.f248MH.insertArguments(explicitInstanceOfCheck ? IS_MAP_SCRIPTOBJECT : IS_MAP, 1, map);
    }

    static boolean needsGuard(Property property, CallSiteDescriptor desc) {
        return property == null || property.isConfigurable() || property.isBound() || property.hasDualFields() || !NashornCallSiteDescriptor.isFastScope(desc) || property.canChangeType();
    }

    public static MethodHandle getGuard(ScriptObject sobj, Property property, CallSiteDescriptor desc, boolean explicitInstanceOfCheck) {
        if (!needsGuard(property, desc)) {
            return null;
        }
        if (NashornCallSiteDescriptor.isScope(desc)) {
            if (property != null && property.isBound() && !property.canChangeType()) {
                return getIdentityGuard(sobj);
            }
            if (!(sobj instanceof Global) && (property == null || property.isConfigurable())) {
                return combineGuards(getIdentityGuard(sobj), getMapGuard(sobj.getMap(), explicitInstanceOfCheck));
            }
        }
        return getMapGuard(sobj.getMap(), explicitInstanceOfCheck);
    }

    public static MethodHandle getIdentityGuard(ScriptObject sobj) {
        return Lookup.f248MH.insertArguments(SAME_OBJECT, 1, new WeakReference(sobj));
    }

    public static MethodHandle getStringGuard() {
        return JSType.IS_STRING.methodHandle();
    }

    public static MethodHandle getNumberGuard() {
        return JSType.IS_NUMBER.methodHandle();
    }

    public static MethodHandle combineGuards(MethodHandle guard1, MethodHandle guard2) {
        if (guard1 == null) {
            return guard2;
        }
        return guard2 == null ? guard1 : Lookup.f248MH.guardWithTest(guard1, guard2, Lookup.f248MH.dropArguments(Lookup.f248MH.constant(Boolean.TYPE, false), 0, Object.class));
    }

    private static boolean isScriptObject(Object self) {
        return self instanceof ScriptObject;
    }

    private static boolean isScriptObject(Class<? extends ScriptObject> clazz, Object self) {
        return clazz.isInstance(self);
    }

    private static boolean isMap(ScriptObject self, PropertyMap map) {
        return self.getMap() == map;
    }

    private static boolean isNotJSObject(Object self) {
        return !(self instanceof JSObject);
    }

    private static boolean isMap(Object self, PropertyMap map) {
        return (self instanceof ScriptObject) && ((ScriptObject) self).getMap() == map;
    }

    private static boolean sameObject(Object self, WeakReference<ScriptObject> ref) {
        return self == ref.get();
    }

    private static boolean isScriptFunction(Object self) {
        return self instanceof ScriptFunction;
    }

    private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), NashornGuards.class, name, Lookup.f248MH.type(rtype, types));
    }
}
