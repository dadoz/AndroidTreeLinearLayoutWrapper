package com.application.davidelm.filetreevisitor.models;


import android.content.Context;
import android.util.Log;

import com.application.davidelm.filetreevisitor.OnNodeVisitCompleted;
import com.application.davidelm.filetreevisitor.helper.SharedPrefHelper;
import com.application.davidelm.filetreevisitor.utils.Utils;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.List;

public class RootNodePersistenceManager {
    private static final String TAG = "RootNodePersistence";
    private static RootNodePersistenceManager instance;
    private TreeNode root;
    private final SharedPrefHelper sharedPref;
    private WeakReference<OnNodeVisitCompleted> onNodeVisitCompletedLst;
    private TreeNode currentTreeNode; //TODO make it persistent

    /**
     * build manager
     * @param context
     */
    private RootNodePersistenceManager(WeakReference<Context> context) {
        this.sharedPref = new SharedPrefHelper(context);

        //check parsed node
        if ((root = readByLocalStorage()) == null) {
            root = initRootNode();
            writeOnLocalStorage();
        }

        //init current node
        currentTreeNode = root;
    }

    /**
     * init root node
     * @return
     */
    private TreeNode initRootNode() {
        return new TreeNode("ROOT", false, TreeNode.ROOT_LEVEL);
    }

    /**
     * init list
     * @param lst2
     */
    public void init(WeakReference<OnNodeVisitCompleted> lst2) {
        this.onNodeVisitCompletedLst = lst2;
    }

    /**
     *
     * @return
     */
    public static RootNodePersistenceManager getInstance(WeakReference<Context> context) {
        return instance == null ? instance = new RootNodePersistenceManager(context) : instance;
    }

    /**
     * add node
     * not optimized
     */
    public void addNode(String nodeName, boolean folder) {
        if (currentTreeNode != null) {
            currentTreeNode.addChild(new TreeNode(nodeName, folder, currentTreeNode.getLevel() + 1));

            //save on storage
            writeOnLocalStorage();

            //update view
            buildViewsByNodeChildren();
        }
    }

    /**
     * build view by node children
     */
    public void buildViewsByNodeChildren() {
        onNodeVisitCompletedLst.get().setParentNode(currentTreeNode);
        onNodeVisitCompletedLst.get().addNodes(currentTreeNode.getChildren());
    }

    /**
     *
     * @param currentTreeNode
     */
    public void setCurrentNode(TreeNode currentTreeNode) {
        this.currentTreeNode = currentTreeNode;
    }

    /**
     * saving on local storage
     */
    public void writeOnLocalStorage() {
        Log.e(TAG, new Gson().toJson(root));
        sharedPref.setValue(SharedPrefHelper.SharedPrefKeysEnum.TREE_NODE,
                new Gson().toJson(root));
    }

    /**
     * reading on local storage
     */
    public TreeNode readByLocalStorage() {
        try {
            currentTreeNode = new Gson().fromJson(sharedPref.getValue(SharedPrefHelper
                    .SharedPrefKeysEnum.TREE_NODE, null).toString(), TreeNode.class);
            updateParentOnCurrentNode(currentTreeNode, TreeNode.ROOT_LEVEL);
        } catch (Exception e) {
            e.printStackTrace();
            currentTreeNode = null;
        }
        return currentTreeNode;
    }

    /**
     * recursion to handle se parent on each node
     * @param currentTreeNode
     * @param level
     */
    private void updateParentOnCurrentNode(TreeNode currentTreeNode, int level) {
        level += 1;
        Log.e(TAG, "set parent on node + " + level);
        List<TreeNode> list;
        if ((list = currentTreeNode.getChildren()) != null) {
            for (TreeNode item : list) {
                item.setParent(currentTreeNode);
                updateParentOnCurrentNode(item, level);
            }
        }
    }
}
