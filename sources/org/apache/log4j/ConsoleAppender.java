package org.apache.log4j;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.log4j.helpers.LogLog;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/ConsoleAppender.class */
public class ConsoleAppender extends WriterAppender {
    public static final String SYSTEM_OUT = "System.out";
    public static final String SYSTEM_ERR = "System.err";
    protected String target;
    private boolean follow;

    public ConsoleAppender() {
        this.target = SYSTEM_OUT;
        this.follow = false;
    }

    public ConsoleAppender(Layout layout) {
        this(layout, SYSTEM_OUT);
    }

    public ConsoleAppender(Layout layout, String target) {
        this.target = SYSTEM_OUT;
        this.follow = false;
        setLayout(layout);
        setTarget(target);
        activateOptions();
    }

    public void setTarget(String value) {
        String v = value.trim();
        if (SYSTEM_OUT.equalsIgnoreCase(v)) {
            this.target = SYSTEM_OUT;
        } else if (SYSTEM_ERR.equalsIgnoreCase(v)) {
            this.target = SYSTEM_ERR;
        } else {
            targetWarn(value);
        }
    }

    public String getTarget() {
        return this.target;
    }

    public final void setFollow(boolean newValue) {
        this.follow = newValue;
    }

    public final boolean getFollow() {
        return this.follow;
    }

    void targetWarn(String val) {
        LogLog.warn(new StringBuffer().append("[").append(val).append("] should be System.out or System.err.").toString());
        LogLog.warn("Using previously set target, System.out by default.");
    }

    @Override // org.apache.log4j.WriterAppender, org.apache.log4j.AppenderSkeleton, org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
        if (this.follow) {
            if (this.target.equals(SYSTEM_ERR)) {
                setWriter(createWriter(new SystemErrStream()));
            } else {
                setWriter(createWriter(new SystemOutStream()));
            }
        } else if (this.target.equals(SYSTEM_ERR)) {
            setWriter(createWriter(System.err));
        } else {
            setWriter(createWriter(System.out));
        }
        super.activateOptions();
    }

    @Override // org.apache.log4j.WriterAppender
    public final void closeWriter() {
        if (this.follow) {
            super.closeWriter();
        }
    }

    /* loaded from: Jackey Client b2.jar:org/apache/log4j/ConsoleAppender$SystemErrStream.class */
    public static class SystemErrStream extends OutputStream {
        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() {
            System.err.flush();
        }

        @Override // java.io.OutputStream
        public void write(byte[] b) throws IOException {
            System.err.write(b);
        }

        @Override // java.io.OutputStream
        public void write(byte[] b, int off, int len) throws IOException {
            System.err.write(b, off, len);
        }

        @Override // java.io.OutputStream
        public void write(int b) throws IOException {
            System.err.write(b);
        }
    }

    /* loaded from: Jackey Client b2.jar:org/apache/log4j/ConsoleAppender$SystemOutStream.class */
    public static class SystemOutStream extends OutputStream {
        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() {
            System.out.flush();
        }

        @Override // java.io.OutputStream
        public void write(byte[] b) throws IOException {
            System.out.write(b);
        }

        @Override // java.io.OutputStream
        public void write(byte[] b, int off, int len) throws IOException {
            System.out.write(b, off, len);
        }

        @Override // java.io.OutputStream
        public void write(int b) throws IOException {
            System.out.write(b);
        }
    }
}
