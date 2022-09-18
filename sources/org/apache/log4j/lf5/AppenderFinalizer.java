package org.apache.log4j.lf5;

import org.apache.log4j.lf5.viewer.LogBrokerMonitor;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/AppenderFinalizer.class */
public class AppenderFinalizer {
    protected LogBrokerMonitor _defaultMonitor;

    public AppenderFinalizer(LogBrokerMonitor defaultMonitor) {
        this._defaultMonitor = null;
        this._defaultMonitor = defaultMonitor;
    }

    protected void finalize() throws Throwable {
        System.out.println("Disposing of the default LogBrokerMonitor instance");
        this._defaultMonitor.dispose();
    }
}
