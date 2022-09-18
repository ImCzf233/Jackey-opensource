package org.apache.log4j.helpers;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/LogLog.class */
public class LogLog {
    public static final String DEBUG_KEY = "log4j.debug";
    public static final String CONFIG_DEBUG_KEY = "log4j.configDebug";
    protected static boolean debugEnabled;
    private static boolean quietMode = false;
    private static final String PREFIX = "log4j: ";
    private static final String ERR_PREFIX = "log4j:ERROR ";
    private static final String WARN_PREFIX = "log4j:WARN ";

    static {
        debugEnabled = false;
        String key = OptionConverter.getSystemProperty(DEBUG_KEY, null);
        if (key == null) {
            key = OptionConverter.getSystemProperty(CONFIG_DEBUG_KEY, null);
        }
        if (key != null) {
            debugEnabled = OptionConverter.toBoolean(key, true);
        }
    }

    public static void setInternalDebugging(boolean enabled) {
        debugEnabled = enabled;
    }

    public static void debug(String msg) {
        if (debugEnabled && !quietMode) {
            System.out.println(new StringBuffer().append(PREFIX).append(msg).toString());
        }
    }

    public static void debug(String msg, Throwable t) {
        if (debugEnabled && !quietMode) {
            System.out.println(new StringBuffer().append(PREFIX).append(msg).toString());
            if (t != null) {
                t.printStackTrace(System.out);
            }
        }
    }

    public static void error(String msg) {
        if (quietMode) {
            return;
        }
        System.err.println(new StringBuffer().append(ERR_PREFIX).append(msg).toString());
    }

    public static void error(String msg, Throwable t) {
        if (quietMode) {
            return;
        }
        System.err.println(new StringBuffer().append(ERR_PREFIX).append(msg).toString());
        if (t != null) {
            t.printStackTrace();
        }
    }

    public static void setQuietMode(boolean quietMode2) {
        quietMode = quietMode2;
    }

    public static void warn(String msg) {
        if (quietMode) {
            return;
        }
        System.err.println(new StringBuffer().append(WARN_PREFIX).append(msg).toString());
    }

    public static void warn(String msg, Throwable t) {
        if (quietMode) {
            return;
        }
        System.err.println(new StringBuffer().append(WARN_PREFIX).append(msg).toString());
        if (t != null) {
            t.printStackTrace();
        }
    }
}
