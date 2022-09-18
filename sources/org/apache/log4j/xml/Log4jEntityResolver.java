package org.apache.log4j.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.apache.log4j.helpers.LogLog;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/xml/Log4jEntityResolver.class */
public class Log4jEntityResolver implements EntityResolver {
    private static final String PUBLIC_ID = "-//APACHE//DTD LOG4J 1.2//EN";

    @Override // org.xml.sax.EntityResolver
    public InputSource resolveEntity(String publicId, String systemId) {
        if (systemId.endsWith("log4j.dtd") || PUBLIC_ID.equals(publicId)) {
            Class clazz = getClass();
            InputStream in = clazz.getResourceAsStream("/org/apache/log4j/xml/log4j.dtd");
            if (in == null) {
                LogLog.warn(new StringBuffer().append("Could not find [log4j.dtd] using [").append(clazz.getClassLoader()).append("] class loader, parsed without DTD.").toString());
                in = new ByteArrayInputStream(new byte[0]);
            }
            return new InputSource(in);
        }
        return null;
    }
}
