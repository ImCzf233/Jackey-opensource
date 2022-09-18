package org.apache.log4j.net;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.CyclicBuffer;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.OptionHandler;
import org.apache.log4j.spi.TriggeringEventEvaluator;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.xml.UnrecognizedElementHandler;
import org.w3c.dom.Element;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/net/SMTPAppender.class */
public class SMTPAppender extends AppenderSkeleton implements UnrecognizedElementHandler {

    /* renamed from: to */
    private String f394to;

    /* renamed from: cc */
    private String f395cc;
    private String bcc;
    private String from;
    private String replyTo;
    private String subject;
    private String smtpHost;
    private String smtpUsername;
    private String smtpPassword;
    private String smtpProtocol;
    private int smtpPort;
    private boolean smtpDebug;
    private int bufferSize;
    private boolean locationInfo;
    private boolean sendOnClose;

    /* renamed from: cb */
    protected CyclicBuffer f396cb;
    protected Message msg;
    protected TriggeringEventEvaluator evaluator;
    static Class class$org$apache$log4j$spi$TriggeringEventEvaluator;

    public SMTPAppender() {
        this(new DefaultEvaluator());
    }

    public SMTPAppender(TriggeringEventEvaluator evaluator) {
        this.smtpPort = -1;
        this.smtpDebug = false;
        this.bufferSize = 512;
        this.locationInfo = false;
        this.sendOnClose = false;
        this.f396cb = new CyclicBuffer(this.bufferSize);
        this.evaluator = evaluator;
    }

    @Override // org.apache.log4j.AppenderSkeleton, org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
        Session session = createSession();
        this.msg = new MimeMessage(session);
        try {
            addressMessage(this.msg);
            if (this.subject != null) {
                try {
                    this.msg.setSubject(MimeUtility.encodeText(this.subject, "UTF-8", (String) null));
                } catch (UnsupportedEncodingException ex) {
                    LogLog.error("Unable to encode SMTP subject", ex);
                }
            }
        } catch (MessagingException e) {
            LogLog.error("Could not activate SMTPAppender options.", e);
        }
        if (this.evaluator instanceof OptionHandler) {
            ((OptionHandler) this.evaluator).activateOptions();
        }
    }

    protected void addressMessage(Message msg) throws MessagingException {
        if (this.from != null) {
            msg.setFrom(getAddress(this.from));
        } else {
            msg.setFrom();
        }
        if (this.replyTo != null && this.replyTo.length() > 0) {
            msg.setReplyTo(parseAddress(this.replyTo));
        }
        if (this.f394to != null && this.f394to.length() > 0) {
            msg.setRecipients(Message.RecipientType.TO, parseAddress(this.f394to));
        }
        if (this.f395cc != null && this.f395cc.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, parseAddress(this.f395cc));
        }
        if (this.bcc != null && this.bcc.length() > 0) {
            msg.setRecipients(Message.RecipientType.BCC, parseAddress(this.bcc));
        }
    }

    protected Session createSession() {
        Properties props;
        try {
            props = new Properties(System.getProperties());
        } catch (SecurityException e) {
            props = new Properties();
        }
        String prefix = "mail.smtp";
        if (this.smtpProtocol != null) {
            props.put("mail.transport.protocol", this.smtpProtocol);
            prefix = new StringBuffer().append("mail.").append(this.smtpProtocol).toString();
        }
        if (this.smtpHost != null) {
            props.put(new StringBuffer().append(prefix).append(".host").toString(), this.smtpHost);
        }
        if (this.smtpPort > 0) {
            props.put(new StringBuffer().append(prefix).append(".port").toString(), String.valueOf(this.smtpPort));
        }
        Authenticator auth = null;
        if (this.smtpPassword != null && this.smtpUsername != null) {
            props.put(new StringBuffer().append(prefix).append(".auth").toString(), "true");
            auth = new Authenticator(this) { // from class: org.apache.log4j.net.SMTPAppender.1
                private final SMTPAppender this$0;

                {
                    this.this$0 = this;
                }

                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(this.this$0.smtpUsername, this.this$0.smtpPassword);
                }
            };
        }
        Session session = Session.getInstance(props, auth);
        if (this.smtpProtocol != null) {
            session.setProtocolForAddress("rfc822", this.smtpProtocol);
        }
        if (this.smtpDebug) {
            session.setDebug(this.smtpDebug);
        }
        return session;
    }

    @Override // org.apache.log4j.AppenderSkeleton
    public void append(LoggingEvent event) {
        if (!checkEntryConditions()) {
            return;
        }
        event.getThreadName();
        event.getNDC();
        event.getMDCCopy();
        if (this.locationInfo) {
            event.getLocationInformation();
        }
        event.getRenderedMessage();
        event.getThrowableStrRep();
        this.f396cb.add(event);
        if (this.evaluator.isTriggeringEvent(event)) {
            sendBuffer();
        }
    }

    protected boolean checkEntryConditions() {
        if (this.msg == null) {
            this.errorHandler.error("Message object not configured.");
            return false;
        } else if (this.evaluator == null) {
            this.errorHandler.error(new StringBuffer().append("No TriggeringEventEvaluator is set for appender [").append(this.name).append("].").toString());
            return false;
        } else if (this.layout == null) {
            this.errorHandler.error(new StringBuffer().append("No layout set for appender named [").append(this.name).append("].").toString());
            return false;
        } else {
            return true;
        }
    }

    @Override // org.apache.log4j.Appender
    public synchronized void close() {
        this.closed = true;
        if (this.sendOnClose && this.f396cb.length() > 0) {
            sendBuffer();
        }
    }

    InternetAddress getAddress(String addressStr) {
        try {
            return new InternetAddress(addressStr);
        } catch (AddressException e) {
            this.errorHandler.error(new StringBuffer().append("Could not parse address [").append(addressStr).append("].").toString(), e, 6);
            return null;
        }
    }

    InternetAddress[] parseAddress(String addressStr) {
        try {
            return InternetAddress.parse(addressStr, true);
        } catch (AddressException e) {
            this.errorHandler.error(new StringBuffer().append("Could not parse address [").append(addressStr).append("].").toString(), e, 6);
            return null;
        }
    }

    public String getTo() {
        return this.f394to;
    }

    @Override // org.apache.log4j.Appender
    public boolean requiresLayout() {
        return true;
    }

    protected String formatBody() {
        String[] s;
        StringBuffer sbuf = new StringBuffer();
        String t = this.layout.getHeader();
        if (t != null) {
            sbuf.append(t);
        }
        int len = this.f396cb.length();
        for (int i = 0; i < len; i++) {
            LoggingEvent event = this.f396cb.get();
            sbuf.append(this.layout.format(event));
            if (this.layout.ignoresThrowable() && (s = event.getThrowableStrRep()) != null) {
                for (String str : s) {
                    sbuf.append(str);
                    sbuf.append(Layout.LINE_SEP);
                }
            }
        }
        String t2 = this.layout.getFooter();
        if (t2 != null) {
            sbuf.append(t2);
        }
        return sbuf.toString();
    }

    protected void sendBuffer() {
        MimeBodyPart part;
        try {
            String s = formatBody();
            boolean allAscii = true;
            for (int i = 0; i < s.length() && allAscii; i++) {
                allAscii = s.charAt(i) <= 127;
            }
            if (allAscii) {
                part = new MimeBodyPart();
                part.setContent(s, this.layout.getContentType());
            } else {
                try {
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    Writer writer = new OutputStreamWriter(MimeUtility.encode(os, "quoted-printable"), "UTF-8");
                    writer.write(s);
                    writer.close();
                    InternetHeaders headers = new InternetHeaders();
                    headers.setHeader("Content-Type", new StringBuffer().append(this.layout.getContentType()).append("; charset=UTF-8").toString());
                    headers.setHeader("Content-Transfer-Encoding", "quoted-printable");
                    part = new MimeBodyPart(headers, os.toByteArray());
                } catch (Exception e) {
                    StringBuffer sbuf = new StringBuffer(s);
                    for (int i2 = 0; i2 < sbuf.length(); i2++) {
                        if (sbuf.charAt(i2) >= 128) {
                            sbuf.setCharAt(i2, '?');
                        }
                    }
                    part = new MimeBodyPart();
                    part.setContent(sbuf.toString(), this.layout.getContentType());
                }
            }
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(part);
            this.msg.setContent(mimeMultipart);
            this.msg.setSentDate(new Date());
            Transport.send(this.msg);
        } catch (RuntimeException e2) {
            LogLog.error("Error occured while sending e-mail notification.", e2);
        } catch (MessagingException e3) {
            LogLog.error("Error occured while sending e-mail notification.", e3);
        }
    }

    public String getEvaluatorClass() {
        if (this.evaluator == null) {
            return null;
        }
        return this.evaluator.getClass().getName();
    }

    public String getFrom() {
        return this.from;
    }

    public String getReplyTo() {
        return this.replyTo;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setReplyTo(String addresses) {
        this.replyTo = addresses;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        this.f396cb.resize(bufferSize);
    }

    public void setSMTPHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSMTPHost() {
        return this.smtpHost;
    }

    public void setTo(String to) {
        this.f394to = to;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setEvaluatorClass(String value) {
        Class cls;
        if (class$org$apache$log4j$spi$TriggeringEventEvaluator == null) {
            cls = class$("org.apache.log4j.spi.TriggeringEventEvaluator");
            class$org$apache$log4j$spi$TriggeringEventEvaluator = cls;
        } else {
            cls = class$org$apache$log4j$spi$TriggeringEventEvaluator;
        }
        this.evaluator = (TriggeringEventEvaluator) OptionConverter.instantiateByClassName(value, cls, this.evaluator);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    public void setLocationInfo(boolean locationInfo) {
        this.locationInfo = locationInfo;
    }

    public boolean getLocationInfo() {
        return this.locationInfo;
    }

    public void setCc(String addresses) {
        this.f395cc = addresses;
    }

    public String getCc() {
        return this.f395cc;
    }

    public void setBcc(String addresses) {
        this.bcc = addresses;
    }

    public String getBcc() {
        return this.bcc;
    }

    public void setSMTPPassword(String password) {
        this.smtpPassword = password;
    }

    public void setSMTPUsername(String username) {
        this.smtpUsername = username;
    }

    public void setSMTPDebug(boolean debug) {
        this.smtpDebug = debug;
    }

    public String getSMTPPassword() {
        return this.smtpPassword;
    }

    public String getSMTPUsername() {
        return this.smtpUsername;
    }

    public boolean getSMTPDebug() {
        return this.smtpDebug;
    }

    public final void setEvaluator(TriggeringEventEvaluator trigger) {
        if (trigger == null) {
            throw new NullPointerException("trigger");
        }
        this.evaluator = trigger;
    }

    public final TriggeringEventEvaluator getEvaluator() {
        return this.evaluator;
    }

    @Override // org.apache.log4j.xml.UnrecognizedElementHandler
    public boolean parseUnrecognizedElement(Element element, Properties props) throws Exception {
        Class cls;
        if ("triggeringPolicy".equals(element.getNodeName())) {
            if (class$org$apache$log4j$spi$TriggeringEventEvaluator == null) {
                cls = class$("org.apache.log4j.spi.TriggeringEventEvaluator");
                class$org$apache$log4j$spi$TriggeringEventEvaluator = cls;
            } else {
                cls = class$org$apache$log4j$spi$TriggeringEventEvaluator;
            }
            Object triggerPolicy = DOMConfigurator.parseElement(element, props, cls);
            if (triggerPolicy instanceof TriggeringEventEvaluator) {
                setEvaluator((TriggeringEventEvaluator) triggerPolicy);
                return true;
            }
            return true;
        }
        return false;
    }

    public final String getSMTPProtocol() {
        return this.smtpProtocol;
    }

    public final void setSMTPProtocol(String val) {
        this.smtpProtocol = val;
    }

    public final int getSMTPPort() {
        return this.smtpPort;
    }

    public final void setSMTPPort(int val) {
        this.smtpPort = val;
    }

    public final boolean getSendOnClose() {
        return this.sendOnClose;
    }

    public final void setSendOnClose(boolean val) {
        this.sendOnClose = val;
    }
}
