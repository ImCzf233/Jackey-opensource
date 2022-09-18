package org.apache.log4j.net;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/net/SimpleSocketServer.class */
public class SimpleSocketServer {
    static Logger cat;
    static int port;
    static Class class$org$apache$log4j$net$SimpleSocketServer;

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    static {
        Class cls;
        if (class$org$apache$log4j$net$SimpleSocketServer == null) {
            cls = class$("org.apache.log4j.net.SimpleSocketServer");
            class$org$apache$log4j$net$SimpleSocketServer = cls;
        } else {
            cls = class$org$apache$log4j$net$SimpleSocketServer;
        }
        cat = Logger.getLogger(cls);
    }

    public static void main(String[] argv) {
        if (argv.length == 2) {
            init(argv[0], argv[1]);
        } else {
            usage("Wrong number of arguments.");
        }
        try {
            cat.info(new StringBuffer().append("Listening on port ").append(port).toString());
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                cat.info("Waiting to accept a new client.");
                Socket socket = serverSocket.accept();
                cat.info(new StringBuffer().append("Connected to client at ").append(socket.getInetAddress()).toString());
                cat.info("Starting new socket node.");
                new Thread(new SocketNode(socket, LogManager.getLoggerRepository()), new StringBuffer().append("SimpleSocketServer-").append(port).toString()).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void usage(String msg) {
        Class cls;
        System.err.println(msg);
        PrintStream printStream = System.err;
        StringBuffer append = new StringBuffer().append("Usage: java ");
        if (class$org$apache$log4j$net$SimpleSocketServer == null) {
            cls = class$("org.apache.log4j.net.SimpleSocketServer");
            class$org$apache$log4j$net$SimpleSocketServer = cls;
        } else {
            cls = class$org$apache$log4j$net$SimpleSocketServer;
        }
        printStream.println(append.append(cls.getName()).append(" port configFile").toString());
        System.exit(1);
    }

    static void init(String portStr, String configFile) {
        try {
            port = Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            usage(new StringBuffer().append("Could not interpret port number [").append(portStr).append("].").toString());
        }
        if (configFile.endsWith(".xml")) {
            DOMConfigurator.configure(configFile);
        } else {
            PropertyConfigurator.configure(configFile);
        }
    }
}
