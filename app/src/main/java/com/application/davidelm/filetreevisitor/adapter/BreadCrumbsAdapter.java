package com.application.davidelm.filetreevisitor.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.davidelm.filetreevisitor.R;

import java.util.ArrayList;

public class BreadCrumbsAdapter extends RecyclerView.Adapter<BreadCrumbsAdapter.ViewHolder> {
    private final ArrayList<String> items;

    public BreadCrumbsAdapter(ArrayList<String> list) {
        items = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.breadcrumbs_item_view, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> Log.e("TAG", "hey" + items.get(position)));
        holder.labelTextView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(String breadCrumb) {
        items.add(breadCrumb);
        notifyDataSetChanged();
    }

    public void removeItem(String breadCrumb) {
        items.remove(breadCrumb);
        notifyDataSetChanged();
    }

    /**
     * view holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView labelTextView;

        ViewHolder(View itemView) {
            super(itemView);
            labelTextView = (TextView) itemView.findViewById(R.id.breadCrumbsLabelTextId);
        }
    }
}
