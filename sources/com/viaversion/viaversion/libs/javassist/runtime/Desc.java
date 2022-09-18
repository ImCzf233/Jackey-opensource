package com.viaversion.viaversion.libs.javassist.runtime;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/runtime/Desc.class */
public class Desc {
    public static boolean useContextClassLoader = false;
    private static final ThreadLocal<Boolean> USE_CONTEXT_CLASS_LOADER_LOCALLY = new ThreadLocal<Boolean>() { // from class: com.viaversion.viaversion.libs.javassist.runtime.Desc.1
        @Override // java.lang.ThreadLocal
        public Boolean initialValue() {
            return false;
        }
    };

    public static void setUseContextClassLoaderLocally() {
        USE_CONTEXT_CLASS_LOADER_LOCALLY.set(true);
    }

    public static void resetUseContextClassLoaderLocally() {
        USE_CONTEXT_CLASS_LOADER_LOCALLY.remove();
    }

    private static Class<?> getClassObject(String name) throws ClassNotFoundException {
        if (useContextClassLoader || USE_CONTEXT_CLASS_LOADER_LOCALLY.get().booleanValue()) {
            return Class.forName(name, true, Thread.currentThread().getContextClassLoader());
        }
        return Class.forName(name);
    }

    public static Class<?> getClazz(String name) {
        try {
            return getClassObject(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("$class: internal error, could not find class '" + name + "' (Desc.useContextClassLoader: " + Boolean.toString(useContextClassLoader) + ")", e);
        }
    }

    public static Class<?>[] getParams(String desc) {
        if (desc.charAt(0) != '(') {
            throw new RuntimeException("$sig: internal error");
        }
        return getType(desc, desc.length(), 1, 0);
    }

    public static Class<?> getType(String desc) {
        Class<?>[] result = getType(desc, desc.length(), 0, 0);
        if (result == null || result.length != 1) {
            throw new RuntimeException("$type: internal error");
        }
        return result[0];
    }

    private static Class<?>[] getType(String desc, int descLen, int start, int num) {
        Class<?> clazz;
        if (start >= descLen) {
            return new Class[num];
        }
        char c = desc.charAt(start);
        switch (c) {
            case 'B':
                clazz = Byte.TYPE;
                break;
            case 'C':
                clazz = Character.TYPE;
                break;
            case 'D':
                clazz = Double.TYPE;
                break;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'W':
            case 'X':
            case 'Y':
            default:
                return new Class[num];
            case 'F':
                clazz = Float.TYPE;
                break;
            case 'I':
                clazz = Integer.TYPE;
                break;
            case 'J':
                clazz = Long.TYPE;
                break;
            case 'L':
            case '[':
                return getClassType(desc, descLen, start, num);
            case 'S':
                clazz = Short.TYPE;
                break;
            case 'V':
                clazz = Void.TYPE;
                break;
            case 'Z':
                clazz = Boolean.TYPE;
                break;
        }
        Class<?>[] result = getType(desc, descLen, start + 1, num + 1);
        result[num] = clazz;
        return result;
    }

    private static Class<?>[] getClassType(String desc, int descLen, int start, int num) {
        String cname;
        int end = start;
        while (desc.charAt(end) == '[') {
            end++;
        }
        if (desc.charAt(end) == 'L') {
            end = desc.indexOf(59, end);
            if (end < 0) {
                throw new IndexOutOfBoundsException("bad descriptor");
            }
        }
        if (desc.charAt(start) == 'L') {
            cname = desc.substring(start + 1, end);
        } else {
            cname = desc.substring(start, end + 1);
        }
        Class<?>[] result = getType(desc, descLen, end + 1, num + 1);
        try {
            result[num] = getClassObject(cname.replace('/', '.'));
            return result;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
