package org.apache.log4j.chainsaw;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:org/apache/log4j/chainsaw/ControlPanel.class */
public class ControlPanel extends JPanel {
    private static final Logger LOG;
    static Class class$org$apache$log4j$chainsaw$ControlPanel;

    static {
        Class cls;
        if (class$org$apache$log4j$chainsaw$ControlPanel == null) {
            cls = class$("org.apache.log4j.chainsaw.ControlPanel");
            class$org$apache$log4j$chainsaw$ControlPanel = cls;
        } else {
            cls = class$org$apache$log4j$chainsaw$ControlPanel;
        }
        LOG = Logger.getLogger(cls);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    public ControlPanel(MyTableModel aModel) {
        setBorder(BorderFactory.createTitledBorder("Controls: "));
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);
        c.ipadx = 5;
        c.ipady = 5;
        c.gridx = 0;
        c.anchor = 13;
        c.gridy = 0;
        JLabel label = new JLabel("Filter Level:");
        gridbag.setConstraints(label, c);
        add(label);
        c.gridy++;
        JLabel label2 = new JLabel("Filter Thread:");
        gridbag.setConstraints(label2, c);
        add(label2);
        c.gridy++;
        JLabel label3 = new JLabel("Filter Logger:");
        gridbag.setConstraints(label3, c);
        add(label3);
        c.gridy++;
        JLabel label4 = new JLabel("Filter NDC:");
        gridbag.setConstraints(label4, c);
        add(label4);
        c.gridy++;
        JLabel label5 = new JLabel("Filter Message:");
        gridbag.setConstraints(label5, c);
        add(label5);
        c.weightx = 1.0d;
        c.gridx = 1;
        c.anchor = 17;
        c.gridy = 0;
        Level[] allPriorities = {Level.FATAL, Level.ERROR, Level.WARN, Level.INFO, Level.DEBUG, Level.TRACE};
        JComboBox priorities = new JComboBox(allPriorities);
        Level lowest = allPriorities[allPriorities.length - 1];
        priorities.setSelectedItem(lowest);
        aModel.setPriorityFilter(lowest);
        gridbag.setConstraints(priorities, c);
        add(priorities);
        priorities.setEditable(false);
        priorities.addActionListener(new ActionListener(this, aModel, priorities) { // from class: org.apache.log4j.chainsaw.ControlPanel.1
            private final MyTableModel val$aModel;
            private final JComboBox val$priorities;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = aModel;
                this.val$priorities = priorities;
            }

            public void actionPerformed(ActionEvent aEvent) {
                this.val$aModel.setPriorityFilter((Priority) this.val$priorities.getSelectedItem());
            }
        });
        c.fill = 2;
        c.gridy++;
        JTextField threadField = new JTextField("");
        threadField.getDocument().addDocumentListener(new DocumentListener(this, aModel, threadField) { // from class: org.apache.log4j.chainsaw.ControlPanel.2
            private final MyTableModel val$aModel;
            private final JTextField val$threadField;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = aModel;
                this.val$threadField = threadField;
            }

            public void insertUpdate(DocumentEvent aEvent) {
                this.val$aModel.setThreadFilter(this.val$threadField.getText());
            }

            public void removeUpdate(DocumentEvent aEvente) {
                this.val$aModel.setThreadFilter(this.val$threadField.getText());
            }

            public void changedUpdate(DocumentEvent aEvent) {
                this.val$aModel.setThreadFilter(this.val$threadField.getText());
            }
        });
        gridbag.setConstraints(threadField, c);
        add(threadField);
        c.gridy++;
        JTextField catField = new JTextField("");
        catField.getDocument().addDocumentListener(new DocumentListener(this, aModel, catField) { // from class: org.apache.log4j.chainsaw.ControlPanel.3
            private final MyTableModel val$aModel;
            private final JTextField val$catField;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = aModel;
                this.val$catField = catField;
            }

            public void insertUpdate(DocumentEvent aEvent) {
                this.val$aModel.setCategoryFilter(this.val$catField.getText());
            }

            public void removeUpdate(DocumentEvent aEvent) {
                this.val$aModel.setCategoryFilter(this.val$catField.getText());
            }

            public void changedUpdate(DocumentEvent aEvent) {
                this.val$aModel.setCategoryFilter(this.val$catField.getText());
            }
        });
        gridbag.setConstraints(catField, c);
        add(catField);
        c.gridy++;
        JTextField ndcField = new JTextField("");
        ndcField.getDocument().addDocumentListener(new DocumentListener(this, aModel, ndcField) { // from class: org.apache.log4j.chainsaw.ControlPanel.4
            private final MyTableModel val$aModel;
            private final JTextField val$ndcField;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = aModel;
                this.val$ndcField = ndcField;
            }

            public void insertUpdate(DocumentEvent aEvent) {
                this.val$aModel.setNDCFilter(this.val$ndcField.getText());
            }

            public void removeUpdate(DocumentEvent aEvent) {
                this.val$aModel.setNDCFilter(this.val$ndcField.getText());
            }

            public void changedUpdate(DocumentEvent aEvent) {
                this.val$aModel.setNDCFilter(this.val$ndcField.getText());
            }
        });
        gridbag.setConstraints(ndcField, c);
        add(ndcField);
        c.gridy++;
        JTextField msgField = new JTextField("");
        msgField.getDocument().addDocumentListener(new DocumentListener(this, aModel, msgField) { // from class: org.apache.log4j.chainsaw.ControlPanel.5
            private final MyTableModel val$aModel;
            private final JTextField val$msgField;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = aModel;
                this.val$msgField = msgField;
            }

            public void insertUpdate(DocumentEvent aEvent) {
                this.val$aModel.setMessageFilter(this.val$msgField.getText());
            }

            public void removeUpdate(DocumentEvent aEvent) {
                this.val$aModel.setMessageFilter(this.val$msgField.getText());
            }

            public void changedUpdate(DocumentEvent aEvent) {
                this.val$aModel.setMessageFilter(this.val$msgField.getText());
            }
        });
        gridbag.setConstraints(msgField, c);
        add(msgField);
        c.weightx = 0.0d;
        c.fill = 2;
        c.anchor = 13;
        c.gridx = 2;
        c.gridy = 0;
        JButton exitButton = new JButton("Exit");
        exitButton.setMnemonic('x');
        exitButton.addActionListener(ExitAction.INSTANCE);
        gridbag.setConstraints(exitButton, c);
        add(exitButton);
        c.gridy++;
        JButton clearButton = new JButton("Clear");
        clearButton.setMnemonic('c');
        clearButton.addActionListener(new ActionListener(this, aModel) { // from class: org.apache.log4j.chainsaw.ControlPanel.6
            private final MyTableModel val$aModel;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = aModel;
            }

            public void actionPerformed(ActionEvent aEvent) {
                this.val$aModel.clear();
            }
        });
        gridbag.setConstraints(clearButton, c);
        add(clearButton);
        c.gridy++;
        JButton toggleButton = new JButton("Pause");
        toggleButton.setMnemonic('p');
        toggleButton.addActionListener(new ActionListener(this, aModel, toggleButton) { // from class: org.apache.log4j.chainsaw.ControlPanel.7
            private final MyTableModel val$aModel;
            private final JButton val$toggleButton;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = aModel;
                this.val$toggleButton = toggleButton;
            }

            public void actionPerformed(ActionEvent aEvent) {
                this.val$aModel.toggle();
                this.val$toggleButton.setText(this.val$aModel.isPaused() ? "Resume" : "Pause");
            }
        });
        gridbag.setConstraints(toggleButton, c);
        add(toggleButton);
    }
}
