package org.apache.log4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Writer;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.QuietWriter;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/FileAppender.class */
public class FileAppender extends WriterAppender {
    protected boolean fileAppend;
    protected String fileName;
    protected boolean bufferedIO;
    protected int bufferSize;

    public FileAppender() {
        this.fileAppend = true;
        this.fileName = null;
        this.bufferedIO = false;
        this.bufferSize = 8192;
    }

    public FileAppender(Layout layout, String filename, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
        this.fileAppend = true;
        this.fileName = null;
        this.bufferedIO = false;
        this.bufferSize = 8192;
        this.layout = layout;
        setFile(filename, append, bufferedIO, bufferSize);
    }

    public FileAppender(Layout layout, String filename, boolean append) throws IOException {
        this.fileAppend = true;
        this.fileName = null;
        this.bufferedIO = false;
        this.bufferSize = 8192;
        this.layout = layout;
        setFile(filename, append, false, this.bufferSize);
    }

    public FileAppender(Layout layout, String filename) throws IOException {
        this(layout, filename, true);
    }

    public void setFile(String file) {
        String val = file.trim();
        this.fileName = val;
    }

    public boolean getAppend() {
        return this.fileAppend;
    }

    public String getFile() {
        return this.fileName;
    }

    @Override // org.apache.log4j.WriterAppender, org.apache.log4j.AppenderSkeleton, org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
        if (this.fileName != null) {
            try {
                setFile(this.fileName, this.fileAppend, this.bufferedIO, this.bufferSize);
                return;
            } catch (IOException e) {
                this.errorHandler.error(new StringBuffer().append("setFile(").append(this.fileName).append(",").append(this.fileAppend).append(") call failed.").toString(), e, 4);
                return;
            }
        }
        LogLog.warn(new StringBuffer().append("File option not set for appender [").append(this.name).append("].").toString());
        LogLog.warn("Are you using FileAppender instead of ConsoleAppender?");
    }

    public void closeFile() {
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

    public boolean getBufferedIO() {
        return this.bufferedIO;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setAppend(boolean flag) {
        this.fileAppend = flag;
    }

    public void setBufferedIO(boolean bufferedIO) {
        this.bufferedIO = bufferedIO;
        if (bufferedIO) {
            this.immediateFlush = false;
        }
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
        FileOutputStream ostream;
        LogLog.debug(new StringBuffer().append("setFile called: ").append(fileName).append(", ").append(append).toString());
        if (bufferedIO) {
            setImmediateFlush(false);
        }
        reset();
        try {
            ostream = new FileOutputStream(fileName, append);
        } catch (FileNotFoundException ex) {
            String parentName = new File(fileName).getParent();
            if (parentName != null) {
                File parentDir = new File(parentName);
                if (!parentDir.exists() && parentDir.mkdirs()) {
                    ostream = new FileOutputStream(fileName, append);
                } else {
                    throw ex;
                }
            } else {
                throw ex;
            }
        }
        Writer fw = createWriter(ostream);
        if (bufferedIO) {
            fw = new BufferedWriter(fw, bufferSize);
        }
        setQWForFiles(fw);
        this.fileName = fileName;
        this.fileAppend = append;
        this.bufferedIO = bufferedIO;
        this.bufferSize = bufferSize;
        writeHeader();
        LogLog.debug("setFile ended");
    }

    protected void setQWForFiles(Writer writer) {
        this.f387qw = new QuietWriter(writer, this.errorHandler);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.apache.log4j.WriterAppender
    public void reset() {
        closeFile();
        this.fileName = null;
        super.reset();
    }
}
