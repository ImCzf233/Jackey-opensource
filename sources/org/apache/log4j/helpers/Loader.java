package org.apache.log4j.helpers;

import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/Loader.class */
public class Loader {
    static final String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";
    private static boolean java1;
    private static boolean ignoreTCL;
    static Class class$org$apache$log4j$helpers$Loader;
    static Class class$java$lang$Thread;

    static {
        int i;
        java1 = true;
        ignoreTCL = false;
        String prop = OptionConverter.getSystemProperty("java.version", null);
        if (prop != null && (i = prop.indexOf(46)) != -1 && prop.charAt(i + 1) != '1') {
            java1 = false;
        }
        String ignoreTCLProp = OptionConverter.getSystemProperty("log4j.ignoreTCL", null);
        if (ignoreTCLProp != null) {
            ignoreTCL = OptionConverter.toBoolean(ignoreTCLProp, true);
        }
    }

    public static URL getResource(String resource, Class clazz) {
        return getResource(resource);
    }

    public static URL getResource(String resource) {
        Class cls;
        ClassLoader classLoader;
        try {
            if (!java1 && !ignoreTCL && (classLoader = getTCL()) != null) {
                LogLog.debug(new StringBuffer().append("Trying to find [").append(resource).append("] using context classloader ").append(classLoader).append(".").toString());
                URL url = classLoader.getResource(resource);
                if (url != null) {
                    return url;
                }
            }
            if (class$org$apache$log4j$helpers$Loader == null) {
                cls = class$("org.apache.log4j.helpers.Loader");
                class$org$apache$log4j$helpers$Loader = cls;
            } else {
                cls = class$org$apache$log4j$helpers$Loader;
            }
            ClassLoader classLoader2 = cls.getClassLoader();
            if (classLoader2 != null) {
                LogLog.debug(new StringBuffer().append("Trying to find [").append(resource).append("] using ").append(classLoader2).append(" class loader.").toString());
                URL url2 = classLoader2.getResource(resource);
                if (url2 != null) {
                    return url2;
                }
            }
        } catch (IllegalAccessException t) {
            LogLog.warn(TSTR, t);
        } catch (InvocationTargetException t2) {
            if ((t2.getTargetException() instanceof InterruptedException) || (t2.getTargetException() instanceof InterruptedIOException)) {
                Thread.currentThread().interrupt();
            }
            LogLog.warn(TSTR, t2);
        } catch (Throwable t3) {
            LogLog.warn(TSTR, t3);
        }
        LogLog.debug(new StringBuffer().append("Trying to find [").append(resource).append("] using ClassLoader.getSystemResource().").toString());
        return ClassLoader.getSystemResource(resource);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    public static boolean isJava1() {
        return java1;
    }

    private static ClassLoader getTCL() throws IllegalAccessException, InvocationTargetException {
        Class cls;
        try {
            if (class$java$lang$Thread == null) {
                cls = class$("java.lang.Thread");
                class$java$lang$Thread = cls;
            } else {
                cls = class$java$lang$Thread;
            }
            Method method = cls.getMethod("getContextClassLoader", null);
            return (ClassLoader) method.invoke(Thread.currentThread(), null);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Class loadClass(String clazz) throws ClassNotFoundException {
        if (java1 || ignoreTCL) {
            return Class.forName(clazz);
        }
        try {
            return getTCL().loadClass(clazz);
        } catch (InvocationTargetException e) {
            if ((e.getTargetException() instanceof InterruptedException) || (e.getTargetException() instanceof InterruptedIOException)) {
                Thread.currentThread().interrupt();
            }
            return Class.forName(clazz);
        } catch (Throwable th) {
            return Class.forName(clazz);
        }
    }
}
