package com.example.photoeditor.Classs;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import com.example.photoeditor.ModelClass.PolishLine;

/* loaded from: classes3.dex */
public class StraightLine implements PolishLine {
    StraightLine attachLineEnd;
    StraightLine attachLineStart;
    public Direction direction;
    private PointF end;
    private float endRatio;
    private PolishLine lowerLine;
    private PointF start;
    private float startRatio;
    private PolishLine upperLine;
    private RectF bounds = new RectF();
    private PointF previousEnd = new PointF();
    private PointF previousStart = new PointF();

    /* JADX INFO: Access modifiers changed from: package-private */
    public StraightLine(PointF pointF, PointF pointF2) {
        this.direction = Direction.HORIZONTAL;
        this.start = pointF;
        this.end = pointF2;
        if (pointF.x == pointF2.x) {
            this.direction = Direction.VERTICAL;
        } else if (pointF.y == pointF2.y) {
            this.direction = Direction.HORIZONTAL;
        } else {
            Log.d("StraightLine", "StraightLine: current only support two direction");
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public void setStartRatio(float f) {
        this.startRatio = f;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public float getStartRatio() {
        return this.startRatio;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public void setEndRatio(float f) {
        this.endRatio = f;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public float getEndRatio() {
        return this.endRatio;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public float length() {
        return (float) Math.sqrt(Math.pow(this.end.x - this.start.x, 2.0d) + Math.pow(this.end.y - this.start.y, 2.0d));
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public PointF startPoint() {
        return this.start;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public PointF endPoint() {
        return this.end;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public PolishLine lowerLine() {
        return this.lowerLine;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public PolishLine upperLine() {
        return this.upperLine;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public PolishLine attachStartLine() {
        return this.attachLineStart;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public PolishLine attachEndLine() {
        return this.attachLineEnd;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public void setLowerLine(PolishLine polishLine) {
        this.lowerLine = polishLine;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public void setUpperLine(PolishLine polishLine) {
        this.upperLine = polishLine;
    }

    public void setAttachLineStart(StraightLine straightLine) {
        this.attachLineStart = straightLine;
    }

    public void setAttachLineEnd(StraightLine straightLine) {
        this.attachLineEnd = straightLine;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public Direction direction() {
        return this.direction;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public float slope() {
        return this.direction == Direction.HORIZONTAL ? 0.0f : Float.MAX_VALUE;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public boolean contains(float f, float f2, float f3) {
        if (this.direction == Direction.HORIZONTAL) {
            this.bounds.left = this.start.x;
            this.bounds.right = this.end.x;
            float f4 = f3 / 2.0f;
            this.bounds.top = this.start.y - f4;
            this.bounds.bottom = this.start.y + f4;
        } else if (this.direction == Direction.VERTICAL) {
            this.bounds.top = this.start.y;
            this.bounds.bottom = this.end.y;
            float f5 = f3 / 2.0f;
            this.bounds.left = this.start.x - f5;
            this.bounds.right = this.start.x + f5;
        }
        return this.bounds.contains(f, f2);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public void prepareMove() {
        this.previousStart.set(this.start);
        this.previousEnd.set(this.end);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public boolean move(float f, float f2) {
        if (this.direction == Direction.HORIZONTAL) {
            if (this.previousStart.y + f < this.lowerLine.maxY() + f2 || this.previousStart.y + f > this.upperLine.minY() - f2 || this.previousEnd.y + f < this.lowerLine.maxY() + f2 || this.previousEnd.y + f > this.upperLine.minY() - f2) {
                return false;
            }
            this.start.y = this.previousStart.y + f;
            this.end.y = this.previousEnd.y + f;
            return true;
        } else if (this.previousStart.x + f < this.lowerLine.maxX() + f2 || this.previousStart.x + f > this.upperLine.minX() - f2 || this.previousEnd.x + f < this.lowerLine.maxX() + f2 || this.previousEnd.x + f > this.upperLine.minX() - f2) {
            return false;
        } else {
            this.start.x = this.previousStart.x + f;
            this.end.x = this.previousEnd.x + f;
            return true;
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public void update(float f, float f2) {
        if (this.direction == Direction.HORIZONTAL) {
            StraightLine straightLine = this.attachLineStart;
            if (straightLine != null) {
                this.start.x = straightLine.getPosition();
            }
            StraightLine straightLine2 = this.attachLineEnd;
            if (straightLine2 != null) {
                this.end.x = straightLine2.getPosition();
            }
        } else if (this.direction == Direction.VERTICAL) {
            StraightLine straightLine3 = this.attachLineStart;
            if (straightLine3 != null) {
                this.start.y = straightLine3.getPosition();
            }
            StraightLine straightLine4 = this.attachLineEnd;
            if (straightLine4 != null) {
                this.end.y = straightLine4.getPosition();
            }
        }
    }

    public float getPosition() {
        if (this.direction == Direction.HORIZONTAL) {
            return this.start.y;
        }
        return this.start.x;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public float minX() {
        return Math.min(this.start.x, this.end.x);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public float maxX() {
        return Math.max(this.start.x, this.end.x);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public float minY() {
        return Math.min(this.start.y, this.end.y);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public float maxY() {
        return Math.max(this.start.y, this.end.y);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public void offset(float f, float f2) {
        this.start.offset(f, f2);
        this.end.offset(f, f2);
    }

    public String toString() {
        return "start --> " + this.start.toString() + ",end --> " + this.end.toString();
    }
}
