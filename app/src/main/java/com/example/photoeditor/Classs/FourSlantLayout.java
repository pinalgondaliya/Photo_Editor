package com.example.photoeditor.Classs;

import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.ModelClass.PolishLine;

/* loaded from: classes3.dex */
public class FourSlantLayout extends NumberSlantLayout {
    @Override // com.example.photoareditor.Classs.NumberSlantLayout
    public int getThemeCount() {
        return 6;
    }

    public FourSlantLayout(int i) {
        super(i);
    }

    public FourSlantLayout(PolishLayout polishLayout, boolean z) {
        super((SlantCollageLayout) polishLayout, z);
    }

    @Override // com.example.photoareditor.Classs.SlantCollageLayout, com.example.photoareditor.ModelClass.PolishLayout
    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.5f);
                addLine(0, PolishLine.Direction.VERTICAL, 0.3f, 0.3f);
                addLine(2, PolishLine.Direction.VERTICAL, 0.7f, 0.7f);
                return;
            case 1:
                addLine(0, PolishLine.Direction.VERTICAL, 0.5f);
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.3f, 0.3f);
                addLine(1, PolishLine.Direction.HORIZONTAL, 0.7f, 0.7f);
                return;
            case 2:
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.6666667f);
                addLine(0, PolishLine.Direction.VERTICAL, 0.6666667f, 0.6666667f);
                addLine(2, PolishLine.Direction.VERTICAL, 0.6666667f, 0.6666667f);
                return;
            case 3:
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.33333334f);
                addLine(0, PolishLine.Direction.VERTICAL, 0.33333334f, 0.33333334f);
                addLine(2, PolishLine.Direction.VERTICAL, 0.33333334f, 0.33333334f);
                return;
            case 4:
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.33333334f);
                addLine(1, PolishLine.Direction.HORIZONTAL, 0.5f);
                addLine(1, PolishLine.Direction.VERTICAL, 0.5f, 0.5f);
                return;
            case 5:
                addLine(0, PolishLine.Direction.VERTICAL, 0.33333334f);
                addLine(1, PolishLine.Direction.VERTICAL, 0.5f);
                addLine(1, PolishLine.Direction.HORIZONTAL, 0.5f, 0.5f);
                return;
            case 6:
                addLine(0, PolishLine.Direction.HORIZONTAL, 0.7f, 0.3f);
                addLine(0, PolishLine.Direction.VERTICAL, 0.3f, 0.5f);
                addLine(2, PolishLine.Direction.VERTICAL, 0.5f, 0.7f);
                return;
            default:
                return;
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public PolishLayout clone(PolishLayout polishLayout) {
        return new FourSlantLayout(polishLayout, true);
    }
}
