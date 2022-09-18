package org.apache.log4j;

import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/LogXF.class */
public abstract class LogXF {
    protected static final Level TRACE = new Level(Level.TRACE_INT, "TRACE", 7);
    private static final String FQCN;
    static Class class$org$apache$log4j$LogXF;

    static {
        Class cls;
        if (class$org$apache$log4j$LogXF == null) {
            cls = class$("org.apache.log4j.LogXF");
            class$org$apache$log4j$LogXF = cls;
        } else {
            cls = class$org$apache$log4j$LogXF;
        }
        FQCN = cls.getName();
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    public static Boolean valueOf(boolean b) {
        if (b) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static Character valueOf(char c) {
        return new Character(c);
    }

    public static Byte valueOf(byte b) {
        return new Byte(b);
    }

    public static Short valueOf(short b) {
        return new Short(b);
    }

    public static Integer valueOf(int b) {
        return new Integer(b);
    }

    public static Long valueOf(long b) {
        return new Long(b);
    }

    public static Float valueOf(float b) {
        return new Float(b);
    }

    public static Double valueOf(double b) {
        return new Double(b);
    }

    public static Object[] toArray(Object param1) {
        return new Object[]{param1};
    }

    public static Object[] toArray(Object param1, Object param2) {
        return new Object[]{param1, param2};
    }

    public static Object[] toArray(Object param1, Object param2, Object param3) {
        return new Object[]{param1, param2, param3};
    }

    public static Object[] toArray(Object param1, Object param2, Object param3, Object param4) {
        return new Object[]{param1, param2, param3, param4};
    }

    public static void entering(Logger logger, String sourceClass, String sourceMethod) {
        if (logger.isDebugEnabled()) {
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, new StringBuffer().append(sourceClass).append(".").append(sourceMethod).append(" ENTRY").toString(), null));
        }
    }

    public static void entering(Logger logger, String sourceClass, String sourceMethod, String param) {
        if (logger.isDebugEnabled()) {
            String msg = new StringBuffer().append(sourceClass).append(".").append(sourceMethod).append(" ENTRY ").append(param).toString();
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, msg, null));
        }
    }

    public static void entering(Logger logger, String sourceClass, String sourceMethod, Object param) {
        if (logger.isDebugEnabled()) {
            String msg = new StringBuffer().append(sourceClass).append(".").append(sourceMethod).append(" ENTRY ").toString();
            if (param == null) {
                msg = new StringBuffer().append(msg).append(Configurator.NULL).toString();
            } else {
                try {
                    msg = new StringBuffer().append(msg).append(param).toString();
                } catch (Throwable th) {
                    msg = new StringBuffer().append(msg).append(LocationInfo.f402NA).toString();
                }
            }
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, msg, null));
        }
    }

    public static void entering(Logger logger, String sourceClass, String sourceMethod, Object[] params) {
        String msg;
        if (logger.isDebugEnabled()) {
            String msg2 = new StringBuffer().append(sourceClass).append(".").append(sourceMethod).append(" ENTRY ").toString();
            if (params != null && params.length > 0) {
                String delim = "{";
                for (Object obj : params) {
                    try {
                        msg2 = new StringBuffer().append(msg2).append(delim).append(obj).toString();
                    } catch (Throwable th) {
                        msg2 = new StringBuffer().append(msg2).append(delim).append(LocationInfo.f402NA).toString();
                    }
                    delim = ",";
                }
                msg = new StringBuffer().append(msg2).append("}").toString();
            } else {
                msg = new StringBuffer().append(msg2).append("{}").toString();
            }
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, msg, null));
        }
    }

    public static void exiting(Logger logger, String sourceClass, String sourceMethod) {
        if (logger.isDebugEnabled()) {
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, new StringBuffer().append(sourceClass).append(".").append(sourceMethod).append(" RETURN").toString(), null));
        }
    }

    public static void exiting(Logger logger, String sourceClass, String sourceMethod, String result) {
        if (logger.isDebugEnabled()) {
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, new StringBuffer().append(sourceClass).append(".").append(sourceMethod).append(" RETURN ").append(result).toString(), null));
        }
    }

    public static void exiting(Logger logger, String sourceClass, String sourceMethod, Object result) {
        if (logger.isDebugEnabled()) {
            String msg = new StringBuffer().append(sourceClass).append(".").append(sourceMethod).append(" RETURN ").toString();
            if (result == null) {
                msg = new StringBuffer().append(msg).append(Configurator.NULL).toString();
            } else {
                try {
                    msg = new StringBuffer().append(msg).append(result).toString();
                } catch (Throwable th) {
                    msg = new StringBuffer().append(msg).append(LocationInfo.f402NA).toString();
                }
            }
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, msg, null));
        }
    }

    public static void throwing(Logger logger, String sourceClass, String sourceMethod, Throwable thrown) {
        if (logger.isDebugEnabled()) {
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, new StringBuffer().append(sourceClass).append(".").append(sourceMethod).append(" THROW").toString(), thrown));
        }
    }
}
