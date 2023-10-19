package com.example.photoeditor.Classs;

import android.util.Log;

/* loaded from: classes3.dex */
public abstract class NumberStraightLayout extends StraightCollageLayout {
    static final String TAG = "NumberStraightLayout";
    protected int theme;

    public abstract int getThemeCount();

    public NumberStraightLayout() {
    }

    public NumberStraightLayout(StraightCollageLayout straightCollageLayout, boolean z) {
        super(straightCollageLayout, z);
    }

    public NumberStraightLayout(int i) {
        if (i >= getThemeCount()) {
            StringBuilder sb = new StringBuilder();
            sb.append("NumberStraightLayout: the most theme count is ");
            sb.append(getThemeCount());
            sb.append(" ,you should let theme from 0 to ");
            sb.append(getThemeCount() - 1);
            sb.append(" .");
            Log.e(TAG, sb.toString());
        }
        this.theme = i;
    }

    @Override
    public StraightArea mo336getOuterArea() {
        return super.mo336getOuterArea();
    }

    public int getTheme() {
        return this.theme;
    }
}
