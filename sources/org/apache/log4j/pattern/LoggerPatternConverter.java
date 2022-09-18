package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/LoggerPatternConverter.class */
public final class LoggerPatternConverter extends NamePatternConverter {
    private static final LoggerPatternConverter INSTANCE = new LoggerPatternConverter(null);

    private LoggerPatternConverter(String[] options) {
        super("Logger", "logger", options);
    }

    public static LoggerPatternConverter newInstance(String[] options) {
        if (options == null || options.length == 0) {
            return INSTANCE;
        }
        return new LoggerPatternConverter(options);
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent event, StringBuffer toAppendTo) {
        int initialLength = toAppendTo.length();
        toAppendTo.append(event.getLoggerName());
        abbreviate(initialLength, toAppendTo);
    }
}
