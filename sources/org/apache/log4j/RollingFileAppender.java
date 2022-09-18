package org.apache.log4j;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Writer;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/RollingFileAppender.class */
public class RollingFileAppender extends FileAppender {
    protected long maxFileSize = 10485760;
    protected int maxBackupIndex = 1;
    private long nextRollover = 0;

    public RollingFileAppender() {
    }

    public RollingFileAppender(Layout layout, String filename, boolean append) throws IOException {
        super(layout, filename, append);
    }

    public RollingFileAppender(Layout layout, String filename) throws IOException {
        super(layout, filename);
    }

    public int getMaxBackupIndex() {
        return this.maxBackupIndex;
    }

    public long getMaximumFileSize() {
        return this.maxFileSize;
    }

    public void rollOver() {
        if (this.f387qw != null) {
            long size = ((CountingQuietWriter) this.f387qw).getCount();
            LogLog.debug(new StringBuffer().append("rolling over count=").append(size).toString());
            this.nextRollover = size + this.maxFileSize;
        }
        LogLog.debug(new StringBuffer().append("maxBackupIndex=").append(this.maxBackupIndex).toString());
        boolean renameSucceeded = true;
        if (this.maxBackupIndex > 0) {
            File file = new File(new StringBuffer().append(this.fileName).append('.').append(this.maxBackupIndex).toString());
            if (file.exists()) {
                renameSucceeded = file.delete();
            }
            for (int i = this.maxBackupIndex - 1; i >= 1 && renameSucceeded; i--) {
                File file2 = new File(new StringBuffer().append(this.fileName).append(".").append(i).toString());
                if (file2.exists()) {
                    File target = new File(new StringBuffer().append(this.fileName).append('.').append(i + 1).toString());
                    LogLog.debug(new StringBuffer().append("Renaming file ").append(file2).append(" to ").append(target).toString());
                    renameSucceeded = file2.renameTo(target);
                }
            }
            if (renameSucceeded) {
                File target2 = new File(new StringBuffer().append(this.fileName).append(".").append(1).toString());
                closeFile();
                File file3 = new File(this.fileName);
                LogLog.debug(new StringBuffer().append("Renaming file ").append(file3).append(" to ").append(target2).toString());
                renameSucceeded = file3.renameTo(target2);
                if (!renameSucceeded) {
                    try {
                        setFile(this.fileName, true, this.bufferedIO, this.bufferSize);
                    } catch (IOException e) {
                        if (e instanceof InterruptedIOException) {
                            Thread.currentThread().interrupt();
                        }
                        LogLog.error(new StringBuffer().append("setFile(").append(this.fileName).append(", true) call failed.").toString(), e);
                    }
                }
            }
        }
        if (renameSucceeded) {
            try {
                setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
                this.nextRollover = 0L;
            } catch (IOException e2) {
                if (e2 instanceof InterruptedIOException) {
                    Thread.currentThread().interrupt();
                }
                LogLog.error(new StringBuffer().append("setFile(").append(this.fileName).append(", false) call failed.").toString(), e2);
            }
        }
    }

    @Override // org.apache.log4j.FileAppender
    public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
        super.setFile(fileName, append, this.bufferedIO, this.bufferSize);
        if (append) {
            File f = new File(fileName);
            ((CountingQuietWriter) this.f387qw).setCount(f.length());
        }
    }

    public void setMaxBackupIndex(int maxBackups) {
        this.maxBackupIndex = maxBackups;
    }

    public void setMaximumFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public void setMaxFileSize(String value) {
        this.maxFileSize = OptionConverter.toFileSize(value, this.maxFileSize + 1);
    }

    @Override // org.apache.log4j.FileAppender
    protected void setQWForFiles(Writer writer) {
        this.f387qw = new CountingQuietWriter(writer, this.errorHandler);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.apache.log4j.WriterAppender
    public void subAppend(LoggingEvent event) {
        super.subAppend(event);
        if (this.fileName != null && this.f387qw != null) {
            long size = ((CountingQuietWriter) this.f387qw).getCount();
            if (size >= this.maxFileSize && size >= this.nextRollover) {
                rollOver();
            }
        }
    }
}
