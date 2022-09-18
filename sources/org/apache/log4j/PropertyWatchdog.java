package org.apache.log4j;

import org.apache.log4j.helpers.FileWatchdog;

/* compiled from: PropertyConfigurator.java */
/* loaded from: Jackey Client b2.jar:org/apache/log4j/PropertyWatchdog.class */
public class PropertyWatchdog extends FileWatchdog {
    public PropertyWatchdog(String filename) {
        super(filename);
    }

    @Override // org.apache.log4j.helpers.FileWatchdog
    public void doOnChange() {
        new PropertyConfigurator().doConfigure(this.filename, LogManager.getLoggerRepository());
    }
}
