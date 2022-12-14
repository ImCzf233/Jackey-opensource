package jdk.nashorn.internal.runtime.linker;

import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/ReflectionCheckLinker.class */
public final class ReflectionCheckLinker implements TypeBasedGuardingDynamicLinker {
    private static final Class<?> STATEMENT_CLASS = getBeanClass("Statement");
    private static final Class<?> XMLENCODER_CLASS = getBeanClass("XMLEncoder");
    private static final Class<?> XMLDECODER_CLASS = getBeanClass("XMLDecoder");

    private static Class<?> getBeanClass(String name) {
        try {
            return Class.forName("java.beans." + name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class<?> type) {
        return isReflectionClass(type);
    }

    private static boolean isReflectionClass(Class<?> type) {
        if (type == Class.class || ClassLoader.class.isAssignableFrom(type)) {
            return true;
        }
        if (STATEMENT_CLASS == null || !STATEMENT_CLASS.isAssignableFrom(type)) {
            if (XMLENCODER_CLASS == null || !XMLENCODER_CLASS.isAssignableFrom(type)) {
                if (XMLDECODER_CLASS != null && XMLDECODER_CLASS.isAssignableFrom(type)) {
                    return true;
                }
                String name = type.getName();
                return name.startsWith("java.lang.reflect.") || name.startsWith("java.lang.invoke.");
            }
            return true;
        }
        return true;
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest origRequest, LinkerServices linkerServices) throws Exception {
        checkLinkRequest(origRequest);
        return null;
    }

    private static boolean isReflectiveCheckNeeded(Class<?> type, boolean isStatic) {
        if (Proxy.class.isAssignableFrom(type)) {
            if (Proxy.isProxyClass(type)) {
                return isStatic;
            }
            return true;
        }
        return isReflectionClass(type);
    }

    public static void checkReflectionAccess(Class<?> clazz, boolean isStatic) {
        Global global = Context.getGlobal();
        ClassFilter cf = global.getClassFilter();
        if (cf != null && isReflectiveCheckNeeded(clazz, isStatic)) {
            throw ECMAErrors.typeError("no.reflection.with.classfilter", new String[0]);
        }
        SecurityManager sm = System.getSecurityManager();
        if (sm != null && isReflectiveCheckNeeded(clazz, isStatic)) {
            checkReflectionPermission(sm);
        }
    }

    private static void checkLinkRequest(LinkRequest origRequest) {
        Global global = Context.getGlobal();
        ClassFilter cf = global.getClassFilter();
        if (cf != null) {
            throw ECMAErrors.typeError("no.reflection.with.classfilter", new String[0]);
        }
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            LinkRequest requestWithoutContext = origRequest.withoutRuntimeContext();
            Object self = requestWithoutContext.getReceiver();
            if ((self instanceof Class) && Modifier.isPublic(((Class) self).getModifiers())) {
                CallSiteDescriptor desc = requestWithoutContext.getCallSiteDescriptor();
                if (CallSiteDescriptorFactory.tokenizeOperators(desc).contains("getProp") && desc.getNameTokenCount() > 2 && "static".equals(desc.getNameToken(2)) && Context.isAccessibleClass((Class) self) && !isReflectionClass((Class) self)) {
                    return;
                }
            }
            checkReflectionPermission(sm);
        }
    }

    private static void checkReflectionPermission(SecurityManager sm) {
        sm.checkPermission(new RuntimePermission(Context.NASHORN_JAVA_REFLECTION));
    }
}
