package com.lib.davidelm.filetreevisitorlibrary;


import com.lib.davidelm.filetreevisitorlibrary.models.TreeNode;

import java.util.List;

public interface OnNodeVisitCompleted {

    void addNodes(List<TreeNode> list);
    void addFolder(String name);
    void addFile(String name);
    void removeFolder(String name);

    void setParentNode(TreeNode parentNode);

    void removeNode(TreeNode childNode);
}
