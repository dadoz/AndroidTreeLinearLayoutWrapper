package com.application.davidelm.filetreevisitor.utils;

import android.os.Bundle;

import com.application.davidelm.filetreevisitor.fragments.DisplayNodeFragment;
import com.application.davidelm.filetreevisitor.models.TreeNode;


public class Utils {

    /**
     *
     * @param node
     * @return
     */
    public static DisplayNodeFragment buildFragment(TreeNode node) {
        DisplayNodeFragment frag = new DisplayNodeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TreeNode.TREE_NODE_PARCELABLE, node);
        frag.setArguments(bundle);
        return frag;
    }

}
