package org.apache.log4j.chainsaw;

import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/chainsaw/EventDetails.class */
public class EventDetails {
    private final long mTimeStamp;
    private final Priority mPriority;
    private final String mCategoryName;
    private final String mNDC;
    private final String mThreadName;
    private final String mMessage;
    private final String[] mThrowableStrRep;
    private final String mLocationDetails;

    public EventDetails(long aTimeStamp, Priority aPriority, String aCategoryName, String aNDC, String aThreadName, String aMessage, String[] aThrowableStrRep, String aLocationDetails) {
        this.mTimeStamp = aTimeStamp;
        this.mPriority = aPriority;
        this.mCategoryName = aCategoryName;
        this.mNDC = aNDC;
        this.mThreadName = aThreadName;
        this.mMessage = aMessage;
        this.mThrowableStrRep = aThrowableStrRep;
        this.mLocationDetails = aLocationDetails;
    }

    public EventDetails(LoggingEvent aEvent) {
        this(aEvent.timeStamp, aEvent.getLevel(), aEvent.getLoggerName(), aEvent.getNDC(), aEvent.getThreadName(), aEvent.getRenderedMessage(), aEvent.getThrowableStrRep(), aEvent.getLocationInformation() == null ? null : aEvent.getLocationInformation().fullInfo);
    }

    public long getTimeStamp() {
        return this.mTimeStamp;
    }

    public Priority getPriority() {
        return this.mPriority;
    }

    public String getCategoryName() {
        return this.mCategoryName;
    }

    public String getNDC() {
        return this.mNDC;
    }

    public String getThreadName() {
        return this.mThreadName;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public String getLocationDetails() {
        return this.mLocationDetails;
    }

    public String[] getThrowableStrRep() {
        return this.mThrowableStrRep;
    }
}
