package org.newsclub.net.unix;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

/* loaded from: Jackey Client b2.jar:org/newsclub/net/unix/AFUNIXServerSocket.class */
public class AFUNIXServerSocket extends ServerSocket {
    private AFUNIXSocketAddress boundEndpoint = null;
    private final Thread shutdownThread = new Thread() { // from class: org.newsclub.net.unix.AFUNIXServerSocket.1
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                if (AFUNIXServerSocket.this.boundEndpoint != null) {
                    NativeUnixSocket.unlink(AFUNIXServerSocket.this.boundEndpoint.getSocketFile());
                }
            } catch (IOException e) {
            }
        }
    };
    private final AFUNIXSocketImpl implementation = new AFUNIXSocketImpl();

    protected AFUNIXServerSocket() throws IOException {
        NativeUnixSocket.initServerImpl(this, this.implementation);
        Runtime.getRuntime().addShutdownHook(this.shutdownThread);
        NativeUnixSocket.setCreatedServer(this);
    }

    public static AFUNIXServerSocket newInstance() throws IOException {
        AFUNIXServerSocket instance = new AFUNIXServerSocket();
        return instance;
    }

    public static AFUNIXServerSocket bindOn(AFUNIXSocketAddress addr) throws IOException {
        AFUNIXServerSocket socket = newInstance();
        socket.bind(addr);
        return socket;
    }

    @Override // java.net.ServerSocket
    public void bind(SocketAddress endpoint, int backlog) throws IOException {
        if (isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (isBound()) {
            throw new SocketException("Already bound");
        }
        if (!(endpoint instanceof AFUNIXSocketAddress)) {
            throw new IOException("Can only bind to endpoints of type " + AFUNIXSocketAddress.class.getName());
        }
        this.implementation.bind(backlog, endpoint);
        this.boundEndpoint = (AFUNIXSocketAddress) endpoint;
    }

    @Override // java.net.ServerSocket
    public boolean isBound() {
        return this.boundEndpoint != null;
    }

    @Override // java.net.ServerSocket
    public Socket accept() throws IOException {
        if (isClosed()) {
            throw new SocketException("Socket is closed");
        }
        AFUNIXSocket as = AFUNIXSocket.newInstance();
        this.implementation.accept(as.impl);
        as.addr = this.boundEndpoint;
        NativeUnixSocket.setConnected(as);
        return as;
    }

    @Override // java.net.ServerSocket
    public String toString() {
        if (!isBound()) {
            return "AFUNIXServerSocket[unbound]";
        }
        return "AFUNIXServerSocket[" + this.boundEndpoint.getSocketFile() + "]";
    }

    @Override // java.net.ServerSocket, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (isClosed()) {
            return;
        }
        super.close();
        this.implementation.close();
        if (this.boundEndpoint != null) {
            NativeUnixSocket.unlink(this.boundEndpoint.getSocketFile());
        }
        try {
            Runtime.getRuntime().removeShutdownHook(this.shutdownThread);
        } catch (IllegalStateException e) {
        }
    }

    public static boolean isSupported() {
        return NativeUnixSocket.isLoaded();
    }
}
