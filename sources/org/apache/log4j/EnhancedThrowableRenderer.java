package org.apache.log4j;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.spi.ThrowableRenderer;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/EnhancedThrowableRenderer.class */
public final class EnhancedThrowableRenderer implements ThrowableRenderer {
    private Method getStackTraceMethod;
    private Method getClassNameMethod;
    static Class class$java$lang$Throwable;

    public EnhancedThrowableRenderer() {
        Class cls;
        try {
            if (class$java$lang$Throwable == null) {
                cls = class$("java.lang.Throwable");
                class$java$lang$Throwable = cls;
            } else {
                cls = class$java$lang$Throwable;
            }
            this.getStackTraceMethod = cls.getMethod("getStackTrace", null);
            Class ste = Class.forName("java.lang.StackTraceElement");
            this.getClassNameMethod = ste.getMethod("getClassName", null);
        } catch (Exception e) {
        }
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    @Override // org.apache.log4j.spi.ThrowableRenderer
    public String[] doRender(Throwable throwable) {
        if (this.getStackTraceMethod != null) {
            try {
                Object[] elements = (Object[]) this.getStackTraceMethod.invoke(throwable, null);
                String[] lines = new String[elements.length + 1];
                lines[0] = throwable.toString();
                Map classMap = new HashMap();
                for (int i = 0; i < elements.length; i++) {
                    lines[i + 1] = formatElement(elements[i], classMap);
                }
                return lines;
            } catch (Exception e) {
            }
        }
        return DefaultThrowableRenderer.render(throwable);
    }

    private String formatElement(Object element, Map classMap) {
        String implVersion;
        URL locationURL;
        StringBuffer buf = new StringBuffer("\tat ");
        buf.append(element);
        try {
            String className = this.getClassNameMethod.invoke(element, null).toString();
            Object classDetails = classMap.get(className);
            if (classDetails != null) {
                buf.append(classDetails);
            } else {
                Class cls = findClass(className);
                int detailStart = buf.length();
                buf.append('[');
                try {
                    CodeSource source = cls.getProtectionDomain().getCodeSource();
                    if (source != null && (locationURL = source.getLocation()) != null) {
                        if ("file".equals(locationURL.getProtocol())) {
                            String path = locationURL.getPath();
                            if (path != null) {
                                int lastSlash = path.lastIndexOf(47);
                                int lastBack = path.lastIndexOf(File.separatorChar);
                                if (lastBack > lastSlash) {
                                    lastSlash = lastBack;
                                }
                                if (lastSlash <= 0 || lastSlash == path.length() - 1) {
                                    buf.append(locationURL);
                                } else {
                                    buf.append(path.substring(lastSlash + 1));
                                }
                            }
                        } else {
                            buf.append(locationURL);
                        }
                    }
                } catch (SecurityException e) {
                }
                buf.append(':');
                Package pkg = cls.getPackage();
                if (pkg != null && (implVersion = pkg.getImplementationVersion()) != null) {
                    buf.append(implVersion);
                }
                buf.append(']');
                classMap.put(className, buf.substring(detailStart));
            }
        } catch (Exception e2) {
        }
        return buf.toString();
    }

    private Class findClass(String className) throws ClassNotFoundException {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e2) {
                return getClass().getClassLoader().loadClass(className);
            }
        }
    }
}
