package com.application.davidelm.filetreevisitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.lib.davidelm.filetreevisitorlibrary.models.TreeNode;
import com.lib.davidelm.filetreevisitorlibrary.views.BreadCrumbsView;
import com.lib.davidelm.filetreevisitorlibrary.views.TreeNodeView;
import com.lib.davidelm.filetreevisitorlibrary.views.OnNavigationCallbacks;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnNavigationCallbacks {

    private TreeNodeView displayNodeView;
    private String TAG = "Display";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onInitView();
    }

    private void onInitView() {
        injectViews();

        findViewById(R.id.menuViewId).findViewById(R.id.addFolderButtonId).setOnClickListener(this);
        findViewById(R.id.menuViewId).findViewById(R.id.addFileButtonId).setOnClickListener(this);
        findViewById(R.id.menuViewId).findViewById(R.id.removeNodeButtonId).setOnClickListener(this);
    }

    /**
     * addd to doc
     */
    private void injectViews() {
        BreadCrumbsView breadCrumbsView = ((BreadCrumbsView) findViewById(R.id.breadCrumbsViewId));
        displayNodeView = ((TreeNodeView) findViewById(R.id.displayNodeViewId));
        displayNodeView.setNavigationCallbacksListener(new WeakReference<>(this));
        displayNodeView.setBreadCrumbsView(breadCrumbsView);
    }

    /**
     * add to doc
     */
    @Override
    public void onBackPressed() {
        if (!displayNodeView.onBackPressed())
            super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.removeNodeButtonId:
                displayNodeView.removeFolder(((EditText) findViewById(R.id.nodeValueEditTextId)).getText().toString());
                break;
            case R.id.addFolderButtonId:
                displayNodeView.addFolder(((EditText) findViewById(R.id.nodeValueEditTextId)).getText().toString());
                break;
            case R.id.addFileButtonId:
                displayNodeView.addFile(((EditText) findViewById(R.id.nodeValueEditTextId)).getText().toString());
                break;
        }
    }

    @Override
    public void onNodeError(int type, TreeNode currentNode, String message) {
        Log.e(TAG, "hey" + message);
    }

    @Override
    public void onFolderNodeClickCb(int position, TreeNode node) {
        Log.e(TAG, "FOLDER" + node.getValue());
    }

    @Override
    public void onFileNodeClickCb(int position, TreeNode node) {
        Log.e(TAG, "FILE" + node.getValue());
    }
}
