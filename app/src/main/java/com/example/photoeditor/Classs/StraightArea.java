package com.example.photoeditor.Classs;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import com.example.photoeditor.ModelClass.PolishArea;
import com.example.photoeditor.ModelClass.PolishLine;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes3.dex */
public class StraightArea implements PolishArea {
    private Path areaPath = new Path();
    private RectF areaRect = new RectF();
    private PointF[] handleBarPoints;
    StraightLine lineBottom;
    StraightLine lineLeft;
    StraightLine lineRight;
    StraightLine lineTop;
    private float paddingBottom;
    private float paddingLeft;
    private float paddingRight;
    private float paddingTop;
    private float radian;

    /* JADX INFO: Access modifiers changed from: package-private */
    public StraightArea() {
        PointF[] pointFArr = new PointF[2];
        this.handleBarPoints = pointFArr;
        pointFArr[0] = new PointF();
        this.handleBarPoints[1] = new PointF();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public StraightArea(StraightArea straightArea) {
        PointF[] pointFArr = new PointF[2];
        this.handleBarPoints = pointFArr;
        this.lineLeft = straightArea.lineLeft;
        this.lineTop = straightArea.lineTop;
        this.lineRight = straightArea.lineRight;
        this.lineBottom = straightArea.lineBottom;
        pointFArr[0] = new PointF();
        this.handleBarPoints[1] = new PointF();
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float left() {
        return this.lineLeft.minX() + this.paddingLeft;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float top() {
        return this.lineTop.minY() + this.paddingTop;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float right() {
        return this.lineRight.maxX() - this.paddingRight;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float bottom() {
        return this.lineBottom.maxY() - this.paddingBottom;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float centerX() {
        return (left() + right()) / 2.0f;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float centerY() {
        return (top() + bottom()) / 2.0f;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float width() {
        return right() - left();
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float height() {
        return bottom() - top();
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public PointF getCenterPoint() {
        return new PointF(centerX(), centerY());
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public boolean contains(PointF pointF) {
        return contains(pointF.x, pointF.y);
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public boolean contains(float f, float f2) {
        return getAreaRect().contains(f, f2);
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public boolean contains(PolishLine polishLine) {
        return this.lineLeft == polishLine || this.lineTop == polishLine || this.lineRight == polishLine || this.lineBottom == polishLine;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public Path getAreaPath() {
        this.areaPath.reset();
        Path path = this.areaPath;
        RectF areaRect2 = getAreaRect();
        float f = this.radian;
        path.addRoundRect(areaRect2, f, f, Path.Direction.CCW);
        return this.areaPath;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public RectF getAreaRect() {
        this.areaRect.set(left(), top(), right(), bottom());
        return this.areaRect;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public List<PolishLine> getLines() {
        return Arrays.asList(this.lineLeft, this.lineTop, this.lineRight, this.lineBottom);
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public PointF[] getHandleBarPoints(PolishLine polishLine) {
        if (polishLine == this.lineLeft) {
            this.handleBarPoints[0].x = left();
            this.handleBarPoints[0].y = top() + (height() / 4.0f);
            this.handleBarPoints[1].x = left();
            this.handleBarPoints[1].y = top() + ((height() / 4.0f) * 3.0f);
        } else if (polishLine == this.lineTop) {
            this.handleBarPoints[0].x = left() + (width() / 4.0f);
            this.handleBarPoints[0].y = top();
            this.handleBarPoints[1].x = left() + ((width() / 4.0f) * 3.0f);
            this.handleBarPoints[1].y = top();
        } else if (polishLine == this.lineRight) {
            this.handleBarPoints[0].x = right();
            this.handleBarPoints[0].y = top() + (height() / 4.0f);
            this.handleBarPoints[1].x = right();
            this.handleBarPoints[1].y = top() + ((height() / 4.0f) * 3.0f);
        } else if (polishLine == this.lineBottom) {
            this.handleBarPoints[0].x = left() + (width() / 4.0f);
            this.handleBarPoints[0].y = bottom();
            this.handleBarPoints[1].x = left() + ((width() / 4.0f) * 3.0f);
            this.handleBarPoints[1].y = bottom();
        }
        return this.handleBarPoints;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float radian() {
        return this.radian;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public void setRadian(float f) {
        this.radian = f;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float getPaddingLeft() {
        return this.paddingLeft;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float getPaddingTop() {
        return this.paddingTop;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float getPaddingRight() {
        return this.paddingRight;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float getPaddingBottom() {
        return this.paddingBottom;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public void setPadding(float f) {
        setPadding(f, f, f, f);
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public void setPadding(float f, float f2, float f3, float f4) {
        this.paddingLeft = f;
        this.paddingTop = f2;
        this.paddingRight = f3;
        this.paddingBottom = f4;
    }

    /* loaded from: classes3.dex */
    public static class AreaComparator implements Comparator<StraightArea> {
        @Override // java.util.Comparator
        public int compare(StraightArea straightArea, StraightArea straightArea2) {
            if (straightArea.top() < straightArea2.top()) {
                return -1;
            }
            if (straightArea.top() != straightArea2.top()) {
                return 1;
            }
            if (straightArea.left() < straightArea2.left()) {
                return -1;
            }
            return straightArea.left() == straightArea2.left() ? 0 : 1;
        }
    }
}
