package org.apache.log4j;

import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OnlyOnceErrorHandler;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.OptionHandler;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/AppenderSkeleton.class */
public abstract class AppenderSkeleton implements Appender, OptionHandler {
    protected Layout layout;
    protected String name;
    protected Priority threshold;
    protected Filter headFilter;
    protected Filter tailFilter;
    protected ErrorHandler errorHandler = new OnlyOnceErrorHandler();
    protected boolean closed = false;

    protected abstract void append(LoggingEvent loggingEvent);

    public AppenderSkeleton() {
    }

    protected AppenderSkeleton(boolean isActive) {
    }

    @Override // org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
    }

    @Override // org.apache.log4j.Appender
    public void addFilter(Filter newFilter) {
        if (this.headFilter == null) {
            this.tailFilter = newFilter;
            this.headFilter = newFilter;
            return;
        }
        this.tailFilter.setNext(newFilter);
        this.tailFilter = newFilter;
    }

    @Override // org.apache.log4j.Appender
    public void clearFilters() {
        this.tailFilter = null;
        this.headFilter = null;
    }

    public void finalize() {
        if (this.closed) {
            return;
        }
        LogLog.debug(new StringBuffer().append("Finalizing appender named [").append(this.name).append("].").toString());
        close();
    }

    @Override // org.apache.log4j.Appender
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    @Override // org.apache.log4j.Appender
    public Filter getFilter() {
        return this.headFilter;
    }

    public final Filter getFirstFilter() {
        return this.headFilter;
    }

    @Override // org.apache.log4j.Appender
    public Layout getLayout() {
        return this.layout;
    }

    @Override // org.apache.log4j.Appender
    public final String getName() {
        return this.name;
    }

    public Priority getThreshold() {
        return this.threshold;
    }

    public boolean isAsSevereAsThreshold(Priority priority) {
        return this.threshold == null || priority.isGreaterOrEqual(this.threshold);
    }

    @Override // org.apache.log4j.Appender
    public synchronized void doAppend(LoggingEvent event) {
        if (this.closed) {
            LogLog.error(new StringBuffer().append("Attempted to append to closed appender named [").append(this.name).append("].").toString());
        } else if (!isAsSevereAsThreshold(event.getLevel())) {
        } else {
            Filter f = this.headFilter;
            while (f != null) {
                switch (f.decide(event)) {
                    case -1:
                        return;
                    case 0:
                        f = f.getNext();
                        break;
                    case 1:
                        append(event);
                }
            }
            append(event);
        }
    }

    @Override // org.apache.log4j.Appender
    public synchronized void setErrorHandler(ErrorHandler eh) {
        if (eh == null) {
            LogLog.warn("You have tried to set a null error-handler.");
        } else {
            this.errorHandler = eh;
        }
    }

    @Override // org.apache.log4j.Appender
    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    @Override // org.apache.log4j.Appender
    public void setName(String name) {
        this.name = name;
    }

    public void setThreshold(Priority threshold) {
        this.threshold = threshold;
    }
}
