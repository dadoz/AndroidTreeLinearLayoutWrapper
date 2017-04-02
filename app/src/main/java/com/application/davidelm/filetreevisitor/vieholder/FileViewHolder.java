package com.application.davidelm.filetreevisitor.vieholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.application.davidelm.filetreevisitor.R;
import com.application.davidelm.filetreevisitor.models.IconTreeItem;
import com.unnamed.b.atv.model.TreeNode;

public class FileViewHolder extends TreeNode.BaseNodeViewHolder<IconTreeItem> {

    public FileViewHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_profile_node, null, false);
        TextView tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.text);

        return view;
    }

}