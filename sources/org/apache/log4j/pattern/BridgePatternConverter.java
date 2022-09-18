package org.apache.log4j.pattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/BridgePatternConverter.class */
public final class BridgePatternConverter extends org.apache.log4j.helpers.PatternConverter {
    private LoggingEventPatternConverter[] patternConverters;
    private FormattingInfo[] patternFields;
    private boolean handlesExceptions = false;

    public BridgePatternConverter(String pattern) {
        this.next = null;
        List converters = new ArrayList();
        List fields = new ArrayList();
        PatternParser.parse(pattern, converters, fields, null, PatternParser.getPatternLayoutRules());
        this.patternConverters = new LoggingEventPatternConverter[converters.size()];
        this.patternFields = new FormattingInfo[converters.size()];
        int i = 0;
        Iterator fieldIter = fields.iterator();
        for (Object converter : converters) {
            if (converter instanceof LoggingEventPatternConverter) {
                this.patternConverters[i] = (LoggingEventPatternConverter) converter;
                this.handlesExceptions |= this.patternConverters[i].handlesThrowable();
            } else {
                this.patternConverters[i] = new LiteralPatternConverter("");
            }
            if (fieldIter.hasNext()) {
                this.patternFields[i] = (FormattingInfo) fieldIter.next();
            } else {
                this.patternFields[i] = FormattingInfo.getDefault();
            }
            i++;
        }
    }

    @Override // org.apache.log4j.helpers.PatternConverter
    protected String convert(LoggingEvent event) {
        StringBuffer sbuf = new StringBuffer();
        format(sbuf, event);
        return sbuf.toString();
    }

    @Override // org.apache.log4j.helpers.PatternConverter
    public void format(StringBuffer sbuf, LoggingEvent e) {
        for (int i = 0; i < this.patternConverters.length; i++) {
            int startField = sbuf.length();
            this.patternConverters[i].format(e, sbuf);
            this.patternFields[i].format(startField, sbuf);
        }
    }

    public boolean ignoresThrowable() {
        return !this.handlesExceptions;
    }
}
