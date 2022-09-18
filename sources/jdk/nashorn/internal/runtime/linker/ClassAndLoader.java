package jdk.nashorn.internal.runtime.linker;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.runtime.ECMAErrors;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/ClassAndLoader.class */
public final class ClassAndLoader {
    private static final AccessControlContext GET_LOADER_ACC_CTXT;
    private final Class<?> representativeClass;
    private ClassLoader loader;
    private boolean loaderRetrieved;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ClassAndLoader.class.desiredAssertionStatus();
        GET_LOADER_ACC_CTXT = createPermAccCtxt("getClassLoader");
    }

    public static AccessControlContext createPermAccCtxt(String... permNames) {
        Permissions perms = new Permissions();
        for (String permName : permNames) {
            perms.add(new RuntimePermission(permName));
        }
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, perms)});
    }

    public ClassAndLoader(Class<?> representativeClass, boolean retrieveLoader) {
        this.representativeClass = representativeClass;
        if (retrieveLoader) {
            retrieveLoader();
        }
    }

    public Class<?> getRepresentativeClass() {
        return this.representativeClass;
    }

    public boolean canSee(ClassAndLoader other) {
        try {
            Class<?> otherClass = other.getRepresentativeClass();
            return Class.forName(otherClass.getName(), false, getLoader()) == otherClass;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public ClassLoader getLoader() {
        if (!this.loaderRetrieved) {
            retrieveLoader();
        }
        return getRetrievedLoader();
    }

    ClassLoader getRetrievedLoader() {
        if ($assertionsDisabled || this.loaderRetrieved) {
            return this.loader;
        }
        throw new AssertionError();
    }

    private void retrieveLoader() {
        this.loader = this.representativeClass.getClassLoader();
        this.loaderRetrieved = true;
    }

    public boolean equals(Object obj) {
        return (obj instanceof ClassAndLoader) && ((ClassAndLoader) obj).getRetrievedLoader() == getRetrievedLoader();
    }

    public int hashCode() {
        return System.identityHashCode(getRetrievedLoader());
    }

    public static ClassAndLoader getDefiningClassAndLoader(final Class<?>[] types) {
        if (types.length == 1) {
            return new ClassAndLoader(types[0], false);
        }
        return (ClassAndLoader) AccessController.doPrivileged(new PrivilegedAction<ClassAndLoader>() { // from class: jdk.nashorn.internal.runtime.linker.ClassAndLoader.1
            @Override // java.security.PrivilegedAction
            public ClassAndLoader run() {
                return ClassAndLoader.getDefiningClassAndLoaderPrivileged(types);
            }
        }, GET_LOADER_ACC_CTXT);
    }

    static ClassAndLoader getDefiningClassAndLoaderPrivileged(Class<?>[] types) {
        Collection<ClassAndLoader> maximumVisibilityLoaders = getMaximumVisibilityLoaders(types);
        Iterator<ClassAndLoader> it = maximumVisibilityLoaders.iterator();
        if (maximumVisibilityLoaders.size() == 1) {
            return it.next();
        }
        if (!$assertionsDisabled && maximumVisibilityLoaders.size() <= 1) {
            throw new AssertionError();
        }
        StringBuilder b = new StringBuilder();
        b.append(it.next().getRepresentativeClass().getCanonicalName());
        while (it.hasNext()) {
            b.append(", ").append(it.next().getRepresentativeClass().getCanonicalName());
        }
        throw ECMAErrors.typeError("extend.ambiguous.defining.class", b.toString());
    }

    private static Collection<ClassAndLoader> getMaximumVisibilityLoaders(Class<?>[] types) {
        List<ClassAndLoader> maximumVisibilityLoaders = new LinkedList<>();
        for (ClassAndLoader maxCandidate : getClassLoadersForTypes(types)) {
            Iterator<ClassAndLoader> it = maximumVisibilityLoaders.iterator();
            while (true) {
                if (it.hasNext()) {
                    ClassAndLoader existingMax = it.next();
                    boolean candidateSeesExisting = maxCandidate.canSee(existingMax);
                    boolean exitingSeesCandidate = existingMax.canSee(maxCandidate);
                    if (candidateSeesExisting) {
                        if (!exitingSeesCandidate) {
                            it.remove();
                        }
                    } else if (exitingSeesCandidate) {
                        break;
                    }
                } else {
                    maximumVisibilityLoaders.add(maxCandidate);
                    break;
                }
            }
        }
        return maximumVisibilityLoaders;
    }

    private static Collection<ClassAndLoader> getClassLoadersForTypes(Class<?>[] types) {
        Map<ClassAndLoader, ClassAndLoader> classesAndLoaders = new LinkedHashMap<>();
        for (Class<?> c : types) {
            ClassAndLoader cl = new ClassAndLoader(c, true);
            if (!classesAndLoaders.containsKey(cl)) {
                classesAndLoaders.put(cl, cl);
            }
        }
        return classesAndLoaders.keySet();
    }
}
