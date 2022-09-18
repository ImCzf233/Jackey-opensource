package org.apache.log4j.helpers;

import java.io.InterruptedIOException;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/OnlyOnceErrorHandler.class */
public class OnlyOnceErrorHandler implements ErrorHandler {
    final String WARN_PREFIX = "log4j warning: ";
    final String ERROR_PREFIX = "log4j error: ";
    boolean firstTime = true;

    @Override // org.apache.log4j.spi.ErrorHandler
    public void setLogger(Logger logger) {
    }

    @Override // org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
    }

    @Override // org.apache.log4j.spi.ErrorHandler
    public void error(String message, Exception e, int errorCode) {
        error(message, e, errorCode, null);
    }

    @Override // org.apache.log4j.spi.ErrorHandler
    public void error(String message, Exception e, int errorCode, LoggingEvent event) {
        if ((e instanceof InterruptedIOException) || (e instanceof InterruptedException)) {
            Thread.currentThread().interrupt();
        }
        if (this.firstTime) {
            LogLog.error(message, e);
            this.firstTime = false;
        }
    }

    @Override // org.apache.log4j.spi.ErrorHandler
    public void error(String message) {
        if (this.firstTime) {
            LogLog.error(message);
            this.firstTime = false;
        }
    }

    @Override // org.apache.log4j.spi.ErrorHandler
    public void setAppender(Appender appender) {
    }

    @Override // org.apache.log4j.spi.ErrorHandler
    public void setBackupAppender(Appender appender) {
    }
}
