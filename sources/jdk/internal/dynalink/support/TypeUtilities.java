package jdk.internal.dynalink.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/TypeUtilities.class */
public class TypeUtilities {
    static final Class<Object> OBJECT_CLASS;
    private static final Map<Class<?>, Class<?>> WRAPPER_TYPES;
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPES;
    private static final Map<String, Class<?>> PRIMITIVE_TYPES_BY_NAME;
    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPES;
    private static final Set<Class<?>> PRIMITIVE_WRAPPER_TYPES;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !TypeUtilities.class.desiredAssertionStatus();
        OBJECT_CLASS = Object.class;
        WRAPPER_TYPES = createWrapperTypes();
        PRIMITIVE_TYPES = invertMap(WRAPPER_TYPES);
        PRIMITIVE_TYPES_BY_NAME = createClassNameMapping(WRAPPER_TYPES.keySet());
        WRAPPER_TO_PRIMITIVE_TYPES = createWrapperToPrimitiveTypes();
        PRIMITIVE_WRAPPER_TYPES = createPrimitiveWrapperTypes();
    }

    private TypeUtilities() {
    }

    public static Class<?> getCommonLosslessConversionType(Class<?> c1, Class<?> c2) {
        if (c1 == c2) {
            return c1;
        }
        if (c1 == Void.TYPE || c2 == Void.TYPE) {
            return Object.class;
        }
        if (isConvertibleWithoutLoss(c2, c1)) {
            return c1;
        }
        if (isConvertibleWithoutLoss(c1, c2)) {
            return c2;
        }
        if (c1.isPrimitive() && c2.isPrimitive()) {
            if ((c1 == Byte.TYPE && c2 == Character.TYPE) || (c1 == Character.TYPE && c2 == Byte.TYPE)) {
                return Integer.TYPE;
            }
            if ((c1 == Short.TYPE && c2 == Character.TYPE) || (c1 == Character.TYPE && c2 == Short.TYPE)) {
                return Integer.TYPE;
            }
            if ((c1 == Integer.TYPE && c2 == Float.TYPE) || (c1 == Float.TYPE && c2 == Integer.TYPE)) {
                return Double.TYPE;
            }
        }
        return getMostSpecificCommonTypeUnequalNonprimitives(c1, c2);
    }

    private static Class<?> getMostSpecificCommonTypeUnequalNonprimitives(Class<?> c1, Class<?> c2) {
        Class<?> npc1 = c1.isPrimitive() ? getWrapperType(c1) : c1;
        Class<?> npc2 = c2.isPrimitive() ? getWrapperType(c2) : c2;
        Set<Class<?>> a1 = getAssignables(npc1, npc2);
        Set<Class<?>> a2 = getAssignables(npc2, npc1);
        a1.retainAll(a2);
        if (a1.isEmpty()) {
            return Object.class;
        }
        List<Class<?>> max = new ArrayList<>();
        for (Class<?> clazz : a1) {
            Iterator<Class<?>> maxiter = max.iterator();
            while (true) {
                if (maxiter.hasNext()) {
                    Class<?> maxClazz = maxiter.next();
                    if (isSubtype(maxClazz, clazz)) {
                        break;
                    } else if (isSubtype(clazz, maxClazz)) {
                        maxiter.remove();
                    }
                } else {
                    max.add(clazz);
                    break;
                }
            }
        }
        if (max.size() > 1) {
            return Object.class;
        }
        return max.get(0);
    }

    private static Set<Class<?>> getAssignables(Class<?> c1, Class<?> c2) {
        Set<Class<?>> s = new HashSet<>();
        collectAssignables(c1, c2, s);
        return s;
    }

    private static void collectAssignables(Class<?> c1, Class<?> c2, Set<Class<?>> s) {
        if (c1.isAssignableFrom(c2)) {
            s.add(c1);
        }
        Class<?> sc = c1.getSuperclass();
        if (sc != null) {
            collectAssignables(sc, c2, s);
        }
        Class<?>[] itf = c1.getInterfaces();
        for (Class<?> cls : itf) {
            collectAssignables(cls, c2, s);
        }
    }

    private static Map<Class<?>, Class<?>> createWrapperTypes() {
        Map<Class<?>, Class<?>> wrapperTypes = new IdentityHashMap<>(8);
        wrapperTypes.put(Boolean.TYPE, Boolean.class);
        wrapperTypes.put(Byte.TYPE, Byte.class);
        wrapperTypes.put(Character.TYPE, Character.class);
        wrapperTypes.put(Short.TYPE, Short.class);
        wrapperTypes.put(Integer.TYPE, Integer.class);
        wrapperTypes.put(Long.TYPE, Long.class);
        wrapperTypes.put(Float.TYPE, Float.class);
        wrapperTypes.put(Double.TYPE, Double.class);
        return Collections.unmodifiableMap(wrapperTypes);
    }

    private static Map<String, Class<?>> createClassNameMapping(Collection<Class<?>> classes) {
        Map<String, Class<?>> map = new HashMap<>();
        for (Class<?> clazz : classes) {
            map.put(clazz.getName(), clazz);
        }
        return map;
    }

    private static <K, V> Map<V, K> invertMap(Map<K, V> map) {
        Map<V, K> inverted = new IdentityHashMap<>(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            inverted.put(entry.getValue(), entry.getKey());
        }
        return Collections.unmodifiableMap(inverted);
    }

    public static boolean isMethodInvocationConvertible(Class<?> sourceType, Class<?> targetType) {
        Class<?> unboxedCallSiteType;
        if (targetType.isAssignableFrom(sourceType)) {
            return true;
        }
        if (!sourceType.isPrimitive()) {
            return targetType.isPrimitive() && (unboxedCallSiteType = PRIMITIVE_TYPES.get(sourceType)) != null && (unboxedCallSiteType == targetType || isProperPrimitiveSubtype(unboxedCallSiteType, targetType));
        } else if (targetType.isPrimitive()) {
            return isProperPrimitiveSubtype(sourceType, targetType);
        } else {
            if (!$assertionsDisabled && WRAPPER_TYPES.get(sourceType) == null) {
                throw new AssertionError(sourceType.getName());
            }
            return targetType.isAssignableFrom(WRAPPER_TYPES.get(sourceType));
        }
    }

    public static boolean isConvertibleWithoutLoss(Class<?> sourceType, Class<?> targetType) {
        if (targetType.isAssignableFrom(sourceType) || targetType == Void.TYPE) {
            return true;
        }
        if (sourceType.isPrimitive()) {
            if (sourceType == Void.TYPE) {
                return targetType == Object.class;
            } else if (targetType.isPrimitive()) {
                return isProperPrimitiveLosslessSubtype(sourceType, targetType);
            } else {
                if (!$assertionsDisabled && WRAPPER_TYPES.get(sourceType) == null) {
                    throw new AssertionError(sourceType.getName());
                }
                return targetType.isAssignableFrom(WRAPPER_TYPES.get(sourceType));
            }
        }
        return false;
    }

    public static boolean isPotentiallyConvertible(Class<?> callSiteType, Class<?> methodType) {
        if (areAssignable(callSiteType, methodType)) {
            return true;
        }
        if (callSiteType.isPrimitive()) {
            return methodType.isPrimitive() || isAssignableFromBoxedPrimitive(methodType);
        } else if (methodType.isPrimitive()) {
            return isAssignableFromBoxedPrimitive(callSiteType);
        } else {
            return false;
        }
    }

    public static boolean areAssignable(Class<?> c1, Class<?> c2) {
        return c1.isAssignableFrom(c2) || c2.isAssignableFrom(c1);
    }

    public static boolean isSubtype(Class<?> subType, Class<?> superType) {
        if (superType.isAssignableFrom(subType)) {
            return true;
        }
        if (superType.isPrimitive() && subType.isPrimitive()) {
            return isProperPrimitiveSubtype(subType, superType);
        }
        return false;
    }

    private static boolean isProperPrimitiveSubtype(Class<?> subType, Class<?> superType) {
        if (superType == Boolean.TYPE || subType == Boolean.TYPE) {
            return false;
        }
        return subType == Byte.TYPE ? superType != Character.TYPE : subType == Character.TYPE ? (superType == Short.TYPE || superType == Byte.TYPE) ? false : true : subType == Short.TYPE ? (superType == Character.TYPE || superType == Byte.TYPE) ? false : true : subType == Integer.TYPE ? superType == Long.TYPE || superType == Float.TYPE || superType == Double.TYPE : subType == Long.TYPE ? superType == Float.TYPE || superType == Double.TYPE : subType == Float.TYPE && superType == Double.TYPE;
    }

    private static boolean isProperPrimitiveLosslessSubtype(Class<?> subType, Class<?> superType) {
        if (superType == Boolean.TYPE || subType == Boolean.TYPE || superType == Character.TYPE || subType == Character.TYPE) {
            return false;
        }
        if (subType == Byte.TYPE) {
            return true;
        }
        return subType == Short.TYPE ? superType != Byte.TYPE : subType == Integer.TYPE ? superType == Long.TYPE || superType == Double.TYPE : subType == Float.TYPE && superType == Double.TYPE;
    }

    private static Map<Class<?>, Class<?>> createWrapperToPrimitiveTypes() {
        Map<Class<?>, Class<?>> classes = new IdentityHashMap<>();
        classes.put(Void.class, Void.TYPE);
        classes.put(Boolean.class, Boolean.TYPE);
        classes.put(Byte.class, Byte.TYPE);
        classes.put(Character.class, Character.TYPE);
        classes.put(Short.class, Short.TYPE);
        classes.put(Integer.class, Integer.TYPE);
        classes.put(Long.class, Long.TYPE);
        classes.put(Float.class, Float.TYPE);
        classes.put(Double.class, Double.TYPE);
        return classes;
    }

    private static Set<Class<?>> createPrimitiveWrapperTypes() {
        Map<Class<?>, Class<?>> classes = new IdentityHashMap<>();
        addClassHierarchy(classes, Boolean.class);
        addClassHierarchy(classes, Byte.class);
        addClassHierarchy(classes, Character.class);
        addClassHierarchy(classes, Short.class);
        addClassHierarchy(classes, Integer.class);
        addClassHierarchy(classes, Long.class);
        addClassHierarchy(classes, Float.class);
        addClassHierarchy(classes, Double.class);
        return classes.keySet();
    }

    private static void addClassHierarchy(Map<Class<?>, Class<?>> map, Class<?> clazz) {
        Class<?>[] interfaces;
        if (clazz == null) {
            return;
        }
        map.put(clazz, clazz);
        addClassHierarchy(map, clazz.getSuperclass());
        for (Class<?> itf : clazz.getInterfaces()) {
            addClassHierarchy(map, itf);
        }
    }

    private static boolean isAssignableFromBoxedPrimitive(Class<?> clazz) {
        return PRIMITIVE_WRAPPER_TYPES.contains(clazz);
    }

    public static Class<?> getPrimitiveTypeByName(String name) {
        return PRIMITIVE_TYPES_BY_NAME.get(name);
    }

    public static Class<?> getPrimitiveType(Class<?> wrapperType) {
        return WRAPPER_TO_PRIMITIVE_TYPES.get(wrapperType);
    }

    public static Class<?> getWrapperType(Class<?> primitiveType) {
        return WRAPPER_TYPES.get(primitiveType);
    }

    public static boolean isWrapperType(Class<?> type) {
        return PRIMITIVE_TYPES.containsKey(type);
    }
}
