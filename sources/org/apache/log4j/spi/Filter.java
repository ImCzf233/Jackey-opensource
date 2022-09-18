package org.apache.log4j.spi;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/spi/Filter.class */
public abstract class Filter implements OptionHandler {
    public Filter next;
    public static final int DENY = -1;
    public static final int NEUTRAL = 0;
    public static final int ACCEPT = 1;

    public abstract int decide(LoggingEvent loggingEvent);

    @Override // org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
    }

    public void setNext(Filter next) {
        this.next = next;
    }

    public Filter getNext() {
        return this.next;
    }
}
