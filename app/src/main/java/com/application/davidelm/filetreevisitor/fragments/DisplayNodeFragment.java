package com.application.davidelm.filetreevisitor.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.application.davidelm.filetreevisitor.OnNodeClickListener;
import com.application.davidelm.filetreevisitor.OnNodeVisitCompleted;
import com.application.davidelm.filetreevisitor.R;
import com.application.davidelm.filetreevisitor.adapter.TreeNodeAdapter;
import com.application.davidelm.filetreevisitor.decorator.SpaceItemDecorator;
import com.application.davidelm.filetreevisitor.presenter.DisplayNodePresenter;
import com.application.davidelm.filetreevisitor.models.TreeNode;
import com.application.davidelm.filetreevisitor.utils.Utils;
import com.application.davidelm.filetreevisitor.views.BreadCrumbsView;
import com.application.davidelm.filetreevisitor.views.BreadCrumbsView.OnPopBackStackInterface;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DisplayNodeFragment extends Fragment implements OnNodeClickListener, OnNodeVisitCompleted,
        View.OnClickListener, OnPopBackStackInterface {
    private static final String TAG = "DisplayNodeFragment";
    private DisplayNodePresenter presenter;
    private BreadCrumbsView breadCrumbsView;
    private View removeNodeButton;
    private View addNodeButton;
    private EditText nodeValueEditText;
    private CheckBox nodeIsFolderCheckbox;
    private RecyclerView treeNodeRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_node_fragment, container, false);
        presenter = DisplayNodePresenter.getInstance(new WeakReference<>(getActivity()));

        bindView(view);
        onInitView();
        return view;
    }

    private void bindView(View view) {
        breadCrumbsView = (BreadCrumbsView) getActivity().findViewById(R.id.breadCrumbsViewId);
        addNodeButton = view.findViewById(R.id.addNodeButtonId);
        removeNodeButton = view.findViewById(R.id.removeNodeButtonId);
        nodeValueEditText = (EditText) view.findViewById(R.id.nodeValueEditTextId);
        nodeIsFolderCheckbox = (CheckBox) view.findViewById(R.id.nodeIsFolderCheckboxId);
        treeNodeRecyclerView = (RecyclerView) view.findViewById(R.id.treeNodeRecyclerViewId);
    }

    /**
     *
     */
    private void onInitView() {
        presenter.init(new WeakReference<>(this));
        presenter.buildViewsByNodeChildren(retrieveTreeNode());

        addNodeButton.setOnClickListener(this);
        removeNodeButton.setOnClickListener(this);
    }

    /**
     *
     * @return
     */
    private TreeNode retrieveTreeNode() {
        try {
            return ((TreeNode) getArguments().get(TreeNode.TREE_NODE_PARCELABLE));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void addAllNodeViews(ArrayList<View> views) {
//        ((ViewGroup) treeNodeRecyclerviewId).removeAllViews();
//        for (View view: views) {
//            ((ViewGroup) treeNodeRecyclerviewId).addView(view);
//        }
    }

    @Override
    public void addNodes(List<TreeNode> list) {
        if (treeNodeRecyclerView.getAdapter() == null)
            initRecyclerView();
        ((TreeNodeAdapter) treeNodeRecyclerView.getAdapter()).addItems(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNodeButtonId:
                if (nodeValueEditText.getText().toString().equals("")) {
                    return;
                }
                presenter.addNode(nodeValueEditText.getText().toString(), nodeIsFolderCheckbox.isChecked());
                nodeValueEditText.setText("");
                break;
            case R.id.removeNodeButtonId:
                presenter.removeFirstNode();
                break;
        }
    }

    @Override
    public void onPopBackStack(int position) {
        getActivity()
                .getSupportFragmentManager()
                .popBackStack();
        breadCrumbsView.removeLatestBreadCrumb();
    }

    @Override
    public void onFolderNodeCLick(View v, int position, TreeNode node) {
        breadCrumbsView.addBreadCrumb(node.getValue().toString());

        //get support frag manager
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerId, Utils.buildFragment(node))
                .addToBackStack("test")
                .commit();

    }

    @Override
    public void onFileNodeCLick(View v, int position, TreeNode node) {
        Snackbar.make(getView(), "file " + node.getValue() + " clicked", Snackbar.LENGTH_SHORT).show();
    }

    /**
     *
     */
    public void initRecyclerView() {
        treeNodeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        treeNodeRecyclerView.addItemDecoration(new SpaceItemDecorator(getResources().getDimensionPixelSize(R.dimen.grid_space)));
        treeNodeRecyclerView.setAdapter(new TreeNodeAdapter(new ArrayList<>(), new WeakReference<>(this)));

    }
}
