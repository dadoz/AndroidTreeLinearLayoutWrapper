package com.application.davidelm.filetreevisitor.presenter;

import android.app.Activity;
import android.view.View;

import com.application.davidelm.filetreevisitor.OnNodeClickListener;
import com.application.davidelm.filetreevisitor.OnNodeVisitCompleted;
import com.application.davidelm.filetreevisitor.R;
import com.application.davidelm.filetreevisitor.treeFileView.ExpandTreeView;
import com.application.davidelm.filetreevisitor.treeFileView.TreeNode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DisplayNodePresenter {
    private final ExpandTreeView expandTreeView;
    private final TreeNode root;
    private static DisplayNodePresenter instance;
    private WeakReference<OnNodeVisitCompleted> onNodeVisitCompletedLst;
    private static WeakReference<Activity> activityRef;
    private TreeNode currentParentNode;

    private DisplayNodePresenter() {
        //treeview
        root = initRoot();
        expandTreeView = new ExpandTreeView();

    }

    public static DisplayNodePresenter getInstance(WeakReference<Activity> activity) {
        activityRef = activity;
        return instance == null ? instance = new DisplayNodePresenter() : instance;
    }

    public void setListener(WeakReference<OnNodeClickListener> listener) {
        expandTreeView.setOnNodeClickListener(listener);
    }

    /**
     *
     * @return
     */
    private TreeNode initRoot() {
        //init tree node
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode("MyParentNode");
        TreeNode child0 = new TreeNode("ChildNode0");
        TreeNode child1 = new TreeNode("ChildNode1");
        child0.addChild(new TreeNode("Test Child of Child"));
        parent.addChildren(child0, child1);
        root.addChild(parent);
        return root;
    }

    public TreeNode getParentNode() {
        //set custom view
        return root.getChildren().get(0);
    }

    public void buildViewsByNodeChildren() {
        buildViewsByNodeChildren(getParentNode());
    }

    public void buildViewsByNodeChildren(TreeNode parentNode) {
        this.currentParentNode = parentNode;
        ArrayList<View> list = new ArrayList<>();
        for (TreeNode node : parentNode.getChildren()) {
            list.add(expandTreeView.displayNode(activityRef, R.layout.node_item, node));
        }

        if (onNodeVisitCompletedLst.get() != null)
           onNodeVisitCompletedLst.get().addAllNodeViews(list);
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
    public void addNode() {
        if (currentParentNode != null) {
            currentParentNode.addChild(new TreeNode("ChildNode" + currentParentNode.getChildren().size()));
            buildViewsByNodeChildren(currentParentNode);
        }
    }
}
