package org.apache.log4j.pattern;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Date;
import java.util.TimeZone;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/CachedDateFormat.class */
public final class CachedDateFormat extends DateFormat {
    private static final long serialVersionUID = 1;
    public static final int NO_MILLISECONDS = -2;
    private static final String DIGITS = "0123456789";
    public static final int UNRECOGNIZED_MILLISECONDS = -1;
    private static final int MAGIC1 = 654;
    private static final String MAGICSTRING1 = "654";
    private static final int MAGIC2 = 987;
    private static final String MAGICSTRING2 = "987";
    private static final String ZERO_STRING = "000";
    private final DateFormat formatter;
    private int millisecondStart;
    private long slotBegin;
    private final int expiration;
    private long previousTime;
    private StringBuffer cache = new StringBuffer(50);
    private final Date tmpDate = new Date(0);

    public CachedDateFormat(DateFormat dateFormat, int expiration) {
        if (dateFormat == null) {
            throw new IllegalArgumentException("dateFormat cannot be null");
        }
        if (expiration < 0) {
            throw new IllegalArgumentException("expiration must be non-negative");
        }
        this.formatter = dateFormat;
        this.expiration = expiration;
        this.millisecondStart = 0;
        this.previousTime = Long.MIN_VALUE;
        this.slotBegin = Long.MIN_VALUE;
    }

    public static int findMillisecondStart(long time, String formatted, DateFormat formatter) {
        long slotBegin = (time / 1000) * 1000;
        if (slotBegin > time) {
            slotBegin -= 1000;
        }
        int millis = (int) (time - slotBegin);
        int magic = MAGIC1;
        String magicString = MAGICSTRING1;
        if (millis == MAGIC1) {
            magic = MAGIC2;
            magicString = MAGICSTRING2;
        }
        String plusMagic = formatter.format(new Date(slotBegin + magic));
        if (plusMagic.length() != formatted.length()) {
            return -1;
        }
        for (int i = 0; i < formatted.length(); i++) {
            if (formatted.charAt(i) != plusMagic.charAt(i)) {
                StringBuffer formattedMillis = new StringBuffer("ABC");
                millisecondFormat(millis, formattedMillis, 0);
                String plusZero = formatter.format(new Date(slotBegin));
                if (plusZero.length() == formatted.length() && magicString.regionMatches(0, plusMagic, i, magicString.length()) && formattedMillis.toString().regionMatches(0, formatted, i, magicString.length()) && ZERO_STRING.regionMatches(0, plusZero, i, ZERO_STRING.length())) {
                    return i;
                }
                return -1;
            }
        }
        return -2;
    }

    @Override // java.text.DateFormat
    public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition) {
        format(date.getTime(), sbuf);
        return sbuf;
    }

    public StringBuffer format(long now, StringBuffer buf) {
        if (now == this.previousTime) {
            buf.append(this.cache);
            return buf;
        } else if (this.millisecondStart != -1 && now < this.slotBegin + this.expiration && now >= this.slotBegin && now < this.slotBegin + 1000) {
            if (this.millisecondStart >= 0) {
                millisecondFormat((int) (now - this.slotBegin), this.cache, this.millisecondStart);
            }
            this.previousTime = now;
            buf.append(this.cache);
            return buf;
        } else {
            this.cache.setLength(0);
            this.tmpDate.setTime(now);
            this.cache.append(this.formatter.format(this.tmpDate));
            buf.append(this.cache);
            this.previousTime = now;
            this.slotBegin = (this.previousTime / 1000) * 1000;
            if (this.slotBegin > this.previousTime) {
                this.slotBegin -= 1000;
            }
            if (this.millisecondStart >= 0) {
                this.millisecondStart = findMillisecondStart(now, this.cache.toString(), this.formatter);
            }
            return buf;
        }
    }

    private static void millisecondFormat(int millis, StringBuffer buf, int offset) {
        buf.setCharAt(offset, DIGITS.charAt(millis / 100));
        buf.setCharAt(offset + 1, DIGITS.charAt((millis / 10) % 10));
        buf.setCharAt(offset + 2, DIGITS.charAt(millis % 10));
    }

    @Override // java.text.DateFormat
    public void setTimeZone(TimeZone timeZone) {
        this.formatter.setTimeZone(timeZone);
        this.previousTime = Long.MIN_VALUE;
        this.slotBegin = Long.MIN_VALUE;
    }

    @Override // java.text.DateFormat
    public Date parse(String s, ParsePosition pos) {
        return this.formatter.parse(s, pos);
    }

    @Override // java.text.DateFormat
    public NumberFormat getNumberFormat() {
        return this.formatter.getNumberFormat();
    }

    public static int getMaximumCacheValidity(String pattern) {
        int firstS = pattern.indexOf(83);
        if (firstS >= 0 && firstS != pattern.lastIndexOf("SSS")) {
            return 1;
        }
        return 1000;
    }
}
