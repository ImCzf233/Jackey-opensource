package org.apache.log4j.helpers;

import java.io.Writer;
import org.apache.log4j.spi.ErrorHandler;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/SyslogQuietWriter.class */
public class SyslogQuietWriter extends QuietWriter {
    int syslogFacility;
    int level;

    public SyslogQuietWriter(Writer writer, int syslogFacility, ErrorHandler eh) {
        super(writer, eh);
        this.syslogFacility = syslogFacility;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setSyslogFacility(int syslogFacility) {
        this.syslogFacility = syslogFacility;
    }

    @Override // org.apache.log4j.helpers.QuietWriter, java.io.Writer
    public void write(String string) {
        super.write(new StringBuffer().append("<").append(this.syslogFacility | this.level).append(">").append(string).toString());
    }
}
