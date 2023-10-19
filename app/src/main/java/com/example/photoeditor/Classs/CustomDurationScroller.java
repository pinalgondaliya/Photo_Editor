package com.example.photoeditor.Classs;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/* loaded from: classes3.dex */
public class CustomDurationScroller extends Scroller {
    private double scrollFactor;

    public CustomDurationScroller(Context context) {
        super(context);
        this.scrollFactor = 1.0d;
    }

    public CustomDurationScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
        this.scrollFactor = 1.0d;
    }

    public void setScrollDurationFactor(double scrollFactor) {
        this.scrollFactor = scrollFactor;
    }

    @Override // android.widget.Scroller
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (int) (duration * this.scrollFactor));
    }
}
