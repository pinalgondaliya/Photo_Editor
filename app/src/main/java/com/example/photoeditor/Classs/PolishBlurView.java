package com.example.photoeditor.Classs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.photoeditor.Activity.BlurLayout;
import com.example.photoeditor.R;

/* loaded from: classes3.dex */
public class PolishBlurView extends AppCompatImageView {
    public static float resRatio;
    BitmapShader bitmapShader;
    Path brushPath;
    public Canvas canvas;
    Canvas canvasPreview;
    Paint circlePaint;
    Path circlePath;
    public boolean coloring;
    Context context;
    PointF curr;
    public int currentImageIndex;
    boolean draw;
    Paint drawPaint;
    Path drawPath;
    public Bitmap drawingBitmap;
    Rect dstRect;
    PointF last;
    Paint logPaintColor;
    Paint logPaintGray;
    float[] m;
    ScaleGestureDetector mScaleDetector;
    Matrix matrix;
    float maxScale;
    float minScale;
    public int mode;
    int oldMeasuredHeight;
    int oldMeasuredWidth;
    float oldX;
    float oldY;
    boolean onMeasureCalled;
    public int opacity;
    protected float origHeight;
    protected float origWidth;
    int pCount1;
    int pCount2;
    public boolean prViewDefaultPosition;
    Paint previewPaint;
    public float radius;
    public float saveScale;
    public Bitmap splashBitmap;
    PointF start;
    Paint tempPaint;
    Bitmap tempPreviewBitmap;
    int viewHeight;
    int viewWidth;
    float x;
    float y;

    public float getFixTrans(float f, float f2, float f3) {
        float f5;
        float f4;
        if (f3 <= f2) {
            f4 = f2 - f3;
            f5 = 0.0f;
        } else {
            f5 = f2 - f3;
            f4 = 0.0f;
        }
        if (f < f5) {
            return (-f) + f5;
        }
        if (f > f4) {
            return (-f) + f4;
        }
        return 0.0f;
    }

    /* loaded from: classes3.dex */
    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            if (PolishBlurView.this.mode == 1 || PolishBlurView.this.mode == 3) {
                PolishBlurView.this.mode = 3;
            } else {
                PolishBlurView.this.mode = 2;
            }
            PolishBlurView.this.draw = false;
            return true;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float scaleFactor = scaleGestureDetector.getScaleFactor();
            float f = PolishBlurView.this.saveScale;
            PolishBlurView.this.saveScale *= scaleFactor;
            if (PolishBlurView.this.saveScale > PolishBlurView.this.maxScale) {
                PolishBlurView polishBlurView = PolishBlurView.this;
                polishBlurView.saveScale = polishBlurView.maxScale;
                scaleFactor = PolishBlurView.this.maxScale / f;
            } else {
                float f2 = PolishBlurView.this.saveScale;
                float f3 = PolishBlurView.this.minScale;
            }
            if (PolishBlurView.this.origWidth * PolishBlurView.this.saveScale <= PolishBlurView.this.viewWidth || PolishBlurView.this.origHeight * PolishBlurView.this.saveScale <= PolishBlurView.this.viewHeight) {
                PolishBlurView.this.matrix.postScale(scaleFactor, scaleFactor, PolishBlurView.this.viewWidth / 2, PolishBlurView.this.viewHeight / 2);
            } else {
                PolishBlurView.this.matrix.postScale(scaleFactor, scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            }
            PolishBlurView.this.matrix.getValues(PolishBlurView.this.m);
            return true;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            PolishBlurView.this.radius = (BlurLayout.seekBarSize.getProgress() + 50) / PolishBlurView.this.saveScale;
            BlurLayout.brushView.setShapeRadiusRatio((BlurLayout.seekBarSize.getProgress() + 50) / PolishBlurView.this.saveScale);
            PolishBlurView.this.updatePreviewPaint();
        }
    }

    public PolishBlurView(Context context2) {
        super(context2);
        this.coloring = true;
        this.curr = new PointF();
        this.currentImageIndex = 0;
        this.draw = false;
        this.last = new PointF();
        this.maxScale = 5.0f;
        this.minScale = 1.0f;
        this.mode = 0;
        this.oldX = 0.0f;
        this.oldY = 0.0f;
        this.onMeasureCalled = false;
        this.opacity = 25;
        this.pCount1 = -1;
        this.pCount2 = -1;
        this.radius = 150.0f;
        this.saveScale = 1.0f;
        this.start = new PointF();
        this.context = context2;
        sharedConstructing(context2);
        this.prViewDefaultPosition = true;
        setDrawingCacheEnabled(true);
    }

    public PolishBlurView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        this.coloring = true;
        this.curr = new PointF();
        this.currentImageIndex = 0;
        this.draw = false;
        this.last = new PointF();
        this.maxScale = 5.0f;
        this.minScale = 1.0f;
        this.mode = 0;
        this.oldX = 0.0f;
        this.oldY = 0.0f;
        this.onMeasureCalled = false;
        this.opacity = 25;
        this.pCount1 = -1;
        this.pCount2 = -1;
        this.radius = 150.0f;
        this.saveScale = 1.0f;
        this.start = new PointF();
        this.context = context2;
        sharedConstructing(context2);
        this.prViewDefaultPosition = true;
        setDrawingCacheEnabled(true);
    }

    public void initDrawing() {
        this.splashBitmap = BlurLayout.bitmapClear.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap copy = Bitmap.createBitmap(BlurLayout.bitmapBlur).copy(Bitmap.Config.ARGB_8888, true);
        this.drawingBitmap = copy;
        setImageBitmap(copy);
        this.canvas = new Canvas(this.drawingBitmap);
        this.circlePath = new Path();
        this.drawPath = new Path();
        this.brushPath = new Path();
        Paint paint = new Paint();
        this.circlePaint = paint;
        paint.setAntiAlias(true);
        this.circlePaint.setDither(true);
        this.circlePaint.setColor(getContext().getResources().getColor(R.color.colorAccent));
        this.circlePaint.setStrokeWidth(SystemUtil.dpToPx(getContext(), 2));
        this.circlePaint.setStyle(Paint.Style.STROKE);
        Paint paint2 = new Paint(1);
        this.drawPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.drawPaint.setStrokeWidth(this.radius);
        this.drawPaint.setStrokeCap(Paint.Cap.ROUND);
        this.drawPaint.setStrokeJoin(Paint.Join.ROUND);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Paint paint3 = new Paint();
        this.tempPaint = paint3;
        paint3.setStyle(Paint.Style.FILL);
        this.tempPaint.setColor(-1);
        Paint paint4 = new Paint();
        this.previewPaint = paint4;
        paint4.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        this.tempPreviewBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        this.canvasPreview = new Canvas(this.tempPreviewBitmap);
        this.dstRect = new Rect(0, 0, 100, 100);
        Paint paint5 = new Paint(this.drawPaint);
        this.logPaintGray = paint5;
        paint5.setShader(new BitmapShader(BlurLayout.bitmapBlur, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        BitmapShader bitmapShader2 = new BitmapShader(this.splashBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        this.bitmapShader = bitmapShader2;
        this.drawPaint.setShader(bitmapShader2);
        this.logPaintColor = new Paint(this.drawPaint);
    }

    public void updatePaintBrush() {
        try {
            this.drawPaint.setStrokeWidth(this.radius * resRatio);
        } catch (Exception e) {
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        updatePreviewPaint();
    }

    public void changeShaderBitmap() {
        BitmapShader bitmapShader2 = new BitmapShader(this.splashBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        this.bitmapShader = bitmapShader2;
        this.drawPaint.setShader(bitmapShader2);
        updatePreviewPaint();
    }

    public void updatePreviewPaint() {
        if (BlurLayout.bitmapClear.getWidth() > BlurLayout.bitmapClear.getHeight()) {
            float width = BlurLayout.displayWidth / BlurLayout.bitmapClear.getWidth();
            resRatio = width;
            resRatio = this.saveScale * width;
        } else {
            float height = this.origHeight / BlurLayout.bitmapClear.getHeight();
            resRatio = height;
            resRatio = this.saveScale * height;
        }
        this.drawPaint.setStrokeWidth(this.radius * resRatio);
        this.drawPaint.setMaskFilter(new BlurMaskFilter(resRatio * 30.0f, BlurMaskFilter.Blur.NORMAL));
    }

    private void sharedConstructing(Context context2) {
        super.setClickable(true);
        this.context = context2;
        this.mScaleDetector = new ScaleGestureDetector(context2, new ScaleListener());
        Matrix matrix2 = new Matrix();
        this.matrix = matrix2;
        this.m = new float[9];
        setImageMatrix(matrix2);
        setScaleType(ScaleType.MATRIX);
        setOnTouchListener(new OnTouchListener() { // from class: com.example.photoareditor.Classs.PolishBlurView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PolishBlurView.this.mScaleDetector.onTouchEvent(motionEvent);
                PolishBlurView.this.pCount2 = motionEvent.getPointerCount();
                PolishBlurView.this.curr = new PointF(motionEvent.getX(), motionEvent.getY() - (BlurLayout.seekBarBlur.getProgress() * 3.0f));
                PolishBlurView polishBlurView = PolishBlurView.this;
                polishBlurView.x = (polishBlurView.curr.x - PolishBlurView.this.m[2]) / PolishBlurView.this.m[0];
                PolishBlurView polishBlurView2 = PolishBlurView.this;
                polishBlurView2.y = (polishBlurView2.curr.y - PolishBlurView.this.m[5]) / PolishBlurView.this.m[4];
                int action = motionEvent.getAction();
                if (action == 0) {
                    PolishBlurView.this.drawPaint.setStrokeWidth(PolishBlurView.this.radius * PolishBlurView.resRatio);
                    PolishBlurView.this.drawPaint.setMaskFilter(new BlurMaskFilter(PolishBlurView.resRatio * 30.0f, BlurMaskFilter.Blur.NORMAL));
                    PolishBlurView.this.drawPaint.getShader().setLocalMatrix(PolishBlurView.this.matrix);
                    PolishBlurView polishBlurView3 = PolishBlurView.this;
                    polishBlurView3.oldX = 0.0f;
                    polishBlurView3.oldY = 0.0f;
                    polishBlurView3.last.set(PolishBlurView.this.curr);
                    PolishBlurView.this.start.set(PolishBlurView.this.last);
                    if (PolishBlurView.this.mode != 1 && PolishBlurView.this.mode != 3) {
                        PolishBlurView.this.draw = true;
                    }
                    PolishBlurView.this.circlePath.reset();
                    PolishBlurView.this.circlePath.moveTo(PolishBlurView.this.curr.x, PolishBlurView.this.curr.y);
                    PolishBlurView.this.circlePath.addCircle(PolishBlurView.this.curr.x, PolishBlurView.this.curr.y, (PolishBlurView.this.radius * PolishBlurView.resRatio) / 2.0f, Path.Direction.CW);
                    PolishBlurView.this.drawPath.moveTo(PolishBlurView.this.x, PolishBlurView.this.y);
                    PolishBlurView.this.brushPath.moveTo(PolishBlurView.this.curr.x, PolishBlurView.this.curr.y);
                    PolishBlurView.this.showBoxPreview();
                } else if (action == 1) {
                    if (PolishBlurView.this.mode == 1) {
                        PolishBlurView.this.matrix.getValues(PolishBlurView.this.m);
                    }
                    int abs = (int) Math.abs(PolishBlurView.this.curr.y - PolishBlurView.this.start.y);
                    if (((int) Math.abs(PolishBlurView.this.curr.x - PolishBlurView.this.start.x)) < 3 && abs < 3) {
                        PolishBlurView.this.performClick();
                    }
                    if (PolishBlurView.this.draw) {
                        PolishBlurView.this.drawPaint.setStrokeWidth(PolishBlurView.this.radius);
                        PolishBlurView.this.drawPaint.setMaskFilter(new BlurMaskFilter(30.0f, BlurMaskFilter.Blur.NORMAL));
                        PolishBlurView.this.drawPaint.getShader().setLocalMatrix(new Matrix());
                        PolishBlurView.this.canvas.drawPath(PolishBlurView.this.drawPath, PolishBlurView.this.drawPaint);
                    }
                    PolishBlurView.this.circlePath.reset();
                    PolishBlurView.this.drawPath.reset();
                    PolishBlurView.this.brushPath.reset();
                    PolishBlurView.this.draw = false;
                } else if (action != 2) {
                    if (action == 6 && PolishBlurView.this.mode == 2) {
                        PolishBlurView.this.mode = 0;
                    }
                } else if (PolishBlurView.this.mode == 1 || PolishBlurView.this.mode == 3 || !PolishBlurView.this.draw) {
                    if (PolishBlurView.this.pCount1 == 1 && PolishBlurView.this.pCount2 == 1) {
                        PolishBlurView.this.matrix.postTranslate(PolishBlurView.this.curr.x - PolishBlurView.this.last.x, PolishBlurView.this.curr.y - PolishBlurView.this.last.y);
                    }
                    PolishBlurView.this.last.set(PolishBlurView.this.curr.x, PolishBlurView.this.curr.y);
                } else {
                    PolishBlurView.this.circlePath.reset();
                    PolishBlurView.this.circlePath.moveTo(PolishBlurView.this.curr.x, PolishBlurView.this.curr.y);
                    PolishBlurView.this.circlePath.addCircle(PolishBlurView.this.curr.x, PolishBlurView.this.curr.y, (PolishBlurView.this.radius * PolishBlurView.resRatio) / 2.0f, Path.Direction.CW);
                    PolishBlurView.this.drawPath.lineTo(PolishBlurView.this.x, PolishBlurView.this.y);
                    PolishBlurView.this.brushPath.lineTo(PolishBlurView.this.curr.x, PolishBlurView.this.curr.y);
                    PolishBlurView.this.showBoxPreview();
                }
                PolishBlurView polishBlurView4 = PolishBlurView.this;
                polishBlurView4.pCount1 = polishBlurView4.pCount2;
                PolishBlurView polishBlurView5 = PolishBlurView.this;
                polishBlurView5.setImageMatrix(polishBlurView5.matrix);
                PolishBlurView.this.invalidate();
                return true;
            }
        });
    }

    public void updateRefMetrix() {
        this.matrix.getValues(this.m);
    }

    public void showBoxPreview() {
        buildDrawingCache();
        try {
            Bitmap createBitmap = Bitmap.createBitmap(getDrawingCache());
            this.canvasPreview.drawRect(this.dstRect, this.tempPaint);
            this.canvasPreview.drawBitmap(createBitmap, new Rect(((int) this.curr.x) - 100, ((int) this.curr.y) - 100, ((int) this.curr.x) + 100, ((int) this.curr.y) + 100), this.dstRect, this.previewPaint);
            destroyDrawingCache();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas2) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        int i = (int) fArr[2];
        int i2 = (int) fArr[5];
        super.onDraw(canvas2);
        float f = this.origHeight;
        float f2 = this.saveScale;
        float f3 = i2;
        float f4 = (f * f2) + f3;
        if (i2 < 0) {
            float f5 = i;
            float f6 = (this.origWidth * f2) + f5;
            float f7 = this.viewHeight;
            if (f4 > f7) {
                f4 = f7;
            }
            canvas2.clipRect(f5, 0.0f, f6, f4);
        } else {
            float f8 = i;
            float f9 = (this.origWidth * f2) + f8;
            float f10 = this.viewHeight;
            if (f4 > f10) {
                f4 = f10;
            }
            canvas2.clipRect(f8, f3, f9, f4);
        }
        if (this.draw) {
            canvas2.drawPath(this.brushPath, this.drawPaint);
            canvas2.drawPath(this.circlePath, this.circlePaint);
        }
    }

    public void fixTrans() {
        this.matrix.getValues(this.m);
        float[] fArr = this.m;
        float f = fArr[2];
        float f2 = fArr[5];
        float fixTrans = getFixTrans(f, this.viewWidth, this.origWidth * this.saveScale);
        float fixTrans2 = getFixTrans(f2, this.viewHeight, this.origHeight * this.saveScale);
        if (fixTrans != 0.0f || fixTrans2 != 0.0f) {
            this.matrix.postTranslate(fixTrans, fixTrans2);
        }
        this.matrix.getValues(this.m);
        updatePreviewPaint();
    }

    @Override // android.widget.ImageView, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (!this.onMeasureCalled) {
            this.viewWidth = MeasureSpec.getSize(i);
            int size = MeasureSpec.getSize(i2);
            this.viewHeight = size;
            int i3 = this.oldMeasuredHeight;
            int i4 = this.viewWidth;
            if ((i3 != i4 || i3 != size) && i4 != 0 && size != 0) {
                this.oldMeasuredHeight = size;
                this.oldMeasuredWidth = i4;
                if (this.saveScale == 1.0f) {
                    fitScreen();
                }
                this.onMeasureCalled = true;
            }
        }
    }

    public void fitScreen() {
        Drawable drawable = getDrawable();
        if (drawable != null && drawable.getIntrinsicWidth() != 0 && drawable.getIntrinsicHeight() != 0) {
            float intrinsicWidth = drawable.getIntrinsicWidth();
            float intrinsicHeight = drawable.getIntrinsicHeight();
            float min = Math.min(this.viewWidth / intrinsicWidth, this.viewHeight / intrinsicHeight);
            this.matrix.setScale(min, min);
            float f = (this.viewHeight - (intrinsicHeight * min)) / 2.0f;
            float f2 = (this.viewWidth - (intrinsicWidth * min)) / 2.0f;
            this.matrix.postTranslate(f2, f);
            this.origWidth = this.viewWidth - (f2 * 2.0f);
            this.origHeight = this.viewHeight - (2.0f * f);
            setImageMatrix(this.matrix);
            this.matrix.getValues(this.m);
            fixTrans();
        }
    }
}
