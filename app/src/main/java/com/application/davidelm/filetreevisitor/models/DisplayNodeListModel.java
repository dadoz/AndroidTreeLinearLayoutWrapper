package com.application.davidelm.filetreevisitor.models;


import com.application.davidelm.filetreevisitor.OnNodeVisitCompleted;

import java.lang.ref.WeakReference;

public class DisplayNodeListModel {
    private static final String TAG = "DisplayNodeListModel";
    private final TreeNode root;
    private static DisplayNodeListModel instance;
    private WeakReference<OnNodeVisitCompleted> onNodeVisitCompletedLst;
    private TreeNode currentParentNode;

    private DisplayNodeListModel() {
        //treeview
        root = initRoot();

    }

    public static DisplayNodeListModel getInstance() {
        return instance == null ? instance = new DisplayNodeListModel() : instance;
    }

    /**
     *
     * @return
     */
    private TreeNode initRoot() {
        //init tree node
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode("MyParentNode", true, 0);
        TreeNode child0 = new TreeNode("ChildNode0", true, 1);
        TreeNode child1 = new TreeNode("ChildNode1", true, 1);
        child0.setParent(parent);
        child1.setParent(parent);
        child0.addChild(new TreeNode("Test Child of Child", true, 2));
        parent.addChildren(child0, child1);
        root.addChild(parent);
        return root;
    }

    public TreeNode getRootNode() {
        //set custom view
        return root.getChildren().get(0);
    }

    /**
     * build views
     * @param parentNode
     */
    public void buildViewsByNodeChildren(TreeNode parentNode) {
        this.currentParentNode = parentNode;
        if (onNodeVisitCompletedLst.get() != null)
           onNodeVisitCompletedLst.get().setParentNode(parentNode);
           onNodeVisitCompletedLst.get().addNodes(parentNode.getChildren());
    }

    /**
     * init list
     * @param lst2
     */
    public void init(WeakReference<OnNodeVisitCompleted> lst2) {
        this.onNodeVisitCompletedLst = lst2;
    }

    /**
     * remove first node
     */
    public void removeFirstNode() {
        if (currentParentNode != null &&
                currentParentNode.getChildren().size() != 0) {
            currentParentNode.deleteChild(currentParentNode.getChildren().get(0));
            buildViewsByNodeChildren(currentParentNode);
        }
    }

    /**
     * add node
     * not optimized
     */
    public void addNode(String nodeName, boolean folder) {
        if (currentParentNode != null) {
            currentParentNode.addChild(new TreeNode(nodeName, folder, currentParentNode.getLevel() + 1));
            buildViewsByNodeChildren(currentParentNode);
        }
    }

    public void setCurrentNode(TreeNode currentNode) {
        this.currentParentNode = currentNode;
    }
}
