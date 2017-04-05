package com.application.davidelm.filetreevisitor.treeFileView;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.davidelm.filetreevisitor.OnNodeClickListener;
import com.application.davidelm.filetreevisitor.R;
import com.application.davidelm.filetreevisitor.models.TreeNode;

import java.lang.ref.WeakReference;


public class ExpandTreeView {
    private WeakReference<OnNodeClickListener> listRef;
    private boolean folder;

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
            ((ImageView) view.findViewById(R.id.nodeIconImageId)).setImageDrawable(ContextCompat.getDrawable(view.getContext(),
                    node.isFolder() ? R.mipmap.ic_folder : R.mipmap.ic_file));
            view.setOnClickListener((v) -> {
                if (listRef.get() != null && node.isFolder())
                    listRef.get().onFolderNodeCLick(node);

                if (listRef.get() != null && !node.isFolder())
                    listRef.get().onFileNodeCLick(node);

            });
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
