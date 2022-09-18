package org.apache.log4j.lf5.viewer;

import javax.swing.table.DefaultTableModel;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/viewer/LogTableModel.class */
public class LogTableModel extends DefaultTableModel {
    private static final long serialVersionUID = 3593300685868700894L;

    public LogTableModel(Object[] colNames, int numRows) {
        super(colNames, numRows);
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
