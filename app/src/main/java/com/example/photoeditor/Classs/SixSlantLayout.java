package com.example.photoeditor.Classs;


import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.ModelClass.PolishLine;

/* loaded from: classes3.dex */
public class SixSlantLayout extends NumberSlantLayout {
    @Override // com.example.photoareditor.Classs.NumberSlantLayout
    public int getThemeCount() {
        return 2;
    }

    public SixSlantLayout(SlantCollageLayout slantCollageLayout, boolean z) {
        super(slantCollageLayout, z);
    }

    public SixSlantLayout(int i) {
        super(i);
    }

    @Override // com.example.photoareditor.Classs.SlantCollageLayout, com.example.photoareditor.ModelClass.PolishLayout
    public void layout() {
        int i = this.theme;
        if (i == 0) {
            addLine(0, PolishLine.Direction.VERTICAL, 0.33333334f);
            addLine(1, PolishLine.Direction.VERTICAL, 0.5f);
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.7f, 0.7f);
            addLine(1, PolishLine.Direction.HORIZONTAL, 0.5f, 0.5f);
            addLine(2, PolishLine.Direction.HORIZONTAL, 0.3f, 0.3f);
        } else if (i == 1) {
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.33333334f);
            addLine(1, PolishLine.Direction.HORIZONTAL, 0.5f);
            addLine(0, PolishLine.Direction.VERTICAL, 0.3f, 0.3f);
            addLine(2, PolishLine.Direction.VERTICAL, 0.5f, 0.5f);
            addLine(4, PolishLine.Direction.VERTICAL, 0.7f, 0.7f);
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public PolishLayout clone(PolishLayout polishLayout) {
        return new SixSlantLayout((SlantCollageLayout) polishLayout, true);
    }
}
