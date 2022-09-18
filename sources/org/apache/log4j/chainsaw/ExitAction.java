package org.apache.log4j.chainsaw;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.apache.log4j.Logger;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/chainsaw/ExitAction.class */
class ExitAction extends AbstractAction {
    private static final Logger LOG;
    public static final ExitAction INSTANCE;
    static Class class$org$apache$log4j$chainsaw$ExitAction;

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    static {
        Class cls;
        if (class$org$apache$log4j$chainsaw$ExitAction == null) {
            cls = class$("org.apache.log4j.chainsaw.ExitAction");
            class$org$apache$log4j$chainsaw$ExitAction = cls;
        } else {
            cls = class$org$apache$log4j$chainsaw$ExitAction;
        }
        LOG = Logger.getLogger(cls);
        INSTANCE = new ExitAction();
    }

    private ExitAction() {
    }

    public void actionPerformed(ActionEvent aIgnore) {
        LOG.info("shutting down");
        System.exit(0);
    }
}
