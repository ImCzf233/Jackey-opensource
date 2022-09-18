package org.apache.log4j.varia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/varia/Roller.class */
public class Roller {
    static Logger cat;
    static String host;
    static int port;
    static Class class$org$apache$log4j$varia$Roller;

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    static {
        Class cls;
        if (class$org$apache$log4j$varia$Roller == null) {
            cls = class$("org.apache.log4j.varia.Roller");
            class$org$apache$log4j$varia$Roller = cls;
        } else {
            cls = class$org$apache$log4j$varia$Roller;
        }
        cat = Logger.getLogger(cls);
    }

    Roller() {
    }

    public static void main(String[] argv) {
        BasicConfigurator.configure();
        if (argv.length == 2) {
            init(argv[0], argv[1]);
        } else {
            usage("Wrong number of arguments.");
        }
        roll();
    }

    static void usage(String msg) {
        Class cls;
        System.err.println(msg);
        PrintStream printStream = System.err;
        StringBuffer append = new StringBuffer().append("Usage: java ");
        if (class$org$apache$log4j$varia$Roller == null) {
            cls = class$("org.apache.log4j.varia.Roller");
            class$org$apache$log4j$varia$Roller = cls;
        } else {
            cls = class$org$apache$log4j$varia$Roller;
        }
        printStream.println(append.append(cls.getName()).append("host_name port_number").toString());
        System.exit(1);
    }

    static void init(String hostArg, String portArg) {
        host = hostArg;
        try {
            port = Integer.parseInt(portArg);
        } catch (NumberFormatException e) {
            usage(new StringBuffer().append("Second argument ").append(portArg).append(" is not a valid integer.").toString());
        }
    }

    static void roll() {
        try {
            Socket socket = new Socket(host, port);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            dos.writeUTF(ExternallyRolledFileAppender.ROLL_OVER);
            String rc = dis.readUTF();
            if (ExternallyRolledFileAppender.f404OK.equals(rc)) {
                cat.info("Roll over signal acknowledged by remote appender.");
            } else {
                cat.warn(new StringBuffer().append("Unexpected return code ").append(rc).append(" from remote entity.").toString());
                System.exit(2);
            }
        } catch (IOException e) {
            cat.error(new StringBuffer().append("Could not send roll signal on host ").append(host).append(" port ").append(port).append(" .").toString(), e);
            System.exit(2);
        }
        System.exit(0);
    }
}
