package com.example.photoeditor.Classs;


import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.ModelClass.PolishLine;

/* loaded from: classes3.dex */
public class OneStraightLayout extends NumberStraightLayout {
    @Override // com.example.photoareditor.Classs.NumberStraightLayout
    public int getThemeCount() {
        return 6;
    }

    public OneStraightLayout(StraightCollageLayout straightCollageLayout, boolean z) {
        super(straightCollageLayout, z);
    }

    public OneStraightLayout(int i) {
        super(i);
    }

    @Override // com.example.photoareditor.Classs.StraightCollageLayout, com.example.photoareditor.ModelClass.PolishLayout
    public void layout() {
        int i = this.theme;
        if (i == 0) {
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.5f);
        } else if (i == 1) {
            addLine(0, PolishLine.Direction.VERTICAL, 0.5f);
        } else if (i == 2) {
            addCross(0, 0.5f);
        } else if (i == 3) {
            cutAreaEqualPart(0, 2, 1);
        } else if (i == 4) {
            cutAreaEqualPart(0, 1, 2);
        } else if (i != 5) {
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.5f);
        } else {
            cutAreaEqualPart(0, 2, 2);
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public PolishLayout clone(PolishLayout polishLayout) {
        return new OneStraightLayout((StraightCollageLayout) polishLayout, true);
    }
}
