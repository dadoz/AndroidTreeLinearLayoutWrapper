package com.application.davidelm.filetreevisitor;



import com.application.davidelm.filetreevisitor.models.TreeNode;

import java.util.List;

public interface OnNodeVisitCompleted {

    void addNodes(List<TreeNode> list);
    void addFolder(String name);
    void addFile(String name);
    void removeFolder(String name);

    void setParentNode(TreeNode parentNode);
}
