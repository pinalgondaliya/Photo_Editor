package com.example.photoeditor.Classs;

import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.ModelClass.PolishLine;

/* loaded from: classes3.dex */
public class TwoStraightLayout extends NumberStraightLayout {
    private float mRadio;

    @Override // com.example.photoareditor.Classs.NumberStraightLayout
    public int getThemeCount() {
        return 6;
    }

    public TwoStraightLayout(StraightCollageLayout straightCollageLayout, boolean z) {
        super(straightCollageLayout, z);
        this.mRadio = 0.5f;
    }

    public TwoStraightLayout(int i) {
        super(i);
        this.mRadio = 0.5f;
    }

    @Override // com.example.photoareditor.Classs.StraightCollageLayout, com.example.photoareditor.ModelClass.PolishLayout
    public void layout() {
        int i = this.theme;
        if (i == 0) {
            addLine(0, PolishLine.Direction.HORIZONTAL, this.mRadio);
        } else if (i == 1) {
            addLine(0, PolishLine.Direction.VERTICAL, this.mRadio);
        } else if (i == 2) {
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.33333334f);
        } else if (i == 3) {
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.6666667f);
        } else if (i == 4) {
            addLine(0, PolishLine.Direction.VERTICAL, 0.33333334f);
        } else if (i != 5) {
            addLine(0, PolishLine.Direction.HORIZONTAL, this.mRadio);
        } else {
            addLine(0, PolishLine.Direction.VERTICAL, 0.6666667f);
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public PolishLayout clone(PolishLayout polishLayout) {
        return new TwoStraightLayout((StraightCollageLayout) polishLayout, true);
    }
}
