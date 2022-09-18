package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyDescriptor;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/BrowserJSObjectLinker.class */
public final class BrowserJSObjectLinker implements TypeBasedGuardingDynamicLinker {
    private static final ClassLoader myLoader;
    private static final String JSOBJECT_CLASS = "netscape.javascript.JSObject";
    private static volatile Class<?> jsObjectClass;
    private final NashornBeansLinker nashornBeansLinker;

    /* renamed from: MH */
    private static final MethodHandleFunctionality f277MH;
    private static final MethodHandle IS_JSOBJECT_GUARD;
    private static final MethodHandle JSOBJECTLINKER_GET;
    private static final MethodHandle JSOBJECTLINKER_PUT;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !BrowserJSObjectLinker.class.desiredAssertionStatus();
        myLoader = BrowserJSObjectLinker.class.getClassLoader();
        f277MH = MethodHandleFactory.getFunctionality();
        IS_JSOBJECT_GUARD = findOwnMH_S("isJSObject", Boolean.TYPE, Object.class);
        JSOBJECTLINKER_GET = findOwnMH_S(PropertyDescriptor.GET, Object.class, MethodHandle.class, Object.class, Object.class);
        JSOBJECTLINKER_PUT = findOwnMH_S("put", Void.TYPE, Object.class, Object.class, Object.class);
    }

    public BrowserJSObjectLinker(NashornBeansLinker nashornBeansLinker) {
        this.nashornBeansLinker = nashornBeansLinker;
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class<?> type) {
        return canLinkTypeStatic(type);
    }

    static boolean canLinkTypeStatic(Class<?> type) {
        if (jsObjectClass != null && jsObjectClass.isAssignableFrom(type)) {
            return true;
        }
        Class<?> cls = type;
        while (true) {
            Class<?> clazz = cls;
            if (clazz != null) {
                if (clazz.getClassLoader() == myLoader && clazz.getName().equals(JSOBJECT_CLASS)) {
                    jsObjectClass = clazz;
                    return true;
                }
                cls = clazz.getSuperclass();
            } else {
                return false;
            }
        }
    }

    public static void checkJSObjectClass() {
        if ($assertionsDisabled || jsObjectClass != null) {
            return;
        }
        throw new AssertionError("netscape.javascript.JSObject not found!");
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest request, LinkerServices linkerServices) throws Exception {
        LinkRequest requestWithoutContext = request.withoutRuntimeContext();
        Object self = requestWithoutContext.getReceiver();
        CallSiteDescriptor desc = requestWithoutContext.getCallSiteDescriptor();
        checkJSObjectClass();
        if (desc.getNameTokenCount() < 2 || !"dyn".equals(desc.getNameToken(0))) {
            return null;
        }
        if (jsObjectClass.isInstance(self)) {
            GuardedInvocation inv = lookup(desc, request, linkerServices);
            return Bootstrap.asTypeSafeReturn(inv.replaceMethods(linkerServices.filterInternalObjects(inv.getInvocation()), inv.getGuard()), linkerServices, desc);
        }
        throw new AssertionError();
    }

    private GuardedInvocation lookup(CallSiteDescriptor desc, LinkRequest request, LinkerServices linkerServices) throws Exception {
        GuardedInvocation inv;
        String operator = CallSiteDescriptorFactory.tokenizeOperators(desc).get(0);
        int c = desc.getNameTokenCount();
        try {
            inv = this.nashornBeansLinker.getGuardedInvocation(request, linkerServices);
        } catch (Throwable th) {
            inv = null;
        }
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
                return c > 2 ? findGetMethod(desc, inv) : findGetIndexMethod(inv);
            case true:
            case true:
                return c > 2 ? findSetMethod(desc, inv) : findSetIndexMethod();
            case true:
                return findCallMethod(desc);
            default:
                return null;
        }
    }

    private static GuardedInvocation findGetMethod(CallSiteDescriptor desc, GuardedInvocation inv) {
        if (inv != null) {
            return inv;
        }
        String name = desc.getNameToken(2);
        MethodHandle getter = f277MH.insertArguments(JSObjectHandles.JSOBJECT_GETMEMBER, 1, name);
        return new GuardedInvocation(getter, IS_JSOBJECT_GUARD);
    }

    private static GuardedInvocation findGetIndexMethod(GuardedInvocation inv) {
        MethodHandle getter = f277MH.insertArguments(JSOBJECTLINKER_GET, 0, inv.getInvocation());
        return inv.replaceMethods(getter, inv.getGuard());
    }

    private static GuardedInvocation findSetMethod(CallSiteDescriptor desc, GuardedInvocation inv) {
        if (inv != null) {
            return inv;
        }
        MethodHandle getter = f277MH.insertArguments(JSObjectHandles.JSOBJECT_SETMEMBER, 1, desc.getNameToken(2));
        return new GuardedInvocation(getter, IS_JSOBJECT_GUARD);
    }

    private static GuardedInvocation findSetIndexMethod() {
        return new GuardedInvocation(JSOBJECTLINKER_PUT, IS_JSOBJECT_GUARD);
    }

    private static GuardedInvocation findCallMethod(CallSiteDescriptor desc) {
        MethodHandle call = f277MH.insertArguments(JSObjectHandles.JSOBJECT_CALL, 1, "call");
        return new GuardedInvocation(f277MH.asCollector(call, Object[].class, desc.getMethodType().parameterCount() - 1), IS_JSOBJECT_GUARD);
    }

    private static boolean isJSObject(Object self) {
        return jsObjectClass.isInstance(self);
    }

    private static Object get(MethodHandle fallback, Object jsobj, Object key) throws Throwable {
        if (key instanceof Integer) {
            return JSObjectHandles.JSOBJECT_GETSLOT.invokeExact(jsobj, ((Integer) key).intValue());
        }
        if (key instanceof Number) {
            int index = getIndex((Number) key);
            if (index > -1) {
                return JSObjectHandles.JSOBJECT_GETSLOT.invokeExact(jsobj, index);
            }
            return null;
        } else if (JSType.isString(key)) {
            String name = key.toString();
            if (name.indexOf(40) != -1) {
                return fallback.invokeExact(jsobj, name);
            }
            return JSObjectHandles.JSOBJECT_GETMEMBER.invokeExact(jsobj, name);
        } else {
            return null;
        }
    }

    private static void put(Object jsobj, Object key, Object value) throws Throwable {
        if (key instanceof Integer) {
            JSObjectHandles.JSOBJECT_SETSLOT.invokeExact(jsobj, ((Integer) key).intValue(), value);
        } else if (key instanceof Number) {
            JSObjectHandles.JSOBJECT_SETSLOT.invokeExact(jsobj, getIndex((Number) key), value);
        } else if (JSType.isString(key)) {
            JSObjectHandles.JSOBJECT_SETMEMBER.invokeExact(jsobj, key.toString(), value);
        }
    }

    private static int getIndex(Number n) {
        double value = n.doubleValue();
        if (JSType.isRepresentableAsInt(value)) {
            return (int) value;
        }
        return -1;
    }

    private static MethodHandle findOwnMH_S(String name, Class<?> rtype, Class<?>... types) {
        return f277MH.findStatic(MethodHandles.lookup(), BrowserJSObjectLinker.class, name, f277MH.type(rtype, types));
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/BrowserJSObjectLinker$JSObjectHandles.class */
    public static class JSObjectHandles {
        static final MethodHandle JSOBJECT_GETMEMBER = findJSObjectMH_V("getMember", Object.class, String.class).asType(BrowserJSObjectLinker.f277MH.type(Object.class, Object.class, String.class));
        static final MethodHandle JSOBJECT_GETSLOT = findJSObjectMH_V("getSlot", Object.class, Integer.TYPE).asType(BrowserJSObjectLinker.f277MH.type(Object.class, Object.class, Integer.TYPE));
        static final MethodHandle JSOBJECT_SETMEMBER = findJSObjectMH_V("setMember", Void.TYPE, String.class, Object.class).asType(BrowserJSObjectLinker.f277MH.type(Void.TYPE, Object.class, String.class, Object.class));
        static final MethodHandle JSOBJECT_SETSLOT = findJSObjectMH_V("setSlot", Void.TYPE, Integer.TYPE, Object.class).asType(BrowserJSObjectLinker.f277MH.type(Void.TYPE, Object.class, Integer.TYPE, Object.class));
        static final MethodHandle JSOBJECT_CALL = findJSObjectMH_V("call", Object.class, String.class, Object[].class).asType(BrowserJSObjectLinker.f277MH.type(Object.class, Object.class, String.class, Object[].class));

        JSObjectHandles() {
        }

        private static MethodHandle findJSObjectMH_V(String name, Class<?> rtype, Class<?>... types) {
            BrowserJSObjectLinker.checkJSObjectClass();
            return BrowserJSObjectLinker.f277MH.findVirtual(MethodHandles.publicLookup(), BrowserJSObjectLinker.jsObjectClass, name, BrowserJSObjectLinker.f277MH.type(rtype, types));
        }
    }
}
