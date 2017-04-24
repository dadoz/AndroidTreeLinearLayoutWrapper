package com.lib.davidelm.filetreevisitorlibrary.views;

import com.lib.davidelm.filetreevisitorlibrary.models.TreeNode;

/**
 * Created by davide on 24/04/2017.
 */

public interface OnNavigationCallbacks {
    void onNodeError(int type, TreeNode currentNode, String message);
    void onFolderNodeClickCb(int position, TreeNode node);
    void onFileNodeClickCb(int position, TreeNode node);
}
