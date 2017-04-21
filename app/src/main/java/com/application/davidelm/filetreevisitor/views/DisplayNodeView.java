package com.application.davidelm.filetreevisitor.views;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.application.davidelm.filetreevisitor.OnNodeClickListener;
import com.application.davidelm.filetreevisitor.OnNodeVisitCompleted;
import com.application.davidelm.filetreevisitor.R;
import com.application.davidelm.filetreevisitor.adapter.TreeNodeAdapter;
import com.application.davidelm.filetreevisitor.decorator.SpaceItemDecorator;
import com.application.davidelm.filetreevisitor.models.RootNodePersistenceManager;
import com.application.davidelm.filetreevisitor.models.TreeNode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DisplayNodeView extends FrameLayout implements OnNodeClickListener, OnNodeVisitCompleted,
        BreadCrumbsView.OnPopBackStackInterface {
    private RecyclerView treeNodeRecyclerView;
    private String TAG = "TAG";
    private TreeNode currentNode;
    private TreeNode rootNode;
    private BreadCrumbsView breadCrumbsView;
    private RootNodePersistenceManager displayNodeListModel;

    public DisplayNodeView(@NonNull Context context) {
        super(context);
        initView();
    }

    public DisplayNodeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DisplayNodeView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public DisplayNodeView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public void initView() {
        inflate(getContext(), R.layout.display_node_layout, this);
        treeNodeRecyclerView = (RecyclerView) findViewById(R.id.treeNodeRecyclerViewId);
        displayNodeListModel = RootNodePersistenceManager.getInstance(new WeakReference<>(getContext()));

        //displayNodeListModel.getRootNode();
        displayNodeListModel.init(new WeakReference<>(this));
    }

    /**
     *
     */
    public void initRecyclerView() {
        treeNodeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        treeNodeRecyclerView.addItemDecoration(new SpaceItemDecorator(getResources().getDimensionPixelSize(R.dimen.grid_space)));
        treeNodeRecyclerView.setAdapter(new TreeNodeAdapter(new ArrayList<>(), new WeakReference<>(this)));

    }

    /**
     *
     * @param list
     */
    public void addNodes(List<TreeNode> list) {
        if (treeNodeRecyclerView.getAdapter() == null)
            initRecyclerView();
        ((TreeNodeAdapter) treeNodeRecyclerView.getAdapter()).addItems(list);
    }

    @Override
    public void setParentNode(TreeNode parentNode) {
        Log.e(TAG, parentNode.getValue() != null ? parentNode.getValue().toString() : "root");
        rootNode = currentNode = parentNode;
    }

    @Override
    public void removeNode(TreeNode childNode) {
        ((TreeNodeAdapter) treeNodeRecyclerView.getAdapter()).removeItem(childNode);
    }

    @Override
    public void onFolderNodeCLick(View v, int position, TreeNode node) {
        //set breadcrumbs
        breadCrumbsView.addBreadCrumb(node.getValue().toString());

        //update and set view
        updateCurrentNode(node);
        ((TreeNodeAdapter) treeNodeRecyclerView.getAdapter()).addItems(node.getChildren());
        treeNodeRecyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     *
     * @param node
     */
    private boolean updateCurrentNode(TreeNode node) {
        //check if isRootNode
        boolean isRootNode = currentNode.isRoot();

        //set current node
        currentNode = (node != null) ? node :
                (currentNode != null && !isRootNode) ? currentNode.getParent() : rootNode;

        //update current node on displayNodeListModel
        displayNodeListModel.setCurrentNode(currentNode);

        //return current node is parent
        return isRootNode;
    }

    @Override
    public void onFileNodeCLick(View v, int position, TreeNode node) {
        Snackbar.make(this, "file " + node.getValue() + " clicked", Snackbar.LENGTH_SHORT).show();
    }

    public boolean onBackPressed() {
        boolean isRootNode = updateCurrentNode(null);
        if (!isRootNode) {
            //update breadCrumbs
            breadCrumbsView.removeLatestBreadCrumb();

            //update node viewsrr
            ((TreeNodeAdapter) treeNodeRecyclerView.getAdapter()).addItems(currentNode.getChildren());
        }
        return !isRootNode;
    }

    /**
     *
     * @param breadCrumbsView
     */
    public void setBreadCrumbsView(BreadCrumbsView breadCrumbsView) {
        this.breadCrumbsView = breadCrumbsView;
        breadCrumbsView.setLst(new WeakReference<>(this));
    }

    @Override
    public void popBackStackTillPosition(int position) {
        Log.e(TAG, "current node " + currentNode.getLevel());
        if (currentNode == null)
            return;

        while (currentNode.getLevel() != position &&
                currentNode.getLevel() > 0) {
            updateCurrentNode(currentNode.getParent());
        }

        ((TreeNodeAdapter) treeNodeRecyclerView.getAdapter()).addItems(currentNode.getChildren());
        treeNodeRecyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     *
     * @param name
     */
    public void addFolder(String name) {
        displayNodeListModel.addNode(name, true);
    }

    /**
     *
     * @param name
     */
    public void addFile(String name) {
        displayNodeListModel.addNode(name, false);
    }

    /**
     *
     * @param name
     */
    public void removeFolder(String name) {
        displayNodeListModel.removeNode(currentNode.getChildByName(name));
    }

    /**
     *
     * @param position
     */
    public void removeFolder(int position) {
        displayNodeListModel.removeNode(currentNode.getChildren().get(position));
    }
}
