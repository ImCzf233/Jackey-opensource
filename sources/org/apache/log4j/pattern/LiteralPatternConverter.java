package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/LiteralPatternConverter.class */
public final class LiteralPatternConverter extends LoggingEventPatternConverter {
    private final String literal;

    public LiteralPatternConverter(String literal) {
        super("Literal", "literal");
        this.literal = literal;
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent event, StringBuffer toAppendTo) {
        toAppendTo.append(this.literal);
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter, org.apache.log4j.pattern.PatternConverter
    public void format(Object obj, StringBuffer toAppendTo) {
        toAppendTo.append(this.literal);
    }
}
