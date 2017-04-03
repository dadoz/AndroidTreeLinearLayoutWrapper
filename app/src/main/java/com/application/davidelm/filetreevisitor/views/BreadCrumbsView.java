package com.application.davidelm.filetreevisitor.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import com.application.davidelm.filetreevisitor.R;
import com.application.davidelm.filetreevisitor.adapter.BreadCrumbsAdapter;

import java.util.ArrayList;

public class BreadCrumbsView extends RecyclerView {


    public BreadCrumbsView(Context context) {
        super(context);
        initView();
    }

    public BreadCrumbsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BreadCrumbsView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    /**
     * init view setting up root breadcrumb
     */
    private void initView() {
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        ArrayList<String> list = new ArrayList<String>();
        list.add("root");

        //set layout manager and adapter
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        setAdapter(new BreadCrumbsAdapter(list));
    }

    /**
     *
     * @param breadCrumb
     */
    public void addBreadCrumb(String breadCrumb) {
        ((BreadCrumbsAdapter) getAdapter()).addItem(breadCrumb);
    }
    /**
     *
     * @param breadCrumb
     */
    public void remmoveBreadCrumb(String breadCrumb) {
        ((BreadCrumbsAdapter) getAdapter()).removeItem(breadCrumb);
    }

}
