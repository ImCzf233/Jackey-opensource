package org.apache.log4j.helpers;

import java.io.InputStream;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LoggerRepository;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/OptionConverter.class */
public class OptionConverter {
    static String DELIM_START = "${";
    static char DELIM_STOP = '}';
    static int DELIM_START_LEN = 2;
    static int DELIM_STOP_LEN = 1;
    static Class class$java$lang$String;
    static Class class$org$apache$log4j$Level;
    static Class class$org$apache$log4j$spi$Configurator;

    private OptionConverter() {
    }

    public static String[] concatanateArrays(String[] l, String[] r) {
        int len = l.length + r.length;
        String[] a = new String[len];
        System.arraycopy(l, 0, a, 0, l.length);
        System.arraycopy(r, 0, a, l.length, r.length);
        return a;
    }

    public static String convertSpecialChars(String s) {
        int len = s.length();
        StringBuffer sbuf = new StringBuffer(len);
        int i = 0;
        while (i < len) {
            int i2 = i;
            i++;
            char c = s.charAt(i2);
            if (c == '\\') {
                i++;
                c = s.charAt(i);
                if (c == 'n') {
                    c = '\n';
                } else if (c == 'r') {
                    c = '\r';
                } else if (c == 't') {
                    c = '\t';
                } else if (c == 'f') {
                    c = '\f';
                } else if (c == '\b') {
                    c = '\b';
                } else if (c == '\"') {
                    c = '\"';
                } else if (c == '\'') {
                    c = '\'';
                } else if (c == '\\') {
                    c = '\\';
                }
            }
            sbuf.append(c);
        }
        return sbuf.toString();
    }

    public static String getSystemProperty(String key, String def) {
        try {
            return System.getProperty(key, def);
        } catch (Throwable th) {
            LogLog.debug(new StringBuffer().append("Was not allowed to read system property \"").append(key).append("\".").toString());
            return def;
        }
    }

    public static Object instantiateByKey(Properties props, String key, Class superClass, Object defaultValue) {
        String className = findAndSubst(key, props);
        if (className == null) {
            LogLog.error(new StringBuffer().append("Could not find value for key ").append(key).toString());
            return defaultValue;
        }
        return instantiateByClassName(className.trim(), superClass, defaultValue);
    }

    public static boolean toBoolean(String value, boolean dEfault) {
        if (value == null) {
            return dEfault;
        }
        String trimmedVal = value.trim();
        if ("true".equalsIgnoreCase(trimmedVal)) {
            return true;
        }
        if ("false".equalsIgnoreCase(trimmedVal)) {
            return false;
        }
        return dEfault;
    }

    public static int toInt(String value, int dEfault) {
        if (value != null) {
            String s = value.trim();
            try {
                return Integer.valueOf(s).intValue();
            } catch (NumberFormatException e) {
                LogLog.error(new StringBuffer().append("[").append(s).append("] is not in proper int form.").toString());
                e.printStackTrace();
            }
        }
        return dEfault;
    }

    public static Level toLevel(String value, Level defaultValue) {
        Class cls;
        Class cls2;
        if (value == null) {
            return defaultValue;
        }
        String value2 = value.trim();
        int hashIndex = value2.indexOf(35);
        if (hashIndex == -1) {
            if (!DateLayout.NULL_DATE_FORMAT.equalsIgnoreCase(value2)) {
                return Level.toLevel(value2, defaultValue);
            }
            return null;
        }
        Level result = defaultValue;
        String clazz = value2.substring(hashIndex + 1);
        String levelName = value2.substring(0, hashIndex);
        if (DateLayout.NULL_DATE_FORMAT.equalsIgnoreCase(levelName)) {
            return null;
        }
        LogLog.debug(new StringBuffer().append("toLevel:class=[").append(clazz).append("]").append(":pri=[").append(levelName).append("]").toString());
        try {
            Class customLevel = Loader.loadClass(clazz);
            Class[] paramTypes = new Class[2];
            if (class$java$lang$String == null) {
                cls = class$("java.lang.String");
                class$java$lang$String = cls;
            } else {
                cls = class$java$lang$String;
            }
            paramTypes[0] = cls;
            if (class$org$apache$log4j$Level == null) {
                cls2 = class$("org.apache.log4j.Level");
                class$org$apache$log4j$Level = cls2;
            } else {
                cls2 = class$org$apache$log4j$Level;
            }
            paramTypes[1] = cls2;
            Method toLevelMethod = customLevel.getMethod("toLevel", paramTypes);
            Object[] params = {levelName, defaultValue};
            Object o = toLevelMethod.invoke(null, params);
            result = (Level) o;
        } catch (ClassCastException e) {
            LogLog.warn(new StringBuffer().append("class [").append(clazz).append("] is not a subclass of org.apache.log4j.Level").toString(), e);
        } catch (ClassNotFoundException e2) {
            LogLog.warn(new StringBuffer().append("custom level class [").append(clazz).append("] not found.").toString());
        } catch (IllegalAccessException e3) {
            LogLog.warn(new StringBuffer().append("class [").append(clazz).append("] cannot be instantiated due to access restrictions").toString(), e3);
        } catch (NoSuchMethodException e4) {
            LogLog.warn(new StringBuffer().append("custom level class [").append(clazz).append("]").append(" does not have a class function toLevel(String, Level)").toString(), e4);
        } catch (RuntimeException e5) {
            LogLog.warn(new StringBuffer().append("class [").append(clazz).append("], level [").append(levelName).append("] conversion failed.").toString(), e5);
        } catch (InvocationTargetException e6) {
            if ((e6.getTargetException() instanceof InterruptedException) || (e6.getTargetException() instanceof InterruptedIOException)) {
                Thread.currentThread().interrupt();
            }
            LogLog.warn(new StringBuffer().append("custom level class [").append(clazz).append("]").append(" could not be instantiated").toString(), e6);
        }
        return result;
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    public static long toFileSize(String value, long dEfault) {
        if (value == null) {
            return dEfault;
        }
        String s = value.trim().toUpperCase();
        long multiplier = 1;
        int index = s.indexOf("KB");
        if (index != -1) {
            multiplier = 1024;
            s = s.substring(0, index);
        } else {
            int index2 = s.indexOf("MB");
            if (index2 != -1) {
                multiplier = 1048576;
                s = s.substring(0, index2);
            } else {
                int index3 = s.indexOf("GB");
                if (index3 != -1) {
                    multiplier = 1073741824;
                    s = s.substring(0, index3);
                }
            }
        }
        if (s != null) {
            try {
                return Long.valueOf(s).longValue() * multiplier;
            } catch (NumberFormatException e) {
                LogLog.error(new StringBuffer().append("[").append(s).append("] is not in proper int form.").toString());
                LogLog.error(new StringBuffer().append("[").append(value).append("] not in expected format.").toString(), e);
            }
        }
        return dEfault;
    }

    public static String findAndSubst(String key, Properties props) {
        String value = props.getProperty(key);
        if (value == null) {
            return null;
        }
        try {
            return substVars(value, props);
        } catch (IllegalArgumentException e) {
            LogLog.error(new StringBuffer().append("Bad option value [").append(value).append("].").toString(), e);
            return value;
        }
    }

    public static Object instantiateByClassName(String className, Class superClass, Object defaultValue) {
        if (className != null) {
            try {
                Class classObj = Loader.loadClass(className);
                if (!superClass.isAssignableFrom(classObj)) {
                    LogLog.error(new StringBuffer().append("A \"").append(className).append("\" object is not assignable to a \"").append(superClass.getName()).append("\" variable.").toString());
                    LogLog.error(new StringBuffer().append("The class \"").append(superClass.getName()).append("\" was loaded by ").toString());
                    LogLog.error(new StringBuffer().append("[").append(superClass.getClassLoader()).append("] whereas object of type ").toString());
                    LogLog.error(new StringBuffer().append("\"").append(classObj.getName()).append("\" was loaded by [").append(classObj.getClassLoader()).append("].").toString());
                    return defaultValue;
                }
                return classObj.newInstance();
            } catch (ClassNotFoundException e) {
                LogLog.error(new StringBuffer().append("Could not instantiate class [").append(className).append("].").toString(), e);
            } catch (IllegalAccessException e2) {
                LogLog.error(new StringBuffer().append("Could not instantiate class [").append(className).append("].").toString(), e2);
            } catch (InstantiationException e3) {
                LogLog.error(new StringBuffer().append("Could not instantiate class [").append(className).append("].").toString(), e3);
            } catch (RuntimeException e4) {
                LogLog.error(new StringBuffer().append("Could not instantiate class [").append(className).append("].").toString(), e4);
            }
        }
        return defaultValue;
    }

    public static String substVars(String val, Properties props) throws IllegalArgumentException {
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        while (true) {
            int i2 = i;
            int j = val.indexOf(DELIM_START, i2);
            if (j == -1) {
                if (i2 == 0) {
                    return val;
                }
                sbuf.append(val.substring(i2, val.length()));
                return sbuf.toString();
            }
            sbuf.append(val.substring(i2, j));
            int k = val.indexOf(DELIM_STOP, j);
            if (k == -1) {
                throw new IllegalArgumentException(new StringBuffer().append('\"').append(val).append("\" has no closing brace. Opening brace at position ").append(j).append('.').toString());
            }
            String key = val.substring(j + DELIM_START_LEN, k);
            String replacement = getSystemProperty(key, null);
            if (replacement == null && props != null) {
                replacement = props.getProperty(key);
            }
            if (replacement != null) {
                String recursiveReplacement = substVars(replacement, props);
                sbuf.append(recursiveReplacement);
            }
            i = k + DELIM_STOP_LEN;
        }
    }

    public static void selectAndConfigure(InputStream inputStream, String clazz, LoggerRepository hierarchy) {
        Configurator configurator;
        Class cls;
        if (clazz != null) {
            LogLog.debug(new StringBuffer().append("Preferred configurator class: ").append(clazz).toString());
            if (class$org$apache$log4j$spi$Configurator == null) {
                cls = class$("org.apache.log4j.spi.Configurator");
                class$org$apache$log4j$spi$Configurator = cls;
            } else {
                cls = class$org$apache$log4j$spi$Configurator;
            }
            configurator = (Configurator) instantiateByClassName(clazz, cls, null);
            if (configurator == null) {
                LogLog.error(new StringBuffer().append("Could not instantiate configurator [").append(clazz).append("].").toString());
                return;
            }
        } else {
            configurator = new PropertyConfigurator();
        }
        configurator.doConfigure(inputStream, hierarchy);
    }

    public static void selectAndConfigure(URL url, String clazz, LoggerRepository hierarchy) {
        Configurator configurator;
        Class cls;
        String filename = url.getFile();
        if (clazz == null && filename != null && filename.endsWith(".xml")) {
            clazz = "org.apache.log4j.xml.DOMConfigurator";
        }
        if (clazz != null) {
            LogLog.debug(new StringBuffer().append("Preferred configurator class: ").append(clazz).toString());
            String str = clazz;
            if (class$org$apache$log4j$spi$Configurator == null) {
                cls = class$("org.apache.log4j.spi.Configurator");
                class$org$apache$log4j$spi$Configurator = cls;
            } else {
                cls = class$org$apache$log4j$spi$Configurator;
            }
            configurator = (Configurator) instantiateByClassName(str, cls, null);
            if (configurator == null) {
                LogLog.error(new StringBuffer().append("Could not instantiate configurator [").append(clazz).append("].").toString());
                return;
            }
        } else {
            configurator = new PropertyConfigurator();
        }
        configurator.doConfigure(url, hierarchy);
    }
}
