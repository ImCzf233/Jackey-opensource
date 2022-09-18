package org.apache.log4j.lf5.viewer;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/viewer/LogFactor5ErrorDialog.class */
public class LogFactor5ErrorDialog extends LogFactor5Dialog {
    public LogFactor5ErrorDialog(JFrame jframe, String message) {
        super(jframe, "Error", true);
        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener(this) { // from class: org.apache.log4j.lf5.viewer.LogFactor5ErrorDialog.1
            private final LogFactor5ErrorDialog this$0;

            {
                this.this$0 = this;
            }

            public void actionPerformed(ActionEvent e) {
                this.this$0.hide();
            }
        });
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        bottom.add(ok);
        JPanel main = new JPanel();
        main.setLayout(new GridBagLayout());
        wrapStringOnPanel(message, main);
        getContentPane().add(main, "Center");
        getContentPane().add(bottom, "South");
        show();
    }
}
