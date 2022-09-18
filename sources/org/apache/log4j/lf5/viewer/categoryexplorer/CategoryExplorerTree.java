package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.tree.TreePath;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/viewer/categoryexplorer/CategoryExplorerTree.class */
public class CategoryExplorerTree extends JTree {
    private static final long serialVersionUID = 8066257446951323576L;
    protected CategoryExplorerModel _model;
    protected boolean _rootAlreadyExpanded = false;

    public CategoryExplorerTree(CategoryExplorerModel model) {
        super(model);
        this._model = model;
        init();
    }

    public CategoryExplorerTree() {
        CategoryNode rootNode = new CategoryNode("Categories");
        this._model = new CategoryExplorerModel(rootNode);
        setModel(this._model);
        init();
    }

    public CategoryExplorerModel getExplorerModel() {
        return this._model;
    }

    public String getToolTipText(MouseEvent e) {
        try {
            return super.getToolTipText(e);
        } catch (Exception e2) {
            return "";
        }
    }

    protected void init() {
        putClientProperty("JTree.lineStyle", "Angled");
        CategoryNodeRenderer renderer = new CategoryNodeRenderer();
        setEditable(true);
        setCellRenderer(renderer);
        CategoryNodeEditor editor = new CategoryNodeEditor(this._model);
        setCellEditor(new CategoryImmediateEditor(this, new CategoryNodeRenderer(), editor));
        setShowsRootHandles(true);
        setToolTipText("");
        ensureRootExpansion();
    }

    protected void expandRootNode() {
        if (this._rootAlreadyExpanded) {
            return;
        }
        this._rootAlreadyExpanded = true;
        TreePath path = new TreePath(this._model.getRootCategoryNode().getPath());
        expandPath(path);
    }

    protected void ensureRootExpansion() {
        this._model.addTreeModelListener(new TreeModelAdapter(this) { // from class: org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerTree.1
            private final CategoryExplorerTree this$0;

            {
                this.this$0 = this;
            }

            @Override // org.apache.log4j.lf5.viewer.categoryexplorer.TreeModelAdapter
            public void treeNodesInserted(TreeModelEvent e) {
                this.this$0.expandRootNode();
            }
        });
    }
}
