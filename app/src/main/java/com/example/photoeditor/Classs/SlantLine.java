package com.example.photoeditor.Classs;

import android.graphics.PointF;
import com.example.photoeditor.ModelClass.PolishLine;

/* loaded from: classes3.dex */
public class SlantLine implements PolishLine {
    SlantLine attachLineEnd;
    SlantLine attachLineStart;
    public final Direction direction;
    CrossoverPointF end;
    private float endRatio;
    PolishLine lowerLine;
    private PointF previousEnd = new PointF();
    private PointF previousStart = new PointF();
    CrossoverPointF start;
    private float startRatio;
    PolishLine upperLine;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SlantLine(PolishLine.Direction direction2) {
        this.direction = direction2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SlantLine(CrossoverPointF crossoverPointF, CrossoverPointF crossoverPointF2, Direction direction2) {
        this.start = crossoverPointF;
        this.end = crossoverPointF2;
        this.direction = direction2;
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

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public Direction direction() {
        return this.direction;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public float slope() {
        return SlantUtils.calculateSlope(this);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public boolean contains(float f, float f2, float f3) {
        return SlantUtils.contains(this, f, f2, f3);
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
    public void prepareMove() {
        this.previousStart.set(this.start);
        this.previousEnd.set(this.end);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLine
    public void update(float f, float f2) {
        SlantUtils.intersectionOfLines(this.start, this, this.attachLineStart);
        SlantUtils.intersectionOfLines(this.end, this, this.attachLineEnd);
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
