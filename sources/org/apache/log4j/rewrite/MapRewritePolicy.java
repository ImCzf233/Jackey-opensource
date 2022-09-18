package org.apache.log4j.rewrite;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/rewrite/MapRewritePolicy.class */
public class MapRewritePolicy implements RewritePolicy {
    @Override // org.apache.log4j.rewrite.RewritePolicy
    public LoggingEvent rewrite(LoggingEvent source) {
        Object msg = source.getMessage();
        if (msg instanceof Map) {
            Map props = new HashMap(source.getProperties());
            Map eventProps = (Map) msg;
            Object newMsg = eventProps.get("message");
            if (newMsg == null) {
                newMsg = msg;
            }
            for (Map.Entry entry : eventProps.entrySet()) {
                if (!"message".equals(entry.getKey())) {
                    props.put(entry.getKey(), entry.getValue());
                }
            }
            return new LoggingEvent(source.getFQNOfLoggerClass(), source.getLogger() != null ? source.getLogger() : Logger.getLogger(source.getLoggerName()), source.getTimeStamp(), source.getLevel(), newMsg, source.getThreadName(), source.getThrowableInformation(), source.getNDC(), source.getLocationInformation(), props);
        }
        return source;
    }
}
