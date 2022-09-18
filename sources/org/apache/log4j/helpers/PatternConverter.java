package org.apache.log4j.helpers;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/PatternConverter.class */
public abstract class PatternConverter {
    public PatternConverter next;
    int min;
    int max;
    boolean leftAlign;
    static String[] SPACES = {" ", "  ", "    ", "        ", "                ", "                                "};

    protected abstract String convert(LoggingEvent loggingEvent);

    public PatternConverter() {
        this.min = -1;
        this.max = Integer.MAX_VALUE;
        this.leftAlign = false;
    }

    public PatternConverter(FormattingInfo fi) {
        this.min = -1;
        this.max = Integer.MAX_VALUE;
        this.leftAlign = false;
        this.min = fi.min;
        this.max = fi.max;
        this.leftAlign = fi.leftAlign;
    }

    public void format(StringBuffer sbuf, LoggingEvent e) {
        String s = convert(e);
        if (s == null) {
            if (0 < this.min) {
                spacePad(sbuf, this.min);
                return;
            }
            return;
        }
        int len = s.length();
        if (len > this.max) {
            sbuf.append(s.substring(len - this.max));
        } else if (len < this.min) {
            if (this.leftAlign) {
                sbuf.append(s);
                spacePad(sbuf, this.min - len);
                return;
            }
            spacePad(sbuf, this.min - len);
            sbuf.append(s);
        } else {
            sbuf.append(s);
        }
    }

    public void spacePad(StringBuffer sbuf, int length) {
        while (length >= 32) {
            sbuf.append(SPACES[5]);
            length -= 32;
        }
        for (int i = 4; i >= 0; i--) {
            if ((length & (1 << i)) != 0) {
                sbuf.append(SPACES[i]);
            }
        }
    }
}
