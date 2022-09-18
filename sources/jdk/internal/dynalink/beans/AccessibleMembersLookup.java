package jdk.internal.dynalink.beans;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/AccessibleMembersLookup.class */
class AccessibleMembersLookup {
    private final Map<MethodSignature, Method> methods = new HashMap();
    private final Set<Class<?>> innerClasses = new LinkedHashSet();
    private final boolean instance;

    public AccessibleMembersLookup(Class<?> clazz, boolean instance) {
        this.instance = instance;
        lookupAccessibleMembers(clazz);
    }

    Method getAccessibleMethod(Method m) {
        if (m == null) {
            return null;
        }
        return this.methods.get(new MethodSignature(m));
    }

    public Collection<Method> getMethods() {
        return this.methods.values();
    }

    public Class<?>[] getInnerClasses() {
        return (Class[]) this.innerClasses.toArray(new Class[this.innerClasses.size()]);
    }

    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/AccessibleMembersLookup$MethodSignature.class */
    public static final class MethodSignature {
        private final String name;
        private final Class<?>[] args;

        MethodSignature(String name, Class<?>[] args) {
            this.name = name;
            this.args = args;
        }

        MethodSignature(Method method) {
            this(method.getName(), method.getParameterTypes());
        }

        public boolean equals(Object o) {
            if (o instanceof MethodSignature) {
                MethodSignature ms = (MethodSignature) o;
                return ms.name.equals(this.name) && Arrays.equals(this.args, ms.args);
            }
            return false;
        }

        public int hashCode() {
            return this.name.hashCode() ^ Arrays.hashCode(this.args);
        }

        public String toString() {
            StringBuilder b = new StringBuilder();
            b.append("[MethodSignature ").append(this.name).append('(');
            if (this.args.length > 0) {
                b.append(this.args[0].getCanonicalName());
                for (int i = 1; i < this.args.length; i++) {
                    b.append(", ").append(this.args[i].getCanonicalName());
                }
            }
            return b.append(")]").toString();
        }
    }

    private void lookupAccessibleMembers(Class<?> clazz) {
        boolean searchSuperTypes;
        Method[] methods;
        Class<?>[] classes;
        if (!CheckRestrictedPackage.isRestrictedClass(clazz)) {
            searchSuperTypes = false;
            for (Method method : clazz.getMethods()) {
                boolean isStatic = Modifier.isStatic(method.getModifiers());
                if (this.instance != isStatic) {
                    MethodSignature sig = new MethodSignature(method);
                    if (!this.methods.containsKey(sig)) {
                        Class<?> declaringClass = method.getDeclaringClass();
                        if (declaringClass != clazz && CheckRestrictedPackage.isRestrictedClass(declaringClass)) {
                            searchSuperTypes = true;
                        } else if (!isStatic || clazz == declaringClass) {
                            this.methods.put(sig, method);
                        }
                    }
                }
            }
            for (Class<?> innerClass : clazz.getClasses()) {
                this.innerClasses.add(innerClass);
            }
        } else {
            searchSuperTypes = true;
        }
        if (this.instance && searchSuperTypes) {
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> cls : interfaces) {
                lookupAccessibleMembers(cls);
            }
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null) {
                lookupAccessibleMembers(superclass);
            }
        }
    }
}
