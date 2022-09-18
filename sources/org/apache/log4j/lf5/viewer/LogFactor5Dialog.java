package org.apache.log4j.lf5.viewer;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JFrame;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/viewer/LogFactor5Dialog.class */
public abstract class LogFactor5Dialog extends JDialog {
    protected static final Font DISPLAY_FONT = new Font("Arial", 1, 12);

    public LogFactor5Dialog(JFrame jframe, String message, boolean modal) {
        super(jframe, message, modal);
    }

    public void show() {
        pack();
        minimumSizeDialog(this, 200, 100);
        centerWindow(this);
        super.show();
    }

    public void centerWindow(Window win) {
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        if (screenDim.width < win.getSize().width) {
            win.setSize(screenDim.width, win.getSize().height);
        }
        if (screenDim.height < win.getSize().height) {
            win.setSize(win.getSize().width, screenDim.height);
        }
        int x = (screenDim.width - win.getSize().width) / 2;
        int y = (screenDim.height - win.getSize().height) / 2;
        win.setLocation(x, y);
    }

    public void wrapStringOnPanel(String message, Container container) {
        String line;
        String str;
        GridBagConstraints c = getDefaultConstraints();
        c.gridwidth = 0;
        c.insets = new Insets(0, 0, 0, 0);
        GridBagLayout gbLayout = container.getLayout();
        while (message.length() > 0) {
            int newLineIndex = message.indexOf(10);
            if (newLineIndex >= 0) {
                line = message.substring(0, newLineIndex);
                str = message.substring(newLineIndex + 1);
            } else {
                line = message;
                str = "";
            }
            message = str;
            Label label = new Label(line);
            label.setFont(DISPLAY_FONT);
            gbLayout.setConstraints(label, c);
            container.add(label);
        }
    }

    protected GridBagConstraints getDefaultConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0d;
        constraints.weighty = 1.0d;
        constraints.gridheight = 1;
        constraints.insets = new Insets(4, 4, 4, 4);
        constraints.fill = 0;
        constraints.anchor = 17;
        return constraints;
    }

    protected void minimumSizeDialog(Component component, int minWidth, int minHeight) {
        if (component.getSize().width < minWidth) {
            component.setSize(minWidth, component.getSize().height);
        }
        if (component.getSize().height < minHeight) {
            component.setSize(component.getSize().width, minHeight);
        }
    }
}
