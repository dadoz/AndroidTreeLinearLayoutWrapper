package com.application.davidelm.filetreevisitor.views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.application.davidelm.filetreevisitor.R;
import com.application.davidelm.filetreevisitor.adapter.BreadCrumbsAdapter;
import com.application.davidelm.filetreevisitor.adapter.BreadCrumbsAdapter.OnSelectedItemClickListener;
import com.application.davidelm.filetreevisitor.fragments.DisplayNodeFragment;
import com.application.davidelm.filetreevisitor.presenter.DisplayNodePresenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class BreadCrumbsView extends RecyclerView implements OnSelectedItemClickListener {


    private WeakReference<Activity> lst;

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
        ArrayList<String> list = new ArrayList<>();
        list.add("root");

        //set layout manager and adapter
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        setAdapter(new BreadCrumbsAdapter(list, new WeakReference<>(this)));
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
    public void removeLatestBreadCrumb(String breadCrumb) {
        ((BreadCrumbsAdapter) getAdapter()).removeItem(breadCrumb);
    }

    public void removeLatestBreadCrumb() {
        ((BreadCrumbsAdapter) getAdapter()).removeLastItem();
    }

    @Override
    public void onItemClick(View view, int position) {
        popBackStackTillPosition(position);
        //remove breadcrumbs later than position
//        ((BreadCrumbsAdapter) getAdapter()).removeItemAfterPosition(position);
    }

    private void popBackStackTillPosition(int position) {
        FragmentManager fragManager = ((AppCompatActivity) lst.get()).getSupportFragmentManager();
        int cnt = fragManager.getBackStackEntryCount();
        while (cnt > position) {
            if (lst.get() != null) {
                DisplayNodeFragment frag = (DisplayNodeFragment) fragManager.getFragments().get(cnt - 1);
                frag.onPopBackStack(position);
                cnt--;
            }
        }
    }

    public void setLst(WeakReference<Activity> lst) {
        this.lst = lst;
    }

    public interface OnSelectedViewNodeInterface {
        void onSelectViewNode(int position);
    }

    public interface OnPopBackStackInterface {
        void onPopBackStack(int position);
    }

}
