package org.apache.log4j.pattern;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/ClassNamePatternConverter.class */
public final class ClassNamePatternConverter extends NamePatternConverter {
    private ClassNamePatternConverter(String[] options) {
        super("Class Name", "class name", options);
    }

    public static ClassNamePatternConverter newInstance(String[] options) {
        return new ClassNamePatternConverter(options);
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent event, StringBuffer toAppendTo) {
        int initialLength = toAppendTo.length();
        LocationInfo li = event.getLocationInformation();
        if (li == null) {
            toAppendTo.append(LocationInfo.f402NA);
        } else {
            toAppendTo.append(li.getClassName());
        }
        abbreviate(initialLength, toAppendTo);
    }
}
