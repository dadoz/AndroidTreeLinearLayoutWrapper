package com.application.davidelm.filetreevisitor.treeFileView;

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.application.davidelm.filetreevisitor.OnNodeClickListener;
import com.application.davidelm.filetreevisitor.R;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.unnamed.b.atv.view.TwoDScrollView;

import java.lang.ref.WeakReference;


public class ExpandTreeView extends AndroidTreeView {
    private final WeakReference<OnNodeClickListener> listRef;

    public ExpandTreeView(Context context, WeakReference<OnNodeClickListener> listener) {
        super(context);
        listRef = listener;
    }

    public ExpandTreeView(Context context, com.unnamed.b.atv.model.TreeNode root,
                          WeakReference<OnNodeClickListener> listener) {
        super(context, root);
        listRef = listener;
    }

    @Override
    public void toggleNode(com.unnamed.b.atv.model.TreeNode node) {
        if (listRef.get() != null)
            listRef.get().onNodeCLick(node);
    }

    public void displayNode(View view, TreeNode node) {
        try {
//            String value = mRoot.getValue().toString();
            String value = node.getValue().toString();

            ((TextView) view.findViewById(R.id.nodeLabelTextId)).setText(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View displayNode(WeakReference<Activity> activity, int viewResourceId, TreeNode node) {
        View view = activity.get().getLayoutInflater().inflate(viewResourceId, null);
        displayNode(view, node);
        return view;
    }
}
