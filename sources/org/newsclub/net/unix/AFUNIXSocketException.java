package org.newsclub.net.unix;

import java.net.SocketException;

/* loaded from: Jackey Client b2.jar:org/newsclub/net/unix/AFUNIXSocketException.class */
public class AFUNIXSocketException extends SocketException {
    private static final long serialVersionUID = 1;
    private final String socketFile;

    public AFUNIXSocketException(String reason) {
        this(reason, (String) null);
    }

    public AFUNIXSocketException(String reason, Throwable cause) {
        this(reason, (String) null);
        initCause(cause);
    }

    public AFUNIXSocketException(String reason, String socketFile) {
        super(reason);
        this.socketFile = socketFile;
    }

    @Override // java.lang.Throwable
    public String toString() {
        if (this.socketFile == null) {
            return super.toString();
        }
        return super.toString() + " (socket: " + this.socketFile + ")";
    }
}
