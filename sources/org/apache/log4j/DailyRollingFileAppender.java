package org.apache.log4j;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/DailyRollingFileAppender.class */
public class DailyRollingFileAppender extends FileAppender {
    static final int TOP_OF_TROUBLE = -1;
    static final int TOP_OF_MINUTE = 0;
    static final int TOP_OF_HOUR = 1;
    static final int HALF_DAY = 2;
    static final int TOP_OF_DAY = 3;
    static final int TOP_OF_WEEK = 4;
    static final int TOP_OF_MONTH = 5;
    private String datePattern;
    private String scheduledFilename;
    private long nextCheck;
    Date now;
    SimpleDateFormat sdf;

    /* renamed from: rc */
    RollingCalendar f382rc;
    int checkPeriod;
    static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");

    public DailyRollingFileAppender() {
        this.datePattern = "'.'yyyy-MM-dd";
        this.nextCheck = System.currentTimeMillis() - 1;
        this.now = new Date();
        this.f382rc = new RollingCalendar();
        this.checkPeriod = -1;
    }

    public DailyRollingFileAppender(Layout layout, String filename, String datePattern) throws IOException {
        super(layout, filename, true);
        this.datePattern = "'.'yyyy-MM-dd";
        this.nextCheck = System.currentTimeMillis() - 1;
        this.now = new Date();
        this.f382rc = new RollingCalendar();
        this.checkPeriod = -1;
        this.datePattern = datePattern;
        activateOptions();
    }

    public void setDatePattern(String pattern) {
        this.datePattern = pattern;
    }

    public String getDatePattern() {
        return this.datePattern;
    }

    @Override // org.apache.log4j.FileAppender, org.apache.log4j.WriterAppender, org.apache.log4j.AppenderSkeleton, org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
        super.activateOptions();
        if (this.datePattern != null && this.fileName != null) {
            this.now.setTime(System.currentTimeMillis());
            this.sdf = new SimpleDateFormat(this.datePattern);
            int type = computeCheckPeriod();
            printPeriodicity(type);
            this.f382rc.setType(type);
            File file = new File(this.fileName);
            this.scheduledFilename = new StringBuffer().append(this.fileName).append(this.sdf.format(new Date(file.lastModified()))).toString();
            return;
        }
        LogLog.error(new StringBuffer().append("Either File or DatePattern options are not set for appender [").append(this.name).append("].").toString());
    }

    void printPeriodicity(int type) {
        switch (type) {
            case 0:
                LogLog.debug(new StringBuffer().append("Appender [").append(this.name).append("] to be rolled every minute.").toString());
                return;
            case 1:
                LogLog.debug(new StringBuffer().append("Appender [").append(this.name).append("] to be rolled on top of every hour.").toString());
                return;
            case 2:
                LogLog.debug(new StringBuffer().append("Appender [").append(this.name).append("] to be rolled at midday and midnight.").toString());
                return;
            case 3:
                LogLog.debug(new StringBuffer().append("Appender [").append(this.name).append("] to be rolled at midnight.").toString());
                return;
            case 4:
                LogLog.debug(new StringBuffer().append("Appender [").append(this.name).append("] to be rolled at start of week.").toString());
                return;
            case 5:
                LogLog.debug(new StringBuffer().append("Appender [").append(this.name).append("] to be rolled at start of every month.").toString());
                return;
            default:
                LogLog.warn(new StringBuffer().append("Unknown periodicity for appender [").append(this.name).append("].").toString());
                return;
        }
    }

    int computeCheckPeriod() {
        RollingCalendar rollingCalendar = new RollingCalendar(gmtTimeZone, Locale.getDefault());
        Date epoch = new Date(0L);
        if (this.datePattern != null) {
            for (int i = 0; i <= 5; i++) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.datePattern);
                simpleDateFormat.setTimeZone(gmtTimeZone);
                String r0 = simpleDateFormat.format(epoch);
                rollingCalendar.setType(i);
                Date next = new Date(rollingCalendar.getNextCheckMillis(epoch));
                String r1 = simpleDateFormat.format(next);
                if (r0 != null && r1 != null && !r0.equals(r1)) {
                    return i;
                }
            }
            return -1;
        }
        return -1;
    }

    void rollOver() throws IOException {
        if (this.datePattern == null) {
            this.errorHandler.error("Missing DatePattern option in rollOver().");
            return;
        }
        String datedFilename = new StringBuffer().append(this.fileName).append(this.sdf.format(this.now)).toString();
        if (this.scheduledFilename.equals(datedFilename)) {
            return;
        }
        closeFile();
        File target = new File(this.scheduledFilename);
        if (target.exists()) {
            target.delete();
        }
        File file = new File(this.fileName);
        boolean result = file.renameTo(target);
        if (result) {
            LogLog.debug(new StringBuffer().append(this.fileName).append(" -> ").append(this.scheduledFilename).toString());
        } else {
            LogLog.error(new StringBuffer().append("Failed to rename [").append(this.fileName).append("] to [").append(this.scheduledFilename).append("].").toString());
        }
        try {
            setFile(this.fileName, true, this.bufferedIO, this.bufferSize);
        } catch (IOException e) {
            this.errorHandler.error(new StringBuffer().append("setFile(").append(this.fileName).append(", true) call failed.").toString());
        }
        this.scheduledFilename = datedFilename;
    }

    @Override // org.apache.log4j.WriterAppender
    public void subAppend(LoggingEvent event) {
        long n = System.currentTimeMillis();
        if (n >= this.nextCheck) {
            this.now.setTime(n);
            this.nextCheck = this.f382rc.getNextCheckMillis(this.now);
            try {
                rollOver();
            } catch (IOException ioe) {
                if (ioe instanceof InterruptedIOException) {
                    Thread.currentThread().interrupt();
                }
                LogLog.error("rollOver() failed.", ioe);
            }
        }
        super.subAppend(event);
    }
}
