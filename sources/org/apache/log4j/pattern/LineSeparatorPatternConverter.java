package org.apache.log4j.pattern;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/LineSeparatorPatternConverter.class */
public final class LineSeparatorPatternConverter extends LoggingEventPatternConverter {
    private static final LineSeparatorPatternConverter INSTANCE = new LineSeparatorPatternConverter();
    private final String lineSep = Layout.LINE_SEP;

    private LineSeparatorPatternConverter() {
        super("Line Sep", "lineSep");
    }

    public static LineSeparatorPatternConverter newInstance(String[] options) {
        return INSTANCE;
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent event, StringBuffer toAppendTo) {
        toAppendTo.append(this.lineSep);
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter, org.apache.log4j.pattern.PatternConverter
    public void format(Object obj, StringBuffer toAppendTo) {
        toAppendTo.append(this.lineSep);
    }
}
