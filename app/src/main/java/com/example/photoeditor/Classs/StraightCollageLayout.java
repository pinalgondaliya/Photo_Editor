package com.example.photoeditor.Classs;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Pair;

import com.example.photoeditor.ModelClass.PolishArea;
import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.ModelClass.PolishLine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class StraightCollageLayout implements PolishLayout {
    private Comparator<StraightArea> areaComparator;
    private List<StraightArea> areas;
    private RectF bounds;
    private int color;
    private List<PolishLine> lines;
    private StraightArea outerArea;
    private List<PolishLine> outerLines;
    private float padding;
    private float radian;
    private ArrayList<Step> steps;

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public abstract void layout();

    /* JADX INFO: Access modifiers changed from: protected */
    public StraightCollageLayout() {
        this.areaComparator = new StraightArea.AreaComparator();
        this.areas = new ArrayList();
        this.color = -1;
        this.lines = new ArrayList();
        this.outerLines = new ArrayList(4);
        this.steps = new ArrayList<>();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public StraightCollageLayout(StraightCollageLayout straightCollageLayout, boolean z) {
        this.areaComparator = new StraightArea.AreaComparator();
        this.areas = new ArrayList();
        this.color = -1;
        this.lines = new ArrayList();
        this.outerLines = new ArrayList(4);
        this.steps = new ArrayList<>();
        this.bounds = straightCollageLayout.getBounds();
        this.outerArea = straightCollageLayout.mo336getOuterArea();
        this.areas = straightCollageLayout.getAreas();
        this.lines = straightCollageLayout.getLines();
        this.outerLines = straightCollageLayout.getOuterLines();
        this.padding = straightCollageLayout.getPadding();
        this.radian = straightCollageLayout.getRadian();
        this.color = straightCollageLayout.getColor();
        this.areaComparator = straightCollageLayout.getAreaComparator();
        this.steps = straightCollageLayout.getSteps();
    }

    public List<StraightArea> getAreas() {
        return this.areas;
    }

    public Comparator<StraightArea> getAreaComparator() {
        return this.areaComparator;
    }

    public ArrayList<Step> getSteps() {
        return this.steps;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public void setOuterBounds(RectF rectF) {
        reset();
        this.bounds = rectF;
        PointF pointF = new PointF(rectF.left, rectF.top);
        PointF pointF2 = new PointF(rectF.right, rectF.top);
        PointF pointF3 = new PointF(rectF.left, rectF.bottom);
        PointF pointF4 = new PointF(rectF.right, rectF.bottom);
        StraightLine straightLine = new StraightLine(pointF, pointF3);
        StraightLine straightLine2 = new StraightLine(pointF, pointF2);
        StraightLine straightLine3 = new StraightLine(pointF2, pointF4);
        StraightLine straightLine4 = new StraightLine(pointF3, pointF4);
        this.outerLines.clear();
        this.outerLines.add(straightLine);
        this.outerLines.add(straightLine2);
        this.outerLines.add(straightLine3);
        this.outerLines.add(straightLine4);
        StraightArea straightArea = new StraightArea();
        this.outerArea = straightArea;
        straightArea.lineLeft = straightLine;
        this.outerArea.lineTop = straightLine2;
        this.outerArea.lineRight = straightLine3;
        this.outerArea.lineBottom = straightLine4;
        this.areas.clear();
        this.areas.add(this.outerArea);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public int getAreaCount() {
        return this.areas.size();
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public List<PolishLine> getOuterLines() {
        return this.outerLines;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public List<PolishLine> getLines() {
        return this.lines;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public void update() {
        for (PolishLine polishLine : this.lines) {
            polishLine.update(width(), height());
        }
    }

    public RectF getBounds() {
        return this.bounds;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public float width() {
        StraightArea straightArea = this.outerArea;
        if (straightArea == null) {
            return 0.0f;
        }
        return straightArea.width();
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public float height() {
        StraightArea straightArea = this.outerArea;
        if (straightArea == null) {
            return 0.0f;
        }
        return straightArea.height();
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public void reset() {
        this.lines.clear();
        this.areas.clear();
        this.areas.add(this.outerArea);
        this.steps.clear();
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    /* renamed from: getArea */
    public PolishArea mo335getArea(int i) {
        return (PolishArea) this.areas.get(i);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    /* renamed from: getOuterArea */
    public StraightArea mo336getOuterArea() {
        return this.outerArea;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public void setPadding(float f) {
        this.padding = f;
        for (StraightArea straightArea : this.areas) {
            straightArea.setPadding(f);
        }
        this.outerArea.lineLeft.startPoint().set(this.bounds.left + f, this.bounds.top + f);
        this.outerArea.lineLeft.endPoint().set(this.bounds.left + f, this.bounds.bottom - f);
        this.outerArea.lineRight.startPoint().set(this.bounds.right - f, this.bounds.top + f);
        this.outerArea.lineRight.endPoint().set(this.bounds.right - f, this.bounds.bottom - f);
        update();
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public float getPadding() {
        return this.padding;
    }

    public void addLine(int i, PolishLine.Direction direction, float f) {
        addLine(this.areas.get(i), direction, f);
        Step step = new Step();
        int i2 = 0;
        step.type = 0;
        if (direction != PolishLine.Direction.HORIZONTAL) {
            i2 = 1;
        }
        step.direction = i2;
        step.position = i;
        this.steps.add(step);
    }

    private List<StraightArea> addLine(StraightArea straightArea, PolishLine.Direction direction, float f) {
        this.areas.remove(straightArea);
        StraightLine createLine = StraightUtils.createLine(straightArea, direction, f);
        this.lines.add(createLine);
        List<StraightArea> cutArea = StraightUtils.cutArea(straightArea, createLine);
        this.areas.addAll(cutArea);
        updateLineLimit();
        sortAreas();
        return cutArea;
    }

    public void cutAreaEqualPart(int i, int i2, PolishLine.Direction direction) {
        int i3;
        StraightArea straightArea = this.areas.get(i);
        int i4 = i2;
        while (true) {
            i3 = 0;
            if (i4 <= 1) {
                break;
            }
            straightArea = addLine(straightArea, direction, (i4 - 1) / i4).get(0);
            i4--;
        }
        Step step = new Step();
        step.type = 3;
        step.part = i2;
        step.numOfLine = i2 - 1;
        step.position = i;
        if (direction != PolishLine.Direction.HORIZONTAL) {
            i3 = 1;
        }
        step.direction = i3;
        this.steps.add(step);
    }

    public void addCross(int i, float f) {
        addCross(i, f, f);
    }

    public void addCross(int i, float f, float f2) {
        StraightArea straightArea = this.areas.get(i);
        this.areas.remove(straightArea);
        StraightLine createLine = StraightUtils.createLine(straightArea, PolishLine.Direction.HORIZONTAL, f);
        StraightLine createLine2 = StraightUtils.createLine(straightArea, PolishLine.Direction.VERTICAL, f2);
        this.lines.add(createLine);
        this.lines.add(createLine2);
        this.areas.addAll(StraightUtils.cutAreaCross(straightArea, createLine, createLine2));
        updateLineLimit();
        sortAreas();
        Step step = new Step();
        step.hRatio = f;
        step.vRatio = f2;
        step.type = 1;
        step.position = i;
        step.numOfLine = 2;
        this.steps.add(step);
    }

    public void cutAreaEqualPart(int i, int i2, int i3) {
        StraightArea straightArea = this.areas.get(i);
        this.areas.remove(straightArea);
        Pair<List<StraightLine>, List<StraightArea>> cutArea = StraightUtils.cutArea(straightArea, i2, i3);
        List list = (List) cutArea.first;
        this.lines.addAll(list);
        this.areas.addAll((List) cutArea.second);
        updateLineLimit();
        sortAreas();
        Step step = new Step();
        step.type = 2;
        step.position = i;
        step.numOfLine = list.size();
        step.hSize = i2;
        step.vSize = i3;
        this.steps.add(step);
    }

    public void cutSpiral(int i) {
        StraightArea straightArea = this.areas.get(i);
        this.areas.remove(straightArea);
        Pair<List<StraightLine>, List<StraightArea>> cutAreaSpiral = StraightUtils.cutAreaSpiral(straightArea);
        this.lines.addAll((Collection) cutAreaSpiral.first);
        this.areas.addAll((Collection) cutAreaSpiral.second);
        updateLineLimit();
        sortAreas();
        Step step = new Step();
        step.numOfLine = this.lines.size();
        step.type = 4;
        step.position = i;
        this.steps.add(step);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public void sortAreas() {
        Collections.sort(this.areas, this.areaComparator);
    }

    private void updateLineLimit() {
        for (int i = 0; i < this.lines.size(); i++) {
            PolishLine polishLine = this.lines.get(i);
            updateUpperLine(polishLine);
            updateLowerLine(polishLine);
        }
    }

    private void updateLowerLine(PolishLine polishLine) {
        for (int i = 0; i < this.lines.size(); i++) {
            PolishLine polishLine2 = this.lines.get(i);
            if (polishLine2 != polishLine && polishLine2.direction() == polishLine.direction()) {
                if (polishLine2.direction() == PolishLine.Direction.HORIZONTAL) {
                    if (polishLine2.maxX() > polishLine.minX() && polishLine.maxX() > polishLine2.minX() && polishLine2.minY() > polishLine.lowerLine().maxY() && polishLine2.maxY() < polishLine.minY()) {
                        polishLine.setLowerLine(polishLine2);
                    }
                } else if (polishLine2.maxY() > polishLine.minY() && polishLine.maxY() > polishLine2.minY() && polishLine2.minX() > polishLine.lowerLine().maxX() && polishLine2.maxX() < polishLine.minX()) {
                    polishLine.setLowerLine(polishLine2);
                }
            }
        }
    }

    private void updateUpperLine(PolishLine polishLine) {
        for (int i = 0; i < this.lines.size(); i++) {
            PolishLine polishLine2 = this.lines.get(i);
            if (polishLine2 != polishLine && polishLine2.direction() == polishLine.direction()) {
                if (polishLine2.direction() == PolishLine.Direction.HORIZONTAL) {
                    if (polishLine2.maxX() > polishLine.minX() && polishLine.maxX() > polishLine2.minX() && polishLine2.maxY() < polishLine.upperLine().minY() && polishLine2.minY() > polishLine.maxY()) {
                        polishLine.setUpperLine(polishLine2);
                    }
                } else if (polishLine2.maxY() > polishLine.minY() && polishLine.maxY() > polishLine2.minY() && polishLine2.maxX() < polishLine.upperLine().minX() && polishLine2.minX() > polishLine.maxX()) {
                    polishLine.setUpperLine(polishLine2);
                }
            }
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public float getRadian() {
        return this.radian;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public void setRadian(float f) {
        this.radian = f;
        for (StraightArea straightArea : this.areas) {
            straightArea.setRadian(f);
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public int getColor() {
        return this.color;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public void setColor(int i) {
        this.color = i;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public Info generateInfo() {
        Info info = new Info();
        info.type = 0;
        info.padding = this.padding;
        info.radian = this.radian;
        info.color = this.color;
        info.steps = this.steps;
        ArrayList<LineInfo> arrayList = new ArrayList<>();
        for (PolishLine polishLine : this.lines) {
            arrayList.add(new LineInfo(polishLine));
        }
        info.lineInfos = arrayList;
        info.lines = new ArrayList<>(this.lines);
        info.left = this.bounds.left;
        info.top = this.bounds.top;
        info.right = this.bounds.right;
        info.bottom = this.bounds.bottom;
        return info;
    }
}
