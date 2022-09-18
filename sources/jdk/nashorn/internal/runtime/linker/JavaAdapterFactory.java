package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.support.LinkRequestImpl;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.linker.AdaptationResult;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/JavaAdapterFactory.class */
public final class JavaAdapterFactory {
    private static final ProtectionDomain MINIMAL_PERMISSION_DOMAIN;
    private static final AccessControlContext CREATE_ADAPTER_INFO_ACC_CTXT;
    private static final ClassValue<Map<List<Class<?>>, AdapterInfo>> ADAPTER_INFO_MAPS;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JavaAdapterFactory.class.desiredAssertionStatus();
        MINIMAL_PERMISSION_DOMAIN = createMinimalPermissionDomain();
        CREATE_ADAPTER_INFO_ACC_CTXT = ClassAndLoader.createPermAccCtxt("createClassLoader", "getClassLoader", "accessDeclaredMembers", "accessClassInPackage.jdk.nashorn.internal.runtime");
        ADAPTER_INFO_MAPS = new ClassValue<Map<List<Class<?>>, AdapterInfo>>() { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterFactory.1
            protected Map<List<Class<?>>, AdapterInfo> computeValue(Class<?> type) {
                return new HashMap();
            }
        };
    }

    public static StaticClass getAdapterClassFor(Class<?>[] types, ScriptObject classOverrides, MethodHandles.Lookup lookup) {
        return getAdapterClassFor(types, classOverrides, getProtectionDomain(lookup));
    }

    private static StaticClass getAdapterClassFor(Class<?>[] types, ScriptObject classOverrides, ProtectionDomain protectionDomain) {
        if ($assertionsDisabled || (types != null && types.length > 0)) {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                for (Class<?> type : types) {
                    Context.checkPackageAccess(type);
                    ReflectionCheckLinker.checkReflectionAccess(type, true);
                }
            }
            return getAdapterInfo(types).getAdapterClass(classOverrides, protectionDomain);
        }
        throw new AssertionError();
    }

    private static ProtectionDomain getProtectionDomain(MethodHandles.Lookup lookup) {
        if ((lookup.lookupModes() & 2) == 0) {
            return MINIMAL_PERMISSION_DOMAIN;
        }
        return getProtectionDomain(lookup.lookupClass());
    }

    private static ProtectionDomain getProtectionDomain(final Class<?> clazz) {
        return (ProtectionDomain) AccessController.doPrivileged(new PrivilegedAction<ProtectionDomain>() { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterFactory.2
            @Override // java.security.PrivilegedAction
            public ProtectionDomain run() {
                return clazz.getProtectionDomain();
            }
        });
    }

    public static MethodHandle getConstructor(Class<?> sourceType, Class<?> targetType, MethodHandles.Lookup lookup) throws Exception {
        StaticClass adapterClass = getAdapterClassFor(new Class[]{targetType}, (ScriptObject) null, lookup);
        return Lookup.f248MH.bindTo(Bootstrap.getLinkerServices().getGuardedInvocation(new LinkRequestImpl(NashornCallSiteDescriptor.get(lookup, "dyn:new", MethodType.methodType(targetType, StaticClass.class, sourceType), 0), null, 0, false, adapterClass, null)).getInvocation(), adapterClass);
    }

    public static boolean isAutoConvertibleFromFunction(Class<?> clazz) {
        return getAdapterInfo(new Class[]{clazz}).autoConvertibleFromFunction;
    }

    private static AdapterInfo getAdapterInfo(Class<?>[] types) {
        AdapterInfo adapterInfo;
        ClassAndLoader definingClassAndLoader = ClassAndLoader.getDefiningClassAndLoader(types);
        Map<List<Class<?>>, AdapterInfo> adapterInfoMap = (Map) ADAPTER_INFO_MAPS.get(definingClassAndLoader.getRepresentativeClass());
        List<Class<?>> typeList = types.length == 1 ? Collections.singletonList(types[0]) : Arrays.asList((Object[]) types.clone());
        synchronized (adapterInfoMap) {
            adapterInfo = adapterInfoMap.get(typeList);
            if (adapterInfo == null) {
                adapterInfo = createAdapterInfo(types, definingClassAndLoader);
                adapterInfoMap.put(typeList, adapterInfo);
            }
        }
        return adapterInfo;
    }

    private static AdapterInfo createAdapterInfo(final Class<?>[] types, final ClassAndLoader definingClassAndLoader) {
        Class<?> superClass = null;
        final List<Class<?>> interfaces = new ArrayList<>(types.length);
        for (Class<?> t : types) {
            int mod = t.getModifiers();
            if (!t.isInterface()) {
                if (superClass != null) {
                    return new AdapterInfo(AdaptationResult.Outcome.ERROR_MULTIPLE_SUPERCLASSES, t.getCanonicalName() + " and " + superClass.getCanonicalName());
                }
                if (Modifier.isFinal(mod)) {
                    return new AdapterInfo(AdaptationResult.Outcome.ERROR_FINAL_CLASS, t.getCanonicalName());
                }
                superClass = t;
            } else if (interfaces.size() > 65535) {
                throw new IllegalArgumentException("interface limit exceeded");
            } else {
                interfaces.add(t);
            }
            if (!Modifier.isPublic(mod)) {
                return new AdapterInfo(AdaptationResult.Outcome.ERROR_NON_PUBLIC_CLASS, t.getCanonicalName());
            }
        }
        final Class<?> effectiveSuperClass = superClass == null ? Object.class : superClass;
        return (AdapterInfo) AccessController.doPrivileged(new PrivilegedAction<AdapterInfo>() { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterFactory.3
            @Override // java.security.PrivilegedAction
            public AdapterInfo run() {
                try {
                    return new AdapterInfo(effectiveSuperClass, interfaces, definingClassAndLoader);
                } catch (RuntimeException e) {
                    return new AdapterInfo(new AdaptationResult(AdaptationResult.Outcome.ERROR_OTHER, Arrays.toString(types), e.toString()));
                } catch (AdaptationException e2) {
                    return new AdapterInfo(e2.getAdaptationResult());
                }
            }
        }, CREATE_ADAPTER_INFO_ACC_CTXT);
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/JavaAdapterFactory$AdapterInfo.class */
    public static class AdapterInfo {
        private static final ClassAndLoader SCRIPT_OBJECT_LOADER = new ClassAndLoader(ScriptFunction.class, true);
        private final ClassLoader commonLoader;
        private final JavaAdapterClassLoader classAdapterGenerator;
        private final JavaAdapterClassLoader instanceAdapterGenerator;
        private final Map<CodeSource, StaticClass> instanceAdapters;
        final boolean autoConvertibleFromFunction;
        final AdaptationResult adaptationResult;

        AdapterInfo(Class<?> superClass, List<Class<?>> interfaces, ClassAndLoader definingLoader) throws AdaptationException {
            this.instanceAdapters = new ConcurrentHashMap();
            this.commonLoader = findCommonLoader(definingLoader);
            JavaAdapterBytecodeGenerator gen = new JavaAdapterBytecodeGenerator(superClass, interfaces, this.commonLoader, false);
            this.autoConvertibleFromFunction = gen.isAutoConvertibleFromFunction();
            this.instanceAdapterGenerator = gen.createAdapterClassLoader();
            this.classAdapterGenerator = new JavaAdapterBytecodeGenerator(superClass, interfaces, this.commonLoader, true).createAdapterClassLoader();
            this.adaptationResult = AdaptationResult.SUCCESSFUL_RESULT;
        }

        AdapterInfo(AdaptationResult.Outcome outcome, String classList) {
            this(new AdaptationResult(outcome, classList));
        }

        AdapterInfo(AdaptationResult adaptationResult) {
            this.instanceAdapters = new ConcurrentHashMap();
            this.commonLoader = null;
            this.classAdapterGenerator = null;
            this.instanceAdapterGenerator = null;
            this.autoConvertibleFromFunction = false;
            this.adaptationResult = adaptationResult;
        }

        StaticClass getAdapterClass(ScriptObject classOverrides, ProtectionDomain protectionDomain) {
            if (this.adaptationResult.getOutcome() != AdaptationResult.Outcome.SUCCESS) {
                throw this.adaptationResult.typeError();
            }
            return classOverrides == null ? getInstanceAdapterClass(protectionDomain) : getClassAdapterClass(classOverrides, protectionDomain);
        }

        private StaticClass getInstanceAdapterClass(ProtectionDomain protectionDomain) {
            CodeSource codeSource = protectionDomain.getCodeSource();
            if (codeSource == null) {
                codeSource = JavaAdapterFactory.MINIMAL_PERMISSION_DOMAIN.getCodeSource();
            }
            StaticClass instanceAdapterClass = this.instanceAdapters.get(codeSource);
            if (instanceAdapterClass == null) {
                ProtectionDomain effectiveDomain = codeSource.equals(JavaAdapterFactory.MINIMAL_PERMISSION_DOMAIN.getCodeSource()) ? JavaAdapterFactory.MINIMAL_PERMISSION_DOMAIN : protectionDomain;
                StaticClass instanceAdapterClass2 = this.instanceAdapterGenerator.generateClass(this.commonLoader, effectiveDomain);
                StaticClass existing = this.instanceAdapters.putIfAbsent(codeSource, instanceAdapterClass2);
                return existing == null ? instanceAdapterClass2 : existing;
            }
            return instanceAdapterClass;
        }

        private StaticClass getClassAdapterClass(ScriptObject classOverrides, ProtectionDomain protectionDomain) {
            JavaAdapterServices.setClassOverrides(classOverrides);
            try {
                StaticClass generateClass = this.classAdapterGenerator.generateClass(this.commonLoader, protectionDomain);
                JavaAdapterServices.setClassOverrides(null);
                return generateClass;
            } catch (Throwable th) {
                JavaAdapterServices.setClassOverrides(null);
                throw th;
            }
        }

        private static ClassLoader findCommonLoader(ClassAndLoader classAndLoader) throws AdaptationException {
            if (classAndLoader.canSee(SCRIPT_OBJECT_LOADER)) {
                return classAndLoader.getLoader();
            }
            if (SCRIPT_OBJECT_LOADER.canSee(classAndLoader)) {
                return SCRIPT_OBJECT_LOADER.getLoader();
            }
            throw new AdaptationException(AdaptationResult.Outcome.ERROR_NO_COMMON_LOADER, classAndLoader.getRepresentativeClass().getCanonicalName());
        }
    }

    private static ProtectionDomain createMinimalPermissionDomain() {
        Permissions permissions = new Permissions();
        permissions.add(new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.objects"));
        permissions.add(new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime"));
        permissions.add(new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime.linker"));
        return new ProtectionDomain(new CodeSource((URL) null, (CodeSigner[]) null), permissions);
    }
}
