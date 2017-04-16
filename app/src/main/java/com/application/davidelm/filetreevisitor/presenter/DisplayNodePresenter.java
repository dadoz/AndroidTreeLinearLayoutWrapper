package com.application.davidelm.filetreevisitor.presenter;


import com.application.davidelm.filetreevisitor.OnNodeVisitCompleted;
import com.application.davidelm.filetreevisitor.models.TreeNode;

import java.lang.ref.WeakReference;

public class DisplayNodePresenter {
    private static final String TAG = "DisplayNodePresenter";
    private final TreeNode root;
    private static DisplayNodePresenter instance;
    private WeakReference<OnNodeVisitCompleted> onNodeVisitCompletedLst;
    private TreeNode currentParentNode;

    private DisplayNodePresenter() {
        //treeview
        root = initRoot();

    }

    public static DisplayNodePresenter getInstance() {
        return instance == null ? instance = new DisplayNodePresenter() : instance;
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
}
