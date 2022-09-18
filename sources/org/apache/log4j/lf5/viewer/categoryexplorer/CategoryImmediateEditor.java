package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.TreePath;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/viewer/categoryexplorer/CategoryImmediateEditor.class */
public class CategoryImmediateEditor extends DefaultTreeCellEditor {
    private CategoryNodeRenderer renderer;
    protected Icon editingIcon = null;

    public CategoryImmediateEditor(JTree tree, CategoryNodeRenderer renderer, CategoryNodeEditor editor) {
        super(tree, renderer, editor);
        this.renderer = renderer;
        renderer.setIcon(null);
        renderer.setLeafIcon(null);
        renderer.setOpenIcon(null);
        renderer.setClosedIcon(null);
        ((DefaultTreeCellEditor) this).editingIcon = null;
    }

    public boolean shouldSelectCell(EventObject e) {
        boolean rv = false;
        if (e instanceof MouseEvent) {
            MouseEvent me2 = (MouseEvent) e;
            TreePath path = this.tree.getPathForLocation(me2.getX(), me2.getY());
            CategoryNode node = (CategoryNode) path.getLastPathComponent();
            rv = node.isLeaf();
        }
        return rv;
    }

    public boolean inCheckBoxHitRegion(MouseEvent e) {
        TreePath path = this.tree.getPathForLocation(e.getX(), e.getY());
        if (path == null) {
            return false;
        }
        CategoryNode categoryNode = (CategoryNode) path.getLastPathComponent();
        Rectangle bounds = this.tree.getRowBounds(this.lastRow);
        Dimension checkBoxOffset = this.renderer.getCheckBoxOffset();
        bounds.translate(this.offset + checkBoxOffset.width, checkBoxOffset.height);
        bounds.contains(e.getPoint());
        return true;
    }

    protected boolean canEditImmediately(EventObject e) {
        boolean rv = false;
        if (e instanceof MouseEvent) {
            MouseEvent me2 = (MouseEvent) e;
            rv = inCheckBoxHitRegion(me2);
        }
        return rv;
    }

    protected void determineOffset(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        this.offset = 0;
    }
}
