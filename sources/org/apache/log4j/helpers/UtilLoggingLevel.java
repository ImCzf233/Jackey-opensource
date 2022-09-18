package org.apache.log4j.helpers;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/UtilLoggingLevel.class */
public class UtilLoggingLevel extends Level {
    private static final long serialVersionUID = 909301162611820211L;
    public static final int UNKNOWN_INT = 10000;
    public static final int SEVERE_INT = 22000;
    public static final UtilLoggingLevel SEVERE = new UtilLoggingLevel(SEVERE_INT, "SEVERE", 0);
    public static final int WARNING_INT = 21000;
    public static final UtilLoggingLevel WARNING = new UtilLoggingLevel(WARNING_INT, "WARNING", 4);
    public static final UtilLoggingLevel INFO = new UtilLoggingLevel(Priority.INFO_INT, "INFO", 5);
    public static final int CONFIG_INT = 14000;
    public static final UtilLoggingLevel CONFIG = new UtilLoggingLevel(CONFIG_INT, "CONFIG", 6);
    public static final int FINE_INT = 13000;
    public static final UtilLoggingLevel FINE = new UtilLoggingLevel(FINE_INT, "FINE", 7);
    public static final int FINER_INT = 12000;
    public static final UtilLoggingLevel FINER = new UtilLoggingLevel(FINER_INT, "FINER", 8);
    public static final int FINEST_INT = 11000;
    public static final UtilLoggingLevel FINEST = new UtilLoggingLevel(FINEST_INT, "FINEST", 9);

    protected UtilLoggingLevel(int level, String levelStr, int syslogEquivalent) {
        super(level, levelStr, syslogEquivalent);
    }

    public static UtilLoggingLevel toLevel(int val, UtilLoggingLevel defaultLevel) {
        switch (val) {
            case FINEST_INT /* 11000 */:
                return FINEST;
            case FINER_INT /* 12000 */:
                return FINER;
            case FINE_INT /* 13000 */:
                return FINE;
            case CONFIG_INT /* 14000 */:
                return CONFIG;
            case Priority.INFO_INT /* 20000 */:
                return INFO;
            case WARNING_INT /* 21000 */:
                return WARNING;
            case SEVERE_INT /* 22000 */:
                return SEVERE;
            default:
                return defaultLevel;
        }
    }

    public static Level toLevel(int val) {
        return toLevel(val, FINEST);
    }

    public static List getAllPossibleLevels() {
        ArrayList list = new ArrayList();
        list.add(FINE);
        list.add(FINER);
        list.add(FINEST);
        list.add(INFO);
        list.add(CONFIG);
        list.add(WARNING);
        list.add(SEVERE);
        return list;
    }

    public static Level toLevel(String s) {
        return toLevel(s, Level.DEBUG);
    }

    public static Level toLevel(String sArg, Level defaultLevel) {
        if (sArg == null) {
            return defaultLevel;
        }
        String s = sArg.toUpperCase();
        if (s.equals("SEVERE")) {
            return SEVERE;
        }
        if (s.equals("WARNING")) {
            return WARNING;
        }
        if (s.equals("INFO")) {
            return INFO;
        }
        if (s.equals("CONFI")) {
            return CONFIG;
        }
        if (s.equals("FINE")) {
            return FINE;
        }
        if (s.equals("FINER")) {
            return FINER;
        }
        if (s.equals("FINEST")) {
            return FINEST;
        }
        return defaultLevel;
    }
}
