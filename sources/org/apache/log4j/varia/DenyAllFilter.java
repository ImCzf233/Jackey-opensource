package org.apache.log4j.varia;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/varia/DenyAllFilter.class */
public class DenyAllFilter extends Filter {
    public String[] getOptionStrings() {
        return null;
    }

    public void setOption(String key, String value) {
    }

    @Override // org.apache.log4j.spi.Filter
    public int decide(LoggingEvent event) {
        return -1;
    }
}
