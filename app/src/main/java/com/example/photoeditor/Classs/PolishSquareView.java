package com.example.photoeditor.Classs;

import android.content.Context;
import android.util.AttributeSet;

/* loaded from: classes3.dex */
public class PolishSquareView extends PolishGridView {
    public PolishSquareView(Context context) {
        super(context);
    }

    public PolishSquareView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PolishSquareView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth > measuredHeight) {
            measuredWidth = measuredHeight;
        }
        setMeasuredDimension(measuredWidth, measuredWidth);
    }
}
