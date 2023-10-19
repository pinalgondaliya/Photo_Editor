package com.example.photoeditor.Classs;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;

/* loaded from: classes3.dex */
public class PolishMotionView extends PolishMotionViewTouchBase {
    protected int mDoubleTapDirection;
    protected boolean mDoubleTapEnabled;
    public OnImageViewTouchDoubleTapListener mDoubleTapListener;
    private OnImageFlingListener mFlingListener;
    protected GestureDetector mGestureDetector;
    protected GestureDetector.OnGestureListener mGestureListener;
    protected ScaleGestureDetector mScaleDetector;
    protected boolean mScaleEnabled;
    protected float mScaleFactor;
    protected ScaleGestureDetector.OnScaleGestureListener mScaleListener;
    protected boolean mScrollEnabled;
    public OnImageViewTouchSingleTapListener mSingleTapListener;
    protected int mTouchSlop;

    /* loaded from: classes3.dex */
    public interface OnImageFlingListener {
        void onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);
    }

    /* loaded from: classes3.dex */
    public interface OnImageViewTouchDoubleTapListener {
        void onDoubleTap();
    }

    /* loaded from: classes3.dex */
    public interface OnImageViewTouchSingleTapListener {
        void onSingleTapConfirmed();
    }

    public PolishMotionView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDoubleTapEnabled = true;
        this.mScaleEnabled = true;
        this.mScrollEnabled = true;
    }

    @Override // com.example.photoareditor.Classs.PolishMotionViewTouchBase
    public void init() {
        super.init();
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mGestureListener = getGestureListener();
        this.mScaleListener = getScaleListener();
        this.mScaleDetector = new ScaleGestureDetector(getContext(), this.mScaleListener);
        this.mGestureDetector = new GestureDetector(getContext(), this.mGestureListener, null, true);
        this.mDoubleTapDirection = 1;
    }

    public GestureDetector.OnGestureListener getGestureListener() {
        return new GestureListener();
    }

    public ScaleGestureDetector.OnScaleGestureListener getScaleListener() {
        return new ScaleListener();
    }

    @Override // com.example.photoareditor.Classs.PolishMotionViewTouchBase
    public void _setImageDrawable(Drawable drawable, Matrix matrix, float f, float f2) {
        super._setImageDrawable(drawable, matrix, f, f2);
        this.mScaleFactor = getMaxScale() / 3.0f;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mScaleDetector.onTouchEvent(motionEvent);
        if (!this.mScaleDetector.isInProgress()) {
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        if ((motionEvent.getAction() & 255) == 1 && getScale() < getMinScale()) {
            zoomTo(getMinScale(), 500.0f);
        }
        return true;
    }

    @Override // com.example.photoareditor.Classs.PolishMotionViewTouchBase
    public void onZoomAnimationCompleted(float f) {
        if (f < getMinScale()) {
            zoomTo(getMinScale(), 50.0f);
        }
    }

    public float onDoubleTapPost(float f, float f2) {
        if (this.mDoubleTapDirection != 1) {
            this.mDoubleTapDirection = 1;
            return 1.0f;
        }
        float f3 = this.mScaleFactor;
        if ((2.0f * f3) + f <= f2) {
            return f + f3;
        }
        this.mDoubleTapDirection = -1;
        return f2;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (!this.mScrollEnabled || motionEvent == null || motionEvent2 == null || motionEvent.getPointerCount() > 1 || motionEvent2.getPointerCount() > 1 || this.mScaleDetector.isInProgress() || getScale() == 1.0f) {
            return false;
        }
        this.mUserScaled = true;
        scrollBy(-f, -f2);
        invalidate();
        return true;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (!this.mScrollEnabled) {
            return false;
        }
        OnImageFlingListener onImageFlingListener = this.mFlingListener;
        if (onImageFlingListener != null) {
            onImageFlingListener.onFling(motionEvent, motionEvent2, f, f2);
        }
        if (motionEvent.getPointerCount() > 1 || motionEvent2.getPointerCount() > 1 || this.mScaleDetector.isInProgress() || getScale() == 1.0f) {
            return false;
        }
        float x = motionEvent2.getX() - motionEvent.getX();
        float y = motionEvent2.getY() - motionEvent.getY();
        if (Math.abs(f) <= 800.0f && Math.abs(f2) <= 800.0f) {
            return false;
        }
        this.mUserScaled = true;
        scrollBy(x / 2.0f, y / 2.0f, 300.0d);
        invalidate();
        return true;
    }

    /* loaded from: classes3.dex */
    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        public GestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if (PolishMotionView.this.mSingleTapListener != null) {
                PolishMotionView.this.mSingleTapListener.onSingleTapConfirmed();
            }
            return super.onSingleTapConfirmed(motionEvent);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            Log.i(PolishMotionViewTouchBase.LOG_TAG, "onDoubleTap. double tap enabled? " + PolishMotionView.this.mDoubleTapEnabled);
            if (PolishMotionView.this.mDoubleTapEnabled) {
                PolishMotionView.this.mUserScaled = true;
                PolishMotionView polishMotionView = PolishMotionView.this;
                float maxScale = polishMotionView.getMaxScale();
                PolishMotionView polishMotionView2 = PolishMotionView.this;
                polishMotionView.zoomTo(Math.min(maxScale, Math.max(polishMotionView2.onDoubleTapPost(polishMotionView2.getScale(), PolishMotionView.this.getMaxScale()), PolishMotionView.this.getMinScale())), motionEvent.getX(), motionEvent.getY(), 200.0f);
                PolishMotionView.this.invalidate();
            }
            if (PolishMotionView.this.mDoubleTapListener != null) {
                PolishMotionView.this.mDoubleTapListener.onDoubleTap();
            }
            return super.onDoubleTap(motionEvent);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            if (PolishMotionView.this.isLongClickable() && !PolishMotionView.this.mScaleDetector.isInProgress()) {
                PolishMotionView.this.setPressed(true);
                PolishMotionView.this.performLongClick();
            }
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return PolishMotionView.this.onScroll(motionEvent, motionEvent2, f, f2);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return PolishMotionView.this.onFling(motionEvent, motionEvent2, f, f2);
        }
    }

    /* loaded from: classes3.dex */
    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        protected boolean mScaled = false;

        public ScaleListener() {
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float currentSpan = scaleGestureDetector.getCurrentSpan() - scaleGestureDetector.getPreviousSpan();
            float scale = PolishMotionView.this.getScale() * scaleGestureDetector.getScaleFactor();
            if (PolishMotionView.this.mScaleEnabled) {
                boolean z = this.mScaled;
                if (z && currentSpan != 0.0f) {
                    PolishMotionView.this.mUserScaled = true;
                    PolishMotionView polishMotionView = PolishMotionView.this;
                    polishMotionView.zoomTo(Math.min(polishMotionView.getMaxScale(), Math.max(scale, PolishMotionView.this.getMinScale() - 0.1f)), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
                    PolishMotionView.this.mDoubleTapDirection = 1;
                    PolishMotionView.this.invalidate();
                    return true;
                } else if (!z) {
                    this.mScaled = true;
                }
            }
            return true;
        }
    }
}
