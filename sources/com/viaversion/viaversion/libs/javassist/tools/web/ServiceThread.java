package com.viaversion.viaversion.libs.javassist.tools.web;

import java.io.IOException;
import java.net.Socket;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Webserver.java */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/tools/web/ServiceThread.class */
public class ServiceThread extends Thread {
    Webserver web;
    Socket sock;

    public ServiceThread(Webserver w, Socket s) {
        this.web = w;
        this.sock = s;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            this.web.process(this.sock);
        } catch (IOException e) {
        }
    }
}
