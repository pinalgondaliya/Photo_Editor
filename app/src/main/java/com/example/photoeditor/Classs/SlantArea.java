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
public class SlantArea implements PolishArea {
    private Path areaPath;
    private RectF areaRect;
    private PointF[] handleBarPoints;
    CrossoverPointF leftBottom;
    CrossoverPointF leftTop;
    SlantLine lineBottom;
    SlantLine lineLeft;
    SlantLine lineRight;
    SlantLine lineTop;
    private float paddingBottom;
    private float paddingLeft;
    private float paddingRight;
    private float paddingTop;
    private float radian;
    CrossoverPointF rightBottom;
    CrossoverPointF rightTop;
    private PointF tempPoint;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SlantArea() {
        this.areaPath = new Path();
        this.areaRect = new RectF();
        PointF[] pointFArr = new PointF[2];
        this.handleBarPoints = pointFArr;
        pointFArr[0] = new PointF();
        this.handleBarPoints[1] = new PointF();
        this.leftTop = new CrossoverPointF();
        this.leftBottom = new CrossoverPointF();
        this.rightTop = new CrossoverPointF();
        this.rightBottom = new CrossoverPointF();
        this.tempPoint = new PointF();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SlantArea(SlantArea slantArea) {
        this();
        this.lineLeft = slantArea.lineLeft;
        this.lineTop = slantArea.lineTop;
        this.lineRight = slantArea.lineRight;
        this.lineBottom = slantArea.lineBottom;
        this.leftTop = slantArea.leftTop;
        this.leftBottom = slantArea.leftBottom;
        this.rightTop = slantArea.rightTop;
        this.rightBottom = slantArea.rightBottom;
        updateCornerPoints();
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float left() {
        return Math.min(this.leftTop.x, this.leftBottom.x) + this.paddingLeft;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float top() {
        return Math.min(this.leftTop.y, this.rightTop.y) + this.paddingTop;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float right() {
        return Math.max(this.rightTop.x, this.rightBottom.x) - this.paddingRight;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public float bottom() {
        return Math.max(this.leftBottom.y, this.rightBottom.y) - this.paddingBottom;
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
    public Path getAreaPath() {
        this.areaPath.reset();
        if (this.radian > 0.0f) {
            SlantUtils.getPoint(this.tempPoint, this.leftTop, this.leftBottom, PolishLine.Direction.VERTICAL, this.radian / SlantUtils.distance(this.leftTop, this.leftBottom));
            this.tempPoint.offset(this.paddingLeft, this.paddingTop);
            this.areaPath.moveTo(this.tempPoint.x, this.tempPoint.y);
            float distance = this.radian / SlantUtils.distance(this.leftTop, this.rightTop);
            SlantUtils.getPoint(this.tempPoint, this.leftTop, this.rightTop, PolishLine.Direction.HORIZONTAL, distance);
            this.tempPoint.offset(this.paddingLeft, this.paddingTop);

            this.areaPath.quadTo(this.leftTop.x + this.paddingLeft, this.leftTop.y + this.paddingTop, this.tempPoint.x, this.tempPoint.y);
            SlantUtils.getPoint(this.tempPoint, this.leftTop, this.rightTop, PolishLine.Direction.HORIZONTAL, 1.0f - distance);
            this.tempPoint.offset(-this.paddingRight, this.paddingTop);
            this.areaPath.lineTo(this.tempPoint.x, this.tempPoint.y);
            float distance2 = this.radian / SlantUtils.distance(this.rightTop, this.rightBottom);
            SlantUtils.getPoint(this.tempPoint, this.rightTop, this.rightBottom, PolishLine.Direction.VERTICAL, distance2);
            this.tempPoint.offset(-this.paddingRight, this.paddingTop);
            this.areaPath.quadTo(this.rightTop.x - this.paddingLeft, this.rightTop.y + this.paddingTop, this.tempPoint.x, this.tempPoint.y);
            SlantUtils.getPoint(this.tempPoint, this.rightTop, this.rightBottom, PolishLine.Direction.VERTICAL, 1.0f - distance2);
            this.tempPoint.offset(-this.paddingRight, -this.paddingBottom);
            this.areaPath.lineTo(this.tempPoint.x, this.tempPoint.y);
            float distance3 = 1.0f - (this.radian / SlantUtils.distance(this.leftBottom, this.rightBottom));
            SlantUtils.getPoint(this.tempPoint, this.leftBottom, this.rightBottom, PolishLine.Direction.HORIZONTAL, distance3);
            this.tempPoint.offset(-this.paddingRight, -this.paddingBottom);
            this.areaPath.quadTo(this.rightBottom.x - this.paddingRight, this.rightBottom.y - this.paddingTop, this.tempPoint.x, this.tempPoint.y);
            SlantUtils.getPoint(this.tempPoint, this.leftBottom, this.rightBottom, PolishLine.Direction.HORIZONTAL, 1.0f - distance3);
            this.tempPoint.offset(this.paddingLeft, -this.paddingBottom);
            this.areaPath.lineTo(this.tempPoint.x, this.tempPoint.y);
            float distance4 = 1.0f - (this.radian / SlantUtils.distance(this.leftTop, this.leftBottom));
            SlantUtils.getPoint(this.tempPoint, this.leftTop, this.leftBottom, PolishLine.Direction.VERTICAL, distance4);
            this.tempPoint.offset(this.paddingLeft, -this.paddingBottom);
            this.areaPath.quadTo(this.leftBottom.x + this.paddingLeft, this.leftBottom.y - this.paddingBottom, this.tempPoint.x, this.tempPoint.y);
            SlantUtils.getPoint(this.tempPoint, this.leftTop, this.leftBottom, PolishLine.Direction.VERTICAL, 1.0f - distance4);
            this.tempPoint.offset(this.paddingLeft, this.paddingTop);
            this.areaPath.lineTo(this.tempPoint.x, this.tempPoint.y);
        } else {
            this.areaPath.moveTo(this.leftTop.x + this.paddingLeft, this.leftTop.y + this.paddingTop);
            this.areaPath.lineTo(this.rightTop.x - this.paddingRight, this.rightTop.y + this.paddingTop);
            this.areaPath.lineTo(this.rightBottom.x - this.paddingRight, this.rightBottom.y - this.paddingBottom);
            this.areaPath.lineTo(this.leftBottom.x + this.paddingLeft, this.leftBottom.y - this.paddingBottom);
            this.areaPath.lineTo(this.leftTop.x + this.paddingLeft, this.leftTop.y + this.paddingTop);
        }
        return this.areaPath;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public RectF getAreaRect() {
        this.areaRect.set(left(), top(), right(), bottom());
        return this.areaRect;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public boolean contains(float f, float f2) {
        return SlantUtils.contains(this, f, f2);
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public boolean contains(PolishLine polishLine) {
        return this.lineLeft == polishLine || this.lineTop == polishLine || this.lineRight == polishLine || this.lineBottom == polishLine;
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public boolean contains(PointF pointF) {
        return contains(pointF.x, pointF.y);
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public List<PolishLine> getLines() {
        return Arrays.asList(this.lineLeft, this.lineTop, this.lineRight, this.lineBottom);
    }

    @Override // com.example.photoareditor.ModelClass.PolishArea
    public PointF[] getHandleBarPoints(PolishLine polishLine) {
        if (polishLine == this.lineLeft) {
            SlantUtils.getPoint(this.handleBarPoints[0], this.leftTop, this.leftBottom, polishLine.direction(), 0.25f);
            SlantUtils.getPoint(this.handleBarPoints[1], this.leftTop, this.leftBottom, polishLine.direction(), 0.75f);
            this.handleBarPoints[0].offset(this.paddingLeft, 0.0f);
            this.handleBarPoints[1].offset(this.paddingLeft, 0.0f);
        } else if (polishLine == this.lineTop) {
            SlantUtils.getPoint(this.handleBarPoints[0], this.leftTop, this.rightTop, polishLine.direction(), 0.25f);
            SlantUtils.getPoint(this.handleBarPoints[1], this.leftTop, this.rightTop, polishLine.direction(), 0.75f);
            this.handleBarPoints[0].offset(0.0f, this.paddingTop);
            this.handleBarPoints[1].offset(0.0f, this.paddingTop);
        } else if (polishLine == this.lineRight) {
            SlantUtils.getPoint(this.handleBarPoints[0], this.rightTop, this.rightBottom, polishLine.direction(), 0.25f);
            SlantUtils.getPoint(this.handleBarPoints[1], this.rightTop, this.rightBottom, polishLine.direction(), 0.75f);
            this.handleBarPoints[0].offset(-this.paddingRight, 0.0f);
            this.handleBarPoints[1].offset(-this.paddingRight, 0.0f);
        } else if (polishLine == this.lineBottom) {
            SlantUtils.getPoint(this.handleBarPoints[0], this.leftBottom, this.rightBottom, polishLine.direction(), 0.25f);
            SlantUtils.getPoint(this.handleBarPoints[1], this.leftBottom, this.rightBottom, polishLine.direction(), 0.75f);
            this.handleBarPoints[0].offset(0.0f, -this.paddingBottom);
            this.handleBarPoints[1].offset(0.0f, -this.paddingBottom);
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

    public void updateCornerPoints() {
        SlantUtils.intersectionOfLines(this.leftTop, this.lineLeft, this.lineTop);
        SlantUtils.intersectionOfLines(this.leftBottom, this.lineLeft, this.lineBottom);
        SlantUtils.intersectionOfLines(this.rightTop, this.lineRight, this.lineTop);
        SlantUtils.intersectionOfLines(this.rightBottom, this.lineRight, this.lineBottom);
    }

    /* loaded from: classes3.dex */
    public static class AreaComparator implements Comparator<SlantArea> {
        @Override // java.util.Comparator
        public int compare(SlantArea slantArea, SlantArea slantArea2) {
            if (slantArea.leftTop.y < slantArea2.leftTop.y) {
                return -1;
            }
            if (slantArea.leftTop.y != slantArea2.leftTop.y) {
                return 1;
            }
            if (slantArea.leftTop.x < slantArea2.leftTop.x) {
                return -1;
            }
            return slantArea.leftTop.x == slantArea2.leftTop.x ? 0 : 1;
        }
    }
}
