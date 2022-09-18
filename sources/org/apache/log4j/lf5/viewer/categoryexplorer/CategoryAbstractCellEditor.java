package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/viewer/categoryexplorer/CategoryAbstractCellEditor.class */
public class CategoryAbstractCellEditor implements TableCellEditor, TreeCellEditor {
    protected Object _value;
    static Class class$javax$swing$event$CellEditorListener;
    protected EventListenerList _listenerList = new EventListenerList();
    protected ChangeEvent _changeEvent = null;
    protected int _clickCountToStart = 1;

    public Object getCellEditorValue() {
        return this._value;
    }

    public void setCellEditorValue(Object value) {
        this._value = value;
    }

    public void setClickCountToStart(int count) {
        this._clickCountToStart = count;
    }

    public int getClickCountToStart() {
        return this._clickCountToStart;
    }

    public boolean isCellEditable(EventObject anEvent) {
        if ((anEvent instanceof MouseEvent) && ((MouseEvent) anEvent).getClickCount() < this._clickCountToStart) {
            return false;
        }
        return true;
    }

    public boolean shouldSelectCell(EventObject anEvent) {
        if (isCellEditable(anEvent)) {
            if (anEvent == null || ((MouseEvent) anEvent).getClickCount() >= this._clickCountToStart) {
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    public void cancelCellEditing() {
        fireEditingCanceled();
    }

    public void addCellEditorListener(CellEditorListener l) {
        Class cls;
        EventListenerList eventListenerList = this._listenerList;
        if (class$javax$swing$event$CellEditorListener == null) {
            cls = class$("javax.swing.event.CellEditorListener");
            class$javax$swing$event$CellEditorListener = cls;
        } else {
            cls = class$javax$swing$event$CellEditorListener;
        }
        eventListenerList.add(cls, l);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    public void removeCellEditorListener(CellEditorListener l) {
        Class cls;
        EventListenerList eventListenerList = this._listenerList;
        if (class$javax$swing$event$CellEditorListener == null) {
            cls = class$("javax.swing.event.CellEditorListener");
            class$javax$swing$event$CellEditorListener = cls;
        } else {
            cls = class$javax$swing$event$CellEditorListener;
        }
        eventListenerList.remove(cls, l);
    }

    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        return null;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return null;
    }

    protected void fireEditingStopped() {
        Class cls;
        Object[] listeners = this._listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            Object obj = listeners[i];
            if (class$javax$swing$event$CellEditorListener == null) {
                cls = class$("javax.swing.event.CellEditorListener");
                class$javax$swing$event$CellEditorListener = cls;
            } else {
                cls = class$javax$swing$event$CellEditorListener;
            }
            if (obj == cls) {
                if (this._changeEvent == null) {
                    this._changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener) listeners[i + 1]).editingStopped(this._changeEvent);
            }
        }
    }

    protected void fireEditingCanceled() {
        Class cls;
        Object[] listeners = this._listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            Object obj = listeners[i];
            if (class$javax$swing$event$CellEditorListener == null) {
                cls = class$("javax.swing.event.CellEditorListener");
                class$javax$swing$event$CellEditorListener = cls;
            } else {
                cls = class$javax$swing$event$CellEditorListener;
            }
            if (obj == cls) {
                if (this._changeEvent == null) {
                    this._changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener) listeners[i + 1]).editingCanceled(this._changeEvent);
            }
        }
    }
}
