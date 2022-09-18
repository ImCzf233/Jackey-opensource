package org.apache.log4j.xml;

import org.apache.log4j.LogManager;
import org.apache.log4j.helpers.FileWatchdog;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DOMConfigurator.java */
/* loaded from: Jackey Client b2.jar:org/apache/log4j/xml/XMLWatchdog.class */
public class XMLWatchdog extends FileWatchdog {
    public XMLWatchdog(String filename) {
        super(filename);
    }

    @Override // org.apache.log4j.helpers.FileWatchdog
    public void doOnChange() {
        new DOMConfigurator().doConfigure(this.filename, LogManager.getLoggerRepository());
    }
}
