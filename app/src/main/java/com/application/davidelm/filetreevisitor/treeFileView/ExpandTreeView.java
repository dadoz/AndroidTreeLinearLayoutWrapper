package com.application.davidelm.filetreevisitor.treeFileView;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.application.davidelm.filetreevisitor.OnNodeClickListener;
import com.application.davidelm.filetreevisitor.R;

import java.lang.ref.WeakReference;


public class ExpandTreeView {
    private WeakReference<OnNodeClickListener> listRef;

    public ExpandTreeView() {
    }

    /**
     *
     * @param view
     * @param node
     */
    public void displayNode(View view, TreeNode node) {
        try {
            ((TextView) view.findViewById(R.id.nodeLabelTextId)).setText(node.getValue().toString());
            view.setOnClickListener((v) -> listRef.get().onNodeCLick(node));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param activity
     * @param viewResourceId
     * @param node
     * @return
     */
    public View displayNode(WeakReference<Activity> activity, int viewResourceId, TreeNode node) {
        View view = activity.get().getLayoutInflater().inflate(viewResourceId, null);
        displayNode(view, node);
        return view;
    }

    public void setOnNodeClickListener(WeakReference<OnNodeClickListener> listRef) {
        this.listRef = listRef;
    }
}
