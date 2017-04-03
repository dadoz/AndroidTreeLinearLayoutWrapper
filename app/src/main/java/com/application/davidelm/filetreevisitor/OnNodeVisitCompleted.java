package com.application.davidelm.filetreevisitor;


import android.view.View;

import com.application.davidelm.filetreevisitor.treeFileView.TreeNode;

import java.util.ArrayList;

public interface OnNodeVisitCompleted {
    void addAllNodeViews(ArrayList<View> view);
}
