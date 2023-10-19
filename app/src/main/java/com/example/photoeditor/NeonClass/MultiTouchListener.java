package com.example.photoeditor.NeonClass;

import android.app.Activity;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.example.photoeditor.Activity.NeonLayout;
import com.example.photoeditor.OutSideClass.ScaleGestureDetector;
import com.example.photoeditor.SpiralClass.DHANVINE_Vector2D;

/* loaded from: classes6.dex */
public class MultiTouchListener implements View.OnTouchListener {
    Boolean forspiral;
    Activity mActivity;
    private float mPrevX;
    private float mPrevY;
    public float minimumScale;
    private Rect rect;
    OnRotateListner rotateListner;
    public boolean isRotateEnabled = true;
    public boolean isTranslateEnabled = true;
    public boolean isScaleEnabled = true;
    public float maximumScale = 10.0f;
    private int mActivePointerId = -1;
    private ScaleGestureDetector mScaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener());

    /* loaded from: classes6.dex */
    public interface OnRotateListner {
        float getRotation(float f);
    }

    private static float adjustAngle(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }

    /* loaded from: classes6.dex */
    private class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private DHANVINE_Vector2D mPrevSpanVector;

        @Override // com.example.photoareditor.SpiralClass.ScaleGestureDetector.SimpleOnScaleGestureListener, com.example.photoareditor.SpiralClass.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(View view, ScaleGestureDetector scaleGestureDetector) {
        }

        private ScaleGestureListener() {
            this.mPrevSpanVector = new DHANVINE_Vector2D(0.0f, 0.0f);
        }

        @Override // com.example.photoareditor.SpiralClass.ScaleGestureDetector.SimpleOnScaleGestureListener, com.example.photoareditor.SpiralClass.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(View view, ScaleGestureDetector scaleGestureDetector) {
            this.mPivotX = scaleGestureDetector.getFocusX();
            this.mPivotY = scaleGestureDetector.getFocusY();
            this.mPrevSpanVector.set(scaleGestureDetector.getCurrentSpanVector());
            return true;
        }

        @Override // com.example.photoareditor.SpiralClass.ScaleGestureDetector.SimpleOnScaleGestureListener, com.example.photoareditor.SpiralClass.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(View view, ScaleGestureDetector scaleGestureDetector) {
            TransformInfo transformInfo = new TransformInfo();
            transformInfo.deltaScale = MultiTouchListener.this.isScaleEnabled ? scaleGestureDetector.getScaleFactor() : 1.0f;
            float f = 0.0f;
            float f2 = 0.0f;
            transformInfo.deltaAngle = MultiTouchListener.this.isRotateEnabled ? DHANVINE_Vector2D.getAngle(this.mPrevSpanVector, scaleGestureDetector.getCurrentSpanVector()) : 0.0f;
            if (MultiTouchListener.this.isTranslateEnabled) {
                f2 = scaleGestureDetector.getFocusX() - this.mPivotX;
            }
            transformInfo.deltaX = f2;
            if (MultiTouchListener.this.isTranslateEnabled) {
                f = scaleGestureDetector.getFocusY() - this.mPivotY;
            }
            transformInfo.deltaY = f;
            transformInfo.pivotX = this.mPivotX;
            transformInfo.pivotY = this.mPivotY;
            transformInfo.minimumScale = MultiTouchListener.this.minimumScale;
            transformInfo.maximumScale = MultiTouchListener.this.maximumScale;
            MultiTouchListener.this.move(view, transformInfo);
            return false;
        }
    }

    /* loaded from: classes6.dex */
    public class TransformInfo {
        public float deltaAngle;
        public float deltaScale;
        public float deltaX;
        public float deltaY;
        public float maximumScale;
        public float minimumScale;
        public float pivotX;
        public float pivotY;

        private TransformInfo() {
        }
    }

    public MultiTouchListener(Activity activity, Boolean bool) {
        this.forspiral = false;
        this.mActivity = activity;
        this.forspiral = bool;
    }

    public void move(View view, TransformInfo transformInfo) {
        computeRenderOffset(view, transformInfo.pivotX, transformInfo.pivotY);
        adjustTranslation(view, transformInfo.deltaX, transformInfo.deltaY);
        float max = Math.max(transformInfo.minimumScale, Math.min(transformInfo.maximumScale, view.getScaleX() * transformInfo.deltaScale));
        view.setScaleX(max);
        view.setScaleY(max);
        view.setRotation(adjustAngle(view.getRotation() + transformInfo.deltaAngle));
    }

    private static void adjustTranslation(View view, float f, float f2) {
        float[] fArr = {f, f2};
        view.getMatrix().mapVectors(fArr);
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(view.getTranslationY() + fArr[1]);
    }

    private static void computeRenderOffset(View view, float f, float f2) {
        if (view.getPivotX() != f || view.getPivotY() != f2) {
            float[] fArr = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr);
            view.setPivotX(f);
            view.setPivotY(f2);
            float[] fArr2 = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr2);
            float f3 = fArr2[1] - fArr[1];
            view.setTranslationX(view.getTranslationX() - (fArr2[0] - fArr[0]));
            view.setTranslationY(view.getTranslationY() - f3);
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.forspiral.booleanValue() && (this.mActivity instanceof NeonLayout)) {
            onTouch2(NeonLayout.imageViewFont, motionEvent);
        }
        this.mScaleGestureDetector.onTouchEvent(view, motionEvent);
        if (this.isTranslateEnabled) {
            int action = motionEvent.getAction();
            int actionMasked = motionEvent.getActionMasked() & action;
            int i = 0;
            if (actionMasked == 6) {
                int i2 = (65280 & action) >> 8;
                if (motionEvent.getPointerId(i2) == this.mActivePointerId) {
                    if (i2 == 0) {
                        i = 1;
                    }
                    this.mPrevX = motionEvent.getX(i);
                    this.mPrevY = motionEvent.getY(i);
                    this.mActivePointerId = motionEvent.getPointerId(i);
                }
            } else if (actionMasked == 0) {
                this.mPrevX = motionEvent.getX();
                this.mPrevY = motionEvent.getY();
                this.rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                this.mActivePointerId = motionEvent.getPointerId(0);
            } else if (actionMasked == 1) {
                this.mActivePointerId = -1;
            } else if (actionMasked == 2) {
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex != -1) {
                    float x = motionEvent.getX(findPointerIndex);
                    float y = motionEvent.getY(findPointerIndex);
                    if (!this.mScaleGestureDetector.isInProgress()) {
                        adjustTranslation(view, x - this.mPrevX, y - this.mPrevY);
                    }
                }
            } else if (actionMasked == 3) {
                this.mActivePointerId = -1;
            }
        }
        return true;
    }

    public boolean onTouch2(View view, MotionEvent motionEvent) {
        this.mScaleGestureDetector.onTouchEvent(view, motionEvent);
        if (this.isTranslateEnabled) {
            int action = motionEvent.getAction();
            int actionMasked = motionEvent.getActionMasked() & action;
            int i = 0;
            if (actionMasked == 6) {
                int i2 = (65280 & action) >> 8;
                if (motionEvent.getPointerId(i2) == this.mActivePointerId) {
                    if (i2 == 0) {
                        i = 1;
                    }
                    this.mPrevX = motionEvent.getX(i);
                    this.mPrevY = motionEvent.getY(i);
                    this.mActivePointerId = motionEvent.getPointerId(i);
                }
            } else if (actionMasked == 0) {
                this.mPrevX = motionEvent.getX();
                this.mPrevY = motionEvent.getY();
                this.rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                this.mActivePointerId = motionEvent.getPointerId(0);
            } else if (actionMasked == 1) {
                this.mActivePointerId = -1;
            } else if (actionMasked == 2) {
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex != -1) {
                    float x = motionEvent.getX(findPointerIndex);
                    float y = motionEvent.getY(findPointerIndex);
                    if (!this.mScaleGestureDetector.isInProgress()) {
                        adjustTranslation(view, x - this.mPrevX, y - this.mPrevY);
                    }
                }
            } else if (actionMasked == 3) {
                this.mActivePointerId = -1;
            }
        }
        return true;
    }
}
