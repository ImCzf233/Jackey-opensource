package com.viaversion.viaversion.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/ReflectionUtil.class */
public class ReflectionUtil {
    public static Object invokeStatic(Class<?> clazz, String method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = clazz.getDeclaredMethod(method, new Class[0]);
        return m.invoke(null, new Object[0]);
    }

    public static Object invoke(Object o, String method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = o.getClass().getDeclaredMethod(method, new Class[0]);
        return m.invoke(o, new Object[0]);
    }

    public static <T> T getStatic(Class<?> clazz, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return type.cast(field.get(null));
    }

    public static void setStatic(Class<?> clazz, String f, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        field.set(null, value);
    }

    public static <T> T getSuper(Object o, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
        Field field = o.getClass().getSuperclass().getDeclaredField(f);
        field.setAccessible(true);
        return type.cast(field.get(o));
    }

    public static <T> T get(Object instance, Class<?> clazz, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return type.cast(field.get(instance));
    }

    public static <T> T get(Object o, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
        Field field = o.getClass().getDeclaredField(f);
        field.setAccessible(true);
        return type.cast(field.get(o));
    }

    public static <T> T getPublic(Object o, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
        Field field = o.getClass().getField(f);
        field.setAccessible(true);
        return type.cast(field.get(o));
    }

    public static void set(Object o, String f, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = o.getClass().getDeclaredField(f);
        field.setAccessible(true);
        field.set(o, value);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/ReflectionUtil$ClassReflection.class */
    public static final class ClassReflection {
        private final Class<?> handle;
        private final Map<String, Field> fields;
        private final Map<String, Method> methods;

        public ClassReflection(Class<?> handle) {
            this(handle, true);
        }

        public ClassReflection(Class<?> handle, boolean recursive) {
            this.fields = new ConcurrentHashMap();
            this.methods = new ConcurrentHashMap();
            this.handle = handle;
            scanFields(handle, recursive);
            scanMethods(handle, recursive);
        }

        private void scanFields(Class<?> host, boolean recursive) {
            Field[] declaredFields;
            if (recursive && host.getSuperclass() != null && host.getSuperclass() != Object.class) {
                scanFields(host.getSuperclass(), true);
            }
            for (Field field : host.getDeclaredFields()) {
                field.setAccessible(true);
                this.fields.put(field.getName(), field);
            }
        }

        private void scanMethods(Class<?> host, boolean recursive) {
            Method[] declaredMethods;
            if (recursive && host.getSuperclass() != null && host.getSuperclass() != Object.class) {
                scanMethods(host.getSuperclass(), true);
            }
            for (Method method : host.getDeclaredMethods()) {
                method.setAccessible(true);
                this.methods.put(method.getName(), method);
            }
        }

        public Object newInstance() throws ReflectiveOperationException {
            return this.handle.getConstructor(new Class[0]).newInstance(new Object[0]);
        }

        public Field getField(String name) {
            return this.fields.get(name);
        }

        public void setFieldValue(String fieldName, Object instance, Object value) throws IllegalAccessException {
            getField(fieldName).set(instance, value);
        }

        public <T> T getFieldValue(String fieldName, Object instance, Class<T> type) throws IllegalAccessException {
            return type.cast(getField(fieldName).get(instance));
        }

        public <T> T invokeMethod(Class<T> type, String methodName, Object instance, Object... args) throws InvocationTargetException, IllegalAccessException {
            return type.cast(getMethod(methodName).invoke(instance, args));
        }

        public Method getMethod(String name) {
            return this.methods.get(name);
        }

        public Collection<Field> getFields() {
            return Collections.unmodifiableCollection(this.fields.values());
        }

        public Collection<Method> getMethods() {
            return Collections.unmodifiableCollection(this.methods.values());
        }
    }
}
