package org.apache.log4j;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;
import org.apache.log4j.helpers.AppenderAttachableImpl;
import org.apache.log4j.helpers.NullEnumeration;
import org.apache.log4j.spi.AppenderAttachable;
import org.apache.log4j.spi.HierarchyEventListener;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/Category.class */
public class Category implements AppenderAttachable {
    protected String name;
    protected volatile Level level;
    protected volatile Category parent;
    private static final String FQCN;
    protected ResourceBundle resourceBundle;
    protected LoggerRepository repository;
    AppenderAttachableImpl aai;
    protected boolean additive = true;
    static Class class$org$apache$log4j$Category;

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    static {
        Class cls;
        if (class$org$apache$log4j$Category == null) {
            cls = class$("org.apache.log4j.Category");
            class$org$apache$log4j$Category = cls;
        } else {
            cls = class$org$apache$log4j$Category;
        }
        FQCN = cls.getName();
    }

    public Category(String name) {
        this.name = name;
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public synchronized void addAppender(Appender newAppender) {
        if (this.aai == null) {
            this.aai = new AppenderAttachableImpl();
        }
        this.aai.addAppender(newAppender);
        this.repository.fireAddAppenderEvent(this, newAppender);
    }

    public void assertLog(boolean assertion, String msg) {
        if (!assertion) {
            error(msg);
        }
    }

    public void callAppenders(LoggingEvent event) {
        int writes = 0;
        Category category = this;
        while (true) {
            Category c = category;
            if (c == null) {
                break;
            }
            synchronized (c) {
                if (c.aai != null) {
                    writes += c.aai.appendLoopOnAppenders(event);
                }
                if (!c.additive) {
                    break;
                }
            }
            break;
            category = c.parent;
        }
        if (writes == 0) {
            this.repository.emitNoAppenderWarning(this);
        }
    }

    public synchronized void closeNestedAppenders() {
        Enumeration enumeration = getAllAppenders();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                Appender a = (Appender) enumeration.nextElement();
                if (a instanceof AppenderAttachable) {
                    a.close();
                }
            }
        }
    }

    public void debug(Object message) {
        if (!this.repository.isDisabled(10000) && Level.DEBUG.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.DEBUG, message, null);
        }
    }

    public void debug(Object message, Throwable t) {
        if (!this.repository.isDisabled(10000) && Level.DEBUG.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.DEBUG, message, t);
        }
    }

    public void error(Object message) {
        if (!this.repository.isDisabled(Priority.ERROR_INT) && Level.ERROR.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.ERROR, message, null);
        }
    }

    public void error(Object message, Throwable t) {
        if (!this.repository.isDisabled(Priority.ERROR_INT) && Level.ERROR.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.ERROR, message, t);
        }
    }

    public static Logger exists(String name) {
        return LogManager.exists(name);
    }

    public void fatal(Object message) {
        if (!this.repository.isDisabled(Priority.FATAL_INT) && Level.FATAL.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.FATAL, message, null);
        }
    }

    public void fatal(Object message, Throwable t) {
        if (!this.repository.isDisabled(Priority.FATAL_INT) && Level.FATAL.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.FATAL, message, t);
        }
    }

    public void forcedLog(String fqcn, Priority level, Object message, Throwable t) {
        callAppenders(new LoggingEvent(fqcn, this, level, message, t));
    }

    public boolean getAdditivity() {
        return this.additive;
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public synchronized Enumeration getAllAppenders() {
        if (this.aai == null) {
            return NullEnumeration.getInstance();
        }
        return this.aai.getAllAppenders();
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public synchronized Appender getAppender(String name) {
        if (this.aai == null || name == null) {
            return null;
        }
        return this.aai.getAppender(name);
    }

    public Level getEffectiveLevel() {
        Category category = this;
        while (true) {
            Category c = category;
            if (c != null) {
                if (c.level == null) {
                    category = c.parent;
                } else {
                    return c.level;
                }
            } else {
                return null;
            }
        }
    }

    public Priority getChainedPriority() {
        Category category = this;
        while (true) {
            Category c = category;
            if (c != null) {
                if (c.level == null) {
                    category = c.parent;
                } else {
                    return c.level;
                }
            } else {
                return null;
            }
        }
    }

    public static Enumeration getCurrentCategories() {
        return LogManager.getCurrentLoggers();
    }

    public static LoggerRepository getDefaultHierarchy() {
        return LogManager.getLoggerRepository();
    }

    public LoggerRepository getHierarchy() {
        return this.repository;
    }

    public LoggerRepository getLoggerRepository() {
        return this.repository;
    }

    public static Category getInstance(String name) {
        return LogManager.getLogger(name);
    }

    public static Category getInstance(Class clazz) {
        return LogManager.getLogger(clazz);
    }

    public final String getName() {
        return this.name;
    }

    public final Category getParent() {
        return this.parent;
    }

    public final Level getLevel() {
        return this.level;
    }

    public final Level getPriority() {
        return this.level;
    }

    public static final Category getRoot() {
        return LogManager.getRootLogger();
    }

    public ResourceBundle getResourceBundle() {
        Category category = this;
        while (true) {
            Category c = category;
            if (c != null) {
                if (c.resourceBundle == null) {
                    category = c.parent;
                } else {
                    return c.resourceBundle;
                }
            } else {
                return null;
            }
        }
    }

    protected String getResourceBundleString(String key) {
        ResourceBundle rb = getResourceBundle();
        if (rb == null) {
            return null;
        }
        try {
            return rb.getString(key);
        } catch (MissingResourceException e) {
            error(new StringBuffer().append("No resource is associated with key \"").append(key).append("\".").toString());
            return null;
        }
    }

    public void info(Object message) {
        if (!this.repository.isDisabled(Priority.INFO_INT) && Level.INFO.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.INFO, message, null);
        }
    }

    public void info(Object message, Throwable t) {
        if (!this.repository.isDisabled(Priority.INFO_INT) && Level.INFO.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.INFO, message, t);
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public boolean isAttached(Appender appender) {
        if (appender == null || this.aai == null) {
            return false;
        }
        return this.aai.isAttached(appender);
    }

    public boolean isDebugEnabled() {
        if (this.repository.isDisabled(10000)) {
            return false;
        }
        return Level.DEBUG.isGreaterOrEqual(getEffectiveLevel());
    }

    public boolean isEnabledFor(Priority level) {
        if (this.repository.isDisabled(level.level)) {
            return false;
        }
        return level.isGreaterOrEqual(getEffectiveLevel());
    }

    public boolean isInfoEnabled() {
        if (this.repository.isDisabled(Priority.INFO_INT)) {
            return false;
        }
        return Level.INFO.isGreaterOrEqual(getEffectiveLevel());
    }

    public void l7dlog(Priority priority, String key, Throwable t) {
        if (!this.repository.isDisabled(priority.level) && priority.isGreaterOrEqual(getEffectiveLevel())) {
            String msg = getResourceBundleString(key);
            if (msg == null) {
                msg = key;
            }
            forcedLog(FQCN, priority, msg, t);
        }
    }

    public void l7dlog(Priority priority, String key, Object[] params, Throwable t) {
        String msg;
        if (!this.repository.isDisabled(priority.level) && priority.isGreaterOrEqual(getEffectiveLevel())) {
            String pattern = getResourceBundleString(key);
            if (pattern == null) {
                msg = key;
            } else {
                msg = MessageFormat.format(pattern, params);
            }
            forcedLog(FQCN, priority, msg, t);
        }
    }

    public void log(Priority priority, Object message, Throwable t) {
        if (!this.repository.isDisabled(priority.level) && priority.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, priority, message, t);
        }
    }

    public void log(Priority priority, Object message) {
        if (!this.repository.isDisabled(priority.level) && priority.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, priority, message, null);
        }
    }

    public void log(String callerFQCN, Priority level, Object message, Throwable t) {
        if (!this.repository.isDisabled(level.level) && level.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(callerFQCN, level, message, t);
        }
    }

    private void fireRemoveAppenderEvent(Appender appender) {
        if (appender != null) {
            if (this.repository instanceof Hierarchy) {
                ((Hierarchy) this.repository).fireRemoveAppenderEvent(this, appender);
            } else if (this.repository instanceof HierarchyEventListener) {
                ((HierarchyEventListener) this.repository).removeAppenderEvent(this, appender);
            }
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public synchronized void removeAllAppenders() {
        if (this.aai != null) {
            Vector appenders = new Vector();
            Enumeration iter = this.aai.getAllAppenders();
            while (iter != null && iter.hasMoreElements()) {
                appenders.add(iter.nextElement());
            }
            this.aai.removeAllAppenders();
            Enumeration iter2 = appenders.elements();
            while (iter2.hasMoreElements()) {
                fireRemoveAppenderEvent((Appender) iter2.nextElement());
            }
            this.aai = null;
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public synchronized void removeAppender(Appender appender) {
        if (appender == null || this.aai == null) {
            return;
        }
        boolean wasAttached = this.aai.isAttached(appender);
        this.aai.removeAppender(appender);
        if (wasAttached) {
            fireRemoveAppenderEvent(appender);
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public synchronized void removeAppender(String name) {
        if (name == null || this.aai == null) {
            return;
        }
        Appender appender = this.aai.getAppender(name);
        this.aai.removeAppender(name);
        if (appender != null) {
            fireRemoveAppenderEvent(appender);
        }
    }

    public void setAdditivity(boolean additive) {
        this.additive = additive;
    }

    public final void setHierarchy(LoggerRepository repository) {
        this.repository = repository;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setPriority(Priority priority) {
        this.level = (Level) priority;
    }

    public void setResourceBundle(ResourceBundle bundle) {
        this.resourceBundle = bundle;
    }

    public static void shutdown() {
        LogManager.shutdown();
    }

    public void warn(Object message) {
        if (!this.repository.isDisabled(Priority.WARN_INT) && Level.WARN.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.WARN, message, null);
        }
    }

    public void warn(Object message, Throwable t) {
        if (!this.repository.isDisabled(Priority.WARN_INT) && Level.WARN.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.WARN, message, t);
        }
    }
}
