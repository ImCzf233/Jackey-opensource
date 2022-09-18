package org.apache.log4j.pattern;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/MethodLocationPatternConverter.class */
public final class MethodLocationPatternConverter extends LoggingEventPatternConverter {
    private static final MethodLocationPatternConverter INSTANCE = new MethodLocationPatternConverter();

    private MethodLocationPatternConverter() {
        super("Method", "method");
    }

    public static MethodLocationPatternConverter newInstance(String[] options) {
        return INSTANCE;
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent event, StringBuffer toAppendTo) {
        LocationInfo locationInfo = event.getLocationInformation();
        if (locationInfo != null) {
            toAppendTo.append(locationInfo.getMethodName());
        }
    }
}
