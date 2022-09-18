package org.apache.log4j.helpers;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/DateLayout.class */
public abstract class DateLayout extends Layout {
    public static final String NULL_DATE_FORMAT = "NULL";
    public static final String RELATIVE_TIME_DATE_FORMAT = "RELATIVE";
    public static final String DATE_FORMAT_OPTION = "DateFormat";
    public static final String TIMEZONE_OPTION = "TimeZone";
    private String timeZoneID;
    private String dateFormatOption;
    protected DateFormat dateFormat;
    protected FieldPosition pos = new FieldPosition(0);
    protected Date date = new Date();

    public String[] getOptionStrings() {
        return new String[]{DATE_FORMAT_OPTION, TIMEZONE_OPTION};
    }

    public void setOption(String option, String value) {
        if (option.equalsIgnoreCase(DATE_FORMAT_OPTION)) {
            this.dateFormatOption = value.toUpperCase();
        } else if (option.equalsIgnoreCase(TIMEZONE_OPTION)) {
            this.timeZoneID = value;
        }
    }

    public void setDateFormat(String dateFormat) {
        if (dateFormat != null) {
            this.dateFormatOption = dateFormat;
        }
        setDateFormat(this.dateFormatOption, TimeZone.getDefault());
    }

    public String getDateFormat() {
        return this.dateFormatOption;
    }

    public void setTimeZone(String timeZone) {
        this.timeZoneID = timeZone;
    }

    public String getTimeZone() {
        return this.timeZoneID;
    }

    @Override // org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
        setDateFormat(this.dateFormatOption);
        if (this.timeZoneID != null && this.dateFormat != null) {
            this.dateFormat.setTimeZone(TimeZone.getTimeZone(this.timeZoneID));
        }
    }

    public void dateFormat(StringBuffer buf, LoggingEvent event) {
        if (this.dateFormat != null) {
            this.date.setTime(event.timeStamp);
            this.dateFormat.format(this.date, buf, this.pos);
            buf.append(' ');
        }
    }

    public void setDateFormat(DateFormat dateFormat, TimeZone timeZone) {
        this.dateFormat = dateFormat;
        this.dateFormat.setTimeZone(timeZone);
    }

    public void setDateFormat(String dateFormatType, TimeZone timeZone) {
        if (dateFormatType == null) {
            this.dateFormat = null;
        } else if (dateFormatType.equalsIgnoreCase(NULL_DATE_FORMAT)) {
            this.dateFormat = null;
        } else if (dateFormatType.equalsIgnoreCase(RELATIVE_TIME_DATE_FORMAT)) {
            this.dateFormat = new RelativeTimeDateFormat();
        } else if (dateFormatType.equalsIgnoreCase(AbsoluteTimeDateFormat.ABS_TIME_DATE_FORMAT)) {
            this.dateFormat = new AbsoluteTimeDateFormat(timeZone);
        } else if (dateFormatType.equalsIgnoreCase(AbsoluteTimeDateFormat.DATE_AND_TIME_DATE_FORMAT)) {
            this.dateFormat = new DateTimeDateFormat(timeZone);
        } else if (dateFormatType.equalsIgnoreCase(AbsoluteTimeDateFormat.ISO8601_DATE_FORMAT)) {
            this.dateFormat = new ISO8601DateFormat(timeZone);
        } else {
            this.dateFormat = new SimpleDateFormat(dateFormatType);
            this.dateFormat.setTimeZone(timeZone);
        }
    }
}
