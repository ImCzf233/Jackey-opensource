package org.apache.log4j.lf5.viewer;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/viewer/LogFactor5LoadingDialog.class */
public class LogFactor5LoadingDialog extends LogFactor5Dialog {
    public LogFactor5LoadingDialog(JFrame jframe, String message) {
        super(jframe, "LogFactor5", false);
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        JPanel main = new JPanel();
        main.setLayout(new GridBagLayout());
        wrapStringOnPanel(message, main);
        getContentPane().add(main, "Center");
        getContentPane().add(bottom, "South");
        show();
    }
}
