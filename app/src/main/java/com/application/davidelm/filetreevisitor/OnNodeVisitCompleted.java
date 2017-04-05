package com.application.davidelm.filetreevisitor;


import android.view.View;

import java.util.ArrayList;

public interface OnNodeVisitCompleted {
    void addAllNodeViews(ArrayList<View> view);
}
