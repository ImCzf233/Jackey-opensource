package org.apache.log4j.pattern;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/DatePatternConverter.class */
public final class DatePatternConverter extends LoggingEventPatternConverter {
    private static final String ABSOLUTE_FORMAT = "ABSOLUTE";
    private static final String ABSOLUTE_TIME_PATTERN = "HH:mm:ss,SSS";
    private static final String DATE_AND_TIME_FORMAT = "DATE";
    private static final String DATE_AND_TIME_PATTERN = "dd MMM yyyy HH:mm:ss,SSS";
    private static final String ISO8601_FORMAT = "ISO8601";
    private static final String ISO8601_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";

    /* renamed from: df */
    private final CachedDateFormat f398df;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/DatePatternConverter$DefaultZoneDateFormat.class */
    public static class DefaultZoneDateFormat extends DateFormat {
        private static final long serialVersionUID = 1;
        private final DateFormat dateFormat;

        public DefaultZoneDateFormat(DateFormat format) {
            this.dateFormat = format;
        }

        @Override // java.text.DateFormat
        public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
            this.dateFormat.setTimeZone(TimeZone.getDefault());
            return this.dateFormat.format(date, toAppendTo, fieldPosition);
        }

        @Override // java.text.DateFormat
        public Date parse(String source, ParsePosition pos) {
            this.dateFormat.setTimeZone(TimeZone.getDefault());
            return this.dateFormat.parse(source, pos);
        }
    }

    private DatePatternConverter(String[] options) {
        super("Date", "date");
        String patternOption;
        String pattern;
        DateFormat simpleFormat;
        if (options == null || options.length == 0) {
            patternOption = null;
        } else {
            patternOption = options[0];
        }
        if (patternOption == null || patternOption.equalsIgnoreCase("ISO8601")) {
            pattern = ISO8601_PATTERN;
        } else if (patternOption.equalsIgnoreCase("ABSOLUTE")) {
            pattern = ABSOLUTE_TIME_PATTERN;
        } else if (patternOption.equalsIgnoreCase("DATE")) {
            pattern = DATE_AND_TIME_PATTERN;
        } else {
            pattern = patternOption;
        }
        int maximumCacheValidity = 1000;
        try {
            simpleFormat = new SimpleDateFormat(pattern);
            maximumCacheValidity = CachedDateFormat.getMaximumCacheValidity(pattern);
        } catch (IllegalArgumentException e) {
            LogLog.warn(new StringBuffer().append("Could not instantiate SimpleDateFormat with pattern ").append(patternOption).toString(), e);
            simpleFormat = new SimpleDateFormat(ISO8601_PATTERN);
        }
        if (options != null && options.length > 1) {
            TimeZone tz = TimeZone.getTimeZone(options[1]);
            simpleFormat.setTimeZone(tz);
        } else {
            simpleFormat = new DefaultZoneDateFormat(simpleFormat);
        }
        this.f398df = new CachedDateFormat(simpleFormat, maximumCacheValidity);
    }

    public static DatePatternConverter newInstance(String[] options) {
        return new DatePatternConverter(options);
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent event, StringBuffer output) {
        synchronized (this) {
            this.f398df.format(event.timeStamp, output);
        }
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter, org.apache.log4j.pattern.PatternConverter
    public void format(Object obj, StringBuffer output) {
        if (obj instanceof Date) {
            format((Date) obj, output);
        }
        super.format(obj, output);
    }

    public void format(Date date, StringBuffer toAppendTo) {
        synchronized (this) {
            this.f398df.format(date.getTime(), toAppendTo);
        }
    }
}
