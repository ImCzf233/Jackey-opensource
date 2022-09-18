package org.apache.log4j;

import org.apache.log4j.helpers.AppenderAttachableImpl;
import org.apache.log4j.helpers.BoundedFIFO;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/Dispatcher.class */
class Dispatcher extends Thread {

    /* renamed from: bf */
    private BoundedFIFO f383bf;
    private AppenderAttachableImpl aai;
    private boolean interrupted = false;
    AsyncAppender container;

    Dispatcher(BoundedFIFO bf, AsyncAppender container) {
        this.f383bf = bf;
        this.container = container;
        this.aai = container.aai;
        setDaemon(true);
        setPriority(1);
        setName(new StringBuffer().append("Dispatcher-").append(getName()).toString());
    }

    void close() {
        synchronized (this.f383bf) {
            this.interrupted = true;
            if (this.f383bf.length() == 0) {
                this.f383bf.notify();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0052, code lost:
        r0 = r3.container.aai;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005b, code lost:
        monitor-enter(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0060, code lost:
        if (r3.aai == null) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0064, code lost:
        if (r0 == null) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0067, code lost:
        r3.aai.appendLoopOnAppenders(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0071, code lost:
        monitor-exit(r0);
     */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r3 = this;
        L0:
            r0 = r3
            org.apache.log4j.helpers.BoundedFIFO r0 = r0.f383bf
            r1 = r0
            r5 = r1
            monitor-enter(r0)
            r0 = r3
            org.apache.log4j.helpers.BoundedFIFO r0 = r0.f383bf     // Catch: java.lang.Throwable -> L4b
            int r0 = r0.length()     // Catch: java.lang.Throwable -> L4b
            if (r0 != 0) goto L2d
            r0 = r3
            boolean r0 = r0.interrupted     // Catch: java.lang.Throwable -> L4b
            if (r0 == 0) goto L1d
            r0 = r5
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L4b
            goto L7f
        L1d:
            r0 = r3
            org.apache.log4j.helpers.BoundedFIFO r0 = r0.f383bf     // Catch: java.lang.InterruptedException -> L27 java.lang.Throwable -> L4b
            r0.wait()     // Catch: java.lang.InterruptedException -> L27 java.lang.Throwable -> L4b
            goto L2d
        L27:
            r6 = move-exception
            r0 = r5
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L4b
            goto L7f
        L2d:
            r0 = r3
            org.apache.log4j.helpers.BoundedFIFO r0 = r0.f383bf     // Catch: java.lang.Throwable -> L4b
            org.apache.log4j.spi.LoggingEvent r0 = r0.get()     // Catch: java.lang.Throwable -> L4b
            r4 = r0
            r0 = r3
            org.apache.log4j.helpers.BoundedFIFO r0 = r0.f383bf     // Catch: java.lang.Throwable -> L4b
            boolean r0 = r0.wasFull()     // Catch: java.lang.Throwable -> L4b
            if (r0 == 0) goto L46
            r0 = r3
            org.apache.log4j.helpers.BoundedFIFO r0 = r0.f383bf     // Catch: java.lang.Throwable -> L4b
            r0.notify()     // Catch: java.lang.Throwable -> L4b
        L46:
            r0 = r5
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L4b
            goto L52
        L4b:
            r7 = move-exception
            r0 = r5
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L4b
            r0 = r7
            throw r0
        L52:
            r0 = r3
            org.apache.log4j.AsyncAppender r0 = r0.container
            org.apache.log4j.helpers.AppenderAttachableImpl r0 = r0.aai
            r1 = r0
            r5 = r1
            monitor-enter(r0)
            r0 = r3
            org.apache.log4j.helpers.AppenderAttachableImpl r0 = r0.aai     // Catch: java.lang.Throwable -> L75
            if (r0 == 0) goto L70
            r0 = r4
            if (r0 == 0) goto L70
            r0 = r3
            org.apache.log4j.helpers.AppenderAttachableImpl r0 = r0.aai     // Catch: java.lang.Throwable -> L75
            r1 = r4
            int r0 = r0.appendLoopOnAppenders(r1)     // Catch: java.lang.Throwable -> L75
        L70:
            r0 = r5
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L75
            goto L7c
        L75:
            r8 = move-exception
            r0 = r5
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L75
            r0 = r8
            throw r0
        L7c:
            goto L0
        L7f:
            r0 = r3
            org.apache.log4j.helpers.AppenderAttachableImpl r0 = r0.aai
            r0.removeAllAppenders()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.log4j.Dispatcher.run():void");
    }
}
