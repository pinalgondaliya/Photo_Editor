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
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.photoeditor.Activity.SplashLayout;
import com.example.photoeditor.R;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PolishSplashView extends AppCompatImageView {
    public static float resRatio;
    BitmapShader bitmapShader;
    Path brushPath;
    public Canvas canvas;
    Canvas canvasPreview;
    Paint circlePaint;
    Path circlePath;
    public int coloring;
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
    ArrayList<PointF> pathPoints;
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
            if (PolishSplashView.this.mode == 1 || PolishSplashView.this.mode == 3) {
                PolishSplashView.this.mode = 3;
            } else {
                PolishSplashView.this.mode = 2;
            }
            PolishSplashView.this.draw = false;
            return true;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float scaleFactor = scaleGestureDetector.getScaleFactor();
            float f = PolishSplashView.this.saveScale;
            PolishSplashView.this.saveScale *= scaleFactor;
            if (PolishSplashView.this.saveScale > PolishSplashView.this.maxScale) {
                PolishSplashView polishSplashView = PolishSplashView.this;
                polishSplashView.saveScale = polishSplashView.maxScale;
                scaleFactor = PolishSplashView.this.maxScale / f;
            } else {
                float f2 = PolishSplashView.this.saveScale;
                float f3 = PolishSplashView.this.minScale;
            }
            if (PolishSplashView.this.origWidth * PolishSplashView.this.saveScale <= PolishSplashView.this.viewWidth || PolishSplashView.this.origHeight * PolishSplashView.this.saveScale <= PolishSplashView.this.viewHeight) {
                PolishSplashView.this.matrix.postScale(scaleFactor, scaleFactor, PolishSplashView.this.viewWidth / 2, PolishSplashView.this.viewHeight / 2);
            } else {
                PolishSplashView.this.matrix.postScale(scaleFactor, scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            }
            PolishSplashView.this.matrix.getValues(PolishSplashView.this.m);
            return true;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            PolishSplashView.this.radius = (SplashLayout.seekBarSize.getProgress() + 10) / PolishSplashView.this.saveScale;
            PolishSplashView.this.updatePreviewPaint();
        }
    }

    public PolishSplashView(Context context2) {
        super(context2);
        this.coloring = -1;
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
        this.opacity = 240;
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

    public PolishSplashView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        this.coloring = -1;
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
        this.opacity = 240;
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
        this.splashBitmap = SplashLayout.colorBitmap;
        Bitmap createBitmap = Bitmap.createBitmap(SplashLayout.grayBitmap);
        this.drawingBitmap = createBitmap;
        setImageBitmap(createBitmap);
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
        paint5.setShader(new BitmapShader(SplashLayout.grayBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        BitmapShader bitmapShader2 = new BitmapShader(this.splashBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        this.bitmapShader = bitmapShader2;
        this.drawPaint.setShader(bitmapShader2);
        this.logPaintColor = new Paint(this.drawPaint);
    }

    public void updatePaintBrush() {
        try {
            this.drawPaint.setStrokeWidth(this.radius * resRatio);
            this.drawPaint.setAlpha(this.opacity);
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
        if (SplashLayout.colorBitmap.getWidth() > SplashLayout.colorBitmap.getHeight()) {
            float width = SplashLayout.displayWidth / SplashLayout.colorBitmap.getWidth();
            resRatio = width;
            resRatio = this.saveScale * width;
        } else {
            float height = this.origHeight / SplashLayout.colorBitmap.getHeight();
            resRatio = height;
            resRatio = this.saveScale * height;
        }
        this.drawPaint.setStrokeWidth(this.radius * resRatio);
        this.drawPaint.setMaskFilter(new BlurMaskFilter(resRatio * 15.0f, BlurMaskFilter.Blur.NORMAL));
        this.drawPaint.getShader().setLocalMatrix(this.matrix);
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
        setOnTouchListener(new OnTouchListener() { // from class: com.example.photoareditor.Classs.PolishSplashView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PolishSplashView.this.mScaleDetector.onTouchEvent(motionEvent);
                PolishSplashView.this.pCount2 = motionEvent.getPointerCount();
                PolishSplashView.this.curr = new PointF(motionEvent.getX(), motionEvent.getY());
                PolishSplashView polishSplashView = PolishSplashView.this;
                polishSplashView.x = (polishSplashView.curr.x - PolishSplashView.this.m[2]) / PolishSplashView.this.m[0];
                PolishSplashView polishSplashView2 = PolishSplashView.this;
                polishSplashView2.y = (polishSplashView2.curr.y - PolishSplashView.this.m[5]) / PolishSplashView.this.m[4];
                int action = motionEvent.getAction();
                if (action != 6) {
                    if (action != 0) {
                        if (action == 1) {
                            if (PolishSplashView.this.mode == 1) {
                                PolishSplashView.this.matrix.getValues(PolishSplashView.this.m);
                            }
                            int abs = (int) Math.abs(PolishSplashView.this.curr.y - PolishSplashView.this.start.y);
                            if (((int) Math.abs(PolishSplashView.this.curr.x - PolishSplashView.this.start.x)) < 3 && abs < 3) {
                                PolishSplashView.this.performClick();
                            }
                            if (PolishSplashView.this.draw) {
                                PolishSplashView.this.drawPaint.setStrokeWidth(PolishSplashView.this.radius);
                                PolishSplashView.this.drawPaint.setMaskFilter(new BlurMaskFilter(15.0f, BlurMaskFilter.Blur.NORMAL));
                                PolishSplashView.this.drawPaint.getShader().setLocalMatrix(new Matrix());
                                PolishSplashView.this.canvas.drawPath(PolishSplashView.this.drawPath, PolishSplashView.this.drawPaint);
                            }
                            SplashLayout.vector.add(new FilePath(PolishSplashView.this.pathPoints, PolishSplashView.this.coloring, PolishSplashView.this.radius));
                            PolishSplashView.this.circlePath.reset();
                            PolishSplashView.this.drawPath.reset();
                            PolishSplashView.this.brushPath.reset();
                            PolishSplashView.this.draw = false;
                        } else if (action == 2) {
                            if (PolishSplashView.this.mode == 1 || PolishSplashView.this.mode == 3 || !PolishSplashView.this.draw) {
                                if (PolishSplashView.this.pCount1 == 1 && PolishSplashView.this.pCount2 == 1) {
                                    PolishSplashView.this.matrix.postTranslate(PolishSplashView.this.curr.x - PolishSplashView.this.last.x, PolishSplashView.this.curr.y - PolishSplashView.this.last.y);
                                }
                                PolishSplashView.this.last.set(PolishSplashView.this.curr.x, PolishSplashView.this.curr.y);
                            } else {
                                PolishSplashView.this.circlePath.reset();
                                PolishSplashView.this.circlePath.moveTo(PolishSplashView.this.curr.x, PolishSplashView.this.curr.y);
                                PolishSplashView.this.circlePath.addCircle(PolishSplashView.this.curr.x, PolishSplashView.this.curr.y, (PolishSplashView.this.radius * PolishSplashView.resRatio) / 2.0f, Path.Direction.CW);
                                PolishSplashView.this.pathPoints.add(new PointF(PolishSplashView.this.x, PolishSplashView.this.y));
                                PolishSplashView.this.drawPath.lineTo(PolishSplashView.this.x, PolishSplashView.this.y);
                                PolishSplashView.this.brushPath.lineTo(PolishSplashView.this.curr.x, PolishSplashView.this.curr.y);
                                PolishSplashView.this.showBoxPreview();
                                if ((PolishSplashView.this.curr.x > 0.0f || PolishSplashView.this.curr.y > 0.0f || !PolishSplashView.this.prViewDefaultPosition) && PolishSplashView.this.curr.x <= 0.0f && PolishSplashView.this.curr.y >= PolishSplashView.this.viewHeight && !PolishSplashView.this.prViewDefaultPosition) {
                                    PolishSplashView.this.prViewDefaultPosition = true;
                                } else {
                                    PolishSplashView.this.prViewDefaultPosition = false;
                                }
                            }
                        }
                    } else {
                        PolishSplashView.this.drawPaint.setStrokeWidth(PolishSplashView.this.radius * PolishSplashView.resRatio);
                        PolishSplashView.this.drawPaint.setMaskFilter(new BlurMaskFilter(PolishSplashView.resRatio * 15.0f, BlurMaskFilter.Blur.NORMAL));
                        PolishSplashView.this.drawPaint.getShader().setLocalMatrix(PolishSplashView.this.matrix);
                        PolishSplashView polishSplashView3 = PolishSplashView.this;
                        polishSplashView3.oldX = 0.0f;
                        polishSplashView3.oldY = 0.0f;
                        polishSplashView3.last.set(PolishSplashView.this.curr);
                        PolishSplashView.this.start.set(PolishSplashView.this.last);
                        if (PolishSplashView.this.mode != 1 && PolishSplashView.this.mode != 3) {
                            PolishSplashView.this.draw = true;
                        }
                        PolishSplashView.this.circlePath.reset();
                        PolishSplashView.this.circlePath.moveTo(PolishSplashView.this.curr.x, PolishSplashView.this.curr.y);
                        PolishSplashView.this.circlePath.addCircle(PolishSplashView.this.curr.x, PolishSplashView.this.curr.y, (PolishSplashView.this.radius * PolishSplashView.resRatio) / 2.0f, Path.Direction.CW);
                        PolishSplashView.this.pathPoints = new ArrayList<>();
                        PolishSplashView.this.pathPoints.add(new PointF(PolishSplashView.this.x, PolishSplashView.this.y));
                        PolishSplashView.this.drawPath.moveTo(PolishSplashView.this.x, PolishSplashView.this.y);
                        PolishSplashView.this.brushPath.moveTo(PolishSplashView.this.curr.x, PolishSplashView.this.curr.y);
                    }
                } else if (PolishSplashView.this.mode == 2) {
                    PolishSplashView.this.mode = 0;
                }
                PolishSplashView polishSplashView4 = PolishSplashView.this;
                polishSplashView4.pCount1 = polishSplashView4.pCount2;
                PolishSplashView polishSplashView5 = PolishSplashView.this;
                polishSplashView5.setImageMatrix(polishSplashView5.matrix);
                PolishSplashView.this.invalidate();
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
        float f = i2;
        float f2 = this.origHeight;
        float f3 = this.saveScale;
        float f4 = (f2 * f3) + f;
        if (i2 < 0) {
            float f5 = i;
            float f6 = (this.origWidth * f3) + f5;
            float f7 = this.viewHeight;
            if (f4 > f7) {
                f4 = f7;
            }
            canvas2.clipRect(f5, 0.0f, f6, f4);
        } else {
            float f8 = i;
            float f9 = (this.origWidth * f3) + f8;
            float f10 = this.viewHeight;
            if (f4 > f10) {
                f4 = f10;
            }
            canvas2.clipRect(f8, f, f9, f4);
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
            Log.wtf("OnMeasured Call :", "OnMeasured Call");
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
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            Log.d("bmSize", "bmWidth: " + intrinsicWidth + " bmHeight : " + intrinsicHeight);
            float f = intrinsicWidth;
            float f2 = intrinsicHeight;
            float min = Math.min(this.viewWidth / f, this.viewHeight / f2);
            this.matrix.setScale(min, min);
            float f3 = (this.viewHeight - (f2 * min)) / 2.0f;
            float f4 = (this.viewWidth - (min * f)) / 2.0f;
            this.matrix.postTranslate(f4, f3);
            this.origWidth = this.viewWidth - (f4 * 2.0f);
            this.origHeight = this.viewHeight - (2.0f * f3);
            setImageMatrix(this.matrix);
            this.matrix.getValues(this.m);
            fixTrans();
        }
    }
}
