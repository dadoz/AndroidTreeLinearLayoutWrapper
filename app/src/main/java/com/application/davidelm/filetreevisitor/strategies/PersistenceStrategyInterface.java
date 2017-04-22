package com.application.davidelm.filetreevisitor.strategies;

import com.application.davidelm.filetreevisitor.models.TreeNode;

/**
 * Created by davide on 22/04/2017.
 */

public interface PersistenceStrategyInterface {

    TreeNode getPersistentNode();

    void setPersistentNode(TreeNode node);
}
