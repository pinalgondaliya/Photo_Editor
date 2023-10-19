package com.example.photoeditor.Classs;

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
public abstract class SlantCollageLayout implements PolishLayout {
    private Comparator<SlantArea> areaComparator;
    private List<SlantArea> areas;
    private RectF bounds;
    private int color;
    private List<PolishLine> lines;
    private SlantArea outerArea;
    private List<PolishLine> outerLines;
    private float padding;
    private float radian;
    private ArrayList<Step> steps;

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public abstract void layout();

    /* JADX INFO: Access modifiers changed from: protected */
    public SlantCollageLayout() {
        this.areaComparator = new SlantArea.AreaComparator();
        this.areas = new ArrayList();
        this.color = -1;
        this.lines = new ArrayList();
        this.outerLines = new ArrayList(4);
        this.steps = new ArrayList<>();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SlantCollageLayout(SlantCollageLayout slantCollageLayout, boolean z) {
        this.areaComparator = new SlantArea.AreaComparator();
        this.areas = new ArrayList();
        this.color = -1;
        this.lines = new ArrayList();
        this.outerLines = new ArrayList(4);
        this.steps = new ArrayList<>();
        this.bounds = slantCollageLayout.getBounds();
        this.outerArea = (SlantArea) slantCollageLayout.mo336getOuterArea();
        this.areas = slantCollageLayout.getAreas();
        this.lines = slantCollageLayout.getLines();
        this.outerLines = slantCollageLayout.getOuterLines();
        this.padding = slantCollageLayout.getPadding();
        this.radian = slantCollageLayout.getRadian();
        this.color = slantCollageLayout.getColor();
        this.areaComparator = slantCollageLayout.getAreaComparator();
        this.steps = slantCollageLayout.getSteps();
    }

    public RectF getBounds() {
        return this.bounds;
    }

    public List<SlantArea> getAreas() {
        return this.areas;
    }

    public Comparator<SlantArea> getAreaComparator() {
        return this.areaComparator;
    }

    public ArrayList<Step> getSteps() {
        return this.steps;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public void setOuterBounds(RectF rectF) {
        reset();
        this.bounds = rectF;
        CrossoverPointF crossoverPointF = new CrossoverPointF(rectF.left, rectF.top);
        CrossoverPointF crossoverPointF2 = new CrossoverPointF(rectF.right, rectF.top);
        CrossoverPointF crossoverPointF3 = new CrossoverPointF(rectF.left, rectF.bottom);
        CrossoverPointF crossoverPointF4 = new CrossoverPointF(rectF.right, rectF.bottom);
        SlantLine slantLine = new SlantLine(crossoverPointF, crossoverPointF3, PolishLine.Direction.VERTICAL);
        SlantLine slantLine2 = new SlantLine(crossoverPointF, crossoverPointF2, PolishLine.Direction.HORIZONTAL);
        SlantLine slantLine3 = new SlantLine(crossoverPointF2, crossoverPointF4, PolishLine.Direction.VERTICAL);
        SlantLine slantLine4 = new SlantLine(crossoverPointF3, crossoverPointF4, PolishLine.Direction.HORIZONTAL);
        this.outerLines.clear();
        this.outerLines.add(slantLine);
        this.outerLines.add(slantLine2);
        this.outerLines.add(slantLine3);
        this.outerLines.add(slantLine4);
        SlantArea slantArea = new SlantArea();
        this.outerArea = slantArea;
        slantArea.lineLeft = slantLine;
        this.outerArea.lineTop = slantLine2;
        this.outerArea.lineRight = slantLine3;
        this.outerArea.lineBottom = slantLine4;
        this.outerArea.updateCornerPoints();
        this.areas.clear();
        this.areas.add(this.outerArea);
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
            if (polishLine2.direction() == polishLine.direction() && polishLine2.attachStartLine() == polishLine.attachStartLine() && polishLine2.attachEndLine() == polishLine.attachEndLine()) {
                if (polishLine2.direction() == PolishLine.Direction.HORIZONTAL) {
                    if (polishLine2.minY() > polishLine.lowerLine().maxY() && polishLine2.maxY() < polishLine.minY()) {
                        polishLine.setLowerLine(polishLine2);
                    }
                } else if (polishLine2.minX() > polishLine.lowerLine().maxX() && polishLine2.maxX() < polishLine.minX()) {
                    polishLine.setLowerLine(polishLine2);
                }
            }
        }
    }

    private void updateUpperLine(PolishLine polishLine) {
        for (int i = 0; i < this.lines.size(); i++) {
            PolishLine polishLine2 = this.lines.get(i);
            if (polishLine2.direction() == polishLine.direction() && polishLine2.attachStartLine() == polishLine.attachStartLine() && polishLine2.attachEndLine() == polishLine.attachEndLine()) {
                if (polishLine2.direction() == PolishLine.Direction.HORIZONTAL) {
                    if (polishLine2.maxY() < polishLine.upperLine().minY() && polishLine2.minY() > polishLine.maxY()) {
                        polishLine.setUpperLine(polishLine2);
                    }
                } else if (polishLine2.maxX() < polishLine.upperLine().minX() && polishLine2.minX() > polishLine.maxX()) {
                    polishLine.setUpperLine(polishLine2);
                }
            }
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public int getAreaCount() {
        return this.areas.size();
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public void reset() {
        this.lines.clear();
        this.areas.clear();
        this.areas.add(this.outerArea);
        this.steps.clear();
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public void update() {
        for (int i = 0; i < this.lines.size(); i++) {
            this.lines.get(i).update(width(), height());
        }
        for (int i2 = 0; i2 < this.areas.size(); i2++) {
            this.areas.get(i2).updateCornerPoints();
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public void sortAreas() {
        Collections.sort(this.areas, this.areaComparator);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public float width() {
        SlantArea slantArea = this.outerArea;
        if (slantArea == null) {
            return 0.0f;
        }
        return slantArea.width();
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public float height() {
        SlantArea slantArea = this.outerArea;
        if (slantArea == null) {
            return 0.0f;
        }
        return slantArea.height();
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public List<PolishLine> getOuterLines() {
        return this.outerLines;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    /* renamed from: getOuterArea */
    public PolishArea mo336getOuterArea() {
        return this.outerArea;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    /* renamed from: getArea */
    public SlantArea mo335getArea(int i) {
        sortAreas();
        return this.areas.get(i);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public List<PolishLine> getLines() {
        return this.lines;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public void setPadding(float f) {
        this.padding = f;
        for (SlantArea slantArea : this.areas) {
            slantArea.setPadding(f);
        }
        this.outerArea.lineLeft.startPoint().set(this.bounds.left + f, this.bounds.top + f);
        this.outerArea.lineLeft.endPoint().set(this.bounds.left + f, this.bounds.bottom - f);
        this.outerArea.lineRight.startPoint().set(this.bounds.right - f, this.bounds.top + f);
        this.outerArea.lineRight.endPoint().set(this.bounds.right - f, this.bounds.bottom - f);
        this.outerArea.updateCornerPoints();
        update();
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public float getPadding() {
        return this.padding;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public float getRadian() {
        return this.radian;
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public void setRadian(float f) {
        this.radian = f;
        for (SlantArea slantArea : this.areas) {
            slantArea.setRadian(f);
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

    public List<SlantArea> addLine(int i, PolishLine.Direction direction, float f) {
        return addLine(i, direction, f, f);
    }

    public List<SlantArea> addLine(int i, PolishLine.Direction direction, float f, float f2) {
        SlantArea slantArea = this.areas.get(i);
        this.areas.remove(slantArea);
        SlantLine createLine = SlantUtils.createLine(slantArea, direction, f, f2);
        this.lines.add(createLine);
        List<SlantArea> cutAreaWith = SlantUtils.cutAreaWith(slantArea, createLine);
        this.areas.addAll(cutAreaWith);
        updateLineLimit();
        sortAreas();
        Step step = new Step();
        int i2 = 0;
        step.type = 0;
        if (direction != PolishLine.Direction.HORIZONTAL) {
            i2 = 1;
        }
        step.direction = i2;
        step.position = i;
        this.steps.add(step);
        return cutAreaWith;
    }

    public void addCross(int i, float f, float f2, float f3, float f4) {
        SlantArea slantArea = this.areas.get(i);
        this.areas.remove(slantArea);
        SlantLine createLine = SlantUtils.createLine(slantArea, PolishLine.Direction.HORIZONTAL, f, f2);
        SlantLine createLine2 = SlantUtils.createLine(slantArea, PolishLine.Direction.VERTICAL, f3, f4);
        this.lines.add(createLine);
        this.lines.add(createLine2);
        this.areas.addAll(SlantUtils.cutAreaCross(slantArea, createLine, createLine2));
        sortAreas();
        Step step = new Step();
        step.type = 1;
        step.position = i;
        this.steps.add(step);
    }

    public void cutArea(int i, int i2, int i3) {
        SlantArea slantArea = this.areas.get(i);
        this.areas.remove(slantArea);
        Pair<List<SlantLine>, List<SlantArea>> cutAreaWith = SlantUtils.cutAreaWith(slantArea, i2, i3);
        this.lines.addAll((Collection) cutAreaWith.first);
        this.areas.addAll((Collection) cutAreaWith.second);
        updateLineLimit();
        sortAreas();
        Step step = new Step();
        step.type = 2;
        step.position = i;
        step.hSize = i2;
        step.vSize = i3;
        this.steps.add(step);
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public Info generateInfo() {
        Info info = new Info();
        info.type = 1;
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
