package com.example.photoeditor.Classs;


import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.ModelClass.PolishLine;

/* loaded from: classes3.dex */
public class FourStraightLayout extends NumberStraightLayout {
    private static final String TAG = "FourStraightLayout";

    @Override // com.example.photoareditor.Classs.NumberStraightLayout
    public int getThemeCount() {
        return 8;
    }

    public FourStraightLayout(StraightCollageLayout straightCollageLayout, boolean z) {
        super(straightCollageLayout, z);
    }

    public FourStraightLayout(int i) {
        super(i);
    }

    @Override // com.example.photoareditor.Classs.StraightCollageLayout, com.example.photoareditor.ModelClass.PolishLayout
    public void layout() {
        switch (this.theme) {
            case 0:
                addCross(0, 0.5f);
                return;
            case 1:
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(0, 3, PolishLine.Direction.VERTICAL);
                return;
            case 2:
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.6666667f);
                cutAreaEqualPart(1, 3, PolishLine.Direction.VERTICAL);
                return;
            case 3:
                addLine(0, PolishLine.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(0, 3, PolishLine.Direction.HORIZONTAL);
                return;
            case 4:
                addLine(0, PolishLine.Direction.VERTICAL, 0.6666667f);
                cutAreaEqualPart(1, 3, PolishLine.Direction.HORIZONTAL);
                return;
            case 5:
                addLine(0, PolishLine.Direction.VERTICAL, 0.5f);
                addLine(1, PolishLine.Direction.HORIZONTAL, 0.6666667f);
                addLine(1, PolishLine.Direction.HORIZONTAL, 0.33333334f);
                return;
            case 6:
                cutAreaEqualPart(0, 4, PolishLine.Direction.HORIZONTAL);
                return;
            case 7:
                cutAreaEqualPart(0, 4, PolishLine.Direction.VERTICAL);
                return;
            default:
                cutAreaEqualPart(0, 4, PolishLine.Direction.HORIZONTAL);
                return;
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public PolishLayout clone(PolishLayout polishLayout) {
        return new FourStraightLayout((StraightCollageLayout) polishLayout, true);
    }
}
