package org.apache.log4j.jmx;

import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import org.apache.log4j.Logger;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/jmx/Agent.class */
public class Agent {
    static Logger log;
    static Class class$org$apache$log4j$jmx$Agent;

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    static {
        Class cls;
        if (class$org$apache$log4j$jmx$Agent == null) {
            cls = class$("org.apache.log4j.jmx.Agent");
            class$org$apache$log4j$jmx$Agent = cls;
        } else {
            cls = class$org$apache$log4j$jmx$Agent;
        }
        log = Logger.getLogger(cls);
    }

    private static Object createServer() {
        try {
            Object newInstance = Class.forName("com.sun.jdmk.comm.HtmlAdapterServer").newInstance();
            return newInstance;
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex.toString());
        } catch (IllegalAccessException ex2) {
            throw new RuntimeException(ex2.toString());
        } catch (InstantiationException ex3) {
            throw new RuntimeException(ex3.toString());
        }
    }

    private static void startServer(Object server) {
        try {
            server.getClass().getMethod("start", new Class[0]).invoke(server, new Object[0]);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex.toString());
        } catch (NoSuchMethodException ex2) {
            throw new RuntimeException(ex2.toString());
        } catch (InvocationTargetException ex3) {
            Throwable cause = ex3.getTargetException();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause != null) {
                if ((cause instanceof InterruptedException) || (cause instanceof InterruptedIOException)) {
                    Thread.currentThread().interrupt();
                }
                throw new RuntimeException(cause.toString());
            }
            throw new RuntimeException();
        }
    }

    public void start() {
        MBeanServer server = MBeanServerFactory.createMBeanServer();
        Object html = createServer();
        try {
            log.info("Registering HtmlAdaptorServer instance.");
            server.registerMBean(html, new ObjectName("Adaptor:name=html,port=8082"));
            log.info("Registering HierarchyDynamicMBean instance.");
            HierarchyDynamicMBean hdm = new HierarchyDynamicMBean();
            server.registerMBean(hdm, new ObjectName("log4j:hiearchy=default"));
            startServer(html);
        } catch (JMException e) {
            log.error("Problem while registering MBeans instances.", e);
        } catch (RuntimeException e2) {
            log.error("Problem while registering MBeans instances.", e2);
        }
    }
}
