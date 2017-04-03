package com.application.davidelm.filetreevisitor.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import com.application.davidelm.filetreevisitor.R;

public class BreadCrumbsView extends HorizontalScrollView {
    public BreadCrumbsView(Context context) {
        super(context);
        initView();
    }


    public BreadCrumbsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BreadCrumbsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public BreadCrumbsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.breadcrumbs_layout, this);
    }

}
