package org.apache.log4j.helpers;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/CyclicBuffer.class */
public class CyclicBuffer {

    /* renamed from: ea */
    LoggingEvent[] f388ea;
    int first;
    int last;
    int numElems;
    int maxSize;

    public CyclicBuffer(int maxSize) throws IllegalArgumentException {
        if (maxSize < 1) {
            throw new IllegalArgumentException(new StringBuffer().append("The maxSize argument (").append(maxSize).append(") is not a positive integer.").toString());
        }
        this.maxSize = maxSize;
        this.f388ea = new LoggingEvent[maxSize];
        this.first = 0;
        this.last = 0;
        this.numElems = 0;
    }

    public void add(LoggingEvent event) {
        this.f388ea[this.last] = event;
        int i = this.last + 1;
        this.last = i;
        if (i == this.maxSize) {
            this.last = 0;
        }
        if (this.numElems < this.maxSize) {
            this.numElems++;
            return;
        }
        int i2 = this.first + 1;
        this.first = i2;
        if (i2 == this.maxSize) {
            this.first = 0;
        }
    }

    public LoggingEvent get(int i) {
        if (i < 0 || i >= this.numElems) {
            return null;
        }
        return this.f388ea[(this.first + i) % this.maxSize];
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public LoggingEvent get() {
        LoggingEvent r = null;
        if (this.numElems > 0) {
            this.numElems--;
            r = this.f388ea[this.first];
            this.f388ea[this.first] = null;
            int i = this.first + 1;
            this.first = i;
            if (i == this.maxSize) {
                this.first = 0;
            }
        }
        return r;
    }

    public int length() {
        return this.numElems;
    }

    public void resize(int newSize) {
        if (newSize < 0) {
            throw new IllegalArgumentException(new StringBuffer().append("Negative array size [").append(newSize).append("] not allowed.").toString());
        }
        if (newSize == this.numElems) {
            return;
        }
        LoggingEvent[] temp = new LoggingEvent[newSize];
        int loopLen = newSize < this.numElems ? newSize : this.numElems;
        for (int i = 0; i < loopLen; i++) {
            temp[i] = this.f388ea[this.first];
            this.f388ea[this.first] = null;
            int i2 = this.first + 1;
            this.first = i2;
            if (i2 == this.numElems) {
                this.first = 0;
            }
        }
        this.f388ea = temp;
        this.first = 0;
        this.numElems = loopLen;
        this.maxSize = newSize;
        if (loopLen == newSize) {
            this.last = 0;
        } else {
            this.last = loopLen;
        }
    }
}
