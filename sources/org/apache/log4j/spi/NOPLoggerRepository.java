package org.apache.log4j.spi;

import java.util.Enumeration;
import java.util.Vector;
import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/spi/NOPLoggerRepository.class */
public final class NOPLoggerRepository implements LoggerRepository {
    @Override // org.apache.log4j.spi.LoggerRepository
    public void addHierarchyEventListener(HierarchyEventListener listener) {
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public boolean isDisabled(int level) {
        return true;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void setThreshold(Level level) {
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void setThreshold(String val) {
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void emitNoAppenderWarning(Category cat) {
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Level getThreshold() {
        return Level.OFF;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger getLogger(String name) {
        return new NOPLogger(this, name);
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger getLogger(String name, LoggerFactory factory) {
        return new NOPLogger(this, name);
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger getRootLogger() {
        return new NOPLogger(this, "root");
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger exists(String name) {
        return null;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void shutdown() {
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Enumeration getCurrentLoggers() {
        return new Vector().elements();
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Enumeration getCurrentCategories() {
        return getCurrentLoggers();
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void fireAddAppenderEvent(Category logger, Appender appender) {
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void resetConfiguration() {
    }
}
