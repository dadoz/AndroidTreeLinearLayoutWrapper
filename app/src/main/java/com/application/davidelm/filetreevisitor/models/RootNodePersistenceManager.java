package com.application.davidelm.filetreevisitor.models;


import android.content.Context;
import android.util.Log;

import com.application.davidelm.filetreevisitor.OnNodeVisitCompleted;
import com.application.davidelm.filetreevisitor.helper.SharedPrefHelper;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;

public class RootNodePersistenceManager {
    private static final String TAG = "RootNodePersistenceManager";
    private static RootNodePersistenceManager instance;
    private final TreeNode root;
    private final SharedPrefHelper sharedPref;
    private WeakReference<OnNodeVisitCompleted> onNodeVisitCompletedLst;
    private TreeNode currentTreeNode; //TODO make it persistent

    private RootNodePersistenceManager(WeakReference<Context> context) {
        this.sharedPref = new SharedPrefHelper(context);
        root = currentTreeNode = readByLocalStorage();
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
        sharedPref.setValue(SharedPrefHelper.SharedPrefKeysEnum.TREE_NODE,
                new Gson().toJson(root));
    }

    public TreeNode readByLocalStorage() {
        currentTreeNode = new Gson().fromJson(sharedPref.getValue(SharedPrefHelper
                .SharedPrefKeysEnum.TREE_NODE, "{}").toString(), TreeNode.class);
        return currentTreeNode;
    }
}
