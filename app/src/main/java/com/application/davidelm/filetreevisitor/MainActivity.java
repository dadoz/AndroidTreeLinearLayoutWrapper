package com.application.davidelm.filetreevisitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.lib.davidelm.filetreevisitorlibrary.views.BreadCrumbsView;
import com.lib.davidelm.filetreevisitorlibrary.views.DisplayNodeView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DisplayNodeView displayNodeView;
    private String TAG = "Display";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onInitView();
    }

    private void onInitView() {
        injectViews();

        findViewById(R.id.addFolderButtonId).setOnClickListener(this);
        findViewById(R.id.addFileButtonId).setOnClickListener(this);
        findViewById(R.id.removeNodeButtonId).setOnClickListener(this);
    }

    /**
     * addd to doc
     */
    private void injectViews() {
        BreadCrumbsView breadCrumbsView = ((BreadCrumbsView) findViewById(R.id.breadCrumbsViewId));
        displayNodeView = ((DisplayNodeView) findViewById(R.id.displayNodeViewId));
        displayNodeView.setBreadCrumbsView(breadCrumbsView);
    }

    /**
     * add to doc
     */
    @Override
    public void onBackPressed() {
        if (!((DisplayNodeView) findViewById(R.id.displayNodeViewId))
                .onBackPressed())
            super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.removeNodeButtonId:
                Log.e(TAG, "jey");
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
}
