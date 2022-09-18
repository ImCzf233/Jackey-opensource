package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.AWTEventMulticaster;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import org.apache.log4j.lf5.LogRecord;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/viewer/categoryexplorer/CategoryExplorerModel.class */
public class CategoryExplorerModel extends DefaultTreeModel {
    private static final long serialVersionUID = -3413887384316015901L;
    protected boolean _renderFatal = true;
    protected ActionListener _listener = null;
    protected ActionEvent _event = new ActionEvent(this, 1001, "Nodes Selection changed");

    public CategoryExplorerModel(CategoryNode node) {
        super(node);
    }

    public void addLogRecord(LogRecord lr) {
        CategoryPath path = new CategoryPath(lr.getCategory());
        addCategory(path);
        CategoryNode node = getCategoryNode(path);
        node.addRecord();
        if (this._renderFatal && lr.isFatal()) {
            CategoryNode[] pathToRoot = getPathToRoot(node);
            int len = pathToRoot.length;
            for (int i = 1; i < len - 1; i++) {
                CategoryNode parent = pathToRoot[i];
                parent.setHasFatalChildren(true);
                nodeChanged(parent);
            }
            node.setHasFatalRecords(true);
            nodeChanged(node);
        }
    }

    public CategoryNode getRootCategoryNode() {
        return (CategoryNode) getRoot();
    }

    public CategoryNode getCategoryNode(String category) {
        CategoryPath path = new CategoryPath(category);
        return getCategoryNode(path);
    }

    public CategoryNode getCategoryNode(CategoryPath path) {
        CategoryNode root = (CategoryNode) getRoot();
        CategoryNode parent = root;
        for (int i = 0; i < path.size(); i++) {
            CategoryElement element = path.categoryElementAt(i);
            Enumeration children = parent.children();
            boolean categoryAlreadyExists = false;
            while (true) {
                if (!children.hasMoreElements()) {
                    break;
                }
                CategoryNode node = (CategoryNode) children.nextElement();
                String title = node.getTitle().toLowerCase();
                String pathLC = element.getTitle().toLowerCase();
                if (title.equals(pathLC)) {
                    categoryAlreadyExists = true;
                    parent = node;
                    break;
                }
            }
            if (!categoryAlreadyExists) {
                return null;
            }
        }
        return parent;
    }

    public boolean isCategoryPathActive(CategoryPath path) {
        CategoryNode root = (CategoryNode) getRoot();
        CategoryNode parent = root;
        boolean active = false;
        for (int i = 0; i < path.size(); i++) {
            CategoryElement element = path.categoryElementAt(i);
            Enumeration children = parent.children();
            boolean categoryAlreadyExists = false;
            active = false;
            while (true) {
                if (!children.hasMoreElements()) {
                    break;
                }
                CategoryNode node = (CategoryNode) children.nextElement();
                String title = node.getTitle().toLowerCase();
                String pathLC = element.getTitle().toLowerCase();
                if (title.equals(pathLC)) {
                    categoryAlreadyExists = true;
                    parent = node;
                    if (parent.isSelected()) {
                        active = true;
                    }
                }
            }
            if (!active || !categoryAlreadyExists) {
                return false;
            }
        }
        return active;
    }

    public CategoryNode addCategory(CategoryPath path) {
        MutableTreeNode mutableTreeNode = (CategoryNode) getRoot();
        for (int i = 0; i < path.size(); i++) {
            CategoryElement element = path.categoryElementAt(i);
            Enumeration children = mutableTreeNode.children();
            boolean categoryAlreadyExists = false;
            while (true) {
                if (!children.hasMoreElements()) {
                    break;
                }
                MutableTreeNode mutableTreeNode2 = (CategoryNode) children.nextElement();
                String title = mutableTreeNode2.getTitle().toLowerCase();
                String pathLC = element.getTitle().toLowerCase();
                if (title.equals(pathLC)) {
                    categoryAlreadyExists = true;
                    mutableTreeNode = mutableTreeNode2;
                    break;
                }
            }
            if (!categoryAlreadyExists) {
                MutableTreeNode categoryNode = new CategoryNode(element.getTitle());
                insertNodeInto(categoryNode, mutableTreeNode, mutableTreeNode.getChildCount());
                refresh(categoryNode);
                mutableTreeNode = categoryNode;
            }
        }
        return mutableTreeNode;
    }

    public void update(CategoryNode node, boolean selected) {
        if (node.isSelected() == selected) {
            return;
        }
        if (selected) {
            setParentSelection(node, true);
        } else {
            setDescendantSelection(node, false);
        }
    }

    public void setDescendantSelection(CategoryNode node, boolean selected) {
        Enumeration descendants = node.depthFirstEnumeration();
        while (descendants.hasMoreElements()) {
            CategoryNode current = (CategoryNode) descendants.nextElement();
            if (current.isSelected() != selected) {
                current.setSelected(selected);
                nodeChanged(current);
            }
        }
        notifyActionListeners();
    }

    public void setParentSelection(CategoryNode node, boolean selected) {
        CategoryNode[] pathToRoot = getPathToRoot(node);
        int len = pathToRoot.length;
        for (int i = 1; i < len; i++) {
            CategoryNode parent = pathToRoot[i];
            if (parent.isSelected() != selected) {
                parent.setSelected(selected);
                nodeChanged(parent);
            }
        }
        notifyActionListeners();
    }

    public synchronized void addActionListener(ActionListener l) {
        this._listener = AWTEventMulticaster.add(this._listener, l);
    }

    public synchronized void removeActionListener(ActionListener l) {
        this._listener = AWTEventMulticaster.remove(this._listener, l);
    }

    public void resetAllNodeCounts() {
        Enumeration nodes = getRootCategoryNode().depthFirstEnumeration();
        while (nodes.hasMoreElements()) {
            CategoryNode current = (CategoryNode) nodes.nextElement();
            current.resetNumberOfContainedRecords();
            nodeChanged(current);
        }
    }

    public TreePath getTreePathToRoot(CategoryNode node) {
        if (node == null) {
            return null;
        }
        return new TreePath(getPathToRoot(node));
    }

    protected void notifyActionListeners() {
        if (this._listener != null) {
            this._listener.actionPerformed(this._event);
        }
    }

    protected void refresh(CategoryNode node) {
        SwingUtilities.invokeLater(new Runnable(this, node) { // from class: org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerModel.1
            private final CategoryNode val$node;
            private final CategoryExplorerModel this$0;

            {
                this.this$0 = this;
                this.val$node = node;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.this$0.nodeChanged(this.val$node);
            }
        });
    }
}
