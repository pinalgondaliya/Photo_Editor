package com.example.photoeditor.Classs;


import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.ModelClass.PolishLine;

/* loaded from: classes3.dex */
public class NineStraightLayout extends NumberStraightLayout {
    @Override // com.example.photoareditor.Classs.NumberStraightLayout
    public int getThemeCount() {
        return 8;
    }

    public NineStraightLayout(StraightCollageLayout straightCollageLayout, boolean z) {
        super(straightCollageLayout, z);
    }

    public NineStraightLayout(int i) {
        super(i);
    }

    @Override // com.example.photoareditor.Classs.StraightCollageLayout, com.example.photoareditor.ModelClass.PolishLayout
    public void layout() {
        switch (this.theme) {
            case 0:
                cutAreaEqualPart(0, 2, 2);
                return;
            case 1:
                addLine(0, PolishLine.Direction.VERTICAL, 0.75f);
                addLine(0, PolishLine.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(2, 4, PolishLine.Direction.HORIZONTAL);
                cutAreaEqualPart(0, 4, PolishLine.Direction.HORIZONTAL);
                return;
            case 2:
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.75f);
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(2, 4, PolishLine.Direction.VERTICAL);
                cutAreaEqualPart(0, 4, PolishLine.Direction.VERTICAL);
                return;
            case 3:
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.75f);
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(2, 3, PolishLine.Direction.VERTICAL);
                addLine(1, PolishLine.Direction.VERTICAL, 0.75f);
                addLine(1, PolishLine.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(0, 3, PolishLine.Direction.VERTICAL);
                return;
            case 4:
                addLine(0, PolishLine.Direction.VERTICAL, 0.75f);
                addLine(0, PolishLine.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(2, 3, PolishLine.Direction.HORIZONTAL);
                addLine(1, PolishLine.Direction.HORIZONTAL, 0.75f);
                addLine(1, PolishLine.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(0, 3, PolishLine.Direction.HORIZONTAL);
                return;
            case 5:
                cutAreaEqualPart(0, 3, PolishLine.Direction.VERTICAL);
                addLine(2, PolishLine.Direction.HORIZONTAL, 0.75f);
                addLine(2, PolishLine.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(1, 3, PolishLine.Direction.HORIZONTAL);
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.75f);
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.33333334f);
                return;
            case 6:
                cutAreaEqualPart(0, 3, PolishLine.Direction.HORIZONTAL);
                addLine(2, PolishLine.Direction.VERTICAL, 0.75f);
                addLine(2, PolishLine.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(1, 3, PolishLine.Direction.VERTICAL);
                addLine(0, PolishLine.Direction.VERTICAL, 0.75f);
                addLine(0, PolishLine.Direction.VERTICAL, 0.33333334f);
                return;
            case 7:
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.5f);
                cutAreaEqualPart(1, 1, 3);
                return;
            default:
                return;
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public PolishLayout clone(PolishLayout polishLayout) {
        return new NineStraightLayout((StraightCollageLayout) polishLayout, true);
    }
}
