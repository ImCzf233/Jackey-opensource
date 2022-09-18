package jdk.internal.dynalink;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.GuardingTypeConverterFactory;
import jdk.internal.dynalink.linker.MethodHandleTransformer;
import jdk.internal.dynalink.linker.MethodTypeConversionStrategy;
import jdk.internal.dynalink.support.AutoDiscovery;
import jdk.internal.dynalink.support.BottomGuardingDynamicLinker;
import jdk.internal.dynalink.support.ClassLoaderGetterContextProvider;
import jdk.internal.dynalink.support.CompositeGuardingDynamicLinker;
import jdk.internal.dynalink.support.CompositeTypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.DefaultPrelinkFilter;
import jdk.internal.dynalink.support.LinkerServicesImpl;
import jdk.internal.dynalink.support.TypeConverterFactory;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/DynamicLinkerFactory.class */
public class DynamicLinkerFactory {
    public static final int DEFAULT_UNSTABLE_RELINK_THRESHOLD = 8;
    private ClassLoader classLoader;
    private List<? extends GuardingDynamicLinker> prioritizedLinkers;
    private List<? extends GuardingDynamicLinker> fallbackLinkers;
    private GuardedInvocationFilter prelinkFilter;
    private MethodTypeConversionStrategy autoConversionStrategy;
    private MethodHandleTransformer internalObjectsFilter;
    private boolean classLoaderExplicitlySet = false;
    private int runtimeContextArgCount = 0;
    private boolean syncOnRelink = false;
    private int unstableRelinkThreshold = 8;

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.classLoaderExplicitlySet = true;
    }

    public void setPrioritizedLinkers(List<? extends GuardingDynamicLinker> prioritizedLinkers) {
        this.prioritizedLinkers = prioritizedLinkers == null ? null : new ArrayList(prioritizedLinkers);
    }

    public void setPrioritizedLinkers(GuardingDynamicLinker... prioritizedLinkers) {
        setPrioritizedLinkers(Arrays.asList(prioritizedLinkers));
    }

    public void setPrioritizedLinker(GuardingDynamicLinker prioritizedLinker) {
        if (prioritizedLinker == null) {
            throw new IllegalArgumentException("prioritizedLinker == null");
        }
        this.prioritizedLinkers = Collections.singletonList(prioritizedLinker);
    }

    public void setFallbackLinkers(List<? extends GuardingDynamicLinker> fallbackLinkers) {
        this.fallbackLinkers = fallbackLinkers == null ? null : new ArrayList(fallbackLinkers);
    }

    public void setFallbackLinkers(GuardingDynamicLinker... fallbackLinkers) {
        setFallbackLinkers(Arrays.asList(fallbackLinkers));
    }

    public void setRuntimeContextArgCount(int runtimeContextArgCount) {
        if (runtimeContextArgCount < 0) {
            throw new IllegalArgumentException("runtimeContextArgCount < 0");
        }
        this.runtimeContextArgCount = runtimeContextArgCount;
    }

    public void setSyncOnRelink(boolean syncOnRelink) {
        this.syncOnRelink = syncOnRelink;
    }

    public void setUnstableRelinkThreshold(int unstableRelinkThreshold) {
        if (unstableRelinkThreshold < 0) {
            throw new IllegalArgumentException("unstableRelinkThreshold < 0");
        }
        this.unstableRelinkThreshold = unstableRelinkThreshold;
    }

    public void setPrelinkFilter(GuardedInvocationFilter prelinkFilter) {
        this.prelinkFilter = prelinkFilter;
    }

    public void setAutoConversionStrategy(MethodTypeConversionStrategy autoConversionStrategy) {
        this.autoConversionStrategy = autoConversionStrategy;
    }

    public void setInternalObjectsFilter(MethodHandleTransformer internalObjectsFilter) {
        this.internalObjectsFilter = internalObjectsFilter;
    }

    public DynamicLinker createLinker() {
        GuardingDynamicLinker composite;
        if (this.prioritizedLinkers == null) {
            this.prioritizedLinkers = Collections.emptyList();
        }
        if (this.fallbackLinkers == null) {
            this.fallbackLinkers = Collections.singletonList(new BeansLinker());
        }
        Set<Class<? extends GuardingDynamicLinker>> knownLinkerClasses = new HashSet<>();
        addClasses(knownLinkerClasses, this.prioritizedLinkers);
        addClasses(knownLinkerClasses, this.fallbackLinkers);
        ClassLoader effectiveClassLoader = this.classLoaderExplicitlySet ? this.classLoader : getThreadContextClassLoader();
        List<GuardingDynamicLinker> discovered = AutoDiscovery.loadLinkers(effectiveClassLoader);
        List<GuardingDynamicLinker> linkers = new ArrayList<>(this.prioritizedLinkers.size() + discovered.size() + this.fallbackLinkers.size());
        linkers.addAll(this.prioritizedLinkers);
        for (GuardingDynamicLinker linker : discovered) {
            if (!knownLinkerClasses.contains(linker.getClass())) {
                linkers.add(linker);
            }
        }
        linkers.addAll(this.fallbackLinkers);
        List<GuardingDynamicLinker> optimized = CompositeTypeBasedGuardingDynamicLinker.optimize(linkers);
        switch (linkers.size()) {
            case 0:
                composite = BottomGuardingDynamicLinker.INSTANCE;
                break;
            case 1:
                composite = optimized.get(0);
                break;
            default:
                composite = new CompositeGuardingDynamicLinker(optimized);
                break;
        }
        List<GuardingTypeConverterFactory> typeConverters = new LinkedList<>();
        for (GuardingDynamicLinker linker2 : linkers) {
            if (linker2 instanceof GuardingTypeConverterFactory) {
                typeConverters.add((GuardingTypeConverterFactory) linker2);
            }
        }
        if (this.prelinkFilter == null) {
            this.prelinkFilter = new DefaultPrelinkFilter();
        }
        return new DynamicLinker(new LinkerServicesImpl(new TypeConverterFactory(typeConverters, this.autoConversionStrategy), composite, this.internalObjectsFilter), this.prelinkFilter, this.runtimeContextArgCount, this.syncOnRelink, this.unstableRelinkThreshold);
    }

    private static ClassLoader getThreadContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() { // from class: jdk.internal.dynalink.DynamicLinkerFactory.1
            @Override // java.security.PrivilegedAction
            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }
        }, ClassLoaderGetterContextProvider.GET_CLASS_LOADER_CONTEXT);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void addClasses(Set<Class<? extends GuardingDynamicLinker>> knownLinkerClasses, List<? extends GuardingDynamicLinker> linkers) {
        for (GuardingDynamicLinker linker : linkers) {
            knownLinkerClasses.add(linker.getClass());
        }
    }
}
