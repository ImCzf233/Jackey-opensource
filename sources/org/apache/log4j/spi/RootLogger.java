package org.apache.log4j.spi;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/spi/RootLogger.class */
public final class RootLogger extends Logger {
    public RootLogger(Level level) {
        super("root");
        setLevel(level);
    }

    public final Level getChainedLevel() {
        return this.level;
    }

    @Override // org.apache.log4j.Category
    public final void setLevel(Level level) {
        if (level == null) {
            LogLog.error("You have tried to set a null level to root.", new Throwable());
        } else {
            this.level = level;
        }
    }
}
