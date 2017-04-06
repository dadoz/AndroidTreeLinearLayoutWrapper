package com.application.davidelm.filetreevisitor.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.davidelm.filetreevisitor.OnNodeClickListener;
import com.application.davidelm.filetreevisitor.R;
import com.application.davidelm.filetreevisitor.models.TreeNode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TreeNodeAdapter extends RecyclerView.Adapter<TreeNodeAdapter.ViewHolder> {
    private final List<TreeNode> items;
    private WeakReference<OnNodeClickListener> lst;

    public TreeNodeAdapter(List<TreeNode> list, WeakReference<OnNodeClickListener> lst) {
        items = list;
        this.lst = lst;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.node_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TreeNode item = items.get(position);
        holder.itemView.setOnClickListener(v -> {
            if (lst.get() != null && item.isFolder())
                lst.get().onFolderNodeCLick(v, position, item);

            if (lst.get() != null && !item.isFolder())
                lst.get().onFileNodeCLick(v, position, item);
        });
        holder.nodeLabelText.setText(items.get(position).getValue().toString());
        holder.nodeIconImage.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(),
                items.get(position).isFolder() ? R.mipmap.ic_folder : R.mipmap.ic_file));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(TreeNode node) {
        items.add(node);
        notifyDataSetChanged();
    }

    public void removeItem(String breadCrumb) {
        items.remove(breadCrumb);
        notifyDataSetChanged();
    }

    public void removeLastItem() {
        items.remove(items.size() -1);
        notifyDataSetChanged();
    }

    public void addItems(List<TreeNode> list) {
        items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }


    /**
     * view holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nodeLabelText;
        private final ImageView nodeIconImage;

        ViewHolder(View itemView) {
            super(itemView);
            nodeIconImage = (ImageView) itemView.findViewById(R.id.nodeIconImageId);
            nodeLabelText = (TextView) itemView.findViewById(R.id.nodeLabelTextId);
        }
    }

    public interface  OnSelectedItemClickListener{
        void onItemClick(View view, int position);
    }
}
