package org.apache.log4j;

import java.util.ResourceBundle;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/LogSF.class */
public final class LogSF extends LogXF {
    private static final String FQCN;
    static Class class$org$apache$log4j$LogSF;

    private LogSF() {
    }

    private static String format(String pattern, Object[] arguments) {
        int i;
        int i2;
        if (pattern != null) {
            String retval = "";
            int count = 0;
            int prev = 0;
            int indexOf = pattern.indexOf("{");
            while (true) {
                int pos = indexOf;
                if (pos >= 0) {
                    if (pos == 0 || pattern.charAt(pos - 1) != '\\') {
                        String retval2 = new StringBuffer().append(retval).append(pattern.substring(prev, pos)).toString();
                        if (pos + 1 < pattern.length() && pattern.charAt(pos + 1) == '}') {
                            if (arguments != null && count < arguments.length) {
                                int i3 = count;
                                count++;
                                retval = new StringBuffer().append(retval2).append(arguments[i3]).toString();
                            } else {
                                retval = new StringBuffer().append(retval2).append("{}").toString();
                            }
                            i2 = pos;
                            i = 2;
                        } else {
                            retval = new StringBuffer().append(retval2).append("{").toString();
                            i2 = pos;
                            i = 1;
                        }
                    } else {
                        retval = new StringBuffer().append(retval).append(pattern.substring(prev, pos - 1)).append("{").toString();
                        i2 = pos;
                        i = 1;
                    }
                    prev = i2 + i;
                    indexOf = pattern.indexOf("{", prev);
                } else {
                    return new StringBuffer().append(retval).append(pattern.substring(prev)).toString();
                }
            }
        } else {
            return null;
        }
    }

    private static String format(String pattern, Object arg0) {
        if (pattern != null) {
            if (pattern.indexOf("\\{") >= 0) {
                return format(pattern, new Object[]{arg0});
            }
            int pos = pattern.indexOf("{}");
            if (pos >= 0) {
                return new StringBuffer().append(pattern.substring(0, pos)).append(arg0).append(pattern.substring(pos + 2)).toString();
            }
        }
        return pattern;
    }

    private static String format(String resourceBundleName, String key, Object[] arguments) {
        String pattern;
        if (resourceBundleName != null) {
            try {
                ResourceBundle bundle = ResourceBundle.getBundle(resourceBundleName);
                pattern = bundle.getString(key);
            } catch (Exception e) {
                pattern = key;
            }
        } else {
            pattern = key;
        }
        return format(pattern, arguments);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    static {
        Class cls;
        if (class$org$apache$log4j$LogSF == null) {
            cls = class$("org.apache.log4j.LogSF");
            class$org$apache$log4j$LogSF = cls;
        } else {
            cls = class$org$apache$log4j$LogSF;
        }
        FQCN = cls.getName();
    }

    private static void forcedLog(Logger logger, Level level, String msg) {
        logger.callAppenders(new LoggingEvent(FQCN, logger, level, msg, null));
    }

    private static void forcedLog(Logger logger, Level level, String msg, Throwable t) {
        logger.callAppenders(new LoggingEvent(FQCN, logger, level, msg, t));
    }

    public static void trace(Logger logger, String pattern, Object[] arguments) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, arguments));
        }
    }

    public static void debug(Logger logger, String pattern, Object[] arguments) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, arguments));
        }
    }

    public static void info(Logger logger, String pattern, Object[] arguments) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, arguments));
        }
    }

    public static void warn(Logger logger, String pattern, Object[] arguments) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, arguments));
        }
    }

    public static void error(Logger logger, String pattern, Object[] arguments) {
        if (logger.isEnabledFor(Level.ERROR)) {
            forcedLog(logger, Level.ERROR, format(pattern, arguments));
        }
    }

    public static void fatal(Logger logger, String pattern, Object[] arguments) {
        if (logger.isEnabledFor(Level.FATAL)) {
            forcedLog(logger, Level.FATAL, format(pattern, arguments));
        }
    }

    public static void trace(Logger logger, Throwable t, String pattern, Object[] arguments) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, arguments), t);
        }
    }

    public static void debug(Logger logger, Throwable t, String pattern, Object[] arguments) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, arguments), t);
        }
    }

    public static void info(Logger logger, Throwable t, String pattern, Object[] arguments) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, arguments), t);
        }
    }

    public static void warn(Logger logger, Throwable t, String pattern, Object[] arguments) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, arguments), t);
        }
    }

    public static void error(Logger logger, Throwable t, String pattern, Object[] arguments) {
        if (logger.isEnabledFor(Level.ERROR)) {
            forcedLog(logger, Level.ERROR, format(pattern, arguments), t);
        }
    }

    public static void fatal(Logger logger, Throwable t, String pattern, Object[] arguments) {
        if (logger.isEnabledFor(Level.FATAL)) {
            forcedLog(logger, Level.FATAL, format(pattern, arguments), t);
        }
    }

    public static void trace(Logger logger, String pattern, boolean argument) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, valueOf(argument)));
        }
    }

    public static void trace(Logger logger, String pattern, char argument) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, valueOf(argument)));
        }
    }

    public static void trace(Logger logger, String pattern, byte argument) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, valueOf(argument)));
        }
    }

    public static void trace(Logger logger, String pattern, short argument) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, valueOf(argument)));
        }
    }

    public static void trace(Logger logger, String pattern, int argument) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, valueOf(argument)));
        }
    }

    public static void trace(Logger logger, String pattern, long argument) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, valueOf(argument)));
        }
    }

    public static void trace(Logger logger, String pattern, float argument) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, valueOf(argument)));
        }
    }

    public static void trace(Logger logger, String pattern, double argument) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, valueOf(argument)));
        }
    }

    public static void trace(Logger logger, String pattern, Object argument) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, argument));
        }
    }

    public static void trace(Logger logger, String pattern, Object arg0, Object arg1) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, toArray(arg0, arg1)));
        }
    }

    public static void trace(Logger logger, String pattern, Object arg0, Object arg1, Object arg2) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, toArray(arg0, arg1, arg2)));
        }
    }

    public static void trace(Logger logger, String pattern, Object arg0, Object arg1, Object arg2, Object arg3) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(pattern, toArray(arg0, arg1, arg2, arg3)));
        }
    }

    public static void debug(Logger logger, String pattern, boolean argument) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, valueOf(argument)));
        }
    }

    public static void debug(Logger logger, String pattern, char argument) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, valueOf(argument)));
        }
    }

    public static void debug(Logger logger, String pattern, byte argument) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, valueOf(argument)));
        }
    }

    public static void debug(Logger logger, String pattern, short argument) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, valueOf(argument)));
        }
    }

    public static void debug(Logger logger, String pattern, int argument) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, valueOf(argument)));
        }
    }

    public static void debug(Logger logger, String pattern, long argument) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, valueOf(argument)));
        }
    }

    public static void debug(Logger logger, String pattern, float argument) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, valueOf(argument)));
        }
    }

    public static void debug(Logger logger, String pattern, double argument) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, valueOf(argument)));
        }
    }

    public static void debug(Logger logger, String pattern, Object argument) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, argument));
        }
    }

    public static void debug(Logger logger, String pattern, Object arg0, Object arg1) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, toArray(arg0, arg1)));
        }
    }

    public static void debug(Logger logger, String pattern, Object arg0, Object arg1, Object arg2) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, toArray(arg0, arg1, arg2)));
        }
    }

    public static void debug(Logger logger, String pattern, Object arg0, Object arg1, Object arg2, Object arg3) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(pattern, toArray(arg0, arg1, arg2, arg3)));
        }
    }

    public static void info(Logger logger, String pattern, boolean argument) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, valueOf(argument)));
        }
    }

    public static void info(Logger logger, String pattern, char argument) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, valueOf(argument)));
        }
    }

    public static void info(Logger logger, String pattern, byte argument) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, valueOf(argument)));
        }
    }

    public static void info(Logger logger, String pattern, short argument) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, valueOf(argument)));
        }
    }

    public static void info(Logger logger, String pattern, int argument) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, valueOf(argument)));
        }
    }

    public static void info(Logger logger, String pattern, long argument) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, valueOf(argument)));
        }
    }

    public static void info(Logger logger, String pattern, float argument) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, valueOf(argument)));
        }
    }

    public static void info(Logger logger, String pattern, double argument) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, valueOf(argument)));
        }
    }

    public static void info(Logger logger, String pattern, Object argument) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, argument));
        }
    }

    public static void info(Logger logger, String pattern, Object arg0, Object arg1) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, toArray(arg0, arg1)));
        }
    }

    public static void info(Logger logger, String pattern, Object arg0, Object arg1, Object arg2) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, toArray(arg0, arg1, arg2)));
        }
    }

    public static void info(Logger logger, String pattern, Object arg0, Object arg1, Object arg2, Object arg3) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(pattern, toArray(arg0, arg1, arg2, arg3)));
        }
    }

    public static void warn(Logger logger, String pattern, boolean argument) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, valueOf(argument)));
        }
    }

    public static void warn(Logger logger, String pattern, char argument) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, valueOf(argument)));
        }
    }

    public static void warn(Logger logger, String pattern, byte argument) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, valueOf(argument)));
        }
    }

    public static void warn(Logger logger, String pattern, short argument) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, valueOf(argument)));
        }
    }

    public static void warn(Logger logger, String pattern, int argument) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, valueOf(argument)));
        }
    }

    public static void warn(Logger logger, String pattern, long argument) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, valueOf(argument)));
        }
    }

    public static void warn(Logger logger, String pattern, float argument) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, valueOf(argument)));
        }
    }

    public static void warn(Logger logger, String pattern, double argument) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, valueOf(argument)));
        }
    }

    public static void warn(Logger logger, String pattern, Object argument) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, argument));
        }
    }

    public static void warn(Logger logger, String pattern, Object arg0, Object arg1) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, toArray(arg0, arg1)));
        }
    }

    public static void warn(Logger logger, String pattern, Object arg0, Object arg1, Object arg2) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, toArray(arg0, arg1, arg2)));
        }
    }

    public static void warn(Logger logger, String pattern, Object arg0, Object arg1, Object arg2, Object arg3) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(pattern, toArray(arg0, arg1, arg2, arg3)));
        }
    }

    public static void log(Logger logger, Level level, String pattern, Object[] parameters) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, parameters));
        }
    }

    public static void log(Logger logger, Level level, Throwable t, String pattern, Object[] parameters) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, parameters), t);
        }
    }

    public static void log(Logger logger, Level level, String pattern, Object param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, toArray(param1)));
        }
    }

    public static void log(Logger logger, Level level, String pattern, boolean param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, toArray(valueOf(param1))));
        }
    }

    public static void log(Logger logger, Level level, String pattern, byte param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, toArray(valueOf(param1))));
        }
    }

    public static void log(Logger logger, Level level, String pattern, char param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, toArray(valueOf(param1))));
        }
    }

    public static void log(Logger logger, Level level, String pattern, short param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, toArray(valueOf(param1))));
        }
    }

    public static void log(Logger logger, Level level, String pattern, int param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, toArray(valueOf(param1))));
        }
    }

    public static void log(Logger logger, Level level, String pattern, long param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, toArray(valueOf(param1))));
        }
    }

    public static void log(Logger logger, Level level, String pattern, float param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, toArray(valueOf(param1))));
        }
    }

    public static void log(Logger logger, Level level, String pattern, double param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, toArray(valueOf(param1))));
        }
    }

    public static void log(Logger logger, Level level, String pattern, Object arg0, Object arg1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, toArray(arg0, arg1)));
        }
    }

    public static void log(Logger logger, Level level, String pattern, Object arg0, Object arg1, Object arg2) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, toArray(arg0, arg1, arg2)));
        }
    }

    public static void log(Logger logger, Level level, String pattern, Object arg0, Object arg1, Object arg2, Object arg3) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(pattern, toArray(arg0, arg1, arg2, arg3)));
        }
    }

    public static void logrb(Logger logger, Level level, String bundleName, String key, Object[] parameters) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, parameters));
        }
    }

    public static void logrb(Logger logger, Level level, Throwable t, String bundleName, String key, Object[] parameters) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, parameters), t);
        }
    }

    public static void logrb(Logger logger, Level level, String bundleName, String key, Object param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, toArray(param1)));
        }
    }

    public static void logrb(Logger logger, Level level, String bundleName, String key, boolean param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, toArray(valueOf(param1))));
        }
    }

    public static void logrb(Logger logger, Level level, String bundleName, String key, char param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, toArray(valueOf(param1))));
        }
    }

    public static void logrb(Logger logger, Level level, String bundleName, String key, byte param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, toArray(valueOf(param1))));
        }
    }

    public static void logrb(Logger logger, Level level, String bundleName, String key, short param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, toArray(valueOf(param1))));
        }
    }

    public static void logrb(Logger logger, Level level, String bundleName, String key, int param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, toArray(valueOf(param1))));
        }
    }

    public static void logrb(Logger logger, Level level, String bundleName, String key, long param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, toArray(valueOf(param1))));
        }
    }

    public static void logrb(Logger logger, Level level, String bundleName, String key, float param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, toArray(valueOf(param1))));
        }
    }

    public static void logrb(Logger logger, Level level, String bundleName, String key, double param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, toArray(valueOf(param1))));
        }
    }

    public static void logrb(Logger logger, Level level, String bundleName, String key, Object param0, Object param1) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, toArray(param0, param1)));
        }
    }

    public static void logrb(Logger logger, Level level, String bundleName, String key, Object param0, Object param1, Object param2) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, toArray(param0, param1, param2)));
        }
    }

    public static void logrb(Logger logger, Level level, String bundleName, String key, Object param0, Object param1, Object param2, Object param3) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(bundleName, key, toArray(param0, param1, param2, param3)));
        }
    }
}
