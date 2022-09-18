package org.apache.log4j.p005nt;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.TTCCLayout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/* renamed from: org.apache.log4j.nt.NTEventLogAppender */
/* loaded from: Jackey Client b2.jar:org/apache/log4j/nt/NTEventLogAppender.class */
public class NTEventLogAppender extends AppenderSkeleton {
    private int _handle;
    private String source;
    private String server;

    private native int registerEventSource(String str, String str2);

    private native void reportEvent(int i, String str, int i2);

    private native void deregisterEventSource(int i);

    public NTEventLogAppender() {
        this(null, null, null);
    }

    public NTEventLogAppender(String source) {
        this(null, source, null);
    }

    public NTEventLogAppender(String server, String source) {
        this(server, source, null);
    }

    public NTEventLogAppender(Layout layout) {
        this(null, null, layout);
    }

    public NTEventLogAppender(String source, Layout layout) {
        this(null, source, layout);
    }

    public NTEventLogAppender(String server, String source, Layout layout) {
        this._handle = 0;
        this.source = null;
        this.server = null;
        source = source == null ? "Log4j" : source;
        if (layout == null) {
            this.layout = new TTCCLayout();
        } else {
            this.layout = layout;
        }
        try {
            this._handle = registerEventSource(server, source);
        } catch (Exception e) {
            e.printStackTrace();
            this._handle = 0;
        }
    }

    @Override // org.apache.log4j.Appender
    public void close() {
    }

    @Override // org.apache.log4j.AppenderSkeleton, org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
        if (this.source != null) {
            try {
                this._handle = registerEventSource(this.server, this.source);
            } catch (Exception e) {
                LogLog.error("Could not register event source.", e);
                this._handle = 0;
            }
        }
    }

    @Override // org.apache.log4j.AppenderSkeleton
    public void append(LoggingEvent event) {
        String[] s;
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(this.layout.format(event));
        if (this.layout.ignoresThrowable() && (s = event.getThrowableStrRep()) != null) {
            for (String str : s) {
                sbuf.append(str);
            }
        }
        int nt_category = event.getLevel().toInt();
        reportEvent(this._handle, sbuf.toString(), nt_category);
    }

    @Override // org.apache.log4j.AppenderSkeleton
    public void finalize() {
        deregisterEventSource(this._handle);
        this._handle = 0;
    }

    public void setSource(String source) {
        this.source = source.trim();
    }

    public String getSource() {
        return this.source;
    }

    @Override // org.apache.log4j.Appender
    public boolean requiresLayout() {
        return true;
    }

    static {
        String[] archs;
        try {
            archs = new String[]{System.getProperty("os.arch")};
        } catch (SecurityException e) {
            archs = new String[]{"amd64", "ia64", "x86"};
        }
        boolean loaded = false;
        for (String str : archs) {
            try {
                System.loadLibrary(new StringBuffer().append("NTEventLogAppender.").append(str).toString());
                loaded = true;
                break;
            } catch (UnsatisfiedLinkError e2) {
                loaded = false;
            }
        }
        if (!loaded) {
            System.loadLibrary("NTEventLogAppender");
        }
    }
}
