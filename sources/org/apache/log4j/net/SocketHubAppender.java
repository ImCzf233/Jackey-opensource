package org.apache.log4j.net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.CyclicBuffer;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/net/SocketHubAppender.class */
public class SocketHubAppender extends AppenderSkeleton {
    static final int DEFAULT_PORT = 4560;
    private int port;
    private Vector oosList;
    private ServerMonitor serverMonitor;
    private boolean locationInfo;
    private CyclicBuffer buffer;
    private String application;
    private boolean advertiseViaMulticastDNS;
    private ZeroConfSupport zeroConf;
    public static final String ZONE = "_log4j_obj_tcpaccept_appender.local.";
    private ServerSocket serverSocket;

    public SocketHubAppender() {
        this.port = 4560;
        this.oosList = new Vector();
        this.serverMonitor = null;
        this.locationInfo = false;
        this.buffer = null;
    }

    public SocketHubAppender(int _port) {
        this.port = 4560;
        this.oosList = new Vector();
        this.serverMonitor = null;
        this.locationInfo = false;
        this.buffer = null;
        this.port = _port;
        startServer();
    }

    @Override // org.apache.log4j.AppenderSkeleton, org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
        if (this.advertiseViaMulticastDNS) {
            this.zeroConf = new ZeroConfSupport(ZONE, this.port, getName());
            this.zeroConf.advertise();
        }
        startServer();
    }

    @Override // org.apache.log4j.Appender
    public synchronized void close() {
        if (this.closed) {
            return;
        }
        LogLog.debug(new StringBuffer().append("closing SocketHubAppender ").append(getName()).toString());
        this.closed = true;
        if (this.advertiseViaMulticastDNS) {
            this.zeroConf.unadvertise();
        }
        cleanUp();
        LogLog.debug(new StringBuffer().append("SocketHubAppender ").append(getName()).append(" closed").toString());
    }

    public void cleanUp() {
        LogLog.debug("stopping ServerSocket");
        this.serverMonitor.stopMonitor();
        this.serverMonitor = null;
        LogLog.debug("closing client connections");
        while (this.oosList.size() != 0) {
            ObjectOutputStream oos = (ObjectOutputStream) this.oosList.elementAt(0);
            if (oos != null) {
                try {
                    oos.close();
                } catch (InterruptedIOException e) {
                    Thread.currentThread().interrupt();
                    LogLog.error("could not close oos.", e);
                } catch (IOException e2) {
                    LogLog.error("could not close oos.", e2);
                }
                this.oosList.removeElementAt(0);
            }
        }
    }

    @Override // org.apache.log4j.AppenderSkeleton
    public void append(LoggingEvent event) {
        if (event != null) {
            if (this.locationInfo) {
                event.getLocationInformation();
            }
            if (this.application != null) {
                event.setProperty("application", this.application);
            }
            event.getNDC();
            event.getThreadName();
            event.getMDCCopy();
            event.getRenderedMessage();
            event.getThrowableStrRep();
            if (this.buffer != null) {
                this.buffer.add(event);
            }
        }
        if (event == null || this.oosList.size() == 0) {
            return;
        }
        int streamCount = 0;
        while (streamCount < this.oosList.size()) {
            ObjectOutputStream oos = null;
            try {
                oos = (ObjectOutputStream) this.oosList.elementAt(streamCount);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            if (oos != null) {
                try {
                    oos.writeObject(event);
                    oos.flush();
                    oos.reset();
                } catch (IOException e2) {
                    if (e2 instanceof InterruptedIOException) {
                        Thread.currentThread().interrupt();
                    }
                    this.oosList.removeElementAt(streamCount);
                    LogLog.debug("dropped connection");
                    streamCount--;
                }
                streamCount++;
            } else {
                return;
            }
        }
    }

    @Override // org.apache.log4j.Appender
    public boolean requiresLayout() {
        return false;
    }

    public void setPort(int _port) {
        this.port = _port;
    }

    public void setApplication(String lapp) {
        this.application = lapp;
    }

    public String getApplication() {
        return this.application;
    }

    public int getPort() {
        return this.port;
    }

    public void setBufferSize(int _bufferSize) {
        this.buffer = new CyclicBuffer(_bufferSize);
    }

    public int getBufferSize() {
        if (this.buffer == null) {
            return 0;
        }
        return this.buffer.getMaxSize();
    }

    public void setLocationInfo(boolean _locationInfo) {
        this.locationInfo = _locationInfo;
    }

    public boolean getLocationInfo() {
        return this.locationInfo;
    }

    public void setAdvertiseViaMulticastDNS(boolean advertiseViaMulticastDNS) {
        this.advertiseViaMulticastDNS = advertiseViaMulticastDNS;
    }

    public boolean isAdvertiseViaMulticastDNS() {
        return this.advertiseViaMulticastDNS;
    }

    private void startServer() {
        this.serverMonitor = new ServerMonitor(this, this.port, this.oosList);
    }

    protected ServerSocket createServerSocket(int socketPort) throws IOException {
        return new ServerSocket(socketPort);
    }

    /* loaded from: Jackey Client b2.jar:org/apache/log4j/net/SocketHubAppender$ServerMonitor.class */
    public class ServerMonitor implements Runnable {
        private int port;
        private Vector oosList;
        private boolean keepRunning = true;
        private Thread monitorThread = new Thread(this);
        private final SocketHubAppender this$0;

        public ServerMonitor(SocketHubAppender socketHubAppender, int _port, Vector _oosList) {
            this.this$0 = socketHubAppender;
            this.port = _port;
            this.oosList = _oosList;
            this.monitorThread.setDaemon(true);
            this.monitorThread.setName(new StringBuffer().append("SocketHubAppender-Monitor-").append(this.port).toString());
            this.monitorThread.start();
        }

        public synchronized void stopMonitor() {
            if (this.keepRunning) {
                LogLog.debug("server monitor thread shutting down");
                this.keepRunning = false;
                try {
                    if (this.this$0.serverSocket != null) {
                        this.this$0.serverSocket.close();
                        this.this$0.serverSocket = null;
                    }
                } catch (IOException e) {
                }
                try {
                    this.monitorThread.join();
                } catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                }
                this.monitorThread = null;
                LogLog.debug("server monitor thread shut down");
            }
        }

        private void sendCachedEvents(ObjectOutputStream stream) throws IOException {
            if (this.this$0.buffer != null) {
                for (int i = 0; i < this.this$0.buffer.length(); i++) {
                    stream.writeObject(this.this$0.buffer.get(i));
                }
                stream.flush();
                stream.reset();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.this$0.serverSocket = null;
            try {
                try {
                    this.this$0.serverSocket = this.this$0.createServerSocket(this.port);
                    this.this$0.serverSocket.setSoTimeout(1000);
                    try {
                        this.this$0.serverSocket.setSoTimeout(1000);
                        while (this.keepRunning) {
                            Socket socket = null;
                            try {
                                socket = this.this$0.serverSocket.accept();
                            } catch (InterruptedIOException e) {
                            } catch (SocketException e2) {
                                LogLog.error("exception accepting socket, shutting down server socket.", e2);
                                this.keepRunning = false;
                            } catch (IOException e3) {
                                LogLog.error("exception accepting socket.", e3);
                            }
                            if (socket != null) {
                                try {
                                    InetAddress remoteAddress = socket.getInetAddress();
                                    LogLog.debug(new StringBuffer().append("accepting connection from ").append(remoteAddress.getHostName()).append(" (").append(remoteAddress.getHostAddress()).append(")").toString());
                                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                    if (this.this$0.buffer != null && this.this$0.buffer.length() > 0) {
                                        sendCachedEvents(oos);
                                    }
                                    this.oosList.addElement(oos);
                                } catch (IOException e4) {
                                    if (e4 instanceof InterruptedIOException) {
                                        Thread.currentThread().interrupt();
                                    }
                                    LogLog.error("exception creating output stream on socket.", e4);
                                }
                            }
                        }
                    } catch (SocketException e5) {
                        LogLog.error("exception setting timeout, shutting down server socket.", e5);
                        try {
                            this.this$0.serverSocket.close();
                        } catch (InterruptedIOException e6) {
                            Thread.currentThread().interrupt();
                        } catch (IOException e7) {
                        }
                    }
                } catch (Exception e8) {
                    if ((e8 instanceof InterruptedIOException) || (e8 instanceof InterruptedException)) {
                        Thread.currentThread().interrupt();
                    }
                    LogLog.error("exception setting timeout, shutting down server socket.", e8);
                    this.keepRunning = false;
                }
            } finally {
                try {
                    this.this$0.serverSocket.close();
                } catch (InterruptedIOException e9) {
                    Thread.currentThread().interrupt();
                } catch (IOException e10) {
                }
            }
        }
    }
}
