package org.apache.log4j.spi;

import java.util.Enumeration;
import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/spi/LoggerRepository.class */
public interface LoggerRepository {
    void addHierarchyEventListener(HierarchyEventListener hierarchyEventListener);

    boolean isDisabled(int i);

    void setThreshold(Level level);

    void setThreshold(String str);

    void emitNoAppenderWarning(Category category);

    Level getThreshold();

    Logger getLogger(String str);

    Logger getLogger(String str, LoggerFactory loggerFactory);

    Logger getRootLogger();

    Logger exists(String str);

    void shutdown();

    Enumeration getCurrentLoggers();

    Enumeration getCurrentCategories();

    void fireAddAppenderEvent(Category category, Appender appender);

    void resetConfiguration();
}
