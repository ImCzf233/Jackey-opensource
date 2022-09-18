package org.spongepowered.asm.service.mojang;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import net.minecraft.launchwrapper.LaunchClassLoader;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/service/mojang/LaunchClassLoaderUtil.class */
final class LaunchClassLoaderUtil {
    private static final String CACHED_CLASSES_FIELD = "cachedClasses";
    private static final String INVALID_CLASSES_FIELD = "invalidClasses";
    private static final String CLASS_LOADER_EXCEPTIONS_FIELD = "classLoaderExceptions";
    private static final String TRANSFORMER_EXCEPTIONS_FIELD = "transformerExceptions";
    private final LaunchClassLoader classLoader;
    private final Map<String, Class<?>> cachedClasses;
    private final Set<String> invalidClasses;
    private final Set<String> classLoaderExceptions;
    private final Set<String> transformerExceptions;

    public LaunchClassLoaderUtil(LaunchClassLoader classLoader) {
        this.classLoader = classLoader;
        this.cachedClasses = (Map) getField(classLoader, CACHED_CLASSES_FIELD);
        this.invalidClasses = (Set) getField(classLoader, INVALID_CLASSES_FIELD);
        this.classLoaderExceptions = (Set) getField(classLoader, CLASS_LOADER_EXCEPTIONS_FIELD);
        this.transformerExceptions = (Set) getField(classLoader, TRANSFORMER_EXCEPTIONS_FIELD);
    }

    LaunchClassLoader getClassLoader() {
        return this.classLoader;
    }

    public boolean isClassLoaded(String name) {
        return this.cachedClasses.containsKey(name);
    }

    public boolean isClassExcluded(String name, String transformedName) {
        return isClassClassLoaderExcluded(name, transformedName) || isClassTransformerExcluded(name, transformedName);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isClassClassLoaderExcluded(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            r0 = r3
            java.util.Set r0 = r0.getClassLoaderExceptions()
            java.util.Iterator r0 = r0.iterator()
            r6 = r0
        La:
            r0 = r6
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L39
            r0 = r6
            java.lang.Object r0 = r0.next()
            java.lang.String r0 = (java.lang.String) r0
            r7 = r0
            r0 = r5
            if (r0 == 0) goto L2b
            r0 = r5
            r1 = r7
            boolean r0 = r0.startsWith(r1)
            if (r0 != 0) goto L34
        L2b:
            r0 = r4
            r1 = r7
            boolean r0 = r0.startsWith(r1)
            if (r0 == 0) goto L36
        L34:
            r0 = 1
            return r0
        L36:
            goto La
        L39:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongepowered.asm.service.mojang.LaunchClassLoaderUtil.isClassClassLoaderExcluded(java.lang.String, java.lang.String):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isClassTransformerExcluded(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            r0 = r3
            java.util.Set r0 = r0.getTransformerExceptions()
            java.util.Iterator r0 = r0.iterator()
            r6 = r0
        La:
            r0 = r6
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L39
            r0 = r6
            java.lang.Object r0 = r0.next()
            java.lang.String r0 = (java.lang.String) r0
            r7 = r0
            r0 = r5
            if (r0 == 0) goto L2b
            r0 = r5
            r1 = r7
            boolean r0 = r0.startsWith(r1)
            if (r0 != 0) goto L34
        L2b:
            r0 = r4
            r1 = r7
            boolean r0 = r0.startsWith(r1)
            if (r0 == 0) goto L36
        L34:
            r0 = 1
            return r0
        L36:
            goto La
        L39:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongepowered.asm.service.mojang.LaunchClassLoaderUtil.isClassTransformerExcluded(java.lang.String, java.lang.String):boolean");
    }

    public void registerInvalidClass(String name) {
        if (this.invalidClasses != null) {
            this.invalidClasses.add(name);
        }
    }

    Set<String> getClassLoaderExceptions() {
        if (this.classLoaderExceptions != null) {
            return this.classLoaderExceptions;
        }
        return Collections.emptySet();
    }

    Set<String> getTransformerExceptions() {
        if (this.transformerExceptions != null) {
            return this.transformerExceptions;
        }
        return Collections.emptySet();
    }

    private static <T> T getField(LaunchClassLoader classLoader, String fieldName) {
        try {
            Field field = LaunchClassLoader.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(classLoader);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
