package org.apache.log4j.rewrite;

import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.AppenderAttachableImpl;
import org.apache.log4j.spi.AppenderAttachable;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.OptionHandler;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.xml.UnrecognizedElementHandler;
import org.w3c.dom.Element;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/rewrite/RewriteAppender.class */
public class RewriteAppender extends AppenderSkeleton implements AppenderAttachable, UnrecognizedElementHandler {
    private RewritePolicy policy;
    private final AppenderAttachableImpl appenders = new AppenderAttachableImpl();
    static Class class$org$apache$log4j$rewrite$RewritePolicy;

    @Override // org.apache.log4j.AppenderSkeleton
    protected void append(LoggingEvent event) {
        LoggingEvent rewritten = event;
        if (this.policy != null) {
            rewritten = this.policy.rewrite(event);
        }
        if (rewritten != null) {
            synchronized (this.appenders) {
                this.appenders.appendLoopOnAppenders(rewritten);
            }
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void addAppender(Appender newAppender) {
        synchronized (this.appenders) {
            this.appenders.addAppender(newAppender);
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public Enumeration getAllAppenders() {
        Enumeration allAppenders;
        synchronized (this.appenders) {
            allAppenders = this.appenders.getAllAppenders();
        }
        return allAppenders;
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public Appender getAppender(String name) {
        Appender appender;
        synchronized (this.appenders) {
            appender = this.appenders.getAppender(name);
        }
        return appender;
    }

    @Override // org.apache.log4j.Appender
    public void close() {
        this.closed = true;
        synchronized (this.appenders) {
            Enumeration iter = this.appenders.getAllAppenders();
            if (iter != null) {
                while (iter.hasMoreElements()) {
                    Object next = iter.nextElement();
                    if (next instanceof Appender) {
                        ((Appender) next).close();
                    }
                }
            }
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public boolean isAttached(Appender appender) {
        boolean isAttached;
        synchronized (this.appenders) {
            isAttached = this.appenders.isAttached(appender);
        }
        return isAttached;
    }

    @Override // org.apache.log4j.Appender
    public boolean requiresLayout() {
        return false;
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void removeAllAppenders() {
        synchronized (this.appenders) {
            this.appenders.removeAllAppenders();
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void removeAppender(Appender appender) {
        synchronized (this.appenders) {
            this.appenders.removeAppender(appender);
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void removeAppender(String name) {
        synchronized (this.appenders) {
            this.appenders.removeAppender(name);
        }
    }

    public void setRewritePolicy(RewritePolicy rewritePolicy) {
        this.policy = rewritePolicy;
    }

    @Override // org.apache.log4j.xml.UnrecognizedElementHandler
    public boolean parseUnrecognizedElement(Element element, Properties props) throws Exception {
        Class cls;
        String nodeName = element.getNodeName();
        if ("rewritePolicy".equals(nodeName)) {
            if (class$org$apache$log4j$rewrite$RewritePolicy == null) {
                cls = class$("org.apache.log4j.rewrite.RewritePolicy");
                class$org$apache$log4j$rewrite$RewritePolicy = cls;
            } else {
                cls = class$org$apache$log4j$rewrite$RewritePolicy;
            }
            Object rewritePolicy = DOMConfigurator.parseElement(element, props, cls);
            if (rewritePolicy != null) {
                if (rewritePolicy instanceof OptionHandler) {
                    ((OptionHandler) rewritePolicy).activateOptions();
                }
                setRewritePolicy((RewritePolicy) rewritePolicy);
                return true;
            }
            return true;
        }
        return false;
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }
}
