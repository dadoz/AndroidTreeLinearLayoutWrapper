package com.application.davidelm.filetreevisitor;


import com.application.davidelm.filetreevisitor.models.TreeNode;

public interface OnNodeClickListener {
    void onFolderNodeCLick(TreeNode node);
    void onFileNodeCLick(TreeNode node);
}
