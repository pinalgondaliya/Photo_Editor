package com.example.photoeditor.Classs;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;

/* loaded from: classes3.dex */
public class Mirror2D_2Layer extends RelativeLayout implements ScaleGestureDetector.OnScaleGestureListener {
    private static final float MAX_ZOOM = 2.0f;
    private static final float MIN_ZOOM = 1.35f;
    private static final String TAG = "DragLayout";
    public float dx;
    public float dy;
    private float lastScaleFactor;
    private Mode mode;
    private float prevDx;
    private float prevDy;
    public float scale;
    private float startX;
    private float startY;

    /* loaded from: classes3.dex */
    public enum Mode {
        NONE,
        DRAG,
        ZOOM
    }

    public Mirror2D_2Layer(Context context) {
        super(context);
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.lastScaleFactor = 1.4f;
        this.mode = Mode.NONE;
        this.prevDx = 0.0f;
        this.prevDy = 0.0f;
        this.scale = 1.36f;
        this.startX = 0.0f;
        this.startY = 0.0f;
        init(context);
    }

    public Mirror2D_2Layer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.lastScaleFactor = 1.4f;
        this.mode = Mode.NONE;
        this.prevDx = 0.0f;
        this.prevDy = 0.0f;
        this.scale = 1.36f;
        this.startX = 0.0f;
        this.startY = 0.0f;
        init(context);
    }

    public Mirror2D_2Layer(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.lastScaleFactor = 1.4f;
        this.mode = Mode.NONE;
        this.prevDx = 0.0f;
        this.prevDy = 0.0f;
        this.scale = 1.36f;
        this.startX = 0.0f;
        this.startY = 0.0f;
        init(context);
    }

    public Mirror2D_2Layer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.lastScaleFactor = 1.4f;
        this.mode = Mode.NONE;
        this.prevDx = 0.0f;
        this.prevDy = 0.0f;
        this.scale = 1.36f;
        this.startX = 0.0f;
        this.startY = 0.0f;
        init(context);
    }

    public void init(Context context, final Mirror2D_2Layer mirror2D_2Layer) {
        final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(new OnTouchListener() { // from class: com.example.photoareditor.Classs.Mirror2D_2Layer.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction() & 255;
                if (action == 0) {
                    Log.i(Mirror2D_2Layer.TAG, "DOWN");
                    if (Mirror2D_2Layer.this.scale > Mirror2D_2Layer.MIN_ZOOM) {
                        Mirror2D_2Layer.this.mode = Mode.DRAG;
                        Mirror2D_2Layer.this.startX = motionEvent.getX() - Mirror2D_2Layer.this.prevDx;
                    }
                } else if (action == 1) {
                    Log.i(Mirror2D_2Layer.TAG, "UP");
                    Mirror2D_2Layer.this.mode = Mode.NONE;
                    Mirror2D_2Layer mirror2D_2Layer2 = Mirror2D_2Layer.this;
                    mirror2D_2Layer2.prevDx = mirror2D_2Layer2.dx;
                } else if (action == 2) {
                    if (Mirror2D_2Layer.this.mode == Mode.DRAG) {
                        Mirror2D_2Layer.this.dx = motionEvent.getX() - Mirror2D_2Layer.this.startX;
                    }
                } else if (action == 5) {
                    Mirror2D_2Layer.this.mode = Mode.ZOOM;
                } else if (action == 6) {
                    Mirror2D_2Layer.this.mode = Mode.DRAG;
                }
                scaleGestureDetector.onTouchEvent(motionEvent);
                if ((Mirror2D_2Layer.this.mode == Mode.DRAG && Mirror2D_2Layer.this.scale >= Mirror2D_2Layer.MIN_ZOOM) || Mirror2D_2Layer.this.mode == Mode.ZOOM) {
                    Mirror2D_2Layer.this.getParent().requestDisallowInterceptTouchEvent(true);
                    float width = ((Mirror2D_2Layer.this.child().getWidth() - (Mirror2D_2Layer.this.child().getWidth() / Mirror2D_2Layer.this.scale)) / 2.0f) * Mirror2D_2Layer.this.scale;
                    float height = ((Mirror2D_2Layer.this.child().getHeight() - (Mirror2D_2Layer.this.child().getHeight() / Mirror2D_2Layer.this.scale)) / 2.0f) * Mirror2D_2Layer.this.scale;
                    Mirror2D_2Layer mirror2D_2Layer22 = Mirror2D_2Layer.this;
                    mirror2D_2Layer22.dx = Math.min(Math.max(mirror2D_2Layer22.dx, -width), width);
                    Mirror2D_2Layer mirror2D_2Layer3 = Mirror2D_2Layer.this;
                    mirror2D_2Layer3.dy = Math.min(Math.max(mirror2D_2Layer3.dy, -height), height);
                    Log.i(Mirror2D_2Layer.TAG, "Width: " + Mirror2D_2Layer.this.child().getWidth() + ", scale " + Mirror2D_2Layer.this.scale + ", dx " + Mirror2D_2Layer.this.dx + ", max " + width);
                    Mirror2D_2Layer.this.applyScaleAndTranslation();
                    mirror2D_2Layer.applyScaleAndTranslation(-Mirror2D_2Layer.this.scale, Mirror2D_2Layer.this.scale, Mirror2D_2Layer.this.dx, -Mirror2D_2Layer.this.dy);
                }
                return true;
            }
        });
    }

    public void init(Context context, final Mirror2D_2Layer mirror2D_2Layer, boolean z) {
        final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(new OnTouchListener() { // from class: com.example.photoareditor.Classs.Mirror2D_2Layer.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction() & 255;
                if (action == 0) {
                    Log.i(Mirror2D_2Layer.TAG, "DOWN");
                    if (Mirror2D_2Layer.this.scale > Mirror2D_2Layer.MIN_ZOOM) {
                        Mirror2D_2Layer.this.mode = Mode.DRAG;
                        Mirror2D_2Layer.this.startY = motionEvent.getY() - Mirror2D_2Layer.this.prevDy;
                    }
                } else if (action == 1) {
                    Log.i(Mirror2D_2Layer.TAG, "UP");
                    Mirror2D_2Layer.this.mode = Mode.NONE;
                    Mirror2D_2Layer mirror2D_2Layer2 = Mirror2D_2Layer.this;
                    mirror2D_2Layer2.prevDy = mirror2D_2Layer2.dy;
                } else if (action == 2) {
                    if (Mirror2D_2Layer.this.mode == Mode.DRAG) {
                        Mirror2D_2Layer.this.dy = motionEvent.getY() - Mirror2D_2Layer.this.startY;
                    }
                } else if (action == 5) {
                    Mirror2D_2Layer.this.mode = Mode.ZOOM;
                } else if (action == 6) {
                    Mirror2D_2Layer.this.mode = Mode.DRAG;
                }
                scaleGestureDetector.onTouchEvent(motionEvent);
                if ((Mirror2D_2Layer.this.mode == Mode.DRAG && Mirror2D_2Layer.this.scale >= Mirror2D_2Layer.MIN_ZOOM) || Mirror2D_2Layer.this.mode == Mode.ZOOM) {
                    Mirror2D_2Layer.this.getParent().requestDisallowInterceptTouchEvent(true);
                    float width = ((Mirror2D_2Layer.this.child().getWidth() - (Mirror2D_2Layer.this.child().getWidth() / Mirror2D_2Layer.this.scale)) / 2.0f) * Mirror2D_2Layer.this.scale;
                    float height = ((Mirror2D_2Layer.this.child().getHeight() - (Mirror2D_2Layer.this.child().getHeight() / Mirror2D_2Layer.this.scale)) / 2.0f) * Mirror2D_2Layer.this.scale;
                    Mirror2D_2Layer mirror2D_2Layer22 = Mirror2D_2Layer.this;
                    mirror2D_2Layer22.dx = Math.min(Math.max(mirror2D_2Layer22.dx, -width), width);
                    Mirror2D_2Layer mirror2D_2Layer3 = Mirror2D_2Layer.this;
                    mirror2D_2Layer3.dy = Math.min(Math.max(mirror2D_2Layer3.dy, -height), height);
                    Log.i(Mirror2D_2Layer.TAG, "Width: " + Mirror2D_2Layer.this.child().getWidth() + ", scale " + Mirror2D_2Layer.this.scale + ", dx " + Mirror2D_2Layer.this.dx + ", max " + width);
                    Mirror2D_2Layer.this.applyScaleAndTranslationVertical();
                    mirror2D_2Layer.applyScaleAndTranslationVertical(Mirror2D_2Layer.this.scale, Mirror2D_2Layer.this.dx, -Mirror2D_2Layer.this.dy);
                }
                return true;
            }
        });
    }

    public void init(Context context, final Mirror2D_2Layer mirror2D_2Layer, Mirror2D_2Layer mirror2D_2Layer2, Mirror2D_2Layer mirror2D_2Layer3) {
        final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(new OnTouchListener() { // from class: com.example.photoareditor.Classs.Mirror2D_2Layer.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction() & 255;
                if (action == 0) {
                    Log.i(Mirror2D_2Layer.TAG, "DOWN");
                    if (Mirror2D_2Layer.this.scale > Mirror2D_2Layer.MIN_ZOOM) {
                        Mirror2D_2Layer.this.mode = Mode.DRAG;
                        Mirror2D_2Layer.this.startX = motionEvent.getX() - Mirror2D_2Layer.this.prevDx;
                    }
                } else if (action == 1) {
                    Log.i(Mirror2D_2Layer.TAG, "UP");
                    Mirror2D_2Layer.this.mode = Mode.NONE;
                    Mirror2D_2Layer mirror2D_2Layer4 = Mirror2D_2Layer.this;
                    mirror2D_2Layer4.prevDx = mirror2D_2Layer4.dx;
                } else if (action == 2) {
                    if (Mirror2D_2Layer.this.mode == Mode.DRAG) {
                        Mirror2D_2Layer.this.dx = motionEvent.getX() - Mirror2D_2Layer.this.startX;
                    }
                } else if (action == 5) {
                    Mirror2D_2Layer.this.mode = Mode.ZOOM;
                } else if (action == 6) {
                    Mirror2D_2Layer.this.mode = Mode.DRAG;
                }
                scaleGestureDetector.onTouchEvent(motionEvent);
                if ((Mirror2D_2Layer.this.mode == Mode.DRAG && Mirror2D_2Layer.this.scale >= Mirror2D_2Layer.MIN_ZOOM) || Mirror2D_2Layer.this.mode == Mode.ZOOM) {
                    Mirror2D_2Layer.this.getParent().requestDisallowInterceptTouchEvent(true);
                    float width = ((Mirror2D_2Layer.this.child().getWidth() - (Mirror2D_2Layer.this.child().getWidth() / Mirror2D_2Layer.this.scale)) / 2.0f) * Mirror2D_2Layer.this.scale;
                    float height = ((Mirror2D_2Layer.this.child().getHeight() - (Mirror2D_2Layer.this.child().getHeight() / Mirror2D_2Layer.this.scale)) / 2.0f) * Mirror2D_2Layer.this.scale;
                    Mirror2D_2Layer mirror2D_2Layer22 = Mirror2D_2Layer.this;
                    mirror2D_2Layer22.dx = Math.min(Math.max(mirror2D_2Layer22.dx, -width), width);
                    Mirror2D_2Layer mirror2D_2Layer32 = Mirror2D_2Layer.this;
                    mirror2D_2Layer32.dy = Math.min(Math.max(mirror2D_2Layer32.dy, -height), height);
                    Log.i(Mirror2D_2Layer.TAG, "Width: " + Mirror2D_2Layer.this.child().getWidth() + ", scale " + Mirror2D_2Layer.this.scale + ", dx " + Mirror2D_2Layer.this.dx + ", max " + width);
                    Mirror2D_2Layer.this.applyScaleAndTranslation();
                    mirror2D_2Layer.applyScaleAndTranslation(-Mirror2D_2Layer.this.scale, Mirror2D_2Layer.this.scale, -Mirror2D_2Layer.this.dx, -Mirror2D_2Layer.this.dy);
                    mirror2D_2Layer.applyScaleAndTranslation(-Mirror2D_2Layer.this.scale, Mirror2D_2Layer.this.scale, -Mirror2D_2Layer.this.dx, Mirror2D_2Layer.this.dy);
                    mirror2D_2Layer22.applyScaleAndTranslation(Mirror2D_2Layer.this.scale, Mirror2D_2Layer.this.scale, Mirror2D_2Layer.this.dx, Mirror2D_2Layer.this.dy);
                    mirror2D_2Layer32.applyScaleAndTranslation(-Mirror2D_2Layer.this.scale, Mirror2D_2Layer.this.scale, -Mirror2D_2Layer.this.dx, Mirror2D_2Layer.this.dy);
                }
                return true;
            }
        });
    }

    public void init(Context context, final Mirror2D_2Layer mirror2D_2Layer, Mirror2D_2Layer mirror2D_2Layer2) {
        final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(new OnTouchListener() { // from class: com.example.photoareditor.Classs.Mirror2D_2Layer.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction() & 255;
                if (action == 0) {
                    Log.i(Mirror2D_2Layer.TAG, "DOWN");
                    if (Mirror2D_2Layer.this.scale > Mirror2D_2Layer.MIN_ZOOM) {
                        Mirror2D_2Layer.this.mode = Mode.DRAG;
                        Mirror2D_2Layer.this.startX = motionEvent.getX() - Mirror2D_2Layer.this.prevDx;
                    }
                } else if (action == 1) {
                    Log.i(Mirror2D_2Layer.TAG, "UP");
                    Mirror2D_2Layer.this.mode = Mode.NONE;
                    Mirror2D_2Layer mirror2D_2Layer3 = Mirror2D_2Layer.this;
                    mirror2D_2Layer3.prevDx = mirror2D_2Layer3.dx;
                } else if (action == 2) {
                    if (Mirror2D_2Layer.this.mode == Mode.DRAG) {
                        Mirror2D_2Layer.this.dx = motionEvent.getX() - Mirror2D_2Layer.this.startX;
                    }
                } else if (action == 5) {
                    Mirror2D_2Layer.this.mode = Mode.ZOOM;
                } else if (action == 6) {
                    Mirror2D_2Layer.this.mode = Mode.DRAG;
                }
                scaleGestureDetector.onTouchEvent(motionEvent);
                if ((Mirror2D_2Layer.this.mode == Mode.DRAG && Mirror2D_2Layer.this.scale >= Mirror2D_2Layer.MIN_ZOOM) || Mirror2D_2Layer.this.mode == Mode.ZOOM) {
                    Mirror2D_2Layer.this.getParent().requestDisallowInterceptTouchEvent(true);
                    float width = ((Mirror2D_2Layer.this.child().getWidth() - (Mirror2D_2Layer.this.child().getWidth() / Mirror2D_2Layer.this.scale)) / 2.0f) * Mirror2D_2Layer.this.scale;
                    float height = ((Mirror2D_2Layer.this.child().getHeight() - (Mirror2D_2Layer.this.child().getHeight() / Mirror2D_2Layer.this.scale)) / 2.0f) * Mirror2D_2Layer.this.scale;
                    Mirror2D_2Layer mirror2D_2Layer22 = Mirror2D_2Layer.this;
                    mirror2D_2Layer22.dx = Math.min(Math.max(mirror2D_2Layer22.dx, -width), width);
                    Mirror2D_2Layer mirror2D_2Layer32 = Mirror2D_2Layer.this;
                    mirror2D_2Layer32.dy = Math.min(Math.max(mirror2D_2Layer32.dy, -height), height);
                    Log.i(Mirror2D_2Layer.TAG, "Width: " + Mirror2D_2Layer.this.child().getWidth() + ", scale " + Mirror2D_2Layer.this.scale + ", dx " + Mirror2D_2Layer.this.dx + ", max " + width);
                    Mirror2D_2Layer.this.applyScaleAndTranslation();
                    mirror2D_2Layer.applyScaleAndTranslation(-Mirror2D_2Layer.this.scale, Mirror2D_2Layer.this.scale, -Mirror2D_2Layer.this.dx, Mirror2D_2Layer.this.dy);
                    mirror2D_2Layer22.applyScaleAndTranslation(Mirror2D_2Layer.this.scale, Mirror2D_2Layer.this.scale, Mirror2D_2Layer.this.dx, Mirror2D_2Layer.this.dy);
                }
                return true;
            }
        });
    }

    public void init(Context context) {
        final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(new OnTouchListener() { // from class: com.example.photoareditor.Classs.Mirror2D_2Layer.5
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction() & 255;
                if (action == 0) {
                    Log.i(Mirror2D_2Layer.TAG, "DOWN");
                    if (Mirror2D_2Layer.this.scale > Mirror2D_2Layer.MIN_ZOOM) {
                        Mirror2D_2Layer.this.mode = Mode.DRAG;
                        Mirror2D_2Layer.this.startX = motionEvent.getX() - Mirror2D_2Layer.this.prevDx;
                        Mirror2D_2Layer.this.startY = motionEvent.getY() - Mirror2D_2Layer.this.prevDy;
                    }
                } else if (action == 1) {
                    Log.i(Mirror2D_2Layer.TAG, "UP");
                    Mirror2D_2Layer.this.mode = Mode.NONE;
                    Mirror2D_2Layer mirror2D_2Layer = Mirror2D_2Layer.this;
                    mirror2D_2Layer.prevDx = mirror2D_2Layer.dx;
                    Mirror2D_2Layer mirror2D_2Layer2 = Mirror2D_2Layer.this;
                    mirror2D_2Layer2.prevDy = mirror2D_2Layer2.dy;
                } else if (action == 2) {
                    if (Mirror2D_2Layer.this.mode == Mode.DRAG) {
                        Mirror2D_2Layer.this.dx = motionEvent.getX() - Mirror2D_2Layer.this.startX;
                        Mirror2D_2Layer.this.dy = motionEvent.getY() - Mirror2D_2Layer.this.startY;
                    }
                } else if (action == 5) {
                    Mirror2D_2Layer.this.mode = Mode.ZOOM;
                } else if (action == 6) {
                    Mirror2D_2Layer.this.mode = Mode.DRAG;
                }
                scaleGestureDetector.onTouchEvent(motionEvent);
                if ((Mirror2D_2Layer.this.mode == Mode.DRAG && Mirror2D_2Layer.this.scale >= Mirror2D_2Layer.MIN_ZOOM) || Mirror2D_2Layer.this.mode == Mode.ZOOM) {
                    Mirror2D_2Layer.this.getParent().requestDisallowInterceptTouchEvent(true);
                    float width = ((Mirror2D_2Layer.this.child().getWidth() - (Mirror2D_2Layer.this.child().getWidth() / Mirror2D_2Layer.this.scale)) / 2.0f) * Mirror2D_2Layer.this.scale;
                    float height = ((Mirror2D_2Layer.this.child().getHeight() - (Mirror2D_2Layer.this.child().getHeight() / Mirror2D_2Layer.this.scale)) / 2.0f) * Mirror2D_2Layer.this.scale;
                    Mirror2D_2Layer mirror2D_2Layer3 = Mirror2D_2Layer.this;
                    mirror2D_2Layer3.dx = Math.min(Math.max(mirror2D_2Layer3.dx, -width), width);
                    Mirror2D_2Layer mirror2D_2Layer4 = Mirror2D_2Layer.this;
                    mirror2D_2Layer4.dy = Math.min(Math.max(mirror2D_2Layer4.dy, -height), height);
                    Log.i(Mirror2D_2Layer.TAG, "Width: " + Mirror2D_2Layer.this.child().getWidth() + ", scale " + Mirror2D_2Layer.this.scale + ", dx " + Mirror2D_2Layer.this.dx + ", max " + width);
                    Mirror2D_2Layer.this.applyScaleAndTranslation();
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
            this.scale = Math.max((float) MIN_ZOOM, Math.min(f, 2.0f));
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

    public void applyScaleAndTranslation() {
        child().setScaleX(this.scale);
        child().setScaleY(this.scale);
        child().setTranslationX(this.dx);
        child().setTranslationY(this.dy);
    }

    public void applyScaleAndTranslationVertical() {
        child().setScaleX(this.scale);
        child().setScaleY(this.scale);
        child().setTranslationX(this.dx);
        child().setTranslationY(this.dy);
    }

    public void applyScaleAndTranslation(float f, float f2, float f3, float f4) {
        child().setScaleX(f);
        child().setScaleY(f2);
        child().setTranslationX(f3);
        child().setTranslationY(f4);
    }

    public void applyScaleAndTranslationVertical(float f, float f2, float f3) {
        child().setScaleX(-f);
        child().setScaleY(f);
        child().setTranslationX(f2);
        child().setTranslationY(f3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View child() {
        return getChildAt(0);
    }
}
