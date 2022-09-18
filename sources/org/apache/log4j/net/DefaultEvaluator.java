package org.apache.log4j.net;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.TriggeringEventEvaluator;

/* compiled from: SMTPAppender.java */
/* loaded from: Jackey Client b2.jar:org/apache/log4j/net/DefaultEvaluator.class */
class DefaultEvaluator implements TriggeringEventEvaluator {
    @Override // org.apache.log4j.spi.TriggeringEventEvaluator
    public boolean isTriggeringEvent(LoggingEvent event) {
        return event.getLevel().isGreaterOrEqual(Level.ERROR);
    }
}
