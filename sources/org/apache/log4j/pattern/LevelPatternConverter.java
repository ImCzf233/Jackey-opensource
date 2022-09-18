package org.apache.log4j.pattern;

import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/LevelPatternConverter.class */
public final class LevelPatternConverter extends LoggingEventPatternConverter {
    private static final int TRACE_INT = 5000;
    private static final LevelPatternConverter INSTANCE = new LevelPatternConverter();

    private LevelPatternConverter() {
        super("Level", "level");
    }

    public static LevelPatternConverter newInstance(String[] options) {
        return INSTANCE;
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent event, StringBuffer output) {
        output.append(event.getLevel().toString());
    }

    @Override // org.apache.log4j.pattern.PatternConverter
    public String getStyleClass(Object e) {
        if (e instanceof LoggingEvent) {
            int lint = ((LoggingEvent) e).getLevel().toInt();
            switch (lint) {
                case 5000:
                    return "level trace";
                case 10000:
                    return "level debug";
                case Priority.INFO_INT /* 20000 */:
                    return "level info";
                case Priority.WARN_INT /* 30000 */:
                    return "level warn";
                case Priority.ERROR_INT /* 40000 */:
                    return "level error";
                case Priority.FATAL_INT /* 50000 */:
                    return "level fatal";
                default:
                    return new StringBuffer().append("level ").append(((LoggingEvent) e).getLevel().toString()).toString();
            }
        }
        return "level";
    }
}
