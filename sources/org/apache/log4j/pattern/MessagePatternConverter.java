package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/MessagePatternConverter.class */
public final class MessagePatternConverter extends LoggingEventPatternConverter {
    private static final MessagePatternConverter INSTANCE = new MessagePatternConverter();

    private MessagePatternConverter() {
        super("Message", "message");
    }

    public static MessagePatternConverter newInstance(String[] options) {
        return INSTANCE;
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent event, StringBuffer toAppendTo) {
        toAppendTo.append(event.getRenderedMessage());
    }
}
