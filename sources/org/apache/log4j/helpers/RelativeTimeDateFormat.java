package org.apache.log4j.helpers;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/RelativeTimeDateFormat.class */
public class RelativeTimeDateFormat extends DateFormat {
    private static final long serialVersionUID = 7055751607085611984L;
    protected final long startTime = System.currentTimeMillis();

    @Override // java.text.DateFormat
    public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition) {
        return sbuf.append(date.getTime() - this.startTime);
    }

    @Override // java.text.DateFormat
    public Date parse(String s, ParsePosition pos) {
        return null;
    }
}
