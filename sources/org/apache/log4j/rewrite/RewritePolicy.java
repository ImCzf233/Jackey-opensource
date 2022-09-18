package org.apache.log4j.rewrite;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/rewrite/RewritePolicy.class */
public interface RewritePolicy {
    LoggingEvent rewrite(LoggingEvent loggingEvent);
}
