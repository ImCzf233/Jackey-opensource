package org.newsclub.net.unix;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

/* loaded from: Jackey Client b2.jar:org/newsclub/net/unix/AFUNIXSocketAddress.class */
public class AFUNIXSocketAddress extends InetSocketAddress {
    private static final long serialVersionUID = 1;
    private final String socketFile;

    public AFUNIXSocketAddress(File socketFile) throws IOException {
        this(socketFile, 0);
    }

    public AFUNIXSocketAddress(File socketFile, int port) throws IOException {
        super(0);
        if (port != 0) {
            NativeUnixSocket.setPort1(this, port);
        }
        this.socketFile = socketFile.getCanonicalPath();
    }

    public String getSocketFile() {
        return this.socketFile;
    }

    @Override // java.net.InetSocketAddress
    public String toString() {
        return getClass().getName() + "[host=" + getHostName() + ";port=" + getPort() + ";file=" + this.socketFile + "]";
    }
}
