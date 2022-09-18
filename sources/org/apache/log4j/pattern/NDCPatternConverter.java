package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/NDCPatternConverter.class */
public final class NDCPatternConverter extends LoggingEventPatternConverter {
    private static final NDCPatternConverter INSTANCE = new NDCPatternConverter();

    private NDCPatternConverter() {
        super("NDC", "ndc");
    }

    public static NDCPatternConverter newInstance(String[] options) {
        return INSTANCE;
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent event, StringBuffer toAppendTo) {
        toAppendTo.append(event.getNDC());
    }
}
