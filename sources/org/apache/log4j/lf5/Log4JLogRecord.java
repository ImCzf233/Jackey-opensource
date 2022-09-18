package org.apache.log4j.lf5;

import org.apache.log4j.spi.ThrowableInformation;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/Log4JLogRecord.class */
public class Log4JLogRecord extends LogRecord {
    @Override // org.apache.log4j.lf5.LogRecord
    public boolean isSevereLevel() {
        boolean isSevere = false;
        if (LogLevel.ERROR.equals(getLevel()) || LogLevel.FATAL.equals(getLevel())) {
            isSevere = true;
        }
        return isSevere;
    }

    public void setThrownStackTrace(ThrowableInformation throwableInfo) {
        String[] stackTraceArray = throwableInfo.getThrowableStrRep();
        StringBuffer stackTrace = new StringBuffer();
        for (String str : stackTraceArray) {
            String nextLine = new StringBuffer().append(str).append("\n").toString();
            stackTrace.append(nextLine);
        }
        this._thrownStackTrace = stackTrace.toString();
    }
}
