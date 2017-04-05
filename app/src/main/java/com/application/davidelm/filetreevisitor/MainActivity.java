package com.application.davidelm.filetreevisitor;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.application.davidelm.filetreevisitor.presenter.DisplayNodePresenter;
import com.application.davidelm.filetreevisitor.models.TreeNode;
import com.application.davidelm.filetreevisitor.utils.Utils;
import com.application.davidelm.filetreevisitor.views.BreadCrumbsView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DisplayNodePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = DisplayNodePresenter.getInstance(new WeakReference<>(this));
        onInitView();
    }

    private void onInitView() {
        ((BreadCrumbsView) findViewById(R.id.breadCrumbsViewId)).setLst(new WeakReference<Activity>(this));

        TreeNode parentNode = presenter.getParentNode();
        //get support frag manager
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerId, Utils.buildFragment(parentNode))
                .commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ((BreadCrumbsView) findViewById(R.id.breadCrumbsViewId))
                .removeLatestBreadCrumb();
    }

}
