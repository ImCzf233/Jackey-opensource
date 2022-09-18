package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/ThrowableInformationPatternConverter.class */
public class ThrowableInformationPatternConverter extends LoggingEventPatternConverter {
    private int maxLines;

    private ThrowableInformationPatternConverter(String[] options) {
        super("Throwable", "throwable");
        this.maxLines = Integer.MAX_VALUE;
        if (options != null && options.length > 0) {
            if ("none".equals(options[0])) {
                this.maxLines = 0;
            } else if ("short".equals(options[0])) {
                this.maxLines = 1;
            } else {
                try {
                    this.maxLines = Integer.parseInt(options[0]);
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    public static ThrowableInformationPatternConverter newInstance(String[] options) {
        return new ThrowableInformationPatternConverter(options);
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent event, StringBuffer toAppendTo) {
        ThrowableInformation information;
        if (this.maxLines != 0 && (information = event.getThrowableInformation()) != null) {
            String[] stringRep = information.getThrowableStrRep();
            int length = stringRep.length;
            if (this.maxLines < 0) {
                length += this.maxLines;
            } else if (length > this.maxLines) {
                length = this.maxLines;
            }
            for (int i = 0; i < length; i++) {
                String string = stringRep[i];
                toAppendTo.append(string).append("\n");
            }
        }
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public boolean handlesThrowable() {
        return true;
    }
}
