package com.application.davidelm.filetreevisitor.presenter;

import android.app.Activity;

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

    public static DisplayNodePresenter getInstance(WeakReference<Activity> activity) {
        return instance == null ? instance = new DisplayNodePresenter() : instance;
    }


    /**
     *
     * @return
     */
    private TreeNode initRoot() {
        //init tree node
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode("MyParentNode", true);
        TreeNode child0 = new TreeNode("ChildNode0", true);
        TreeNode child1 = new TreeNode("ChildNode1", true);
        child0.addChild(new TreeNode("Test Child of Child", true));
        parent.addChildren(child0, child1);
        root.addChild(parent);
        return root;
    }

    public TreeNode getParentNode() {
        //set custom view
        return root.getChildren().get(0);
    }


    public void buildViewsByNodeChildren(TreeNode parentNode) {
        this.currentParentNode = parentNode;
        if (onNodeVisitCompletedLst.get() != null)
           onNodeVisitCompletedLst.get().addNodes(parentNode.getChildren());
    }

    public void init(WeakReference<OnNodeVisitCompleted> lst2) {
        this.onNodeVisitCompletedLst = lst2;
    }

    public void removeFirstNode() {
        if (currentParentNode != null &&
                currentParentNode.getChildren().size() != 0) {
            currentParentNode.deleteChild(currentParentNode.getChildren().get(0));
            buildViewsByNodeChildren(currentParentNode);
        }
    }

    /**
     * not optimized
     */
    public void addNode(String nodeName, boolean folder) {
        if (currentParentNode != null) {
            currentParentNode.addChild(new TreeNode(nodeName, folder));
            buildViewsByNodeChildren(currentParentNode);
        }
    }
}
