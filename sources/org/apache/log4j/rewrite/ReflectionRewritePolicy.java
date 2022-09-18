package org.apache.log4j.rewrite;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/rewrite/ReflectionRewritePolicy.class */
public class ReflectionRewritePolicy implements RewritePolicy {
    static Class class$java$lang$Object;

    @Override // org.apache.log4j.rewrite.RewritePolicy
    public LoggingEvent rewrite(LoggingEvent source) {
        Class cls;
        Object msg = source.getMessage();
        if (!(msg instanceof String)) {
            Object newMsg = msg;
            Map rewriteProps = new HashMap(source.getProperties());
            try {
                Class<?> cls2 = msg.getClass();
                if (class$java$lang$Object == null) {
                    cls = class$("java.lang.Object");
                    class$java$lang$Object = cls;
                } else {
                    cls = class$java$lang$Object;
                }
                PropertyDescriptor[] props = Introspector.getBeanInfo(cls2, cls).getPropertyDescriptors();
                if (props.length > 0) {
                    for (int i = 0; i < props.length; i++) {
                        try {
                            Object propertyValue = props[i].getReadMethod().invoke(msg, null);
                            if ("message".equalsIgnoreCase(props[i].getName())) {
                                newMsg = propertyValue;
                            } else {
                                rewriteProps.put(props[i].getName(), propertyValue);
                            }
                        } catch (Exception e) {
                            LogLog.warn(new StringBuffer().append("Unable to evaluate property ").append(props[i].getName()).toString(), e);
                        }
                    }
                    return new LoggingEvent(source.getFQNOfLoggerClass(), source.getLogger() != null ? source.getLogger() : Logger.getLogger(source.getLoggerName()), source.getTimeStamp(), source.getLevel(), newMsg, source.getThreadName(), source.getThrowableInformation(), source.getNDC(), source.getLocationInformation(), rewriteProps);
                }
            } catch (Exception e2) {
                LogLog.warn("Unable to get property descriptors", e2);
            }
        }
        return source;
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }
}
