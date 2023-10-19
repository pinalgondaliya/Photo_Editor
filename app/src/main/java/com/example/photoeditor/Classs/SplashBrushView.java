package com.example.photoeditor.Classs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/* loaded from: classes3.dex */
public class SplashBrushView extends View {
    public BrushSize brushSize;
    public boolean isBrushSize;
    float opacity;
    float ratioRadius;

    public SplashBrushView(Context context) {
        super(context);
        this.isBrushSize = true;
        initMyView();
    }

    public SplashBrushView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isBrushSize = true;
        initMyView();
    }

    public SplashBrushView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isBrushSize = true;
        initMyView();
    }

    public void initMyView() {
        this.brushSize = new BrushSize();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float f2;
        float f;
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (width != 0 && height != 0) {
            float f3 = width / 2.0f;
            float f4 = height / 2.0f;
            if (width > height) {
                f2 = this.ratioRadius;
                f = PolishSplashView.resRatio;
            } else {
                f2 = this.ratioRadius;
                f = PolishSplashView.resRatio;
            }
            float f5 = (f2 * f) / 2.0f;
            if (((int) f5) * 2 > 150) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
                int i = ((int) (2.0f * f5)) + 40;
                layoutParams.height = i;
                layoutParams.width = i;
                layoutParams.alignWithParent = true;
                setLayoutParams(layoutParams);
            }
            this.brushSize.setCircle(f3, f4, f5, Path.Direction.CCW);
            canvas.drawPath(this.brushSize.getPath(), this.brushSize.getPaint());
            if (!this.isBrushSize) {
                canvas.drawPath(this.brushSize.getPath(), this.brushSize.getInnerPaint());
            }
        }
    }

    public void setShapeRadiusRatio(float f) {
        this.ratioRadius = f;
    }

    public void setShapeOpacity(float f) {
        this.opacity = f;
    }
}
