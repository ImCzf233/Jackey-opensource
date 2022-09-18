package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTree;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/viewer/categoryexplorer/CategoryNodeEditorRenderer.class */
public class CategoryNodeEditorRenderer extends CategoryNodeRenderer {
    private static final long serialVersionUID = -6094804684259929574L;

    @Override // org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeRenderer
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        return c;
    }

    public JCheckBox getCheckBox() {
        return this._checkBox;
    }
}
