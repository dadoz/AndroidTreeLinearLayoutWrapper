package com.application.davidelm.filetreevisitor.models;



import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TreeNode implements Parcelable {
    public static final String NODES_ID_SEPARATOR = ":";
    public static String TREE_NODE_BUNDLE = "TREE_NODE_BUNDLE";
    public static String TREE_NODE_PARCELABLE = "TREE_NODE_PARCELABLE";

    private int mId;
    private int mLastId;
    private TreeNode mParent;
    private boolean mSelected;
    private boolean mSelectable = true;
    private List<TreeNode> children = new ArrayList<>();
//    private BaseNodeViewHolder mViewHolder;
    private TreeNodeClickListener mClickListener;
    private TreeNodeLongClickListener mLongClickListener;
    private String mValue;
    private boolean folder;
    private boolean mExpanded;
    private TreeNode parent;

    protected TreeNode(Parcel in) {
        if (in != null) {
            mId = in.readInt();
            mLastId = in.readInt();
            mParent = in.readParcelable(TreeNode.class.getClassLoader());
            mSelected = in.readByte() != 0;
            mSelectable = in.readByte() != 0;
            children = in.createTypedArrayList(TreeNode.CREATOR);
            folder = in.readByte() != 0;
            mExpanded = in.readByte() != 0;
        }
    }

    public static final Creator<TreeNode> CREATOR = new Creator<TreeNode>() {
        @Override
        public TreeNode createFromParcel(Parcel in) {
            return new TreeNode(in);
        }

        @Override
        public TreeNode[] newArray(int size) {
            return new TreeNode[size];
        }
    };

    public static TreeNode root() {
        TreeNode root = new TreeNode(null);
        root.setSelectable(false);
        return root;
    }

    private int generateId() {
        return ++mLastId;
    }

    public TreeNode(String nodeName, boolean folder) {
        this.mValue = nodeName;
        this.folder = folder;
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

    public String getPath() {
        final StringBuilder path = new StringBuilder();
        TreeNode node = this;
        while (node.mParent != null) {
            path.append(node.getId());
            node = node.mParent;
            if (node.mParent != null) {
                path.append(NODES_ID_SEPARATOR);
            }
        }
        return path.toString();
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

//    public TreeNode setViewHolder(BaseNodeViewHolder viewHolder) {
//        mViewHolder = viewHolder;
//        if (viewHolder != null) {
//            viewHolder.mNode = this;
//        }
//        return this;
//    }

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

//    public BaseNodeViewHolder getViewHolder() {
//        return mViewHolder;
//    }

    public boolean isFirstChild() {
        if (!isRoot()) {
            List<TreeNode> parentChildren = mParent.children;
            return parentChildren.get(0).mId == mId;
        }
        return false;
    }

    public boolean isRoot() {
        return mParent == null;
    }

    public TreeNode getRoot() {
        TreeNode root = this;
        while (root.mParent != null) {
            root = root.mParent;
        }
        return root;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeInt(mLastId);
        dest.writeParcelable(mParent, flags);
        dest.writeByte((byte) (mSelected ? 1 : 0));
        dest.writeByte((byte) (mSelectable ? 1 : 0));
        dest.writeTypedList(children);
        dest.writeByte((byte) (folder ? 1 : 0));
        dest.writeByte((byte) (mExpanded ? 1 : 0));
    }

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public interface TreeNodeClickListener {
        void onClick(TreeNode node, Object value);
    }

    public interface TreeNodeLongClickListener {
        boolean onLongClick(TreeNode node, Object value);
    }

//    public static abstract class BaseNodeViewHolder<E> {
//        protected AndroidTreeView tView;
//        protected TreeNode mNode;
//        private View mView;
//        protected int containerStyle;
//        protected Context context;
//
//        public BaseNodeViewHolder(Context context) {
//            this.context = context;
//        }
//
//        public View getView() {
//            if (mView != null) {
//                return mView;
//            }
//            final View nodeView = getNodeView();
//            final TreeNodeWrapperView nodeWrapperView = new TreeNodeWrapperView(nodeView.getContext(), getContainerStyle());
//            nodeWrapperView.insertNodeView(nodeView);
//            mView = nodeWrapperView;
//
//            return mView;
//        }
//
//        public void setTreeViev(AndroidTreeView treeViev) {
//            this.tView = treeViev;
//        }
//
//        public AndroidTreeView getTreeView() {
//            return tView;
//        }
//
//        public void setContainerStyle(int style) {
//            containerStyle = style;
//        }
//
//        public View getNodeView() {
//            return createNodeView(mNode, (E) mNode.getValue());
//        }
//
//        public ViewGroup getNodeItemsView() {
//            return (ViewGroup) getView().findViewById(R.id.node_items);
//        }
//
//        public boolean isInitialized() {
//            return mView != null;
//        }
//
//        public int getContainerStyle() {
//            return containerStyle;
//        }
//
//
//        public abstract View createNodeView(TreeNode node, E value);
//
//        public void toggle(boolean active) {
//            // empty
//        }
//
//        public void toggleSelectionMode(boolean editModeEnabled) {
//            // empty
//        }
//    }
}
