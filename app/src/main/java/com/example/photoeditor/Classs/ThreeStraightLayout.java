package com.example.photoeditor.Classs;

import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.ModelClass.PolishLine;

/* loaded from: classes3.dex */
public class ThreeStraightLayout extends NumberStraightLayout {
    @Override // com.example.photoareditor.Classs.NumberStraightLayout
    public int getThemeCount() {
        return 6;
    }

    public ThreeStraightLayout(StraightCollageLayout straightCollageLayout, boolean z) {
        super(straightCollageLayout, z);
    }

    public ThreeStraightLayout(int i) {
        super(i);
    }

    @Override // com.example.photoareditor.Classs.StraightCollageLayout, com.example.photoareditor.ModelClass.PolishLayout
    public void layout() {
        int i = this.theme;
        if (i == 0) {
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.5f);
            addLine(0, PolishLine.Direction.VERTICAL, 0.5f);
        } else if (i == 1) {
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.5f);
            addLine(1, PolishLine.Direction.VERTICAL, 0.5f);
        } else if (i == 2) {
            addLine(0, PolishLine.Direction.VERTICAL, 0.5f);
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.5f);
        } else if (i == 3) {
            addLine(0, PolishLine.Direction.VERTICAL, 0.5f);
            addLine(1, PolishLine.Direction.HORIZONTAL, 0.5f);
        } else if (i == 4) {
            cutAreaEqualPart(0, 3, PolishLine.Direction.HORIZONTAL);
        } else if (i != 5) {
            cutAreaEqualPart(0, 3, PolishLine.Direction.HORIZONTAL);
        } else {
            cutAreaEqualPart(0, 3, PolishLine.Direction.VERTICAL);
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public PolishLayout clone(PolishLayout polishLayout) {
        return new ThreeStraightLayout((StraightCollageLayout) polishLayout, true);
    }
}
