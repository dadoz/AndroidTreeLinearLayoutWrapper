package com.application.davidelm.filetreevisitor;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import com.application.davidelm.filetreevisitor.treeFileView.ExpandTreeView;
import com.unnamed.b.atv.model.TreeNode;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements OnNodeClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onInitView();
    }

    private void onInitView() {
        initTreeNode();
    }

    private void initTreeNode() {

        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode("MyParentNode");
        TreeNode child0 = new TreeNode("ChildNode0");
        TreeNode child1 = new TreeNode("ChildNode1");
        parent.addChildren(child0, child1);
        root.addChild(parent);


        //treeview
        ExpandTreeView expandTreeView = new ExpandTreeView(this, root, new WeakReference<>(this));

        //set custom view
        TreeNode parentNode = root.getChildren().get(0);

        //render all children
        for (TreeNode node : parentNode.getChildren()) {
            View view = expandTreeView.displayNode(new WeakReference<>(this), R.layout.node_item, node);
            ((ViewGroup) findViewById(R.id.containerLayoutId)).addView(view);
        }
    }


    @Override
    public void onNodeCLick(TreeNode node) {
        Log.e(TAG, "Hye clicking :)" + node.getChildren().size());
        Log.e(TAG, "Hye clicking :)" + node.getValue().toString());
    }
}
