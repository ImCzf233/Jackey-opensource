package org.apache.log4j.spi;

import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/spi/NOPLogger.class */
public final class NOPLogger extends Logger {
    public NOPLogger(NOPLoggerRepository repo, String name) {
        super(name);
        this.repository = repo;
        this.level = Level.OFF;
        this.parent = this;
    }

    @Override // org.apache.log4j.Category, org.apache.log4j.spi.AppenderAttachable
    public void addAppender(Appender newAppender) {
    }

    @Override // org.apache.log4j.Category
    public void assertLog(boolean assertion, String msg) {
    }

    @Override // org.apache.log4j.Category
    public void callAppenders(LoggingEvent event) {
    }

    void closeNestedAppenders() {
    }

    @Override // org.apache.log4j.Category
    public void debug(Object message) {
    }

    @Override // org.apache.log4j.Category
    public void debug(Object message, Throwable t) {
    }

    @Override // org.apache.log4j.Category
    public void error(Object message) {
    }

    @Override // org.apache.log4j.Category
    public void error(Object message, Throwable t) {
    }

    @Override // org.apache.log4j.Category
    public void fatal(Object message) {
    }

    @Override // org.apache.log4j.Category
    public void fatal(Object message, Throwable t) {
    }

    @Override // org.apache.log4j.Category, org.apache.log4j.spi.AppenderAttachable
    public Enumeration getAllAppenders() {
        return new Vector().elements();
    }

    @Override // org.apache.log4j.Category, org.apache.log4j.spi.AppenderAttachable
    public Appender getAppender(String name) {
        return null;
    }

    @Override // org.apache.log4j.Category
    public Level getEffectiveLevel() {
        return Level.OFF;
    }

    @Override // org.apache.log4j.Category
    public Priority getChainedPriority() {
        return getEffectiveLevel();
    }

    @Override // org.apache.log4j.Category
    public ResourceBundle getResourceBundle() {
        return null;
    }

    @Override // org.apache.log4j.Category
    public void info(Object message) {
    }

    @Override // org.apache.log4j.Category
    public void info(Object message, Throwable t) {
    }

    @Override // org.apache.log4j.Category, org.apache.log4j.spi.AppenderAttachable
    public boolean isAttached(Appender appender) {
        return false;
    }

    @Override // org.apache.log4j.Category
    public boolean isDebugEnabled() {
        return false;
    }

    @Override // org.apache.log4j.Category
    public boolean isEnabledFor(Priority level) {
        return false;
    }

    @Override // org.apache.log4j.Category
    public boolean isInfoEnabled() {
        return false;
    }

    @Override // org.apache.log4j.Category
    public void l7dlog(Priority priority, String key, Throwable t) {
    }

    @Override // org.apache.log4j.Category
    public void l7dlog(Priority priority, String key, Object[] params, Throwable t) {
    }

    @Override // org.apache.log4j.Category
    public void log(Priority priority, Object message, Throwable t) {
    }

    @Override // org.apache.log4j.Category
    public void log(Priority priority, Object message) {
    }

    @Override // org.apache.log4j.Category
    public void log(String callerFQCN, Priority level, Object message, Throwable t) {
    }

    @Override // org.apache.log4j.Category, org.apache.log4j.spi.AppenderAttachable
    public void removeAllAppenders() {
    }

    @Override // org.apache.log4j.Category, org.apache.log4j.spi.AppenderAttachable
    public void removeAppender(Appender appender) {
    }

    @Override // org.apache.log4j.Category, org.apache.log4j.spi.AppenderAttachable
    public void removeAppender(String name) {
    }

    @Override // org.apache.log4j.Category
    public void setLevel(Level level) {
    }

    @Override // org.apache.log4j.Category
    public void setPriority(Priority priority) {
    }

    @Override // org.apache.log4j.Category
    public void setResourceBundle(ResourceBundle bundle) {
    }

    @Override // org.apache.log4j.Category
    public void warn(Object message) {
    }

    @Override // org.apache.log4j.Category
    public void warn(Object message, Throwable t) {
    }

    @Override // org.apache.log4j.Logger
    public void trace(Object message) {
    }

    @Override // org.apache.log4j.Logger
    public void trace(Object message, Throwable t) {
    }

    @Override // org.apache.log4j.Logger
    public boolean isTraceEnabled() {
        return false;
    }
}
