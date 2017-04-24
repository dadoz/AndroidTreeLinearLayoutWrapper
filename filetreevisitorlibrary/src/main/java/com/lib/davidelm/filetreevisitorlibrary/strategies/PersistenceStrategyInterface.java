package com.lib.davidelm.filetreevisitorlibrary.strategies;


import com.lib.davidelm.filetreevisitorlibrary.models.TreeNode;

/**
 * Created by davide on 22/04/2017.
 */

public interface PersistenceStrategyInterface {

    TreeNode getPersistentNode();

    void setPersistentNode(TreeNode node);
}
