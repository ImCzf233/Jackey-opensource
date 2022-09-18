package org.apache.log4j.lf5.viewer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/viewer/LogFactor5InputDialog.class */
public class LogFactor5InputDialog extends LogFactor5Dialog {
    public static final int SIZE = 30;
    private JTextField _textField;

    public LogFactor5InputDialog(JFrame jframe, String title, String label) {
        this(jframe, title, label, 30);
    }

    public LogFactor5InputDialog(JFrame jframe, String title, String label, int size) {
        super(jframe, title, true);
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        JPanel main = new JPanel();
        main.setLayout(new FlowLayout());
        main.add(new JLabel(label));
        this._textField = new JTextField(size);
        main.add(this._textField);
        addKeyListener(new KeyAdapter(this) { // from class: org.apache.log4j.lf5.viewer.LogFactor5InputDialog.1
            private final LogFactor5InputDialog this$0;

            {
                this.this$0 = this;
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    this.this$0.hide();
                }
            }
        });
        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener(this) { // from class: org.apache.log4j.lf5.viewer.LogFactor5InputDialog.2
            private final LogFactor5InputDialog this$0;

            {
                this.this$0 = this;
            }

            public void actionPerformed(ActionEvent e) {
                this.this$0.hide();
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener(this) { // from class: org.apache.log4j.lf5.viewer.LogFactor5InputDialog.3
            private final LogFactor5InputDialog this$0;

            {
                this.this$0 = this;
            }

            public void actionPerformed(ActionEvent e) {
                this.this$0.hide();
                this.this$0._textField.setText("");
            }
        });
        bottom.add(ok);
        bottom.add(cancel);
        getContentPane().add(main, "Center");
        getContentPane().add(bottom, "South");
        pack();
        centerWindow(this);
        show();
    }

    public String getText() {
        String s = this._textField.getText();
        if (s != null && s.trim().length() == 0) {
            return null;
        }
        return s;
    }
}
