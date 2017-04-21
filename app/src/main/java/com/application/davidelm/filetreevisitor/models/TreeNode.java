package com.application.davidelm.filetreevisitor.models;


import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TreeNode implements Serializable {
    public static final String NODES_ID_SEPARATOR = ":";
    public static final String TREE_NODE_BUNDLE = "TREE_NODE_BUNDLE";
    public static final String TREE_NODE_PARCELABLE = "TREE_NODE_PARCELABLE";
    public static final int ROOT_LEVEL = -1;
    private static final String TAG = "TreeNodeTAG";
    private int level;

    private int mId;
    private int mLastId;
    private transient TreeNode mParent;
    private boolean mSelected;
    private boolean mSelectable = true;
    private List<TreeNode> children = new ArrayList<>();
    private TreeNodeClickListener mClickListener;
    private TreeNodeLongClickListener mLongClickListener;
    private String mValue;
    private boolean folder;
    private boolean mExpanded;

    public TreeNode(String nodeName, boolean folder, int level) {
        this.mValue = nodeName;
        this.folder = folder;
        this.level = level;
    }

    public static TreeNode root() {
        TreeNode root = new TreeNode(null, false, -1);
        root.setSelectable(false);
        return root;
    }

    private int generateId() {
        return ++mLastId;
    }

    public TreeNode addChild(TreeNode childNode) {
        childNode.mParent = this;
        childNode.mId = generateId();
        children.add(childNode);
        return this;
    }

    public TreeNode addChildren(TreeNode... nodes) {
        for (TreeNode n : nodes) {
            addChild(n);
        }
        return this;
    }

    public TreeNode addChildren(Collection<TreeNode> nodes) {
        for (TreeNode n : nodes) {
            addChild(n);
        }
        return this;
    }

    public int deleteChild(TreeNode child) {
        if (children.size() == 0)
            return -1;

        for (int i = 0; i < children.size(); i++) {
            if (child.mId == children.get(i).mId) {
                children.remove(i);
                return i;
            }
        }
        return -1;
    }

    public List<TreeNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public int size() {
        return children.size();
    }

    public TreeNode getParent() {
        return mParent;
    }

    public int getId() {
        return mId;
    }

    public boolean isLeaf() {
        return size() == 0;
    }

    public Object getValue() {
        return mValue;
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public TreeNode setExpanded(boolean expanded) {
        mExpanded = expanded;
        return this;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public boolean isSelected() {
        return mSelectable && mSelected;
    }

    public void setSelectable(boolean selectable) {
        mSelectable = selectable;
    }

    public boolean isSelectable() {
        return mSelectable;
    }


    public int getLevel() {
        int level = 0;
        TreeNode root = this;
        while (root.mParent != null) {
            root = root.mParent;
            level++;
        }
        return level;
    }

    public boolean isLastChild() {
        if (!isRoot()) {
            int parentSize = mParent.children.size();
            if (parentSize > 0) {
                final List<TreeNode> parentChildren = mParent.children;
                return parentChildren.get(parentSize - 1).mId == mId;
            }
        }
        return false;
    }


    public TreeNode setClickListener(TreeNodeClickListener listener) {
        mClickListener = listener;
        return this;
    }

    public TreeNodeClickListener getClickListener() {
        return this.mClickListener;
    }

    public TreeNode setLongClickListener(TreeNodeLongClickListener listener) {
        mLongClickListener = listener;
        return this;
    }

    public TreeNodeLongClickListener getLongClickListener() {
        return mLongClickListener;
    }


    public boolean isFirstChild() {
        if (!isRoot()) {
            List<TreeNode> parentChildren = mParent.children;
            return parentChildren.get(0).mId == mId;
        }
        return false;
    }

    public boolean isRoot() {
        Log.e(TAG, "check parent node value" + mValue);
        return mParent == null && level == ROOT_LEVEL;
    }

    public TreeNode getRoot() {
        TreeNode root = this;
        while (root.mParent != null) {
            root = root.mParent;
        }
        return root;
    }

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public void setParent(TreeNode parent) {
        this.mParent = parent;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public TreeNode getChildByName(String name) {
        for (TreeNode item : children) {
            if (item.getValue().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public interface TreeNodeClickListener {
        void onClick(TreeNode node, Object value);
    }

    public interface TreeNodeLongClickListener {
        boolean onLongClick(TreeNode node, Object value);
    }

}
