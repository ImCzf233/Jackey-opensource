package org.apache.log4j;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/SimpleLayout.class */
public class SimpleLayout extends Layout {
    StringBuffer sbuf = new StringBuffer(128);

    @Override // org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
    }

    @Override // org.apache.log4j.Layout
    public String format(LoggingEvent event) {
        this.sbuf.setLength(0);
        this.sbuf.append(event.getLevel().toString());
        this.sbuf.append(" - ");
        this.sbuf.append(event.getRenderedMessage());
        this.sbuf.append(LINE_SEP);
        return this.sbuf.toString();
    }

    @Override // org.apache.log4j.Layout
    public boolean ignoresThrowable() {
        return true;
    }
}
