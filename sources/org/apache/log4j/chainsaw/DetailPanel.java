package org.apache.log4j.chainsaw;

import java.awt.BorderLayout;
import java.text.MessageFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/chainsaw/DetailPanel.class */
class DetailPanel extends JPanel implements ListSelectionListener {
    private static final Logger LOG;
    private static final MessageFormat FORMATTER;
    private final MyTableModel mModel;
    private final JEditorPane mDetails = new JEditorPane();
    static Class class$org$apache$log4j$chainsaw$DetailPanel;

    static {
        Class cls;
        if (class$org$apache$log4j$chainsaw$DetailPanel == null) {
            cls = class$("org.apache.log4j.chainsaw.DetailPanel");
            class$org$apache$log4j$chainsaw$DetailPanel = cls;
        } else {
            cls = class$org$apache$log4j$chainsaw$DetailPanel;
        }
        LOG = Logger.getLogger(cls);
        FORMATTER = new MessageFormat("<b>Time:</b> <code>{0,time,medium}</code>&nbsp;&nbsp;<b>Priority:</b> <code>{1}</code>&nbsp;&nbsp;<b>Thread:</b> <code>{2}</code>&nbsp;&nbsp;<b>NDC:</b> <code>{3}</code><br><b>Logger:</b> <code>{4}</code><br><b>Location:</b> <code>{5}</code><br><b>Message:</b><pre>{6}</pre><b>Throwable:</b><pre>{7}</pre>");
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    public DetailPanel(JTable aTable, MyTableModel aModel) {
        this.mModel = aModel;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Details: "));
        this.mDetails.setEditable(false);
        this.mDetails.setContentType("text/html");
        add(new JScrollPane(this.mDetails), "Center");
        ListSelectionModel rowSM = aTable.getSelectionModel();
        rowSM.addListSelectionListener(this);
    }

    public void valueChanged(ListSelectionEvent aEvent) {
        if (aEvent.getValueIsAdjusting()) {
            return;
        }
        ListSelectionModel lsm = (ListSelectionModel) aEvent.getSource();
        if (lsm.isSelectionEmpty()) {
            this.mDetails.setText("Nothing selected");
            return;
        }
        int selectedRow = lsm.getMinSelectionIndex();
        EventDetails e = this.mModel.getEventDetails(selectedRow);
        Object[] args = {new Date(e.getTimeStamp()), e.getPriority(), escape(e.getThreadName()), escape(e.getNDC()), escape(e.getCategoryName()), escape(e.getLocationDetails()), escape(e.getMessage()), escape(getThrowableStrRep(e))};
        this.mDetails.setText(FORMATTER.format(args));
        this.mDetails.setCaretPosition(0);
    }

    private static String getThrowableStrRep(EventDetails aEvent) {
        String[] strs = aEvent.getThrowableStrRep();
        if (strs == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (String str : strs) {
            sb.append(str).append("\n");
        }
        return sb.toString();
    }

    private String escape(String aStr) {
        if (aStr == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < aStr.length(); i++) {
            char c = aStr.charAt(i);
            switch (c) {
                case '\"':
                    buf.append("&quot;");
                    break;
                case '&':
                    buf.append("&amp;");
                    break;
                case '<':
                    buf.append("&lt;");
                    break;
                case '>':
                    buf.append("&gt;");
                    break;
                default:
                    buf.append(c);
                    break;
            }
        }
        return buf.toString();
    }
}
