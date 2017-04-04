package com.application.davidelm.filetreevisitor.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.davidelm.filetreevisitor.OnNodeClickListener;
import com.application.davidelm.filetreevisitor.OnNodeVisitCompleted;
import com.application.davidelm.filetreevisitor.R;
import com.application.davidelm.filetreevisitor.presenter.DisplayNodePresenter;
import com.application.davidelm.filetreevisitor.treeFileView.TreeNode;
import com.application.davidelm.filetreevisitor.utils.Utils;
import com.application.davidelm.filetreevisitor.views.BreadCrumbsView;
import com.application.davidelm.filetreevisitor.views.BreadCrumbsView.OnPopBackStackInterface;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DisplayNodeFragment extends Fragment implements OnNodeClickListener, OnNodeVisitCompleted, View.OnClickListener, OnPopBackStackInterface {
    private static final String TAG = "DisplayNodeFragment";
    private View mainDisplayLayoutId;
    private DisplayNodePresenter presenter;
    private BreadCrumbsView breadCrumbsView;
    private View removeNodeButton;
    private View addNodeButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_node_fragment, container, false);
        mainDisplayLayoutId = view.findViewById(R.id.mainDisplayLayoutId);
        presenter = DisplayNodePresenter.getInstance(new WeakReference<>(getActivity()));

        bindView(view);
        onInitView();
        return view;
    }

    private void bindView(View view) {
        breadCrumbsView = (BreadCrumbsView) getActivity().findViewById(R.id.breadCrumbsViewId);
        addNodeButton = view.findViewById(R.id.addNodeButtonId);
        removeNodeButton = view.findViewById(R.id.removeNodeButtonId);

    }

    /**
     *
     */
    private void onInitView() {
        presenter.init(new WeakReference<>(this));
        presenter.setListener(new WeakReference<>(this));

        TreeNode node = retrieveTreeNode();
        presenter.buildViewsByNodeChildren(node);

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
        ((ViewGroup) mainDisplayLayoutId).removeAllViews();
        for (View view: views) {
            ((ViewGroup) mainDisplayLayoutId).addView(view);
        }
    }


    @Override
    public void onNodeCLick(TreeNode node) {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNodeButtonId:
                presenter.addNode();
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
}
