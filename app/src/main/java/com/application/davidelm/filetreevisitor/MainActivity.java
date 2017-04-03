package com.application.davidelm.filetreevisitor;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.application.davidelm.filetreevisitor.fragments.DisplayNodeFragment;
import com.application.davidelm.filetreevisitor.presenter.DisplayNodePresenter;
import com.application.davidelm.filetreevisitor.treeFileView.TreeNode;
import com.application.davidelm.filetreevisitor.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnNodeClickListener {

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
        presenter.setListener(new WeakReference<>(this));
        TreeNode parentNode = presenter.getParentNode();
        //get support frag manager
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerId, Utils.buildFragment(parentNode))
                .commit();

    }

    private void initTreeNode() {
//        //render all children
//        for (TreeNode node : parentNode.getChildren()) {
//            View view = expandTreeView.displayNode(new WeakReference<>(this), R.layout.node_item, node);
//            ((ViewGroup) findViewById(R.id.fragmentContainerId)).addView(view);
//        }
    }



    @Override
    public void onNodeCLick(TreeNode node) {
        Log.e(TAG, "Hye clicking :)" + node.getChildren().size());
        Log.e(TAG, "Hye clicking :)" + node.getValue().toString());
    }

}
