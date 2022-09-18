package org.apache.log4j.net;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/net/SocketNode.class */
public class SocketNode implements Runnable {
    Socket socket;
    LoggerRepository hierarchy;
    ObjectInputStream ois;
    static Logger logger;
    static Class class$org$apache$log4j$net$SocketNode;

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    static {
        Class cls;
        if (class$org$apache$log4j$net$SocketNode == null) {
            cls = class$("org.apache.log4j.net.SocketNode");
            class$org$apache$log4j$net$SocketNode = cls;
        } else {
            cls = class$org$apache$log4j$net$SocketNode;
        }
        logger = Logger.getLogger(cls);
    }

    public SocketNode(Socket socket, LoggerRepository hierarchy) {
        this.socket = socket;
        this.hierarchy = hierarchy;
        try {
            this.ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
            logger.error(new StringBuffer().append("Could not open ObjectInputStream to ").append(socket).toString(), e);
        } catch (IOException e2) {
            logger.error(new StringBuffer().append("Could not open ObjectInputStream to ").append(socket).toString(), e2);
        } catch (RuntimeException e3) {
            logger.error(new StringBuffer().append("Could not open ObjectInputStream to ").append(socket).toString(), e3);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            try {
                try {
                    try {
                        try {
                            if (this.ois == null) {
                                if (this.ois != null) {
                                    try {
                                        this.ois.close();
                                    } catch (Exception e) {
                                        logger.info("Could not close connection.", e);
                                    }
                                }
                                if (this.socket == null) {
                                    return;
                                }
                                try {
                                    this.socket.close();
                                    return;
                                } catch (InterruptedIOException e2) {
                                    Thread.currentThread().interrupt();
                                    return;
                                } catch (IOException e3) {
                                    return;
                                }
                            }
                            while (true) {
                                LoggingEvent event = (LoggingEvent) this.ois.readObject();
                                Logger remoteLogger = this.hierarchy.getLogger(event.getLoggerName());
                                if (event.getLevel().isGreaterOrEqual(remoteLogger.getEffectiveLevel())) {
                                    remoteLogger.callAppenders(event);
                                }
                            }
                        } catch (SocketException e4) {
                            logger.info("Caught java.net.SocketException closing conneciton.");
                            if (this.ois != null) {
                                try {
                                    this.ois.close();
                                } catch (Exception e5) {
                                    logger.info("Could not close connection.", e5);
                                }
                            }
                            if (this.socket == null) {
                                return;
                            }
                            try {
                                this.socket.close();
                            } catch (InterruptedIOException e6) {
                                Thread.currentThread().interrupt();
                            } catch (IOException e7) {
                            }
                        }
                    } catch (IOException e8) {
                        logger.info(new StringBuffer().append("Caught java.io.IOException: ").append(e8).toString());
                        logger.info("Closing connection.");
                        if (this.ois != null) {
                            try {
                                this.ois.close();
                            } catch (Exception e9) {
                                logger.info("Could not close connection.", e9);
                            }
                        }
                        if (this.socket == null) {
                            return;
                        }
                        try {
                            this.socket.close();
                        } catch (InterruptedIOException e10) {
                            Thread.currentThread().interrupt();
                        } catch (IOException e11) {
                        }
                    }
                } catch (Throwable th) {
                    if (this.ois != null) {
                        try {
                            this.ois.close();
                        } catch (Exception e12) {
                            logger.info("Could not close connection.", e12);
                        }
                    }
                    if (this.socket != null) {
                        try {
                            this.socket.close();
                        } catch (InterruptedIOException e13) {
                            Thread.currentThread().interrupt();
                        } catch (IOException e14) {
                        }
                    }
                    throw th;
                }
            } catch (EOFException e15) {
                logger.info("Caught java.io.EOFException closing conneciton.");
                if (this.ois != null) {
                    try {
                        this.ois.close();
                    } catch (Exception e16) {
                        logger.info("Could not close connection.", e16);
                    }
                }
                if (this.socket == null) {
                    return;
                }
                try {
                    this.socket.close();
                } catch (InterruptedIOException e17) {
                    Thread.currentThread().interrupt();
                } catch (IOException e18) {
                }
            }
        } catch (InterruptedIOException e19) {
            Thread.currentThread().interrupt();
            logger.info(new StringBuffer().append("Caught java.io.InterruptedIOException: ").append(e19).toString());
            logger.info("Closing connection.");
            if (this.ois != null) {
                try {
                    this.ois.close();
                } catch (Exception e20) {
                    logger.info("Could not close connection.", e20);
                }
            }
            if (this.socket == null) {
                return;
            }
            try {
                this.socket.close();
            } catch (InterruptedIOException e21) {
                Thread.currentThread().interrupt();
            } catch (IOException e22) {
            }
        } catch (Exception e23) {
            logger.error("Unexpected exception. Closing conneciton.", e23);
            if (this.ois != null) {
                try {
                    this.ois.close();
                } catch (Exception e24) {
                    logger.info("Could not close connection.", e24);
                }
            }
            if (this.socket == null) {
                return;
            }
            try {
                this.socket.close();
            } catch (InterruptedIOException e25) {
                Thread.currentThread().interrupt();
            } catch (IOException e26) {
            }
        }
    }
}
