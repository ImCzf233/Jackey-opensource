package org.apache.log4j.lf5.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.lf5.LogLevel;
import org.apache.log4j.lf5.LogRecord;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/util/AdapterLogRecord.class */
public class AdapterLogRecord extends LogRecord {
    private static LogLevel severeLevel = null;

    /* renamed from: sw */
    private static StringWriter f392sw = new StringWriter();

    /* renamed from: pw */
    private static PrintWriter f393pw = new PrintWriter(f392sw);

    @Override // org.apache.log4j.lf5.LogRecord
    public void setCategory(String category) {
        super.setCategory(category);
        super.setLocation(getLocationInfo(category));
    }

    @Override // org.apache.log4j.lf5.LogRecord
    public boolean isSevereLevel() {
        if (severeLevel == null) {
            return false;
        }
        return severeLevel.equals(getLevel());
    }

    public static void setSevereLevel(LogLevel level) {
        severeLevel = level;
    }

    public static LogLevel getSevereLevel() {
        return severeLevel;
    }

    protected String getLocationInfo(String category) {
        String stackTrace = stackTraceToString(new Throwable());
        String line = parseLine(stackTrace, category);
        return line;
    }

    protected String stackTraceToString(Throwable t) {
        String s;
        synchronized (f392sw) {
            t.printStackTrace(f393pw);
            s = f392sw.toString();
            f392sw.getBuffer().setLength(0);
        }
        return s;
    }

    protected String parseLine(String trace, String category) {
        int index = trace.indexOf(category);
        if (index == -1) {
            return null;
        }
        String trace2 = trace.substring(index);
        return trace2.substring(0, trace2.indexOf(")") + 1);
    }
}
