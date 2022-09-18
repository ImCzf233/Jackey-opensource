package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/RelativeTimePatternConverter.class */
public class RelativeTimePatternConverter extends LoggingEventPatternConverter {
    private CachedTimestamp lastTimestamp = new CachedTimestamp(0, "");

    public RelativeTimePatternConverter() {
        super("Time", "time");
    }

    public static RelativeTimePatternConverter newInstance(String[] options) {
        return new RelativeTimePatternConverter();
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent event, StringBuffer toAppendTo) {
        long timestamp = event.timeStamp;
        if (!this.lastTimestamp.format(timestamp, toAppendTo)) {
            String formatted = Long.toString(timestamp - LoggingEvent.getStartTime());
            toAppendTo.append(formatted);
            this.lastTimestamp = new CachedTimestamp(timestamp, formatted);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/RelativeTimePatternConverter$CachedTimestamp.class */
    public static final class CachedTimestamp {
        private final long timestamp;
        private final String formatted;

        public CachedTimestamp(long timestamp, String formatted) {
            this.timestamp = timestamp;
            this.formatted = formatted;
        }

        public boolean format(long newTimestamp, StringBuffer toAppendTo) {
            if (newTimestamp == this.timestamp) {
                toAppendTo.append(this.formatted);
                return true;
            }
            return false;
        }
    }
}
