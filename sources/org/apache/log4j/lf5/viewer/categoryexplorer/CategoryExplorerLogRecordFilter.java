package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.util.Enumeration;
import javax.swing.tree.TreeNode;
import org.apache.log4j.lf5.LogRecord;
import org.apache.log4j.lf5.LogRecordFilter;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/lf5/viewer/categoryexplorer/CategoryExplorerLogRecordFilter.class */
public class CategoryExplorerLogRecordFilter implements LogRecordFilter {
    protected CategoryExplorerModel _model;

    public CategoryExplorerLogRecordFilter(CategoryExplorerModel model) {
        this._model = model;
    }

    @Override // org.apache.log4j.lf5.LogRecordFilter
    public boolean passes(LogRecord record) {
        CategoryPath path = new CategoryPath(record.getCategory());
        return this._model.isCategoryPathActive(path);
    }

    public void reset() {
        resetAllNodes();
    }

    protected void resetAllNodes() {
        Enumeration nodes = this._model.getRootCategoryNode().depthFirstEnumeration();
        while (nodes.hasMoreElements()) {
            TreeNode treeNode = (CategoryNode) nodes.nextElement();
            treeNode.resetNumberOfContainedRecords();
            this._model.nodeChanged(treeNode);
        }
    }
}
