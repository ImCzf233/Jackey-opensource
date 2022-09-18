package org.apache.log4j.xml;

import org.apache.log4j.helpers.LogLog;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/xml/SAXErrorHandler.class */
public class SAXErrorHandler implements ErrorHandler {
    @Override // org.xml.sax.ErrorHandler
    public void error(SAXParseException ex) {
        emitMessage("Continuable parsing error ", ex);
    }

    @Override // org.xml.sax.ErrorHandler
    public void fatalError(SAXParseException ex) {
        emitMessage("Fatal parsing error ", ex);
    }

    @Override // org.xml.sax.ErrorHandler
    public void warning(SAXParseException ex) {
        emitMessage("Parsing warning ", ex);
    }

    private static void emitMessage(String msg, SAXParseException ex) {
        LogLog.warn(new StringBuffer().append(msg).append(ex.getLineNumber()).append(" and column ").append(ex.getColumnNumber()).toString());
        LogLog.warn(ex.getMessage(), ex.getException());
    }
}
