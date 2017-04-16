package com.application.davidelm.filetreevisitor;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.application.davidelm.filetreevisitor.presenter.DisplayNodePresenter;
import com.application.davidelm.filetreevisitor.views.BreadCrumbsView;
import com.application.davidelm.filetreevisitor.views.DisplayNodeView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DisplayNodePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onInitView();
    }

    private void onInitView() {
        BreadCrumbsView breadCrumbsView = ((BreadCrumbsView) findViewById(R.id.breadCrumbsViewId));
        ((DisplayNodeView) findViewById(R.id.displayNodeViewId)).setBreadCrumbsView(breadCrumbsView);
//        ((BreadCrumbsView) findViewById(R.id.breadCrumbsViewId))
//                .setLst(new WeakReference<>(this));
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        //handle this
//        ((BreadCrumbsView) findViewById(R.id.breadCrumbsViewId))
//                .removeLatestBreadCrumb();

        boolean isHandled = ((DisplayNodeView) findViewById(R.id.displayNodeViewId))
                .onBackPressed();
        if (!isHandled)
            super.onBackPressed();
    }

}
