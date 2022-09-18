package org.apache.log4j;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.QuietWriter;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/WriterAppender.class */
public class WriterAppender extends AppenderSkeleton {
    protected boolean immediateFlush;
    protected String encoding;

    /* renamed from: qw */
    protected QuietWriter f387qw;

    public WriterAppender() {
        this.immediateFlush = true;
    }

    public WriterAppender(Layout layout, OutputStream os) {
        this(layout, new OutputStreamWriter(os));
    }

    public WriterAppender(Layout layout, Writer writer) {
        this.immediateFlush = true;
        this.layout = layout;
        setWriter(writer);
    }

    public void setImmediateFlush(boolean value) {
        this.immediateFlush = value;
    }

    public boolean getImmediateFlush() {
        return this.immediateFlush;
    }

    @Override // org.apache.log4j.AppenderSkeleton, org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
    }

    @Override // org.apache.log4j.AppenderSkeleton
    public void append(LoggingEvent event) {
        if (!checkEntryConditions()) {
            return;
        }
        subAppend(event);
    }

    protected boolean checkEntryConditions() {
        if (this.closed) {
            LogLog.warn("Not allowed to write to a closed appender.");
            return false;
        } else if (this.f387qw == null) {
            this.errorHandler.error(new StringBuffer().append("No output stream or file set for the appender named [").append(this.name).append("].").toString());
            return false;
        } else if (this.layout == null) {
            this.errorHandler.error(new StringBuffer().append("No layout set for the appender named [").append(this.name).append("].").toString());
            return false;
        } else {
            return true;
        }
    }

    @Override // org.apache.log4j.Appender
    public synchronized void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        writeFooter();
        reset();
    }

    public void closeWriter() {
        if (this.f387qw != null) {
            try {
                this.f387qw.close();
            } catch (IOException e) {
                if (e instanceof InterruptedIOException) {
                    Thread.currentThread().interrupt();
                }
                LogLog.error(new StringBuffer().append("Could not close ").append(this.f387qw).toString(), e);
            }
        }
    }

    public OutputStreamWriter createWriter(OutputStream os) {
        OutputStreamWriter retval = null;
        String enc = getEncoding();
        if (enc != null) {
            try {
                retval = new OutputStreamWriter(os, enc);
            } catch (IOException e) {
                if (e instanceof InterruptedIOException) {
                    Thread.currentThread().interrupt();
                }
                LogLog.warn("Error initializing output writer.");
                LogLog.warn("Unsupported encoding?");
            }
        }
        if (retval == null) {
            retval = new OutputStreamWriter(os);
        }
        return retval;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String value) {
        this.encoding = value;
    }

    @Override // org.apache.log4j.AppenderSkeleton, org.apache.log4j.Appender
    public synchronized void setErrorHandler(ErrorHandler eh) {
        if (eh == null) {
            LogLog.warn("You have tried to set a null error-handler.");
            return;
        }
        this.errorHandler = eh;
        if (this.f387qw != null) {
            this.f387qw.setErrorHandler(eh);
        }
    }

    public synchronized void setWriter(Writer writer) {
        reset();
        this.f387qw = new QuietWriter(writer, this.errorHandler);
        writeHeader();
    }

    public void subAppend(LoggingEvent event) {
        String[] s;
        this.f387qw.write(this.layout.format(event));
        if (this.layout.ignoresThrowable() && (s = event.getThrowableStrRep()) != null) {
            for (String str : s) {
                this.f387qw.write(str);
                this.f387qw.write(Layout.LINE_SEP);
            }
        }
        if (shouldFlush(event)) {
            this.f387qw.flush();
        }
    }

    @Override // org.apache.log4j.Appender
    public boolean requiresLayout() {
        return true;
    }

    public void reset() {
        closeWriter();
        this.f387qw = null;
    }

    protected void writeFooter() {
        String f;
        if (this.layout != null && (f = this.layout.getFooter()) != null && this.f387qw != null) {
            this.f387qw.write(f);
            this.f387qw.flush();
        }
    }

    public void writeHeader() {
        String h;
        if (this.layout != null && (h = this.layout.getHeader()) != null && this.f387qw != null) {
            this.f387qw.write(h);
        }
    }

    protected boolean shouldFlush(LoggingEvent event) {
        return this.immediateFlush;
    }
}
