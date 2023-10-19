package com.example.photoeditor.Classs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.core.content.ContextCompat;

import com.example.photoeditor.Interface.OnSaveBitmap;
import com.example.photoeditor.R;

import java.util.ArrayList;
import java.util.List;
import org.wysaid.view.ImageGLSurfaceView;

/* loaded from: classes3.dex */
public class PolishView extends PolishStickerView implements ScaleGestureDetector.OnScaleGestureListener {
    private static final float MAX_ZOOM = 4.0f;
    private static final float MIN_ZOOM = 1.0f;
    private static final String TAG = "ZoomLayout";
    private List<Bitmap> bitmaplist;
    private BrushDrawingView brushDrawingView;
    private Bitmap currentBitmap;
    private float dx;
    private float dy;
    private FilterImageView filterImageView;
    private boolean firstTouch;
    public ImageGLSurfaceView imageGLSurfaceView;
    private int index;
    private float lastScaleFactor;
    private Mode mode;
    private float prevDx;
    private float prevDy;
    private boolean restore;
    private float scale;
    private float startX;
    private float startY;
    private long time;

    /* loaded from: classes3.dex */
    public enum Mode {
        NONE,
        DRAG,
        ZOOM
    }

    static float access$332(PolishView polishView, float f) {
        float f2 = polishView.scale * f;
        polishView.scale = f2;
        return f2;
    }

    public PolishView(Context context) {
        super(context);
        this.bitmaplist = new ArrayList();
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.firstTouch = false;
        this.index = -1;
        this.lastScaleFactor = 1.8f;
        this.mode = Mode.NONE;
        this.prevDx = 0.0f;
        this.prevDy = 0.0f;
        this.restore = false;
        this.scale = 1.0f;
        this.startX = 0.0f;
        this.startY = 0.0f;
        this.time = System.currentTimeMillis();
        init(context);
    }

    public PolishView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.bitmaplist = new ArrayList();
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.firstTouch = false;
        this.index = -1;
        this.lastScaleFactor = 1.8f;
        this.mode = Mode.NONE;
        this.prevDx = 0.0f;
        this.prevDy = 0.0f;
        this.restore = false;
        this.scale = 1.0f;
        this.startX = 0.0f;
        this.startY = 0.0f;
        this.time = System.currentTimeMillis();
        init(attributeSet);
    }

    public PolishView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.bitmaplist = new ArrayList();
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.firstTouch = false;
        this.index = -1;
        this.lastScaleFactor = 1.8f;
        this.mode = Mode.NONE;
        this.prevDx = 0.0f;
        this.prevDy = 0.0f;
        this.restore = false;
        this.scale = 1.0f;
        this.startX = 0.0f;
        this.startY = 0.0f;
        this.time = System.currentTimeMillis();
        init(attributeSet);
    }

    @SuppressLint("ResourceType")
    private void init(AttributeSet attributeSet) {
        FilterImageView filterImageView2 = new FilterImageView(getContext());
        this.filterImageView = filterImageView2;
        filterImageView2.setId(1);
        this.filterImageView.setAdjustViewBounds(true);
        this.filterImageView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.BackgroundCardColor));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(13, -1);
        BrushDrawingView brushDrawingView2 = new BrushDrawingView(getContext());
        this.brushDrawingView = brushDrawingView2;
        brushDrawingView2.setVisibility(8);
        this.brushDrawingView.setId(2);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams2.addRule(13, -1);
        layoutParams2.addRule(6, 1);
        layoutParams2.addRule(8, 1);
        ImageGLSurfaceView imageGLSurfaceView2 = new ImageGLSurfaceView(getContext(), attributeSet);
        this.imageGLSurfaceView = imageGLSurfaceView2;
        imageGLSurfaceView2.setId(3);
        this.imageGLSurfaceView.setVisibility(0);
        this.imageGLSurfaceView.setAlpha(1.0f);
        this.imageGLSurfaceView.setDisplayMode(ImageGLSurfaceView.DisplayMode.DISPLAY_ASPECT_FIT);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams3.addRule(13, -1);
        layoutParams3.addRule(6, 1);
        layoutParams3.addRule(8, 1);
        addView(this.filterImageView, layoutParams);
        addView(this.imageGLSurfaceView, layoutParams3);
        addView(this.brushDrawingView, layoutParams2);
    }

    private void init(Context context) {
        final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(new View.OnTouchListener() { // from class: com.example.photoareditor.Classs.PolishView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction() & 255;
                if (action == 0) {
                    if (!PolishView.this.firstTouch || System.currentTimeMillis() - PolishView.this.time > 300) {
                        if (PolishView.this.scale > 1.0f) {
                            PolishView.this.mode = Mode.DRAG;
                            PolishView.this.startX = motionEvent.getX() - PolishView.this.prevDx;
                            PolishView.this.startY = motionEvent.getY() - PolishView.this.prevDy;
                        }
                        PolishView.this.firstTouch = true;
                        PolishView.this.time = System.currentTimeMillis();
                    } else {
                        if (PolishView.this.restore) {
                            PolishView.this.scale = 1.0f;
                            PolishView.this.restore = false;
                        } else {
                            PolishView.access$332(PolishView.this, 2.0f);
                            PolishView.this.restore = true;
                        }
                        PolishView.this.mode = Mode.ZOOM;
                        PolishView.this.firstTouch = false;
                    }
                } else if (action == 1) {
                    Log.i(PolishView.TAG, "UP");
                    PolishView.this.mode = Mode.NONE;
                    PolishView polishView = PolishView.this;
                    polishView.prevDx = polishView.dx;
                    PolishView polishView2 = PolishView.this;
                    polishView2.prevDy = polishView2.dy;
                } else if (action == 2) {
                    if (PolishView.this.mode == Mode.DRAG) {
                        PolishView.this.dx = motionEvent.getX() - PolishView.this.startX;
                        PolishView.this.dy = motionEvent.getY() - PolishView.this.startY;
                    }
                } else if (action == 5) {
                    PolishView.this.mode = Mode.ZOOM;
                } else if (action == 6) {
                    PolishView.this.mode = Mode.NONE;
                }
                scaleGestureDetector.onTouchEvent(motionEvent);
                if ((PolishView.this.mode == Mode.DRAG && PolishView.this.scale >= 1.0f) || PolishView.this.mode == Mode.ZOOM) {
                    PolishView.this.getParent().requestDisallowInterceptTouchEvent(true);
                    float width = ((PolishView.this.child().getWidth() - (PolishView.this.child().getWidth() / PolishView.this.scale)) / 2.0f) * PolishView.this.scale;
                    float height = ((PolishView.this.child().getHeight() - (PolishView.this.child().getHeight() / PolishView.this.scale)) / 2.0f) * PolishView.this.scale;
                    PolishView polishView3 = PolishView.this;
                    polishView3.dx = Math.min(Math.max(polishView3.dx, -width), width);
                    PolishView polishView4 = PolishView.this;
                    polishView4.dy = Math.min(Math.max(polishView4.dy, -height), height);
                    Log.i(PolishView.TAG, "Width: " + PolishView.this.child().getWidth() + ", scale " + PolishView.this.scale + ", dx " + PolishView.this.dx + ", max " + width);
                    PolishView.this.applyScaleAndTranslation();
                }
                return true;
            }
        });
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        Log.i(TAG, "onScaleBegin");
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        Log.i(TAG, "onScale" + scaleFactor);
        if (this.lastScaleFactor == 0.0f || Math.signum(scaleFactor) == Math.signum(this.lastScaleFactor)) {
            float f = this.scale * scaleFactor;
            this.scale = f;
            this.scale = Math.max(1.0f, Math.min(f, (float) MAX_ZOOM));
            this.lastScaleFactor = scaleFactor;
            return true;
        }
        this.lastScaleFactor = 0.0f;
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        Log.i(TAG, "onScaleEnd");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyScaleAndTranslation() {
        child().setScaleX(this.scale);
        child().setScaleY(this.scale);
        child().setTranslationX(this.dx);
        child().setTranslationY(this.dy);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View child() {
        return getChildAt(0);
    }

    public void setImageSource(final Bitmap bitmap) {
        this.filterImageView.setImageBitmap(bitmap);
        if (this.imageGLSurfaceView.getImageHandler() != null) {
            this.imageGLSurfaceView.setImageBitmap(bitmap);
        } else {
            this.imageGLSurfaceView.setSurfaceCreatedCallback(new ImageGLSurfaceView.OnSurfaceCreatedCallback() { // from class: com.example.photoareditor.Classs.PolishView.2
                @Override // org.wysaid.view.ImageGLSurfaceView.OnSurfaceCreatedCallback
                public void surfaceCreated() {
                    PolishView.this.imageGLSurfaceView.setImageBitmap(bitmap);
                }
            });
        }
        this.currentBitmap = bitmap;
        this.bitmaplist.add(bitmap);
        this.index++;
    }

    public void setImageSourceUndoRedo(final Bitmap bitmap) {
        this.filterImageView.setImageBitmap(bitmap);
        if (this.imageGLSurfaceView.getImageHandler() != null) {
            this.imageGLSurfaceView.setImageBitmap(bitmap);
        } else {
            this.imageGLSurfaceView.setSurfaceCreatedCallback(new ImageGLSurfaceView.OnSurfaceCreatedCallback() { // from class: com.example.photoareditor.Classs.PolishView.3
                @Override // org.wysaid.view.ImageGLSurfaceView.OnSurfaceCreatedCallback
                public void surfaceCreated() {
                    PolishView.this.imageGLSurfaceView.setImageBitmap(bitmap);
                }
            });
        }
        this.currentBitmap = bitmap;
    }

    public void setImageSource(Bitmap bitmap, ImageGLSurfaceView.OnSurfaceCreatedCallback onSurfaceCreatedCallback) {
        this.filterImageView.setImageBitmap(bitmap);
        if (this.imageGLSurfaceView.getImageHandler() != null) {
            this.imageGLSurfaceView.setImageBitmap(bitmap);
        } else {
            this.imageGLSurfaceView.setSurfaceCreatedCallback(onSurfaceCreatedCallback);
        }
        this.currentBitmap = bitmap;
    }

    public boolean undo() {
        Log.d("TAG", "undo: " + this.index);
        int i = this.index;
        if (i <= 0) {
            return false;
        }
        List<Bitmap> list = this.bitmaplist;
        int i2 = i - 1;
        this.index = i2;
        setImageSourceUndoRedo(list.get(i2));
        return true;
    }

    public boolean redo() {
        Log.d("TAG", "redo: " + this.index);
        if (this.index + 1 >= this.bitmaplist.size()) {
            return false;
        }
        List<Bitmap> list = this.bitmaplist;
        int i = this.index + 1;
        this.index = i;
        setImageSourceUndoRedo(list.get(i));
        return true;
    }

    public Bitmap getCurrentBitmap() {
        return this.currentBitmap;
    }

    public BrushDrawingView getBrushDrawingView() {
        return this.brushDrawingView;
    }

    public ImageGLSurfaceView getGLSurfaceView() {
        return this.imageGLSurfaceView;
    }

    public void saveGLSurfaceViewAsBitmap(final OnSaveBitmap onSaveBitmap) {
        if (this.imageGLSurfaceView.getVisibility() == View.VISIBLE) {
            this.imageGLSurfaceView.getResultBitmap(new ImageGLSurfaceView.QueryResultBitmapCallback() { // from class: com.example.photoareditor.Classs.PolishView.4
                @Override // org.wysaid.view.ImageGLSurfaceView.QueryResultBitmapCallback
                public void get(Bitmap bitmap) {
                    onSaveBitmap.onBitmapReady(bitmap);
                }
            });
        }
    }

    public void setFilterEffect(String str) {
        this.imageGLSurfaceView.setFilterWithConfig(str);
    }

    public void setFilterIntensity(float f) {
        this.imageGLSurfaceView.setFilterIntensity(f);
    }
}
