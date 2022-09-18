package org.apache.log4j.varia;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/varia/NullAppender.class */
public class NullAppender extends AppenderSkeleton {
    private static NullAppender instance = new NullAppender();

    @Override // org.apache.log4j.AppenderSkeleton, org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
    }

    public NullAppender getInstance() {
        return instance;
    }

    public static NullAppender getNullAppender() {
        return instance;
    }

    @Override // org.apache.log4j.Appender
    public void close() {
    }

    @Override // org.apache.log4j.AppenderSkeleton, org.apache.log4j.Appender
    public void doAppend(LoggingEvent event) {
    }

    @Override // org.apache.log4j.AppenderSkeleton
    protected void append(LoggingEvent event) {
    }

    @Override // org.apache.log4j.Appender
    public boolean requiresLayout() {
        return false;
    }
}
