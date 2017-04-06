package com.application.davidelm.filetreevisitor;


import android.view.View;

import com.application.davidelm.filetreevisitor.models.TreeNode;

import java.util.ArrayList;
import java.util.List;

public interface OnNodeVisitCompleted {
    void addAllNodeViews(ArrayList<View> view);

    void addNodes(List<TreeNode> list);
}
