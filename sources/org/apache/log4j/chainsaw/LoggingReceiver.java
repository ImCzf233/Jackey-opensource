package org.apache.log4j.chainsaw;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:org/apache/log4j/chainsaw/LoggingReceiver.class */
public class LoggingReceiver extends Thread {
    private static final Logger LOG;
    private MyTableModel mModel;
    private ServerSocket mSvrSock;
    static Class class$org$apache$log4j$chainsaw$LoggingReceiver;

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    static {
        Class cls;
        if (class$org$apache$log4j$chainsaw$LoggingReceiver == null) {
            cls = class$("org.apache.log4j.chainsaw.LoggingReceiver");
            class$org$apache$log4j$chainsaw$LoggingReceiver = cls;
        } else {
            cls = class$org$apache$log4j$chainsaw$LoggingReceiver;
        }
        LOG = Logger.getLogger(cls);
    }

    /* loaded from: Jackey Client b2.jar:org/apache/log4j/chainsaw/LoggingReceiver$Slurper.class */
    private class Slurper implements Runnable {
        private final Socket mClient;
        private final LoggingReceiver this$0;

        Slurper(LoggingReceiver loggingReceiver, Socket aClient) {
            this.this$0 = loggingReceiver;
            this.mClient = aClient;
        }

        @Override // java.lang.Runnable
        public void run() {
            LoggingReceiver.LOG.debug("Starting to get data");
            try {
                ObjectInputStream ois = new ObjectInputStream(this.mClient.getInputStream());
                while (true) {
                    LoggingEvent event = (LoggingEvent) ois.readObject();
                    this.this$0.mModel.addEvent(new EventDetails(event));
                }
            } catch (EOFException e) {
                LoggingReceiver.LOG.info("Reached EOF, closing connection");
                try {
                    this.mClient.close();
                } catch (IOException e2) {
                    LoggingReceiver.LOG.warn("Error closing connection", e2);
                }
            } catch (ClassNotFoundException e3) {
                LoggingReceiver.LOG.warn("Got ClassNotFoundException, closing connection", e3);
                this.mClient.close();
            } catch (SocketException e4) {
                LoggingReceiver.LOG.info("Caught SocketException, closing connection");
                this.mClient.close();
            } catch (IOException e5) {
                LoggingReceiver.LOG.warn("Got IOException, closing connection", e5);
                this.mClient.close();
            }
        }
    }

    public LoggingReceiver(MyTableModel aModel, int aPort) throws IOException {
        setDaemon(true);
        this.mModel = aModel;
        this.mSvrSock = new ServerSocket(aPort);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        LOG.info("Thread started");
        while (true) {
            try {
                LOG.debug("Waiting for a connection");
                Socket client = this.mSvrSock.accept();
                LOG.debug(new StringBuffer().append("Got a connection from ").append(client.getInetAddress().getHostName()).toString());
                Thread t = new Thread(new Slurper(this, client));
                t.setDaemon(true);
                t.start();
            } catch (IOException e) {
                LOG.error("Error in accepting connections, stopping.", e);
                return;
            }
        }
    }
}
