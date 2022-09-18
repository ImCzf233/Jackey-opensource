package org.apache.log4j.varia;

import org.apache.log4j.RollingFileAppender;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/varia/ExternallyRolledFileAppender.class */
public class ExternallyRolledFileAppender extends RollingFileAppender {
    public static final String ROLL_OVER = "RollOver";

    /* renamed from: OK */
    public static final String f404OK = "OK";
    int port = 0;
    HUP hup;

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    @Override // org.apache.log4j.FileAppender, org.apache.log4j.WriterAppender, org.apache.log4j.AppenderSkeleton, org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
        super.activateOptions();
        if (this.port != 0) {
            if (this.hup != null) {
                this.hup.interrupt();
            }
            this.hup = new HUP(this, this.port);
            this.hup.setDaemon(true);
            this.hup.start();
        }
    }
}
