package com.application.davidelm.filetreevisitor.treeFileView;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.application.davidelm.filetreevisitor.R;

public class DisplayNodeView extends FrameLayout {
    public DisplayNodeView(@NonNull Context context) {
        super(context);
        initView();
    }

    public DisplayNodeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DisplayNodeView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public DisplayNodeView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public void initView() {
        inflate(getContext(), R.layout.display_node_layout, this);
    }
}
