package org.apache.log4j.pattern;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/LineLocationPatternConverter.class */
public final class LineLocationPatternConverter extends LoggingEventPatternConverter {
    private static final LineLocationPatternConverter INSTANCE = new LineLocationPatternConverter();

    private LineLocationPatternConverter() {
        super("Line", "line");
    }

    public static LineLocationPatternConverter newInstance(String[] options) {
        return INSTANCE;
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent event, StringBuffer output) {
        LocationInfo locationInfo = event.getLocationInformation();
        if (locationInfo != null) {
            output.append(locationInfo.getLineNumber());
        }
    }
}
