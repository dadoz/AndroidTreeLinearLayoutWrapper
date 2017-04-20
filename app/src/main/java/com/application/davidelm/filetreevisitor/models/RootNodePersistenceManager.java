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
    private final TreeNode root;
    private final SharedPrefHelper sharedPref;
    private WeakReference<OnNodeVisitCompleted> onNodeVisitCompletedLst;
    private TreeNode currentTreeNode; //TODO make it persistent

    private RootNodePersistenceManager(WeakReference<Context> context) {
        this.sharedPref = new SharedPrefHelper(context);

        //init root
        TreeNode parsedNode = readByLocalStorage();
        root = currentTreeNode  = parsedNode != null ? parsedNode : initRootNode();
        saveOnLocalStorage();
    }

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
            saveOnLocalStorage();

            //update view
            buildViewsByNodeChildren();
        }
    }

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

    public void saveOnLocalStorage() {
        Log.e(TAG, new Gson().toJson(root));
        sharedPref.setValue(SharedPrefHelper.SharedPrefKeysEnum.TREE_NODE,
                new Gson().toJson(root));
    }

    public TreeNode readByLocalStorage() {
        try {
            Log.e(TAG, sharedPref.getValue(SharedPrefHelper
                    .SharedPrefKeysEnum.TREE_NODE, null) + "---------------");
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
        Log.e(TAG, "set parent on node + " + level);
        List<TreeNode> list;
        if ((list = currentTreeNode.getChildren()) != null) {
            for (TreeNode item : list) {
                item.setParent(currentTreeNode);
                updateParentOnCurrentNode(item, level++);
            }
        }
    }
}
