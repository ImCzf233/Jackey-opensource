package org.apache.log4j.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.xml.DOMConfigurator;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/net/JMSSink.class */
public class JMSSink implements MessageListener {
    static Logger logger;
    static Class class$org$apache$log4j$net$JMSSink;

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    static {
        Class cls;
        if (class$org$apache$log4j$net$JMSSink == null) {
            cls = class$("org.apache.log4j.net.JMSSink");
            class$org$apache$log4j$net$JMSSink = cls;
        } else {
            cls = class$org$apache$log4j$net$JMSSink;
        }
        logger = Logger.getLogger(cls);
    }

    public static void main(String[] args) throws Exception {
        String s;
        if (args.length != 5) {
            usage("Wrong number of arguments.");
        }
        String tcfBindingName = args[0];
        String topicBindingName = args[1];
        String username = args[2];
        String password = args[3];
        String configFile = args[4];
        if (configFile.endsWith(".xml")) {
            DOMConfigurator.configure(configFile);
        } else {
            PropertyConfigurator.configure(configFile);
        }
        new JMSSink(tcfBindingName, topicBindingName, username, password);
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type \"exit\" to quit JMSSink.");
        do {
            s = stdin.readLine();
        } while (!s.equalsIgnoreCase("exit"));
        System.out.println("Exiting. Kill the application if it does not exit due to daemon threads.");
    }

    public JMSSink(String tcfBindingName, String topicBindingName, String username, String password) {
        try {
            InitialContext initialContext = new InitialContext();
            TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) lookup(initialContext, tcfBindingName);
            TopicConnection topicConnection = topicConnectionFactory.createTopicConnection(username, password);
            topicConnection.start();
            TopicSession topicSession = topicConnection.createTopicSession(false, 1);
            Topic topic = (Topic) initialContext.lookup(topicBindingName);
            TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);
            topicSubscriber.setMessageListener(this);
        } catch (JMSException e) {
            logger.error("Could not read JMS message.", e);
        } catch (RuntimeException e2) {
            logger.error("Could not read JMS message.", e2);
        } catch (NamingException e3) {
            logger.error("Could not read JMS message.", e3);
        }
    }

    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                LoggingEvent event = (LoggingEvent) objectMessage.getObject();
                Logger remoteLogger = Logger.getLogger(event.getLoggerName());
                remoteLogger.callAppenders(event);
            } else {
                logger.warn(new StringBuffer().append("Received message is of type ").append(message.getJMSType()).append(", was expecting ObjectMessage.").toString());
            }
        } catch (JMSException e) {
            logger.error("Exception thrown while processing incoming message.", e);
        }
    }

    protected static Object lookup(Context ctx, String name) throws NamingException {
        try {
            return ctx.lookup(name);
        } catch (NameNotFoundException e) {
            logger.error(new StringBuffer().append("Could not find name [").append(name).append("].").toString());
            throw e;
        }
    }

    static void usage(String msg) {
        Class cls;
        System.err.println(msg);
        PrintStream printStream = System.err;
        StringBuffer append = new StringBuffer().append("Usage: java ");
        if (class$org$apache$log4j$net$JMSSink == null) {
            cls = class$("org.apache.log4j.net.JMSSink");
            class$org$apache$log4j$net$JMSSink = cls;
        } else {
            cls = class$org$apache$log4j$net$JMSSink;
        }
        printStream.println(append.append(cls.getName()).append(" TopicConnectionFactoryBindingName TopicBindingName username password configFile").toString());
        System.exit(1);
    }
}
