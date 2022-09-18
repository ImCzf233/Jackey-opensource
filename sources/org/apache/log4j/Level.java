package org.apache.log4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/Level.class */
public class Level extends Priority implements Serializable {
    static final long serialVersionUID = 3491141966387921974L;
    static Class class$org$apache$log4j$Level;
    public static final Level OFF = new Level(Integer.MAX_VALUE, "OFF", 0);
    public static final Level FATAL = new Level(Priority.FATAL_INT, "FATAL", 0);
    public static final Level ERROR = new Level(Priority.ERROR_INT, "ERROR", 3);
    public static final Level WARN = new Level(Priority.WARN_INT, "WARN", 4);
    public static final Level INFO = new Level(Priority.INFO_INT, "INFO", 6);
    public static final Level DEBUG = new Level(10000, "DEBUG", 7);
    public static final int TRACE_INT = 5000;
    public static final Level TRACE = new Level(TRACE_INT, "TRACE", 7);
    public static final Level ALL = new Level(Integer.MIN_VALUE, "ALL", 7);

    public Level(int level, String levelStr, int syslogEquivalent) {
        super(level, levelStr, syslogEquivalent);
    }

    public static Level toLevel(String sArg) {
        return toLevel(sArg, DEBUG);
    }

    public static Level toLevel(int val) {
        return toLevel(val, DEBUG);
    }

    public static Level toLevel(int val, Level defaultLevel) {
        switch (val) {
            case Integer.MIN_VALUE:
                return ALL;
            case TRACE_INT /* 5000 */:
                return TRACE;
            case 10000:
                return DEBUG;
            case Priority.INFO_INT /* 20000 */:
                return INFO;
            case Priority.WARN_INT /* 30000 */:
                return WARN;
            case Priority.ERROR_INT /* 40000 */:
                return ERROR;
            case Priority.FATAL_INT /* 50000 */:
                return FATAL;
            case Integer.MAX_VALUE:
                return OFF;
            default:
                return defaultLevel;
        }
    }

    public static Level toLevel(String sArg, Level defaultLevel) {
        if (sArg == null) {
            return defaultLevel;
        }
        String s = sArg.toUpperCase();
        return s.equals("ALL") ? ALL : s.equals("DEBUG") ? DEBUG : s.equals("INFO") ? INFO : s.equals("WARN") ? WARN : s.equals("ERROR") ? ERROR : s.equals("FATAL") ? FATAL : s.equals("OFF") ? OFF : s.equals("TRACE") ? TRACE : s.equals("İNFO") ? INFO : defaultLevel;
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.level = s.readInt();
        this.syslogEquivalent = s.readInt();
        this.levelStr = s.readUTF();
        if (this.levelStr == null) {
            this.levelStr = "";
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(this.level);
        s.writeInt(this.syslogEquivalent);
        s.writeUTF(this.levelStr);
    }

    private Object readResolve() throws ObjectStreamException {
        Class<?> cls;
        Class<?> cls2 = getClass();
        if (class$org$apache$log4j$Level == null) {
            cls = class$("org.apache.log4j.Level");
            class$org$apache$log4j$Level = cls;
        } else {
            cls = class$org$apache$log4j$Level;
        }
        if (cls2 == cls) {
            return toLevel(this.level);
        }
        return this;
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }
}
