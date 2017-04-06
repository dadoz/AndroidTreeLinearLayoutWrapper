package com.application.davidelm.filetreevisitor;


import android.view.View;

import com.application.davidelm.filetreevisitor.models.TreeNode;

public interface OnNodeClickListener {
    void onFolderNodeCLick(View v, int position, TreeNode node);
    void onFileNodeCLick(View v, int position, TreeNode node);
}
