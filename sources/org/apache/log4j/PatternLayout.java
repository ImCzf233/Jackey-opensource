package org.apache.log4j;

import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/PatternLayout.class */
public class PatternLayout extends Layout {
    public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
    public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %x - %m%n";
    protected final int BUF_SIZE = 256;
    protected final int MAX_CAPACITY = 1024;
    private StringBuffer sbuf;
    private String pattern;
    private PatternConverter head;

    public PatternLayout() {
        this("%m%n");
    }

    public PatternLayout(String pattern) {
        this.BUF_SIZE = 256;
        this.MAX_CAPACITY = 1024;
        this.sbuf = new StringBuffer(256);
        this.pattern = pattern;
        this.head = createPatternParser(pattern == null ? "%m%n" : pattern).parse();
    }

    public void setConversionPattern(String conversionPattern) {
        this.pattern = conversionPattern;
        this.head = createPatternParser(conversionPattern).parse();
    }

    public String getConversionPattern() {
        return this.pattern;
    }

    @Override // org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
    }

    @Override // org.apache.log4j.Layout
    public boolean ignoresThrowable() {
        return true;
    }

    protected PatternParser createPatternParser(String pattern) {
        return new PatternParser(pattern);
    }

    @Override // org.apache.log4j.Layout
    public String format(LoggingEvent event) {
        if (this.sbuf.capacity() > 1024) {
            this.sbuf = new StringBuffer(256);
        } else {
            this.sbuf.setLength(0);
        }
        PatternConverter patternConverter = this.head;
        while (true) {
            PatternConverter c = patternConverter;
            if (c != null) {
                c.format(this.sbuf, event);
                patternConverter = c.next;
            } else {
                return this.sbuf.toString();
            }
        }
    }
}
