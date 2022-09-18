package org.apache.log4j.rewrite;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/rewrite/PropertyRewritePolicy.class */
public class PropertyRewritePolicy implements RewritePolicy {
    private Map properties = Collections.EMPTY_MAP;

    public void setProperties(String props) {
        Map hashTable = new HashMap();
        StringTokenizer pairs = new StringTokenizer(props, ",");
        while (pairs.hasMoreTokens()) {
            StringTokenizer entry = new StringTokenizer(pairs.nextToken(), "=");
            hashTable.put(entry.nextElement().toString().trim(), entry.nextElement().toString().trim());
        }
        synchronized (this) {
            this.properties = hashTable;
        }
    }

    @Override // org.apache.log4j.rewrite.RewritePolicy
    public LoggingEvent rewrite(LoggingEvent source) {
        if (!this.properties.isEmpty()) {
            Map rewriteProps = new HashMap(source.getProperties());
            for (Map.Entry entry : this.properties.entrySet()) {
                if (!rewriteProps.containsKey(entry.getKey())) {
                    rewriteProps.put(entry.getKey(), entry.getValue());
                }
            }
            return new LoggingEvent(source.getFQNOfLoggerClass(), source.getLogger() != null ? source.getLogger() : Logger.getLogger(source.getLoggerName()), source.getTimeStamp(), source.getLevel(), source.getMessage(), source.getThreadName(), source.getThrowableInformation(), source.getNDC(), source.getLocationInformation(), rewriteProps);
        }
        return source;
    }
}
