package org.apache.log4j.chainsaw;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/chainsaw/LoadXMLAction.class */
class LoadXMLAction extends AbstractAction {
    private static final Logger LOG;
    private final JFrame mParent;
    private final JFileChooser mChooser = new JFileChooser();
    private final XMLReader mParser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    private final XMLFileHandler mHandler;
    static Class class$org$apache$log4j$chainsaw$LoadXMLAction;

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    static {
        Class cls;
        if (class$org$apache$log4j$chainsaw$LoadXMLAction == null) {
            cls = class$("org.apache.log4j.chainsaw.LoadXMLAction");
            class$org$apache$log4j$chainsaw$LoadXMLAction = cls;
        } else {
            cls = class$org$apache$log4j$chainsaw$LoadXMLAction;
        }
        LOG = Logger.getLogger(cls);
    }

    public LoadXMLAction(JFrame aParent, MyTableModel aModel) throws SAXException, ParserConfigurationException {
        this.mChooser.setMultiSelectionEnabled(false);
        this.mChooser.setFileSelectionMode(0);
        this.mParent = aParent;
        this.mHandler = new XMLFileHandler(aModel);
        this.mParser.setContentHandler(this.mHandler);
    }

    public void actionPerformed(ActionEvent aIgnore) {
        LOG.info("load file called");
        if (this.mChooser.showOpenDialog(this.mParent) == 0) {
            LOG.info("Need to load a file");
            File chosen = this.mChooser.getSelectedFile();
            LOG.info(new StringBuffer().append("loading the contents of ").append(chosen.getAbsolutePath()).toString());
            try {
                int num = loadFile(chosen.getAbsolutePath());
                JOptionPane.showMessageDialog(this.mParent, new StringBuffer().append("Loaded ").append(num).append(" events.").toString(), "CHAINSAW", 1);
            } catch (Exception e) {
                LOG.warn("caught an exception loading the file", e);
                JOptionPane.showMessageDialog(this.mParent, new StringBuffer().append("Error parsing file - ").append(e.getMessage()).toString(), "CHAINSAW", 0);
            }
        }
    }

    private int loadFile(String aFile) throws SAXException, IOException {
        int numEvents;
        synchronized (this.mParser) {
            StringBuffer buf = new StringBuffer();
            buf.append("<?xml version=\"1.0\" standalone=\"yes\"?>\n");
            buf.append("<!DOCTYPE log4j:eventSet ");
            buf.append("[<!ENTITY data SYSTEM \"file:///");
            buf.append(aFile);
            buf.append("\">]>\n");
            buf.append("<log4j:eventSet xmlns:log4j=\"Claira\">\n");
            buf.append("&data;\n");
            buf.append("</log4j:eventSet>\n");
            InputSource is = new InputSource(new StringReader(buf.toString()));
            this.mParser.parse(is);
            numEvents = this.mHandler.getNumEvents();
        }
        return numEvents;
    }
}
