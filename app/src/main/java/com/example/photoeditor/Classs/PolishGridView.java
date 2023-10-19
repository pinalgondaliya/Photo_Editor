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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.photoeditor.ModelClass.PolishArea;
import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.ModelClass.PolishLine;
import com.example.photoeditor.R;
import com.steelkiwi.cropiwa.AspectRatio;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class PolishGridView extends PolishStickerView {
    private static final String TAG = "QueShotGridView";
    public ActionMode actionMode;
    private AspectRatio aspectRatio;
    private int backgroundResource;
    private boolean canDrag;
    private boolean canMoveLine;
    public boolean canSwap;
    private boolean canZoom;
    private float collagePadding;
    private float collageRadian;
    private float downX;
    private float downY;
    private int duration;
    private int handleBarColor;
    private Paint handleBarPaint;
    private PolishLayout.Info info;
    private int lineColor;
    private Paint linePaint;
    private int lineSize;
    private PointF midPoint;
    private boolean needDrawLine;
    private boolean needDrawOuterLine;
    public onQueShotSelectedListener onQueShotSelectedListener;
    private onQueShotUnSelectedListener onQueShotUnSelectedListener;
    public PolishGrid prevHandlingQueShotGrid;
    private float previousDistance;
    private PolishLine quShotLine;
    private Map<PolishArea, PolishGrid> queShotAreaQueShotGridMap;
    public PolishGrid queShotGrid;
    private List<PolishGrid> queShotGridList;
    public List<PolishGrid> queShotGrids;
    private PolishLayout queShotLayout;
    private boolean quickMode;
    private RectF rectF;
    private PolishGrid replaceQuShotGrid;
    private boolean resetQueShotMatrix;
    private Paint selectedAreaPaint;
    private int selectedLineColor;
    private Runnable switchToSwapAction;
    private boolean touchEnable;

    /* loaded from: classes3.dex */
    public enum ActionMode {
        NONE,
        DRAG,
        ZOOM,
        MOVE,
        SWAP
    }

    /* loaded from: classes3.dex */
    public interface onQueShotSelectedListener {
        void onQuShotSelected(PolishGrid polishGrid, int i);
    }

    /* loaded from: classes3.dex */
    public interface onQueShotUnSelectedListener {
        void onQuShotUnSelected();
    }

    public PolishGridView(Context context) {
        this(context, null);
    }

    public PolishGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PolishGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.actionMode = ActionMode.NONE;
        this.queShotGrids = new ArrayList();
        this.queShotGridList = new ArrayList();
        this.queShotAreaQueShotGridMap = new HashMap();
        this.touchEnable = true;
        this.resetQueShotMatrix = true;
        this.quickMode = false;
        this.canDrag = true;
        this.canMoveLine = true;
        this.canZoom = true;
        this.canSwap = true;
        this.switchToSwapAction = new Runnable() { // from class: com.example.photoareditor.Classs.PolishGridView.1
            @Override // java.lang.Runnable
            public void run() {
                if (PolishGridView.this.canSwap) {
                    PolishGridView.this.actionMode = ActionMode.SWAP;
                    PolishGridView.this.invalidate();
                }
            }
        };
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{R.attr.animation_duration, R.attr.handle_bar_color, R.attr.line_color, R.attr.line_size, R.attr.need_draw_line, R.attr.need_draw_outer_line, R.attr.piece_padding, R.attr.radian, R.attr.selected_line_color});
        this.lineSize = obtainStyledAttributes.getInt(3, 4);
        this.lineColor = obtainStyledAttributes.getColor(2, -1);
        this.selectedLineColor = obtainStyledAttributes.getColor(8, Color.parseColor("#99BBFB"));
        this.handleBarColor = obtainStyledAttributes.getColor(1, Color.parseColor("#99BBFB"));
        this.collagePadding = obtainStyledAttributes.getDimensionPixelSize(6, 0);
        this.needDrawLine = obtainStyledAttributes.getBoolean(4, true);
        this.needDrawOuterLine = obtainStyledAttributes.getBoolean(5, true);
        this.duration = obtainStyledAttributes.getInt(0, HttpStatus.SC_MULTIPLE_CHOICES);
        this.collageRadian = obtainStyledAttributes.getFloat(7, 0.0f);
        obtainStyledAttributes.recycle();
        this.rectF = new RectF();
        Paint paint = new Paint();
        this.linePaint = paint;
        paint.setAntiAlias(true);
        this.linePaint.setColor(this.lineColor);
        this.linePaint.setStrokeWidth(this.lineSize);
        this.linePaint.setStyle(Paint.Style.STROKE);
        this.linePaint.setStrokeJoin(Paint.Join.ROUND);
        this.linePaint.setStrokeCap(Paint.Cap.SQUARE);
        Paint paint2 = new Paint();
        this.selectedAreaPaint = paint2;
        paint2.setAntiAlias(true);
        this.selectedAreaPaint.setStyle(Paint.Style.STROKE);
        this.selectedAreaPaint.setStrokeJoin(Paint.Join.ROUND);
        this.selectedAreaPaint.setStrokeCap(Paint.Cap.ROUND);
        this.selectedAreaPaint.setColor(this.selectedLineColor);
        this.selectedAreaPaint.setStrokeWidth(this.lineSize);
        Paint paint3 = new Paint();
        this.handleBarPaint = paint3;
        paint3.setAntiAlias(true);
        this.handleBarPaint.setStyle(Paint.Style.FILL);
        this.handleBarPaint.setColor(this.handleBarColor);
        this.handleBarPaint.setStrokeWidth(this.lineSize * 3);
        this.midPoint = new PointF();
    }

    @Override // com.example.photoareditor.Classs.PolishStickerView, android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        resetCollageBounds();
        this.queShotAreaQueShotGridMap.clear();
        if (this.queShotGrids.size() != 0) {
            for (int i5 = 0; i5 < this.queShotGrids.size(); i5++) {
                PolishGrid polishGrid = this.queShotGrids.get(i5);
                PolishArea area = this.queShotLayout.mo335getArea(i5);
                polishGrid.setArea(area);
                this.queShotAreaQueShotGridMap.put(area, polishGrid);
                if (this.resetQueShotMatrix) {
                    polishGrid.set(MatrixUtils.generateMatrix(polishGrid, 0.0f));
                } else {
                    polishGrid.fillArea(this, true);
                }
            }
        }
        invalidate();
    }

    public AspectRatio getAspectRatio() {
        return this.aspectRatio;
    }

    public void setAspectRatio(AspectRatio aspectRatio2) {
        this.aspectRatio = aspectRatio2;
    }

    private void resetCollageBounds() {
        this.rectF.left = getPaddingLeft();
        this.rectF.top = getPaddingTop();
        this.rectF.right = getWidth() - getPaddingRight();
        this.rectF.bottom = getHeight() - getPaddingBottom();
        PolishLayout polishLayout = this.queShotLayout;
        if (polishLayout != null) {
            polishLayout.reset();
            this.queShotLayout.setOuterBounds(this.rectF);
            this.queShotLayout.layout();
            this.queShotLayout.setPadding(this.collagePadding);
            this.queShotLayout.setRadian(this.collageRadian);
            PolishLayout.Info info2 = this.info;
            if (info2 != null) {
                int size = info2.lineInfos.size();
                for (int i = 0; i < size; i++) {
                    PolishLayout.LineInfo lineInfo = this.info.lineInfos.get(i);
                    PolishLine polishLine = this.queShotLayout.getLines().get(i);
                    polishLine.startPoint().x = lineInfo.startX;
                    polishLine.startPoint().y = lineInfo.startY;
                    polishLine.endPoint().x = lineInfo.endX;
                    polishLine.endPoint().y = lineInfo.endY;
                }
            }
            this.queShotLayout.sortAreas();
            this.queShotLayout.update();
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.queShotLayout != null) {
            this.linePaint.setStrokeWidth(this.lineSize);
            this.selectedAreaPaint.setStrokeWidth(this.lineSize);
            this.handleBarPaint.setStrokeWidth(this.lineSize * 3);
            for (int i = 0; i < this.queShotLayout.getAreaCount() && i < this.queShotGrids.size(); i++) {
                PolishGrid polishGrid = this.queShotGrids.get(i);
                if ((polishGrid != this.queShotGrid || this.actionMode != ActionMode.SWAP) && this.queShotGrids.size() > i) {
                    polishGrid.draw(canvas, this.quickMode);
                }
            }
            if (this.needDrawOuterLine) {
                for (PolishLine polishLine : this.queShotLayout.getOuterLines()) {
                    drawLine(canvas, polishLine);
                }
            }
            if (this.needDrawLine) {
                for (PolishLine polishLine2 : this.queShotLayout.getLines()) {
                    drawLine(canvas, polishLine2);
                }
            }
            if (this.queShotGrid != null && this.actionMode != ActionMode.SWAP) {
                drawSelectedArea(canvas, this.queShotGrid);
            }
            if (this.queShotGrid != null && this.actionMode == ActionMode.SWAP) {
                this.queShotGrid.draw(canvas, 128, this.quickMode);
                PolishGrid polishGrid2 = this.replaceQuShotGrid;
                if (polishGrid2 != null) {
                    drawSelectedArea(canvas, polishGrid2);
                }
            }
        }
    }

    private void drawSelectedArea(Canvas canvas, PolishGrid polishGrid) {
        PolishArea area = polishGrid.getArea();
        canvas.drawPath(area.getAreaPath(), this.selectedAreaPaint);
        for (PolishLine polishLine : area.getLines()) {
            if (this.queShotLayout.getLines().contains(polishLine)) {
                PointF[] handleBarPoints = area.getHandleBarPoints(polishLine);
                canvas.drawLine(handleBarPoints[0].x, handleBarPoints[0].y, handleBarPoints[1].x, handleBarPoints[1].y, this.handleBarPaint);
                canvas.drawCircle(handleBarPoints[0].x, handleBarPoints[0].y, (this.lineSize * 3) / 2, this.handleBarPaint);
                canvas.drawCircle(handleBarPoints[1].x, handleBarPoints[1].y, (this.lineSize * 3) / 2, this.handleBarPaint);
            }
        }
    }

    private void drawLine(Canvas canvas, PolishLine polishLine) {
        canvas.drawLine(polishLine.startPoint().x, polishLine.startPoint().y, polishLine.endPoint().x, polishLine.endPoint().y, this.linePaint);
    }

    public void updateLayout(PolishLayout polishLayout) {
        ArrayList arrayList = new ArrayList(this.queShotGrids);
        setQueShotLayout(polishLayout);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            addQuShotCollage(((PolishGrid) it.next()).getDrawable());
        }
        invalidate();
    }

    public void setQueShotLayout(PolishLayout polishLayout) {
        clearQuShot();
        this.queShotLayout = polishLayout;
        polishLayout.setOuterBounds(this.rectF);
        polishLayout.layout();
        invalidate();
    }

    public void setCollageLayout(PolishLayout.Info info2) {
        this.info = info2;
        clearQuShot();
        this.queShotLayout = PolishLayoutParser.parse(info2);
        this.collagePadding = info2.padding;
        this.collageRadian = info2.radian;
        setBackgroundColor(info2.color);
        invalidate();
    }

    public PolishLayout getQueShotLayout() {
        return this.queShotLayout;
    }

    @Override // com.example.photoareditor.Classs.PolishStickerView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.touchEnable) {
            return super.onTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction() & 255;
        Log.e(TAG, "onTouchEvent: " + action);
        if (action == 5) {
            this.previousDistance = calculateDistance(motionEvent);
            calculateMidPoint(motionEvent, this.midPoint);
            decideActionMode(motionEvent);
        } else if (action != 0) {
            if (action != 1 && action == 2) {
                performAction(motionEvent);
                if ((Math.abs(motionEvent.getX() - this.downX) > 10.0f || Math.abs(motionEvent.getY() - this.downY) > 10.0f) && this.actionMode != ActionMode.SWAP) {
                    removeCallbacks(this.switchToSwapAction);
                }
            }
            if (motionEvent.getAction() == 1) {
                finishAction(motionEvent);
            }
            removeCallbacks(this.switchToSwapAction);
        } else {
            this.downX = motionEvent.getX();
            this.downY = motionEvent.getY();
            decideActionMode(motionEvent);
            prepareAction(motionEvent);
        }
        invalidate();
        return true;
    }

    private void decideActionMode(MotionEvent motionEvent) {
        PolishGrid polishGrid;
        for (PolishGrid polishGrid2 : this.queShotGrids) {
            if (polishGrid2.isAnimateRunning()) {
                this.actionMode = ActionMode.NONE;
                return;
            }
        }
        if (motionEvent.getPointerCount() == 1) {
            PolishLine findHandlingLine = findHandlingLine();
            this.quShotLine = findHandlingLine;
            if (findHandlingLine == null || !this.canMoveLine) {
                PolishGrid findHandlingQuShot = findHandlingQuShot();
                this.queShotGrid = findHandlingQuShot;
                if (findHandlingQuShot != null && this.canDrag) {
                    this.actionMode = ActionMode.DRAG;
                    postDelayed(this.switchToSwapAction, 500L);
                    return;
                }
                return;
            }
            this.actionMode = ActionMode.MOVE;
        } else if (motionEvent.getPointerCount() > 1 && (polishGrid = this.queShotGrid) != null && polishGrid.contains(motionEvent.getX(1), motionEvent.getY(1)) && this.actionMode == ActionMode.DRAG && this.canZoom) {
            this.actionMode = ActionMode.ZOOM;
        }
    }

    /* loaded from: classes3.dex */
    public static class AnonymousClass2 {
        static final int[] $SwitchMap$com$meta$photoeditor$pro$polish$PolishGridView$ActionMode;

        static {
            int[] iArr = new int[ActionMode.values().length];
            $SwitchMap$com$meta$photoeditor$pro$polish$PolishGridView$ActionMode = iArr;
            iArr[ActionMode.DRAG.ordinal()] = 1;
            iArr[ActionMode.ZOOM.ordinal()] = 2;
            iArr[ActionMode.MOVE.ordinal()] = 3;
            try {
                iArr[ActionMode.SWAP.ordinal()] = 4;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    private void prepareAction(MotionEvent motionEvent) {
        int i = AnonymousClass2.$SwitchMap$com$meta$photoeditor$pro$polish$PolishGridView$ActionMode[this.actionMode.ordinal()];
        if (i == 1) {
            this.queShotGrid.record();
        } else if (i == 2) {
            this.queShotGrid.record();
        } else if (i == 3) {
            this.quShotLine.prepareMove();
            this.queShotGridList.clear();
            this.queShotGridList.addAll(findNeedChangedPieces());
            for (PolishGrid polishGrid : this.queShotGridList) {
                polishGrid.record();
                polishGrid.setPreviousMoveX(this.downX);
                polishGrid.setPreviousMoveY(this.downY);
            }
        }
    }

    private void performAction(MotionEvent motionEvent) {
        int i = AnonymousClass2.$SwitchMap$com$meta$photoeditor$pro$polish$PolishGridView$ActionMode[this.actionMode.ordinal()];
        if (i == 1) {
            dragPiece(this.queShotGrid, motionEvent);
        } else if (i == 2) {
            zoomPolish(this.queShotGrid, motionEvent);
        } else if (i == 3) {
            moveLine(this.quShotLine, motionEvent);
        } else if (i == 4) {
            dragPiece(this.queShotGrid, motionEvent);
            this.replaceQuShotGrid = findReplacePiece(motionEvent);
        }
    }

    private void finishAction(MotionEvent motionEvent) {
        onQueShotUnSelectedListener onqueshotunselectedlistener;
        onQueShotSelectedListener onqueshotselectedlistener;
        int i = AnonymousClass2.$SwitchMap$com$meta$photoeditor$pro$polish$PolishGridView$ActionMode[this.actionMode.ordinal()];
        if (i == 1) {
            PolishGrid polishGrid = this.queShotGrid;
            if (polishGrid != null && !polishGrid.isFilledArea()) {
                this.queShotGrid.moveToFillArea(this);
            }
            if (this.prevHandlingQueShotGrid == this.queShotGrid && Math.abs(this.downX - motionEvent.getX()) < 3.0f && Math.abs(this.downY - motionEvent.getY()) < 3.0f) {
                this.queShotGrid = null;
            }
            this.prevHandlingQueShotGrid = this.queShotGrid;
        } else if (i == 2) {
            PolishGrid polishGrid2 = this.queShotGrid;
            if (polishGrid2 != null && !polishGrid2.isFilledArea()) {
                if (this.queShotGrid.canFilledArea()) {
                    this.queShotGrid.moveToFillArea(this);
                } else {
                    this.queShotGrid.fillArea(this, false);
                }
            }
            this.prevHandlingQueShotGrid = this.queShotGrid;
        } else if (i == 4 && this.queShotGrid != null && this.replaceQuShotGrid != null) {
            swapPiece();
            this.queShotGrid = null;
            this.replaceQuShotGrid = null;
            this.prevHandlingQueShotGrid = null;
        }
        PolishGrid polishGrid3 = this.queShotGrid;
        if (polishGrid3 != null && (onqueshotselectedlistener = this.onQueShotSelectedListener) != null) {
            onqueshotselectedlistener.onQuShotSelected(polishGrid3, this.queShotGrids.indexOf(polishGrid3));
        } else if (polishGrid3 == null && (onqueshotunselectedlistener = this.onQueShotUnSelectedListener) != null) {
            onqueshotunselectedlistener.onQuShotUnSelected();
        }
        this.quShotLine = null;
        this.queShotGridList.clear();
    }

    public void setPrevHandlingQueShotGrid(PolishGrid polishGrid) {
        this.prevHandlingQueShotGrid = polishGrid;
    }

    private void swapPiece() {
        Drawable drawable = this.queShotGrid.getDrawable();
        String path = this.queShotGrid.getPath();
        this.queShotGrid.setDrawable(this.replaceQuShotGrid.getDrawable());
        this.queShotGrid.setPath(this.replaceQuShotGrid.getPath());
        this.replaceQuShotGrid.setDrawable(drawable);
        this.replaceQuShotGrid.setPath(path);
        this.queShotGrid.fillArea(this, true);
        this.replaceQuShotGrid.fillArea(this, true);
    }

    private void moveLine(PolishLine polishLine, MotionEvent motionEvent) {
        boolean z;
        if (polishLine != null && motionEvent != null) {
            if (polishLine.direction() == PolishLine.Direction.HORIZONTAL) {
                z = polishLine.move(motionEvent.getY() - this.downY, 80.0f);
            } else {
                z = polishLine.move(motionEvent.getX() - this.downX, 80.0f);
            }
            if (z) {
                this.queShotLayout.update();
                this.queShotLayout.sortAreas();
                updatePiecesInArea(polishLine, motionEvent);
            }
        }
    }

    private void updatePiecesInArea(PolishLine polishLine, MotionEvent motionEvent) {
        for (int i = 0; i < this.queShotGridList.size(); i++) {
            this.queShotGridList.get(i).updateWith(motionEvent, polishLine);
        }
    }

    private void zoomPolish(PolishGrid polishGrid, MotionEvent motionEvent) {
        if (polishGrid != null && motionEvent != null && motionEvent.getPointerCount() >= 2) {
            float calculateDistance = calculateDistance(motionEvent) / this.previousDistance;
            polishGrid.zoomAndTranslate(calculateDistance, calculateDistance, this.midPoint, motionEvent.getX() - this.downX, motionEvent.getY() - this.downY);
        }
    }

    private void dragPiece(PolishGrid polishGrid, MotionEvent motionEvent) {
        if (polishGrid != null && motionEvent != null) {
            polishGrid.translate(motionEvent.getX() - this.downX, motionEvent.getY() - this.downY);
        }
    }

    public void replace(Bitmap bitmap, String str) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        bitmapDrawable.setAntiAlias(true);
        bitmapDrawable.setFilterBitmap(true);
        replace(bitmapDrawable, str);
    }

    public void replace(Drawable drawable, String str) {
        PolishGrid polishGrid = this.queShotGrid;
        if (polishGrid != null) {
            polishGrid.setPath(str);
            this.queShotGrid.setDrawable(drawable);
            PolishGrid polishGrid2 = this.queShotGrid;
            polishGrid2.set(MatrixUtils.generateMatrix(polishGrid2, 0.0f));
            invalidate();
        }
    }

    public void setQueShotGrid(PolishGrid polishGrid) {
        this.queShotGrid = polishGrid;
    }

    public void flipVertically() {
        PolishGrid polishGrid = this.queShotGrid;
        if (polishGrid != null) {
            polishGrid.postFlipVertically();
            this.queShotGrid.record();
            invalidate();
        }
    }

    public void flipHorizontally() {
        PolishGrid polishGrid = this.queShotGrid;
        if (polishGrid != null) {
            polishGrid.postFlipHorizontally();
            this.queShotGrid.record();
            invalidate();
        }
    }

    public void rotate(float f) {
        PolishGrid polishGrid = this.queShotGrid;
        if (polishGrid != null) {
            polishGrid.postRotate(f);
            this.queShotGrid.record();
            invalidate();
        }
    }

    private PolishGrid findHandlingQuShot() {
        for (PolishGrid polishGrid : this.queShotGrids) {
            if (polishGrid.contains(this.downX, this.downY)) {
                return polishGrid;
            }
        }
        return null;
    }

    private PolishLine findHandlingLine() {
        for (PolishLine polishLine : this.queShotLayout.getLines()) {
            if (polishLine.contains(this.downX, this.downY, 40.0f)) {
                return polishLine;
            }
        }
        return null;
    }

    private PolishGrid findReplacePiece(MotionEvent motionEvent) {
        for (PolishGrid polishGrid : this.queShotGrids) {
            if (polishGrid.contains(motionEvent.getX(), motionEvent.getY())) {
                return polishGrid;
            }
        }
        return null;
    }

    private List<PolishGrid> findNeedChangedPieces() {
        if (this.quShotLine == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (PolishGrid polishGrid : this.queShotGrids) {
            if (polishGrid.contains(this.quShotLine)) {
                arrayList.add(polishGrid);
            }
        }
        return arrayList;
    }

    @Override // com.example.photoareditor.Classs.PolishStickerView
    public float calculateDistance(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((x * x) + (y * y));
    }

    private void calculateMidPoint(MotionEvent motionEvent, PointF pointF) {
        pointF.x = (motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f;
        pointF.y = (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f;
    }

    public void reset() {
        clearQuShot();
        PolishLayout polishLayout = this.queShotLayout;
        if (polishLayout != null) {
            polishLayout.reset();
        }
    }

    public void clearQuShot() {
        clearHandlingPieces();
        this.queShotGrids.clear();
        invalidate();
    }

    public void clearHandlingPieces() {
        this.quShotLine = null;
        this.queShotGrid = null;
        this.replaceQuShotGrid = null;
        this.queShotGridList.clear();
        invalidate();
    }

    public void addPieces(List<Bitmap> list) {
        for (Bitmap bitmap : list) {
            addQuShotCollage(bitmap);
        }
        postInvalidate();
    }

    public void addQuShotCollage(Bitmap bitmap) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        bitmapDrawable.setAntiAlias(true);
        bitmapDrawable.setFilterBitmap(true);
        addQuShotCollage(bitmapDrawable, null);
    }

    public void addQuShotCollage(Drawable drawable) {
        addQuShotCollage(drawable, null);
    }

    public void addQuShotCollage(Drawable drawable, Matrix matrix) {
        addQuShotCollage(drawable, matrix, "");
    }

    public void addQuShotCollage(Drawable drawable, Matrix matrix, String str) {
        Matrix matrix2;
        int size = this.queShotGrids.size();
        if (size >= this.queShotLayout.getAreaCount()) {
            Log.e(TAG, "addQuShotCollage: can not add more. the current collage layout can contains " + this.queShotLayout.getAreaCount() + " puzzle piece.");
            return;
        }
        PolishArea area = this.queShotLayout.mo335getArea(size);
        area.setPadding(this.collagePadding);
        PolishGrid polishGrid = new PolishGrid(drawable, area, new Matrix());
        if (matrix != null) {
            matrix2 = new Matrix(matrix);
        } else {
            matrix2 = MatrixUtils.generateMatrix(area, drawable, 0.0f);
        }
        polishGrid.set(matrix2);
        polishGrid.setAnimateDuration(this.duration);
        polishGrid.setPath(str);
        this.queShotGrids.add(polishGrid);
        this.queShotAreaQueShotGridMap.put(area, polishGrid);
        setCollagePadding(this.collagePadding);
        setCollageRadian(this.collageRadian);
        invalidate();
    }

    public PolishGrid getQueShotGrid() {
        return this.queShotGrid;
    }

    public void setAnimateDuration(int i) {
        this.duration = i;
        for (PolishGrid polishGrid : this.queShotGrids) {
            polishGrid.setAnimateDuration(i);
        }
    }

    public void setNeedDrawLine(boolean z) {
        this.needDrawLine = z;
        this.queShotGrid = null;
        this.prevHandlingQueShotGrid = null;
        invalidate();
    }

    public void setNeedDrawOuterLine(boolean z) {
        this.needDrawOuterLine = z;
        invalidate();
    }

    public void setLineColor(int i) {
        this.lineColor = i;
        this.linePaint.setColor(i);
        invalidate();
    }

    public void setLineSize(int i) {
        this.lineSize = i;
        invalidate();
    }

    public void setSelectedLineColor(int i) {
        this.selectedLineColor = i;
        this.selectedAreaPaint.setColor(i);
        invalidate();
    }

    public void setHandleBarColor(int i) {
        this.handleBarColor = i;
        this.handleBarPaint.setColor(i);
        invalidate();
    }

    public void setTouchEnable(boolean z) {
        this.touchEnable = z;
    }

    public void clearHandling() {
        this.queShotGrid = null;
        this.quShotLine = null;
        this.replaceQuShotGrid = null;
        this.prevHandlingQueShotGrid = null;
        this.queShotGridList.clear();
    }

    public void setCollagePadding(float f) {
        this.collagePadding = f;
        PolishLayout polishLayout = this.queShotLayout;
        if (polishLayout != null) {
            polishLayout.setPadding(f);
            int size = this.queShotGrids.size();
            for (int i = 0; i < size; i++) {
                PolishGrid polishGrid = this.queShotGrids.get(i);
                if (polishGrid.canFilledArea()) {
                    polishGrid.moveToFillArea(null);
                } else {
                    polishGrid.fillArea(this, true);
                }
            }
        }
        invalidate();
    }

    public void setCollageRadian(float f) {
        this.collageRadian = f;
        PolishLayout polishLayout = this.queShotLayout;
        if (polishLayout != null) {
            polishLayout.setRadian(f);
        }
        invalidate();
    }

    public int getBackgroundResourceMode() {
        return this.backgroundResource;
    }

    public void setBackgroundResourceMode(int i) {
        this.backgroundResource = i;
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
        PolishLayout polishLayout = this.queShotLayout;
        if (polishLayout != null) {
            polishLayout.setColor(i);
        }
    }

    public float getCollagePadding() {
        return this.collagePadding;
    }

    public float getCollageRadian() {
        return this.collageRadian;
    }

    public List<PolishGrid> getQueShotGrids() {
        int size = this.queShotGrids.size();
        ArrayList arrayList = new ArrayList(size);
        this.queShotLayout.sortAreas();
        for (int i = 0; i < size; i++) {
            arrayList.add(this.queShotAreaQueShotGridMap.get(this.queShotLayout.mo335getArea(i)));
        }
        return arrayList;
    }

    public void setOnQueShotSelectedListener(onQueShotSelectedListener onqueshotselectedlistener) {
        this.onQueShotSelectedListener = onqueshotselectedlistener;
    }

    public void setOnQueShotUnSelectedListener(onQueShotUnSelectedListener onqueshotunselectedlistener) {
        this.onQueShotUnSelectedListener = onqueshotunselectedlistener;
    }
}
