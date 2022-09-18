package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.InstanceCreator;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.internal.reflect.ReflectionAccessor;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/internal/ConstructorConstructor.class */
public final class ConstructorConstructor {
    private final Map<Type, InstanceCreator<?>> instanceCreators;
    private final ReflectionAccessor accessor = ReflectionAccessor.getInstance();

    public ConstructorConstructor(Map<Type, InstanceCreator<?>> instanceCreators) {
        this.instanceCreators = instanceCreators;
    }

    public <T> ObjectConstructor<T> get(TypeToken<T> typeToken) {
        final Type type = typeToken.getType();
        Class<? super T> rawType = typeToken.getRawType();
        final InstanceCreator<?> instanceCreator = this.instanceCreators.get(type);
        if (instanceCreator != null) {
            return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.1
                /* JADX WARN: Type inference failed for: r0v2, types: [T, java.lang.Object] */
                @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
                public T construct() {
                    return instanceCreator.createInstance(type);
                }
            };
        }
        final InstanceCreator<?> instanceCreator2 = this.instanceCreators.get(rawType);
        if (instanceCreator2 != null) {
            return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.2
                /* JADX WARN: Type inference failed for: r0v2, types: [T, java.lang.Object] */
                @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
                public T construct() {
                    return instanceCreator2.createInstance(type);
                }
            };
        }
        ObjectConstructor<T> defaultConstructor = newDefaultConstructor(rawType);
        if (defaultConstructor != null) {
            return defaultConstructor;
        }
        ObjectConstructor<T> defaultImplementation = newDefaultImplementationConstructor(type, rawType);
        if (defaultImplementation != null) {
            return defaultImplementation;
        }
        return newUnsafeAllocator(type, rawType);
    }

    private <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> rawType) {
        try {
            final Constructor<? super T> constructor = rawType.getDeclaredConstructor(new Class[0]);
            if (!constructor.isAccessible()) {
                this.accessor.makeAccessible(constructor);
            }
            return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.3
                /* JADX WARN: Type inference failed for: r0v6, types: [T, java.lang.Object] */
                @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
                public T construct() {
                    try {
                        return constructor.newInstance(null);
                    } catch (IllegalAccessException e) {
                        throw new AssertionError(e);
                    } catch (InstantiationException e2) {
                        throw new RuntimeException("Failed to invoke " + constructor + " with no args", e2);
                    } catch (InvocationTargetException e3) {
                        throw new RuntimeException("Failed to invoke " + constructor + " with no args", e3.getTargetException());
                    }
                }
            };
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private <T> ObjectConstructor<T> newDefaultImplementationConstructor(final Type type, Class<? super T> rawType) {
        if (Collection.class.isAssignableFrom(rawType)) {
            if (SortedSet.class.isAssignableFrom(rawType)) {
                return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.4
                    /* JADX WARN: Type inference failed for: r0v0, types: [T, java.util.TreeSet] */
                    @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
                    public T construct() {
                        return new TreeSet();
                    }
                };
            }
            if (EnumSet.class.isAssignableFrom(rawType)) {
                return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.5
                    /* JADX WARN: Type inference failed for: r0v14, types: [T, java.util.EnumSet] */
                    @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
                    public T construct() {
                        if (type instanceof ParameterizedType) {
                            Type elementType = ((ParameterizedType) type).getActualTypeArguments()[0];
                            if (elementType instanceof Class) {
                                return EnumSet.noneOf((Class) elementType);
                            }
                            throw new JsonIOException("Invalid EnumSet type: " + type.toString());
                        }
                        throw new JsonIOException("Invalid EnumSet type: " + type.toString());
                    }
                };
            }
            if (Set.class.isAssignableFrom(rawType)) {
                return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.6
                    /* JADX WARN: Type inference failed for: r0v0, types: [T, java.util.LinkedHashSet] */
                    @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
                    public T construct() {
                        return new LinkedHashSet();
                    }
                };
            }
            if (Queue.class.isAssignableFrom(rawType)) {
                return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.7
                    /* JADX WARN: Type inference failed for: r0v0, types: [T, java.util.ArrayDeque] */
                    @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
                    public T construct() {
                        return new ArrayDeque();
                    }
                };
            }
            return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.8
                /* JADX WARN: Type inference failed for: r0v0, types: [T, java.util.ArrayList] */
                @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
                public T construct() {
                    return new ArrayList();
                }
            };
        } else if (Map.class.isAssignableFrom(rawType)) {
            if (ConcurrentNavigableMap.class.isAssignableFrom(rawType)) {
                return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.9
                    /* JADX WARN: Type inference failed for: r0v0, types: [java.util.concurrent.ConcurrentSkipListMap, T] */
                    @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
                    public T construct() {
                        return new ConcurrentSkipListMap();
                    }
                };
            }
            if (ConcurrentMap.class.isAssignableFrom(rawType)) {
                return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.10
                    /* JADX WARN: Type inference failed for: r0v0, types: [T, java.util.concurrent.ConcurrentHashMap] */
                    @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
                    public T construct() {
                        return new ConcurrentHashMap();
                    }
                };
            }
            if (SortedMap.class.isAssignableFrom(rawType)) {
                return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.11
                    /* JADX WARN: Type inference failed for: r0v0, types: [java.util.TreeMap, T] */
                    @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
                    public T construct() {
                        return new TreeMap();
                    }
                };
            }
            if ((type instanceof ParameterizedType) && !String.class.isAssignableFrom(TypeToken.get(((ParameterizedType) type).getActualTypeArguments()[0]).getRawType())) {
                return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.12
                    /* JADX WARN: Type inference failed for: r0v0, types: [java.util.LinkedHashMap, T] */
                    @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
                    public T construct() {
                        return new LinkedHashMap();
                    }
                };
            }
            return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.13
                /* JADX WARN: Type inference failed for: r0v0, types: [T, com.viaversion.viaversion.libs.gson.internal.LinkedTreeMap] */
                @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
                public T construct() {
                    return new LinkedTreeMap();
                }
            };
        } else {
            return null;
        }
    }

    private <T> ObjectConstructor<T> newUnsafeAllocator(final Type type, final Class<? super T> rawType) {
        return new ObjectConstructor<T>() { // from class: com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor.14
            private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();

            /* JADX WARN: Type inference failed for: r0v3, types: [T, java.lang.Object] */
            @Override // com.viaversion.viaversion.libs.gson.internal.ObjectConstructor
            public T construct() {
                try {
                    return this.unsafeAllocator.newInstance(rawType);
                } catch (Exception e) {
                    throw new RuntimeException("Unable to invoke no-args constructor for " + type + ". Registering an InstanceCreator with Gson for this type may fix this problem.", e);
                }
            }
        };
    }

    public String toString() {
        return this.instanceCreators.toString();
    }
}
