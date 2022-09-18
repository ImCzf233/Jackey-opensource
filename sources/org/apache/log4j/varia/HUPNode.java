package org.apache.log4j.varia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import org.apache.log4j.helpers.LogLog;

/* compiled from: ExternallyRolledFileAppender.java */
/* loaded from: Jackey Client b2.jar:org/apache/log4j/varia/HUPNode.class */
class HUPNode implements Runnable {
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    /* renamed from: er */
    ExternallyRolledFileAppender f406er;

    public HUPNode(Socket socket, ExternallyRolledFileAppender er) {
        this.socket = socket;
        this.f406er = er;
        try {
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (RuntimeException e3) {
            e3.printStackTrace();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            String line = this.dis.readUTF();
            LogLog.debug("Got external roll over signal.");
            if (ExternallyRolledFileAppender.ROLL_OVER.equals(line)) {
                synchronized (this.f406er) {
                    this.f406er.rollOver();
                }
                this.dos.writeUTF(ExternallyRolledFileAppender.f404OK);
            } else {
                this.dos.writeUTF("Expecting [RollOver] string.");
            }
            this.dos.close();
        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
            LogLog.error("Unexpected exception. Exiting HUPNode.", e);
        } catch (IOException e2) {
            LogLog.error("Unexpected exception. Exiting HUPNode.", e2);
        } catch (RuntimeException e3) {
            LogLog.error("Unexpected exception. Exiting HUPNode.", e3);
        }
    }
}
