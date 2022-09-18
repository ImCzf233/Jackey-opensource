package org.newsclub.net.unix;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;

/* loaded from: Jackey Client b2.jar:org/newsclub/net/unix/NativeUnixSocket.class */
public final class NativeUnixSocket {
    private static boolean loaded;

    public static native void bind(String str, FileDescriptor fileDescriptor, int i) throws IOException;

    public static native void listen(FileDescriptor fileDescriptor, int i) throws IOException;

    public static native void accept(String str, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2) throws IOException;

    public static native void connect(String str, FileDescriptor fileDescriptor) throws IOException;

    public static native int read(FileDescriptor fileDescriptor, byte[] bArr, int i, int i2) throws IOException;

    public static native int write(FileDescriptor fileDescriptor, byte[] bArr, int i, int i2) throws IOException;

    public static native void close(FileDescriptor fileDescriptor) throws IOException;

    public static native void shutdown(FileDescriptor fileDescriptor, int i) throws IOException;

    public static native int getSocketOptionInt(FileDescriptor fileDescriptor, int i) throws IOException;

    public static native void setSocketOptionInt(FileDescriptor fileDescriptor, int i, int i2) throws IOException;

    public static native void unlink(String str) throws IOException;

    public static native int available(FileDescriptor fileDescriptor) throws IOException;

    public static native void initServerImpl(AFUNIXServerSocket aFUNIXServerSocket, AFUNIXSocketImpl aFUNIXSocketImpl);

    public static native void setCreated(AFUNIXSocket aFUNIXSocket);

    public static native void setConnected(AFUNIXSocket aFUNIXSocket);

    static native void setBound(AFUNIXSocket aFUNIXSocket);

    public static native void setCreatedServer(AFUNIXServerSocket aFUNIXServerSocket);

    static native void setBoundServer(AFUNIXServerSocket aFUNIXServerSocket);

    static native void setPort(AFUNIXSocketAddress aFUNIXSocketAddress, int i);

    NativeUnixSocket() {
    }

    static {
        loaded = false;
        try {
            Class.forName("org.newsclub.net.unix.NarSystem").getMethod("loadLibrary", new Class[0]).invoke(null, new Object[0]);
            loaded = true;
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Could not find NarSystem class.\n\n*** ECLIPSE USERS ***\nIf you're running from within Eclipse, please try closing the \"junixsocket-native-common\" project\n", e);
        } catch (Exception e2) {
            throw new IllegalStateException(e2);
        }
    }

    public static boolean isLoaded() {
        return loaded;
    }

    static void checkSupported() {
    }

    public static void setPort1(AFUNIXSocketAddress addr, int port) throws AFUNIXSocketException {
        Field portField;
        if (port < 0) {
            throw new IllegalArgumentException("port out of range:" + port);
        }
        boolean setOk = false;
        try {
            Field holderField = InetSocketAddress.class.getDeclaredField("holder");
            if (holderField != null) {
                holderField.setAccessible(true);
                Object holder = holderField.get(addr);
                if (holder != null && (portField = holder.getClass().getDeclaredField("port")) != null) {
                    portField.setAccessible(true);
                    portField.set(holder, Integer.valueOf(port));
                    setOk = true;
                }
            } else {
                setPort(addr, port);
            }
            if (!setOk) {
                throw new AFUNIXSocketException("Could not set port");
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            if (e2 instanceof AFUNIXSocketException) {
                throw ((AFUNIXSocketException) e2);
            }
            throw new AFUNIXSocketException("Could not set port", e2);
        }
    }
}
