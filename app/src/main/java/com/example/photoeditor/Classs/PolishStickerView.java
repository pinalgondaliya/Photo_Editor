package com.example.photoeditor.Classs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.example.photoeditor.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolishStickerView extends RelativeLayout {
    private static final String TAG = "QuShotStickerView";
    private final float[] bitmapPoints;
    private final Paint borderPaint;
    private final Paint borderPaintRed;
    private final float[] bounds;
    private boolean bringToFrontCurrentSticker;
    private int circleRadius;
    private boolean constrained;
    private final PointF currentCenterPoint;
    private PolishStickerIcons currentIcon;
    private int currentMode;
    private float currentMoveingX;
    private float currentMoveingY;
    private final Matrix downMatrix;
    private float downX;
    private float downY;
    private boolean drawCirclePoint;
    private Sticker handlingSticker;
    private final List<PolishStickerIcons> icons;
    private long lastClickTime;
    private Sticker lastHandlingSticker;
    private final Paint linePaint;
    private boolean locked;
    private PointF midPoint;
    private int minClickDelayTime;
    private final Matrix moveMatrix;
    private float oldDistance;
    private float oldRotation;
    private boolean onMoving;
    private OnStickerOperationListener onStickerOperationListener;
    private Paint paintCircle;
    private final float[] point;
    private boolean showBorder;
    private boolean showIcons;
    private final Matrix sizeMatrix;
    private final RectF stickerRect;
    private final List<Sticker> stickers;
    private final float[] tmp;
    private int touchSlop;

    public interface OnStickerOperationListener {
        void onAddSticker(Sticker sticker);

        void onStickerDeleted(Sticker sticker);

        void onStickerDoubleTap(Sticker sticker);

        void onStickerDrag(Sticker sticker);

        void onStickerFlip(Sticker sticker);

        void onStickerSelected(Sticker sticker);

        void onStickerTouchOutside();

        void onStickerTouchedDown(Sticker sticker);

        void onStickerZoom(Sticker sticker);

        void onTouchDownBeauty(float f, float f2);

        void onTouchDragBeauty(float f, float f2);

        void onTouchUpBeauty(float f, float f2);
    }

    public PolishStickerView(Context context) {
        this(context, null);
    }

    public PolishStickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PolishStickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.stickers = new ArrayList();
        this.icons = new ArrayList(4);
        Paint paint = new Paint();
        this.borderPaint = paint;
        Paint paint2 = new Paint();
        this.borderPaintRed = paint2;
        this.linePaint = new Paint();
        this.stickerRect = new RectF();
        this.sizeMatrix = new Matrix();
        this.downMatrix = new Matrix();
        this.moveMatrix = new Matrix();
        this.bitmapPoints = new float[8];
        this.bounds = new float[8];
        this.point = new float[2];
        this.currentCenterPoint = new PointF();
        this.tmp = new float[2];
        this.midPoint = new PointF();
        this.drawCirclePoint = false;
        this.onMoving = false;
        this.oldDistance = 0.0f;
        this.oldRotation = 0.0f;
        this.currentMode = 0;
        this.lastClickTime = 0;
        this.minClickDelayTime = 200;
        Paint paint3 = new Paint();
        this.paintCircle = paint3;
        paint3.setAntiAlias(true);
        this.paintCircle.setDither(true);
        this.paintCircle.setColor(getContext().getResources().getColor(R.color.colorAccent));
        this.paintCircle.setStrokeWidth((float) SystemUtil.dpToPx(getContext(), 2));
        this.paintCircle.setStyle(Paint.Style.STROKE);
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        try {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{R.attr.borderAlpha, R.attr.borderColor, R.attr.bringToFrontCurrentSticker, R.attr.showBorder, R.attr.showIcons});
            try {
                this.showIcons = obtainStyledAttributes.getBoolean(4, false);
                this.showBorder = obtainStyledAttributes.getBoolean(3, false);
                this.bringToFrontCurrentSticker = obtainStyledAttributes.getBoolean(2, false);
                paint.setAntiAlias(true);
                paint.setColor(obtainStyledAttributes.getColor(1, Color.parseColor("#000000")));
                paint.setAlpha(obtainStyledAttributes.getInteger(0, 255));
                paint2.setAntiAlias(true);
                paint2.setColor(obtainStyledAttributes.getColor(1, Color.parseColor("#000000")));
                paint2.setAlpha(obtainStyledAttributes.getInteger(0, 255));
                getStickerIcons();
            } finally {
                if (obtainStyledAttributes != null) {
                    obtainStyledAttributes.recycle();
                }
            }
        } catch (Throwable th) {
        }
    }

    public PolishStickerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.stickers = new ArrayList();
        this.icons = new ArrayList(4);
        this.borderPaint = new Paint();
        this.borderPaintRed = new Paint();
        this.linePaint = new Paint();
        this.stickerRect = new RectF();
        this.sizeMatrix = new Matrix();
        this.downMatrix = new Matrix();
        this.moveMatrix = new Matrix();
        this.bitmapPoints = new float[8];
        this.bounds = new float[8];
        this.point = new float[2];
        this.currentCenterPoint = new PointF();
        this.tmp = new float[2];
        this.midPoint = new PointF();
        this.drawCirclePoint = false;
        this.onMoving = false;
        this.oldDistance = 0.0f;
        this.oldRotation = 0.0f;
        this.currentMode = 0;
        this.lastClickTime = 0;
        this.minClickDelayTime = 200;
    }

    public Matrix getSizeMatrix() {
        return this.sizeMatrix;
    }

    public Matrix getDownMatrix() {
        return this.downMatrix;
    }

    public Matrix getMoveMatrix() {
        return this.moveMatrix;
    }

    public List<Sticker> getStickers() {
        return this.stickers;
    }

    public void getStickerIcons() {
        PolishStickerIcons polishStickerIcons = new PolishStickerIcons(ContextCompat.getDrawable(getContext(), R.drawable.ic_outline_close), 0, "DELETE");
        polishStickerIcons.setIconEvent(new DeleteIconEvent());
        PolishStickerIcons polishStickerIcons2 = new PolishStickerIcons(ContextCompat.getDrawable(getContext(), R.drawable.ic_outline_scale), 3, PolishStickerIcons.SCALE);
        polishStickerIcons2.setIconEvent(new ZoomIconEvent());
        PolishStickerIcons polishStickerIcons3 = new PolishStickerIcons(ContextCompat.getDrawable(getContext(), R.drawable.ic_outline_flip), 1, PolishStickerIcons.FLIP);
        polishStickerIcons3.setIconEvent(new FlipHorizontallyEvent());
        PolishStickerIcons polishStickerIcons4 = new PolishStickerIcons(ContextCompat.getDrawable(getContext(), R.drawable.ic_outline_edit), 2, PolishStickerIcons.EDIT);
        polishStickerIcons4.setIconEvent(new FlipHorizontallyEvent());
        this.icons.clear();
        this.icons.add(polishStickerIcons);
        this.icons.add(polishStickerIcons2);
        this.icons.add(polishStickerIcons3);
        this.icons.add(polishStickerIcons4);
    }

    public void setHandlingSticker(Sticker sticker) {
        this.lastHandlingSticker = this.handlingSticker;
        this.handlingSticker = sticker;
        invalidate();
    }

    public void showLastHandlingSticker() {
        Sticker sticker = this.lastHandlingSticker;
        if (sticker != null && !sticker.isShow()) {
            this.lastHandlingSticker.setShow(true);
            invalidate();
        }
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            this.stickerRect.left = (float) i;
            this.stickerRect.top = (float) i2;
            this.stickerRect.right = (float) i3;
            this.stickerRect.bottom = (float) i4;
        }
    }

    public void setCircleRadius(int i) {
        this.circleRadius = i;
    }

    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.drawCirclePoint && this.onMoving) {
            canvas.drawCircle(this.downX, this.downY, (float) this.circleRadius, this.paintCircle);
            canvas.drawLine(this.downX, this.downY, this.currentMoveingX, this.currentMoveingY, this.paintCircle);
        }
        drawStickers(canvas);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00fe, code lost:
        if (r12.getTag().equals(com.example.photoareditor.Classs.PolishStickerIcons.ROTATE) == false) goto L_0x0103;
     */
    public void drawStickers(Canvas canvas) {
        float f7;
        float f8;
        float f6;
        float f5;
        Canvas canvas2;
        Sticker sticker2;
        float[] fArr;
        float f62;
        float f2;
        float f3;
        float f4;
        float f42;
        Sticker sticker22;
        float[] fArr2;
        float f82;
        float f72;
        for (int i = 0; i < this.stickers.size(); i++) {
            Sticker sticker = this.stickers.get(i);
            if (sticker != null && sticker.isShow()) {
                sticker.draw(canvas);
            }
        }
        Sticker sticker23 = this.handlingSticker;
        if (sticker23 != null && !this.locked) {
            if (this.showBorder || this.showIcons) {
                getStickerPoints(sticker23, this.bitmapPoints);
                float[] fArr3 = this.bitmapPoints;
                float f52 = fArr3[0];
                float f63 = fArr3[1];
                float f73 = fArr3[2];
                float f83 = fArr3[3];
                float f9 = fArr3[4];
                float f10 = fArr3[5];
                float f11 = fArr3[6];
                float f12 = fArr3[7];
                if (this.showBorder) {
                    f8 = f83;
                    canvas.drawLine(f52, f63, f73, f83, this.borderPaint);
                    canvas.drawLine(f52, f63, f9, f10, this.borderPaint);
                    fArr = fArr3;
                    sticker2 = sticker23;
                    canvas2 = canvas;
                    canvas.drawLine(f73, f8, f11, f12, this.borderPaint);
                    f3 = f11;
                    f5 = f52;
                    f6 = f63;
                    f62 = f9;
                    f7 = f73;
                    canvas.drawLine(f3, f12, f62, f10, this.borderPaint);
                    f2 = f10;
                    f4 = f12;
                } else {
                    f8 = f83;
                    f7 = f73;
                    fArr = fArr3;
                    f5 = f52;
                    sticker2 = sticker23;
                    f6 = f63;
                    canvas2 = canvas;
                    f4 = f12;
                    f3 = f11;
                    f2 = f10;
                    f62 = f9;
                }
                if (this.showIcons) {
                    float calculateRotation = calculateRotation(f3, f4, f62, f2);
                    int i4 = 0;
                    int i2 = 1;
                    int i3 = 2;
                    while (i4 < this.icons.size()) {
                        PolishStickerIcons polishStickerIcons = this.icons.get(i4);
                        int position = polishStickerIcons.getPosition();
                        if (position != 0) {
                            if (position == i2) {
                                fArr2 = fArr;
                                sticker22 = sticker2;
                                if ((!(this.handlingSticker instanceof PolishTextView) || !polishStickerIcons.getTag().equals(PolishStickerIcons.EDIT)) && (!(this.handlingSticker instanceof DrawableSticker) || !polishStickerIcons.getTag().equals(PolishStickerIcons.FLIP))) {
                                    f82 = f8;
                                    f72 = f7;
                                } else {
                                    f82 = f8;
                                    f72 = f7;
                                    configIconMatrix(polishStickerIcons, f72, f82, calculateRotation);
                                    polishStickerIcons.draw(canvas2, this.borderPaint);
                                }
                            } else if (position != i3) {
                                fArr2 = fArr;
                                if (position == 3) {
                                    if (this.handlingSticker instanceof PolishTextView) {
                                        sticker22 = sticker2;
                                    } else {
                                        sticker22 = sticker2;
                                    }
                                    if (!(this.handlingSticker instanceof DrawableSticker) || !polishStickerIcons.getTag().equals(PolishStickerIcons.SCALE)) {
                                        Sticker sticker3 = this.handlingSticker;
                                        if (sticker3 instanceof PolishSticker) {
                                            PolishSticker polishSticker = (PolishSticker) sticker3;
                                            if (polishSticker.getType() != i2) {
                                                if (!(polishSticker.getType() == 2 || polishSticker.getType() == 8)) {
                                                    polishSticker.getType();
                                                }
                                                configIconMatrix(polishStickerIcons, f3, f4, calculateRotation);
                                                polishStickerIcons.draw(canvas2, this.borderPaint);
                                            } else {
                                                configIconMatrix(polishStickerIcons, f3, f4, calculateRotation);
                                                polishStickerIcons.draw(canvas2, this.borderPaint);
                                            }
                                        }
                                        f82 = f8;
                                        f72 = f7;
                                    }
                                    configIconMatrix(polishStickerIcons, f3, f4, calculateRotation);
                                    polishStickerIcons.draw(canvas2, this.borderPaint);
                                    f82 = f8;
                                    f72 = f7;
                                } else {
                                    sticker22 = sticker2;
                                    f82 = f8;
                                    f72 = f7;
                                }
                            } else {
                                fArr2 = fArr;
                                sticker22 = sticker2;
                                f82 = f8;
                                f72 = f7;
                            }
                            Sticker sticker4 = this.handlingSticker;
                            f42 = f4;
                            if (!(sticker4 instanceof PolishSticker)) {
                                configIconMatrix(polishStickerIcons, f62, f2, calculateRotation);
                                polishStickerIcons.draw(canvas2, this.borderPaint);
                            } else if (((PolishSticker) sticker4).getType() == 0) {
                                configIconMatrix(polishStickerIcons, f62, f2, calculateRotation);
                                polishStickerIcons.draw(canvas2, this.borderPaint);
                            }
                        } else {
                            fArr2 = fArr;
                            sticker22 = sticker2;
                            f82 = f8;
                            f72 = f7;
                            f42 = f4;
                            configIconMatrix(polishStickerIcons, f5, f6, calculateRotation);
                            polishStickerIcons.draw(canvas2, this.borderPaintRed);
                        }
                        i4++;
                        i2 = 1;
                        i3 = 2;
                        f7 = f72;
                        fArr = fArr2;
                        f4 = f42;
                        f8 = f82;
                        sticker2 = sticker22;
                    }
                }
            }
        }
        invalidate();
    }

    public void configIconMatrix(PolishStickerIcons polishStickerIcons, float f, float f2, float f3) {
        polishStickerIcons.setX(f);
        polishStickerIcons.setY(f2);
        polishStickerIcons.getMatrix().reset();
        polishStickerIcons.getMatrix().postRotate(f3, (float) (polishStickerIcons.getWidth() / 2), (float) (polishStickerIcons.getHeight() / 2));
        polishStickerIcons.getMatrix().postTranslate(f - ((float) (polishStickerIcons.getWidth() / 2)), f2 - ((float) (polishStickerIcons.getHeight() / 2)));
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.locked) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        if (motionEvent.getAction() != 0) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        this.downX = motionEvent.getX();
        this.downY = motionEvent.getY();
        return (findCurrentIconTouched() == null && findHandlingSticker() == null) ? false : true;
    }

    public void setDrawCirclePoint(boolean z) {
        this.drawCirclePoint = z;
        this.onMoving = false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Sticker sticker;
        OnStickerOperationListener onStickerOperationListener2;
        if (this.locked) {
            return super.onTouchEvent(motionEvent);
        }
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                onTouchUp(motionEvent);
            } else if (actionMasked != 2) {
                if (actionMasked == 5) {
                    this.oldDistance = calculateDistance(motionEvent);
                    this.oldRotation = calculateRotation(motionEvent);
                    this.midPoint = calculateMidPoint(motionEvent);
                    Sticker sticker2 = this.handlingSticker;
                    if (sticker2 != null && isInStickerArea(sticker2, motionEvent.getX(1), motionEvent.getY(1)) && findCurrentIconTouched() == null) {
                        this.currentMode = 2;
                    }
                }
                if (!(this.currentMode != 2 || (sticker = this.handlingSticker) == null || (onStickerOperationListener2 = this.onStickerOperationListener) == null)) {
                    onStickerOperationListener2.onStickerZoom(sticker);
                }
                this.currentMode = 0;
            } else {
                handleCurrentMode(motionEvent);
                invalidate();
            }
        } else if (!onTouchDown(motionEvent)) {
            OnStickerOperationListener onStickerOperationListener3 = this.onStickerOperationListener;
            if (onStickerOperationListener3 == null) {
                return false;
            }
            onStickerOperationListener3.onStickerTouchOutside();
            invalidate();
            if (!this.drawCirclePoint) {
                return false;
            }
        }
        return true;
    }

    public boolean onTouchDown(MotionEvent motionEvent) {
        this.currentMode = 1;
        this.downX = motionEvent.getX();
        this.downY = motionEvent.getY();
        this.onMoving = true;
        this.currentMoveingX = motionEvent.getX();
        this.currentMoveingY = motionEvent.getY();
        PointF calculateMidPoint = calculateMidPoint();
        this.midPoint = calculateMidPoint;
        this.oldDistance = calculateDistance(calculateMidPoint.x, this.midPoint.y, this.downX, this.downY);
        this.oldRotation = calculateRotation(this.midPoint.x, this.midPoint.y, this.downX, this.downY);
        PolishStickerIcons findCurrentIconTouched = findCurrentIconTouched();
        this.currentIcon = findCurrentIconTouched;
        if (findCurrentIconTouched != null) {
            this.currentMode = 3;
            findCurrentIconTouched.onActionDown(this, motionEvent);
        } else {
            this.handlingSticker = findHandlingSticker();
        }
        Sticker sticker = this.handlingSticker;
        if (sticker != null) {
            this.downMatrix.set(sticker.getMatrix());
            if (this.bringToFrontCurrentSticker) {
                this.stickers.remove(this.handlingSticker);
                this.stickers.add(this.handlingSticker);
            }
            OnStickerOperationListener onStickerOperationListener2 = this.onStickerOperationListener;
            if (onStickerOperationListener2 != null) {
                onStickerOperationListener2.onStickerTouchedDown(this.handlingSticker);
            }
        }
        if (this.drawCirclePoint) {
            this.onStickerOperationListener.onTouchDownBeauty(this.currentMoveingX, this.currentMoveingY);
            invalidate();
            return true;
        } else if (this.currentIcon == null && this.handlingSticker == null) {
            return false;
        } else {
            invalidate();
            return true;
        }
    }

    public void onTouchUp(MotionEvent motionEvent) {
        Sticker sticker;
        OnStickerOperationListener onStickerOperationListener2;
        Sticker sticker2;
        OnStickerOperationListener onStickerOperationListener3;
        PolishStickerIcons polishStickerIcons;
        long uptimeMillis = SystemClock.uptimeMillis();
        this.onMoving = false;
        if (this.drawCirclePoint) {
            this.onStickerOperationListener.onTouchUpBeauty(motionEvent.getX(), motionEvent.getY());
        }
        if (!(this.currentMode != 3 || (polishStickerIcons = this.currentIcon) == null || this.handlingSticker == null)) {
            polishStickerIcons.onActionUp(this, motionEvent);
        }
        if (this.currentMode == 1 && Math.abs(motionEvent.getX() - this.downX) < ((float) this.touchSlop) && Math.abs(motionEvent.getY() - this.downY) < ((float) this.touchSlop) && (sticker2 = this.handlingSticker) != null) {
            this.currentMode = 4;
            OnStickerOperationListener onStickerOperationListener4 = this.onStickerOperationListener;
            if (onStickerOperationListener4 != null) {
                onStickerOperationListener4.onStickerSelected(sticker2);
            }
            if (uptimeMillis - this.lastClickTime < ((long) this.minClickDelayTime) && (onStickerOperationListener3 = this.onStickerOperationListener) != null) {
                onStickerOperationListener3.onStickerDoubleTap(this.handlingSticker);
            }
        }
        if (!(this.currentMode != 1 || (sticker = this.handlingSticker) == null || (onStickerOperationListener2 = this.onStickerOperationListener) == null)) {
            onStickerOperationListener2.onStickerDrag(sticker);
        }
        this.currentMode = 0;
        this.lastClickTime = uptimeMillis;
    }

    public void handleCurrentMode(MotionEvent motionEvent) {
        PolishStickerIcons polishStickerIcons;
        int i = this.currentMode;
        if (i == 1) {
            this.currentMoveingX = motionEvent.getX();
            float y = motionEvent.getY();
            this.currentMoveingY = y;
            if (this.drawCirclePoint) {
                this.onStickerOperationListener.onTouchDragBeauty(this.currentMoveingX, y);
            }
            if (this.handlingSticker != null) {
                this.moveMatrix.set(this.downMatrix);
                Sticker sticker = this.handlingSticker;
                if (sticker instanceof PolishSticker) {
                    PolishSticker polishSticker = (PolishSticker) sticker;
                    if (polishSticker.getType() == 10 || polishSticker.getType() == 11) {
                        this.moveMatrix.postTranslate(0.0f, motionEvent.getY() - this.downY);
                    } else {
                        this.moveMatrix.postTranslate(motionEvent.getX() - this.downX, motionEvent.getY() - this.downY);
                    }
                } else {
                    this.moveMatrix.postTranslate(motionEvent.getX() - this.downX, motionEvent.getY() - this.downY);
                }
                this.handlingSticker.setMatrix(this.moveMatrix);
                if (this.constrained) {
                    constrainSticker(this.handlingSticker);
                }
            }
        } else if (i != 2) {
            if (i == 3 && this.handlingSticker != null && (polishStickerIcons = this.currentIcon) != null) {
                polishStickerIcons.onActionMove(this, motionEvent);
            }
        } else if (this.handlingSticker != null) {
            float calculateDistance = calculateDistance(motionEvent);
            float calculateRotation = calculateRotation(motionEvent);
            this.moveMatrix.set(this.downMatrix);
            Matrix matrix = this.moveMatrix;
            float f = this.oldDistance;
            matrix.postScale(calculateDistance / f, calculateDistance / f, this.midPoint.x, this.midPoint.y);
            this.moveMatrix.postRotate(calculateRotation - this.oldRotation, this.midPoint.x, this.midPoint.y);
            this.handlingSticker.setMatrix(this.moveMatrix);
        }
    }

    public void zoomAndRotateCurrentSticker(MotionEvent motionEvent) {
        zoomAndRotateSticker(this.handlingSticker, motionEvent);
    }

    public void alignHorizontally() {
        this.moveMatrix.set(this.downMatrix);
        this.moveMatrix.postRotate(-getCurrentSticker().getCurrentAngle(), this.midPoint.x, this.midPoint.y);
        this.handlingSticker.setMatrix(this.moveMatrix);
    }

    public void zoomAndRotateSticker(Sticker sticker, MotionEvent motionEvent) {
        float f;
        if (sticker != null) {
            boolean z = sticker instanceof PolishSticker;
            if (z) {
                PolishSticker polishSticker = (PolishSticker) sticker;
                if (polishSticker.getType() == 10 || polishSticker.getType() == 11) {
                    return;
                }
            }
            if (sticker instanceof PolishTextView) {
                f = this.oldDistance;
            } else {
                f = calculateDistance(this.midPoint.x, this.midPoint.y, motionEvent.getX(), motionEvent.getY());
            }
            float calculateRotation = calculateRotation(this.midPoint.x, this.midPoint.y, motionEvent.getX(), motionEvent.getY());
            this.moveMatrix.set(this.downMatrix);
            Matrix matrix = this.moveMatrix;
            float f2 = this.oldDistance;
            matrix.postScale(f / f2, f / f2, this.midPoint.x, this.midPoint.y);
            if (!z) {
                this.moveMatrix.postRotate(calculateRotation - this.oldRotation, this.midPoint.x, this.midPoint.y);
            }
            this.handlingSticker.setMatrix(this.moveMatrix);
        }
    }

    public void constrainSticker(Sticker sticker) {
        int width = getWidth();
        int height = getHeight();
        sticker.getMappedCenterPoint(this.currentCenterPoint, this.point, this.tmp);
        float f = 0.0f;
        float f2 = this.currentCenterPoint.x < 0.0f ? -this.currentCenterPoint.x : 0.0f;
        float f3 = (float) width;
        if (this.currentCenterPoint.x > f3) {
            f2 = f3 - this.currentCenterPoint.x;
        }
        if (this.currentCenterPoint.y < 0.0f) {
            f = -this.currentCenterPoint.y;
        }
        float f4 = (float) height;
        if (this.currentCenterPoint.y > f4) {
            f = f4 - this.currentCenterPoint.y;
        }
        sticker.getMatrix().postTranslate(f2, f);
    }

    public PolishStickerIcons findCurrentIconTouched() {
        for (PolishStickerIcons polishStickerIcons : this.icons) {
            float x = polishStickerIcons.getX() - this.downX;
            float y = polishStickerIcons.getY() - this.downY;
            if (((double) ((x * x) + (y * y))) <= Math.pow((double) (polishStickerIcons.getIconRadius() + polishStickerIcons.getIconRadius()), 2.0d)) {
                return polishStickerIcons;
            }
        }
        return null;
    }

    public Sticker findHandlingSticker() {
        for (int size = this.stickers.size() - 1; size >= 0; size--) {
            if (isInStickerArea(this.stickers.get(size), this.downX, this.downY)) {
                return this.stickers.get(size);
            }
        }
        return null;
    }

    public boolean isInStickerArea(Sticker sticker, float f, float f2) {
        float[] fArr = this.tmp;
        fArr[0] = f;
        fArr[1] = f2;
        return sticker.contains(fArr);
    }

    public PointF calculateMidPoint(MotionEvent motionEvent) {
        if (motionEvent == null || motionEvent.getPointerCount() < 2) {
            this.midPoint.set(0.0f, 0.0f);
            return this.midPoint;
        }
        this.midPoint.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
        return this.midPoint;
    }

    public PointF calculateMidPoint() {
        Sticker sticker = this.handlingSticker;
        if (sticker == null) {
            this.midPoint.set(0.0f, 0.0f);
            return this.midPoint;
        }
        sticker.getMappedCenterPoint(this.midPoint, this.point, this.tmp);
        return this.midPoint;
    }

    public float calculateRotation(MotionEvent motionEvent) {
        if (motionEvent == null || motionEvent.getPointerCount() < 2) {
            return 0.0f;
        }
        return calculateRotation(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1));
    }

    public float calculateRotation(float f, float f2, float f3, float f4) {
        return (float) Math.toDegrees(Math.atan2((double) (f2 - f4), (double) (f - f3)));
    }

    public float calculateDistance(MotionEvent motionEvent) {
        if (motionEvent == null || motionEvent.getPointerCount() < 2) {
            return 0.0f;
        }
        return calculateDistance(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1));
    }

    public float calculateDistance(float f, float f2, float f3, float f4) {
        double d = (double) (f - f3);
        double d2 = (double) (f2 - f4);
        return (float) Math.sqrt((d * d) + (d2 * d2));
    }

    public void transformSticker(Sticker sticker) {
        if (sticker == null) {
            Log.e(TAG, "transformSticker: the bitmapSticker is null or the bitmapSticker bitmap is null");
            return;
        }
        this.sizeMatrix.reset();
        float width = (float) getWidth();
        float height = (float) getHeight();
        float width2 = (float) sticker.getWidth();
        float height2 = (float) sticker.getHeight();
        this.sizeMatrix.postTranslate((width - width2) / 2.0f, (height - height2) / 2.0f);
        float f = (width < height ? width / width2 : height / height2) / 2.0f;
        this.sizeMatrix.postScale(f, f, width / 2.0f, height / 2.0f);
        sticker.getMatrix().reset();
        sticker.setMatrix(this.sizeMatrix);
        invalidate();
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        for (int i5 = 0; i5 < this.stickers.size(); i5++) {
            Sticker sticker = this.stickers.get(i5);
            if (sticker != null) {
                transformSticker(sticker);
            }
        }
    }

    public void flipCurrentSticker(int i) {
        flip(this.handlingSticker, i);
    }

    public void flip(Sticker sticker, int i) {
        if (sticker != null) {
            sticker.getCenterPoint(this.midPoint);
            if ((i & 1) > 0) {
                sticker.getMatrix().preScale(-1.0f, 1.0f, this.midPoint.x, this.midPoint.y);
                sticker.setFlippedHorizontally(!sticker.isFlippedHorizontally());
            }
            if ((i & 2) > 0) {
                sticker.getMatrix().preScale(1.0f, -1.0f, this.midPoint.x, this.midPoint.y);
                sticker.setFlippedVertically(!sticker.isFlippedVertically());
            }
            OnStickerOperationListener onStickerOperationListener2 = this.onStickerOperationListener;
            if (onStickerOperationListener2 != null) {
                onStickerOperationListener2.onStickerFlip(sticker);
            }
            invalidate();
        }
    }

    public boolean replace(Sticker sticker) {
        return replace(sticker, true);
    }

    public Sticker getLastHandlingSticker() {
        return this.lastHandlingSticker;
    }

    /* JADX INFO: Multiple debug info for r2v0 java.util.List<com.example.photoareditor.Classs.Sticker>: [D('f' float), D('list' java.util.List<com.example.photoareditor.Classs.Sticker>)] */
    /* JADX INFO: Multiple debug info for r3v8 float: [D('i' int), D('f2' float)] */
    /* JADX INFO: Multiple debug info for r2v6 com.example.photoareditor.Classs.Sticker: [D('f' float), D('sticker3' com.example.photoareditor.Classs.Sticker)] */
    public boolean replace(Sticker sticker, boolean z) {
        float f;
        int i;
        int i2;
        if (this.handlingSticker == null) {
            this.handlingSticker = this.lastHandlingSticker;
        }
        if (this.handlingSticker == null || sticker == null) {
            return false;
        }
        float width = (float) getWidth();
        float height = (float) getHeight();
        if (z) {
            sticker.setMatrix(this.handlingSticker.getMatrix());
            sticker.setFlippedVertically(this.handlingSticker.isFlippedVertically());
            sticker.setFlippedHorizontally(this.handlingSticker.isFlippedHorizontally());
        } else {
            this.handlingSticker.getMatrix().reset();
            sticker.getMatrix().postTranslate((width - ((float) this.handlingSticker.getWidth())) / 2.0f, (height - ((float) this.handlingSticker.getHeight())) / 2.0f);
            if (width < height) {
                Sticker sticker2 = this.handlingSticker;
                if (sticker2 instanceof PolishTextView) {
                    i2 = sticker2.getWidth();
                } else {
                    i2 = sticker2.getDrawable().getIntrinsicWidth();
                }
                f = width / ((float) i2);
            } else {
                Sticker sticker3 = this.handlingSticker;
                if (sticker3 instanceof PolishTextView) {
                    i = sticker3.getHeight();
                } else {
                    i = sticker3.getDrawable().getIntrinsicHeight();
                }
                f = height / ((float) i);
            }
            float f2 = f / 2.0f;
            sticker.getMatrix().postScale(f2, f2, width / 2.0f, height / 2.0f);
        }
        List<Sticker> list = this.stickers;
        list.set(list.indexOf(this.handlingSticker), sticker);
        this.handlingSticker = sticker;
        invalidate();
        return true;
    }

    public boolean remove(Sticker sticker) {
        if (this.stickers.contains(sticker)) {
            this.stickers.remove(sticker);
            OnStickerOperationListener onStickerOperationListener2 = this.onStickerOperationListener;
            if (onStickerOperationListener2 != null) {
                onStickerOperationListener2.onStickerDeleted(sticker);
            }
            if (this.handlingSticker == sticker) {
                this.handlingSticker = null;
            }
            invalidate();
            return true;
        }
        Log.d(TAG, "remove: the sticker is not in this StickerView");
        return false;
    }

    public boolean removeCurrentSticker() {
        return remove(this.handlingSticker);
    }

    public void removeAllStickers() {
        this.stickers.clear();
        Sticker sticker = this.handlingSticker;
        if (sticker != null) {
            sticker.release();
            this.handlingSticker = null;
        }
        invalidate();
    }

    public PolishStickerView addSticker(Sticker sticker) {
        return addSticker(sticker, 1);
    }

    public PolishStickerView addSticker(final Sticker sticker, final int i) {
        if (ViewCompat.isLaidOut(this)) {
            addStickerImmediately(sticker, i);
        } else {
            post(new Runnable() {
                /* class com.example.photoareditor.Classs.PolishStickerView.AnonymousClass1 */

                public void run() {
                    PolishStickerView.this.addStickerImmediately(sticker, i);
                }
            });
        }
        return this;
    }

    public void addStickerImmediately(Sticker sticker, int i) {
        setStickerPosition(sticker, i);
        sticker.getMatrix().postScale(1.0f, 1.0f, (float) getWidth(), (float) getHeight());
        this.handlingSticker = sticker;
        this.stickers.add(sticker);
        OnStickerOperationListener onStickerOperationListener2 = this.onStickerOperationListener;
        if (onStickerOperationListener2 != null) {
            onStickerOperationListener2.onAddSticker(sticker);
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void setStickerPosition(Sticker sticker, int position) {
        float offsetY;
        float offsetX;
        float offsetX2 = ((float) getWidth()) - ((float) sticker.getWidth());
        float offsetY2 = ((float) getHeight()) - ((float) sticker.getHeight());
        if ((position & 2) > 0) {
            offsetY = offsetY2 / 4.0f;
        } else if ((position & 16) > 0) {
            offsetY = offsetY2 * 0.75f;
        } else {
            offsetY = offsetY2 / 2.0f;
        }
        if ((position & 4) > 0) {
            offsetX = offsetX2 / 4.0f;
        } else if ((position & 8) > 0) {
            offsetX = offsetX2 * 0.75f;
        } else {
            offsetX = offsetX2 / 2.0f;
        }
        sticker.getMatrix().postTranslate(offsetX, offsetY);
    }

    public void editTextSticker() {
        this.onStickerOperationListener.onStickerDoubleTap(this.handlingSticker);
    }

    public float[] getStickerPoints(Sticker sticker) {
        float[] fArr = new float[8];
        getStickerPoints(sticker, fArr);
        return fArr;
    }

    public void getStickerPoints(Sticker sticker, float[] fArr) {
        if (sticker == null) {
            Arrays.fill(fArr, 0.0f);
            return;
        }
        sticker.getBoundPoints(this.bounds);
        sticker.getMappedPoints(fArr, this.bounds);
    }

    public void save(File file) {
        try {
            StickerUtils.saveImageToGallery(file, createBitmap());
            StickerUtils.notifySystemGallery(getContext(), file);
        } catch (IllegalArgumentException | IllegalStateException e) {
        }
    }

    public Bitmap createBitmap() throws OutOfMemoryError {
        this.handlingSticker = null;
        Bitmap createBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public int getStickerCount() {
        return this.stickers.size();
    }

    public boolean isNoneSticker() {
        return getStickerCount() == 0;
    }

    public PolishStickerView setLocked(boolean z) {
        this.locked = z;
        invalidate();
        return this;
    }

    public PolishStickerView setMinClickDelayTime(int i) {
        this.minClickDelayTime = i;
        return this;
    }

    public int getMinClickDelayTime() {
        return this.minClickDelayTime;
    }

    public boolean isConstrained() {
        return this.constrained;
    }

    public PolishStickerView setConstrained(boolean z) {
        this.constrained = z;
        postInvalidate();
        return this;
    }

    public PolishStickerView setOnStickerOperationListener(OnStickerOperationListener onStickerOperationListener2) {
        this.onStickerOperationListener = onStickerOperationListener2;
        return this;
    }

    public OnStickerOperationListener getOnStickerOperationListener() {
        return this.onStickerOperationListener;
    }

    public Sticker getCurrentSticker() {
        return this.handlingSticker;
    }

    public List<PolishStickerIcons> getIcons() {
        return this.icons;
    }

    public void setIcons(List<PolishStickerIcons> list) {
        this.icons.clear();
        this.icons.addAll(list);
        invalidate();
    }
}