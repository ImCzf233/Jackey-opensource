package org.apache.log4j.lf5;

import org.apache.log4j.lf5.viewer.LogBrokerMonitor;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/StartLogFactor5.class */
public class StartLogFactor5 {
    public static final void main(String[] args) {
        LogBrokerMonitor monitor = new LogBrokerMonitor(LogLevel.getLog4JLevels());
        monitor.setFrameSize(LF5Appender.getDefaultMonitorWidth(), LF5Appender.getDefaultMonitorHeight());
        monitor.setFontSize(12);
        monitor.show();
    }
}
