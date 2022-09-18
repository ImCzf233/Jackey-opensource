package org.newsclub.net.unix;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;

/* loaded from: Jackey Client b2.jar:org/newsclub/net/unix/AFUNIXSocketImpl.class */
public class AFUNIXSocketImpl extends SocketImpl {
    private static final int SHUT_RD = 0;
    private static final int SHUT_WR = 1;
    private static final int SHUT_RD_WR = 2;
    private String socketFile;
    private boolean closed = false;
    private boolean bound = false;
    private boolean connected = false;
    private boolean closedInputStream = false;
    private boolean closedOutputStream = false;

    /* renamed from: in */
    private final AFUNIXInputStream f410in = new AFUNIXInputStream();
    private final AFUNIXOutputStream out = new AFUNIXOutputStream();

    public AFUNIXSocketImpl() {
        this.fd = new FileDescriptor();
    }

    public FileDescriptor getFD() {
        return this.fd;
    }

    @Override // java.net.SocketImpl
    public void accept(SocketImpl socket) throws IOException {
        AFUNIXSocketImpl si = (AFUNIXSocketImpl) socket;
        NativeUnixSocket.accept(this.socketFile, this.fd, si.fd);
        si.socketFile = this.socketFile;
        si.connected = true;
    }

    @Override // java.net.SocketImpl
    protected int available() throws IOException {
        return NativeUnixSocket.available(this.fd);
    }

    protected void bind(SocketAddress addr) throws IOException {
        bind(0, addr);
    }

    public void bind(int backlog, SocketAddress addr) throws IOException {
        if (!(addr instanceof AFUNIXSocketAddress)) {
            throw new SocketException("Cannot bind to this type of address: " + addr.getClass());
        }
        AFUNIXSocketAddress socketAddress = (AFUNIXSocketAddress) addr;
        this.socketFile = socketAddress.getSocketFile();
        NativeUnixSocket.bind(this.socketFile, this.fd, backlog);
        this.bound = true;
        this.localport = socketAddress.getPort();
    }

    @Override // java.net.SocketImpl
    protected void bind(InetAddress host, int port) throws IOException {
        throw new SocketException("Cannot bind to this type of address: " + InetAddress.class);
    }

    public void checkClose() throws IOException {
        if (!this.closedInputStream || this.closedOutputStream) {
        }
    }

    @Override // java.net.SocketImpl
    public synchronized void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        if (this.fd.valid()) {
            NativeUnixSocket.shutdown(this.fd, 2);
            NativeUnixSocket.close(this.fd);
        }
        if (this.bound) {
            NativeUnixSocket.unlink(this.socketFile);
        }
        this.connected = false;
    }

    @Override // java.net.SocketImpl
    protected void connect(String host, int port) throws IOException {
        throw new SocketException("Cannot bind to this type of address: " + InetAddress.class);
    }

    @Override // java.net.SocketImpl
    protected void connect(InetAddress address, int port) throws IOException {
        throw new SocketException("Cannot bind to this type of address: " + InetAddress.class);
    }

    @Override // java.net.SocketImpl
    public void connect(SocketAddress addr, int timeout) throws IOException {
        if (!(addr instanceof AFUNIXSocketAddress)) {
            throw new SocketException("Cannot bind to this type of address: " + addr.getClass());
        }
        AFUNIXSocketAddress socketAddress = (AFUNIXSocketAddress) addr;
        this.socketFile = socketAddress.getSocketFile();
        NativeUnixSocket.connect(this.socketFile, this.fd);
        this.address = socketAddress.getAddress();
        this.port = socketAddress.getPort();
        this.localport = 0;
        this.connected = true;
    }

    @Override // java.net.SocketImpl
    protected void create(boolean stream) throws IOException {
    }

    @Override // java.net.SocketImpl
    protected InputStream getInputStream() throws IOException {
        if (!this.connected && !this.bound) {
            throw new IOException("Not connected/not bound");
        }
        return this.f410in;
    }

    @Override // java.net.SocketImpl
    protected OutputStream getOutputStream() throws IOException {
        if (!this.connected && !this.bound) {
            throw new IOException("Not connected/not bound");
        }
        return this.out;
    }

    @Override // java.net.SocketImpl
    protected void listen(int backlog) throws IOException {
        NativeUnixSocket.listen(this.fd, backlog);
    }

    @Override // java.net.SocketImpl
    protected void sendUrgentData(int data) throws IOException {
        NativeUnixSocket.write(this.fd, new byte[]{(byte) (data & 255)}, 0, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:org/newsclub/net/unix/AFUNIXSocketImpl$AFUNIXInputStream.class */
    public final class AFUNIXInputStream extends InputStream {
        private boolean streamClosed;

        private AFUNIXInputStream() {
            AFUNIXSocketImpl.this = r4;
            this.streamClosed = false;
        }

        @Override // java.io.InputStream
        public int read(byte[] buf, int off, int len) throws IOException {
            if (this.streamClosed) {
                throw new IOException("This InputStream has already been closed.");
            }
            if (len == 0) {
                return 0;
            }
            int maxRead = buf.length - off;
            if (len > maxRead) {
                len = maxRead;
            }
            try {
                return NativeUnixSocket.read(AFUNIXSocketImpl.this.fd, buf, off, len);
            } catch (IOException e) {
                throw ((IOException) new IOException(e.getMessage() + " at " + AFUNIXSocketImpl.this.toString()).initCause(e));
            }
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            byte[] buf1 = new byte[1];
            int numRead = read(buf1, 0, 1);
            if (numRead <= 0) {
                return -1;
            }
            return buf1[0] & 255;
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.streamClosed) {
                return;
            }
            this.streamClosed = true;
            if (AFUNIXSocketImpl.this.fd.valid()) {
                NativeUnixSocket.shutdown(AFUNIXSocketImpl.this.fd, 0);
            }
            AFUNIXSocketImpl.this.closedInputStream = true;
            AFUNIXSocketImpl.this.checkClose();
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            int av = NativeUnixSocket.available(AFUNIXSocketImpl.this.fd);
            return av;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:org/newsclub/net/unix/AFUNIXSocketImpl$AFUNIXOutputStream.class */
    public final class AFUNIXOutputStream extends OutputStream {
        private boolean streamClosed;

        private AFUNIXOutputStream() {
            AFUNIXSocketImpl.this = r4;
            this.streamClosed = false;
        }

        @Override // java.io.OutputStream
        public void write(int oneByte) throws IOException {
            byte[] buf1 = {(byte) oneByte};
            write(buf1, 0, 1);
        }

        @Override // java.io.OutputStream
        public void write(byte[] buf, int off, int len) throws IOException {
            if (this.streamClosed) {
                throw new AFUNIXSocketException("This OutputStream has already been closed.");
            }
            if (len > buf.length - off) {
                throw new IndexOutOfBoundsException();
            }
            while (len > 0) {
                try {
                    if (Thread.interrupted()) {
                        break;
                    }
                    int written = NativeUnixSocket.write(AFUNIXSocketImpl.this.fd, buf, off, len);
                    if (written == -1) {
                        throw new IOException("Unspecific error while writing");
                    }
                    len -= written;
                    off += written;
                } catch (IOException e) {
                    throw ((IOException) new IOException(e.getMessage() + " at " + AFUNIXSocketImpl.this.toString()).initCause(e));
                }
            }
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.streamClosed) {
                return;
            }
            this.streamClosed = true;
            if (AFUNIXSocketImpl.this.fd.valid()) {
                NativeUnixSocket.shutdown(AFUNIXSocketImpl.this.fd, 1);
            }
            AFUNIXSocketImpl.this.closedOutputStream = true;
            AFUNIXSocketImpl.this.checkClose();
        }
    }

    @Override // java.net.SocketImpl
    public String toString() {
        return super.toString() + "[fd=" + this.fd + "; file=" + this.socketFile + "; connected=" + this.connected + "; bound=" + this.bound + "]";
    }

    private static int expectInteger(Object value) throws SocketException {
        try {
            return ((Integer) value).intValue();
        } catch (ClassCastException e) {
            throw new AFUNIXSocketException("Unsupported value: " + value, e);
        } catch (NullPointerException e2) {
            throw new AFUNIXSocketException("Value must not be null", e2);
        }
    }

    private static int expectBoolean(Object value) throws SocketException {
        try {
            return ((Boolean) value).booleanValue() ? 1 : 0;
        } catch (ClassCastException e) {
            throw new AFUNIXSocketException("Unsupported value: " + value, e);
        } catch (NullPointerException e2) {
            throw new AFUNIXSocketException("Value must not be null", e2);
        }
    }

    @Override // java.net.SocketOptions
    public Object getOption(int optID) throws SocketException {
        try {
            switch (optID) {
                case 1:
                case 8:
                    return Boolean.valueOf(NativeUnixSocket.getSocketOptionInt(this.fd, optID) != 0);
                case 128:
                case 4097:
                case 4098:
                case 4102:
                    return Integer.valueOf(NativeUnixSocket.getSocketOptionInt(this.fd, optID));
                default:
                    throw new AFUNIXSocketException("Unsupported option: " + optID);
            }
        } catch (AFUNIXSocketException e) {
            throw e;
        } catch (Exception e2) {
            throw new AFUNIXSocketException("Error while getting option", e2);
        }
    }

    @Override // java.net.SocketOptions
    public void setOption(int optID, Object value) throws SocketException {
        try {
            switch (optID) {
                case 1:
                case 8:
                    NativeUnixSocket.setSocketOptionInt(this.fd, optID, expectBoolean(value));
                    return;
                case 128:
                    if (value instanceof Boolean) {
                        boolean b = ((Boolean) value).booleanValue();
                        if (b) {
                            throw new SocketException("Only accepting Boolean.FALSE here");
                        }
                        NativeUnixSocket.setSocketOptionInt(this.fd, optID, -1);
                        return;
                    }
                    NativeUnixSocket.setSocketOptionInt(this.fd, optID, expectInteger(value));
                    return;
                case 4097:
                case 4098:
                case 4102:
                    NativeUnixSocket.setSocketOptionInt(this.fd, optID, expectInteger(value));
                    return;
                default:
                    throw new AFUNIXSocketException("Unsupported option: " + optID);
            }
        } catch (AFUNIXSocketException e) {
            throw e;
        } catch (Exception e2) {
            throw new AFUNIXSocketException("Error while setting option", e2);
        }
    }

    @Override // java.net.SocketImpl
    protected void shutdownInput() throws IOException {
        if (!this.closed && this.fd.valid()) {
            NativeUnixSocket.shutdown(this.fd, 0);
        }
    }

    @Override // java.net.SocketImpl
    protected void shutdownOutput() throws IOException {
        if (!this.closed && this.fd.valid()) {
            NativeUnixSocket.shutdown(this.fd, 1);
        }
    }

    /* loaded from: Jackey Client b2.jar:org/newsclub/net/unix/AFUNIXSocketImpl$Lenient.class */
    public static class Lenient extends AFUNIXSocketImpl {
        @Override // org.newsclub.net.unix.AFUNIXSocketImpl, java.net.SocketOptions
        public void setOption(int optID, Object value) throws SocketException {
            try {
                super.setOption(optID, value);
            } catch (SocketException e) {
                switch (optID) {
                    case 1:
                        return;
                    default:
                        throw e;
                }
            }
        }

        @Override // org.newsclub.net.unix.AFUNIXSocketImpl, java.net.SocketOptions
        public Object getOption(int optID) throws SocketException {
            try {
                return super.getOption(optID);
            } catch (SocketException e) {
                switch (optID) {
                    case 1:
                    case 8:
                        return false;
                    default:
                        throw e;
                }
            }
        }
    }
}
